package vn.hoangkhang.laptopshop.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoangkhang.laptopshop.domain.CartDetailMongo;
import vn.hoangkhang.laptopshop.domain.CartMongo;
import vn.hoangkhang.laptopshop.domain.OrderDetailMongo;
import vn.hoangkhang.laptopshop.domain.OrderMongo;
import vn.hoangkhang.laptopshop.domain.ProductMongo;
import vn.hoangkhang.laptopshop.domain.ReviewMongo;
import vn.hoangkhang.laptopshop.domain.UserMongo;
import vn.hoangkhang.laptopshop.repository.ProductMongoRepository;
import vn.hoangkhang.laptopshop.repository.UserMongoRepository;

@Service
public class ProductMongoService {

    private final ProductMongoRepository productMongoRepository;
    private final UserMongoRepository userMongoRepository;
    private final UserMongoService userMongoService;
    private final MongoTemplate mongoTemplate;

    public ProductMongoService(ProductMongoRepository productMongoRepository, UserMongoRepository userMongoRepository,
            UserMongoService userMongoService, MongoTemplate mongoTemplate) {
        this.productMongoRepository = productMongoRepository;
        this.userMongoRepository = userMongoRepository;
        this.userMongoService = userMongoService;
        this.mongoTemplate = mongoTemplate;
    }

    public Page<ProductMongo> getAllProducts(Pageable pageable) {
        return this.productMongoRepository.findAll(pageable);
    }

    public Page<ProductMongo> getAllProductsRandom(Pageable pageable) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.sample(pageable.getPageSize()),
                Aggregation.skip((long) pageable.getOffset()),
                Aggregation.limit(pageable.getPageSize()));

        AggregationResults<ProductMongo> results = mongoTemplate.aggregate(aggregation, "products",
                ProductMongo.class);
        List<ProductMongo> productList = results.getMappedResults();

        long totalProducts = mongoTemplate.count(new Query(), ProductMongo.class);

        return new PageImpl<>(productList, pageable, totalProducts);
    }

    public ProductMongo handleSaveProduct(ProductMongo productMongo) {
        productMongo.setId(UUID.randomUUID().toString());
        return this.productMongoRepository.save(productMongo);
    }

    public ProductMongo getProductById(String productId) {
        return this.productMongoRepository.findById(productId).orElse(null);
    }

    public ProductMongo handleUpdateProduct(ProductMongo productMongo) {
        ProductMongo product = getProductById(productMongo.getId());

        if (product == null)
            return null;

        product.setName(productMongo.getName());
        product.setPrice(productMongo.getPrice());
        product.setQuantity(productMongo.getQuantity());
        product.setDetailDesc(productMongo.getDetailDesc());
        product.setShortDesc(productMongo.getShortDesc());
        product.setFactory(productMongo.getFactory());
        product.setTarget(productMongo.getTarget());
        product.setImage(productMongo.getImage());
        product.setReviews(productMongo.getReviews());
        return this.productMongoRepository.save(product);
    }

    public void handleDeleteProduct(String productId) {
        this.productMongoRepository.deleteById(productId);
    }

    public void handleAddProductToCart(String productId, String email, HttpSession session, Long quantity) {
        UserMongo user = this.userMongoService.getUserByEmail(email);
        if (user != null) {
            // check user đã có Cart chưa? nếu chưa -> tạo mới
            CartMongo cart = this.userMongoService.fetchCartByUser(user);

            if (cart == null) {
                // tạo mới cart
                CartMongo newCart = new CartMongo();
                newCart.setId(UUID.randomUUID().toString());
                newCart.setSum(0);

                cart = newCart;
            }

            // save cart detail
            // find Product by Id
            Optional<ProductMongo> productOptional = this.productMongoRepository.findById(productId);
            if (productOptional.isPresent()) {
                ProductMongo realProduct = productOptional.get();

                // check sản phẩm đã từng được thêm vào giỏ hàng trước đây hay chưa?
                CartDetailMongo oldDetail = this.userMongoService.findByCartAndProduct(cart.getId(),
                        realProduct.getId());

                if (cart.getCartDetails() == null)
                    cart.setCartDetails(new ArrayList<CartDetailMongo>());

                if (oldDetail == null) {
                    CartDetailMongo cartDetail = new CartDetailMongo();
                    cartDetail.setId(UUID.randomUUID().toString());

                    ProductMongo newProduct = new ProductMongo();
                    newProduct.setId(realProduct.getId());
                    newProduct.setName(realProduct.getName());
                    newProduct.setImage(realProduct.getImage());
                    newProduct.setPrice(realProduct.getPrice());
                    newProduct.setQuantity(realProduct.getQuantity());

                    cartDetail.setProduct(newProduct);
                    cartDetail.setPrice(realProduct.getPrice());
                    cartDetail.setQuantity(quantity);
                    cart.getCartDetails().add(cartDetail);

                    // update cart - sum
                    int s = cart.getSum() + 1;
                    cart.setSum(s);
                    session.setAttribute("sum", s);
                } else {
                    cart.getCartDetails().removeIf(cd -> cd.getId().equals(oldDetail.getId()));
                    oldDetail.setQuantity(oldDetail.getQuantity() + quantity);
                    cart.getCartDetails().add(oldDetail);
                }
                user.setCart(cart);
            }

            this.userMongoRepository.save(user);
        }
    }

    public void handleRemoveCartDetail(String cartDetailId, HttpSession session) {
        String email = (String) session.getAttribute("email");
        UserMongo user = this.userMongoService.getUserByEmail(email);
        CartDetailMongo cartDetail = this.userMongoService.findCartDetailById(cartDetailId);
        if (cartDetail != null) {

            CartMongo currentCart = user.getCart();

            // delete cart detail
            currentCart.getCartDetails().removeIf(cd -> cd.getId().equals(cartDetail.getId()));

            // update cart
            if (currentCart.getSum() > 1) {
                // update current cart
                int s = currentCart.getSum() - 1;
                currentCart.setSum(s);
                session.setAttribute("sum", s);
            } else {
                // delete cart (sum = 1)
                user.setCart(null);
                session.setAttribute("sum", 0);
            }
        }

        this.userMongoRepository.save(user);
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetailMongo> cartDetailsRequest, HttpSession session) {
        String email = (String) session.getAttribute("email");
        UserMongo user = this.userMongoService.getUserByEmail(email);

        List<CartDetailMongo> cartDetails = new ArrayList<CartDetailMongo>();
        for (CartDetailMongo cartDetail : cartDetailsRequest) {
            CartDetailMongo currentCartDetail = this.userMongoService.findCartDetailById(cartDetail.getId());
            if (currentCartDetail != null) {
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                cartDetails.add(currentCartDetail);
            }
        }

        user.getCart().setCartDetails(cartDetails);

        this.userMongoRepository.save(user);
    }

    public void handlePlaceOrder(UserMongo userRequest, HttpSession session, String receiverName,
            String receiverAddress, String receiverPhone) {
        String email = (String) session.getAttribute("email");
        UserMongo user = this.userMongoService.getUserByEmail(email);

        if (user.getOrders() == null)
            user.setOrders(new ArrayList<OrderMongo>());

        // step 1: get cart by user
        CartMongo cart = this.userMongoService.fetchCartByUser(userRequest);
        if (cart != null) {
            List<CartDetailMongo> cartDetails = cart.getCartDetails();

            if (cartDetails != null) {

                // create order
                OrderMongo order = new OrderMongo();
                order.setId(UUID.randomUUID().toString());
                order.setReceiverName(receiverName);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setStatus("PENDING");

                double sum = 0;
                for (CartDetailMongo cd : cartDetails) {
                    sum += (cd.getPrice() * cd.getQuantity());
                }
                order.setTotalPrice(sum);

                order.setOrderDetails(new ArrayList<OrderDetailMongo>());

                // create orderDetail
                for (CartDetailMongo cd : cartDetails) {
                    OrderDetailMongo orderDetail = new OrderDetailMongo();
                    orderDetail.setId(UUID.randomUUID().toString());
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setPrice(cd.getPrice());
                    orderDetail.setQuantity(cd.getQuantity());
                    order.getOrderDetails().add(orderDetail);
                }

                user.getOrders().add(order);

                // step 2: delete cart_detail and cart
                user.setCart(null);

                // step 3 : update session
                session.setAttribute("sum", 0);
            }

            this.userMongoRepository.save(user);
        }
    }

    public void handleAddProductReview(ReviewMongo reviewRequest, String productId, HttpSession session) {
        String email = (String) session.getAttribute("email");
        UserMongo userMongo = this.userMongoService.getUserByEmail(email);
        Optional<ProductMongo> productMongo = this.productMongoRepository.findById(productId);

        if (productMongo.isPresent() && userMongo != null) {
            ProductMongo product = productMongo.get();

            ReviewMongo review = new ReviewMongo();
            review.setId(UUID.randomUUID().toString());
            review.setRating(reviewRequest.getRating());
            review.setContent(reviewRequest.getContent());
            review.setCreatedAt(LocalDateTime.now());
            review.setUpdatedAt(LocalDateTime.now());

            UserMongo user = new UserMongo();
            user.setId(userMongo.getId());
            user.setFullName(userMongo.getFullName());
            user.setEmail(userMongo.getEmail());

            review.setUser(user);

            product.getReviews().add(review);

            this.productMongoRepository.save(product);
        }
    }
}
