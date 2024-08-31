package vn.hoangkhang.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoangkhang.laptopshop.domain.Cart;
import vn.hoangkhang.laptopshop.domain.CartDetail;
import vn.hoangkhang.laptopshop.domain.Order;
import vn.hoangkhang.laptopshop.domain.OrderDetail;
import vn.hoangkhang.laptopshop.domain.Product;
import vn.hoangkhang.laptopshop.domain.User;
import vn.hoangkhang.laptopshop.domain.dto.ProductCriteriaDTO;
import vn.hoangkhang.laptopshop.repository.CartDetailRepository;
import vn.hoangkhang.laptopshop.repository.CartRepository;
import vn.hoangkhang.laptopshop.repository.OrderDetailRepository;
import vn.hoangkhang.laptopshop.repository.OrderRepository;
import vn.hoangkhang.laptopshop.repository.ProductRepository;
import vn.hoangkhang.laptopshop.service.specification.ProductSpecs;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository, UserService userService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.userService = userService;
    }

    // Get All Products
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> getAllProductsWithSpec(Pageable pageable, ProductCriteriaDTO productCriteriaDTO) {
        if (productCriteriaDTO.getFactory() == null &&
                productCriteriaDTO.getTarget() == null &&
                productCriteriaDTO.getPrice() == null) {
            return this.productRepository.findAll(pageable);
        }

        Specification<Product> combinedSpec = Specification.where(null);

        if (productCriteriaDTO.getTarget() != null && productCriteriaDTO.getTarget().isPresent()) {
            Specification<Product> currentSpec = ProductSpecs.targetIn(productCriteriaDTO.getTarget().get());
            combinedSpec = combinedSpec.and(currentSpec);
        }
        if (productCriteriaDTO.getFactory() != null && productCriteriaDTO.getFactory().isPresent()) {
            Specification<Product> currentSpec = ProductSpecs.factoryIn(productCriteriaDTO.getFactory().get());
            combinedSpec = combinedSpec.and(currentSpec);
        }
        if (productCriteriaDTO.getPrice() != null && productCriteriaDTO.getPrice().isPresent()) {
            Specification<Product> currentSpec = this.buildPriceSpecification(productCriteriaDTO, productCriteriaDTO.getPrice().get());
            combinedSpec = combinedSpec.and(currentSpec);
        }

        return productRepository.findAll(combinedSpec, pageable);
    }

    // Case 6:
    public Specification<Product> buildPriceSpecification(ProductCriteriaDTO productCriteriaDTO, List<String> price) {
        // Khởi tạo Specification
        Specification<Product> combinedSpec = Specification.where(null);
        for (String p : price) {
            double min = 0;
            double max = 0;

            // Set the appropriate min and max based on the price range string
            switch (p) {
                case "duoi-10-trieu":
                    min = 5000000;
                    max = 10000000;
                    break;
                case "10-15-trieu":
                    min = 10000000;
                    max = 15000000;
                    break;
                case "15-20-trieu":
                    min = 15000000;
                    max = 20000000;
                    break;
                case "tren-20-trieu":
                    min = 20000000;
                    max = 200000000;
                    break;
                // Add more cases as needed
            }

            if (min != 0 && max != 0) {
                Specification<Product> rangeSpec = ProductSpecs.priceMultiRanges(min, max);
                combinedSpec = combinedSpec.or(rangeSpec); // kết hợp nhiều điều kiện với or
            }
        }

        return combinedSpec;
    }

    // Case 1:
    // public Page<Product> getAllProductsWithSpec(Pageable pageable, Double
    // minPrice) {
    // return
    // productRepository.findAll(ProductSpecs.priceGreaterThanOrEqualTo(minPrice),
    // pageable);
    // }

    // Case 2:
    // public Page<Product> getAllProductsWithSpec(Pageable pageable, Double
    // maxPrice) {
    // return
    // productRepository.findAll(ProductSpecs.priceLessThanOrEqualTo(maxPrice),
    // pageable);
    // }

    // Case 3:
    // public Page<Product> getAllProductsWithSpec(Pageable pageable, String
    // factory) {
    // return productRepository.findAll(ProductSpecs.factoryEqualTo(factory),
    // pageable);
    // }

    // Case 4:
    // public Page<Product> getAllProductsWithSpec(Pageable pageable, List<String>
    // factories) {
    // return productRepository.findAll(ProductSpecs.factoryIn(factories),
    // pageable);
    // }

    // Case 5:
    // public Page<Product> getAllProductsWithSpec(Pageable pageable, String price)
    // {
    // if (price.equals("10-toi-15-trieu")) {
    // double min = 10000000;
    // double max = 15000000;
    // return this.productRepository.findAll(ProductSpecs.priceRange(min, max),
    // pageable);
    // } else if (price.equals("15-toi-30-trieu")) {
    // double min = 15000000;
    // double max = 30000000;
    // return this.productRepository.findAll(ProductSpecs.priceRange(min, max),
    // pageable);
    // } else {
    // return this.productRepository.findAll(pageable);
    // }
    // }

    // Save Product
    public Product handleSaveProduct(Product product) {
        return productRepository.save(product);
    }

    // View Detail Product
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    // Update Product
    public Product handleUpdateUser(Product updateProduct) {
        Product product = getProductById(updateProduct.getId());
        if (product != null) {
            product.setName(updateProduct.getName());
            product.setPrice(updateProduct.getPrice());
            product.setQuantity(updateProduct.getQuantity());
            product.setDetailDesc(updateProduct.getDetailDesc());
            product.setShortDesc(updateProduct.getShortDesc());
            product.setFactory(updateProduct.getFactory());
            product.setTarget(updateProduct.getTarget());
            product.setImage(updateProduct.getImage());
            return handleSaveProduct(product);
        }
        return null;
    }

    // Delete Product By Id
    public void handleDeleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    public void handleAddProductToCart(Long productId, String email, HttpSession session, Long quantity) {
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // check user đã có Cart chưa? nếu chưa -> tạo mới
            Cart cart = this.cartRepository.findByUser(user);

            if (cart == null) {
                // tạo mới cart
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setSum(0);

                cart = this.cartRepository.save(newCart);
            }

            // save cart detail
            // find Product by Id
            Optional<Product> productOptional = this.productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product realProduct = productOptional.get();

                // check sản phẩm đã từng được thêm vào giỏ hàng trước đây hay chưa?
                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);

                if (oldDetail == null) {
                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProduct(realProduct);
                    cartDetail.setPrice(realProduct.getPrice());
                    cartDetail.setQuantity(quantity);
                    this.cartDetailRepository.save(cartDetail);

                    // update cart - sum
                    int s = cart.getSum() + 1;
                    cart.setSum(s);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", s);
                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + quantity);
                    this.cartDetailRepository.save(oldDetail);
                }
            }
        }
    }

    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    public void handleRemoveCartDetail(Long cartDetailId, HttpSession session) {
        Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailId);
        if (cartDetailOptional.isPresent()) {
            CartDetail cartDetail = cartDetailOptional.get();

            Cart currentCart = cartDetail.getCart();

            // delete cart detail
            this.cartDetailRepository.deleteById(cartDetailId);

            // update cart
            if (currentCart.getSum() > 1) {
                // update current cart
                int s = currentCart.getSum() - 1;
                currentCart.setSum(s);
                session.setAttribute("sum", s);
                this.cartRepository.save(currentCart);
            } else {
                // delete cart (sum = 1)
                this.cartRepository.deleteById(currentCart.getId());
                session.setAttribute("sum", 0);
            }
        }
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCartDetail);
            }
        }
    }

    public void handlePlaceOrder(User user, HttpSession session, String receiverName,
            String receiverAddress, String receiverPhone) {
        // step 1: get cart by user
        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();

            if (cartDetails != null) {

                // create order
                Order order = new Order();
                order.setUser(user);
                order.setReceiverName(receiverName);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setStatus("PENDING");

                double sum = 0;
                for (CartDetail cd : cartDetails) {
                    sum += (cd.getPrice() * cd.getQuantity());
                }
                order.setTotalPrice(sum);
                order = this.orderRepository.save(order);

                // create orderDetail
                for (CartDetail cd : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setPrice(cd.getPrice());
                    orderDetail.setQuantity(cd.getQuantity());

                    this.orderDetailRepository.save(orderDetail);
                }

                // step 2: delete cart_detail and cart
                for (CartDetail cd : cartDetails) {
                    this.cartDetailRepository.deleteById(cd.getId());
                }

                this.cartRepository.deleteById(cart.getId());

                // step 3 : update session
                session.setAttribute("sum", 0);
            }
        }
    }
}
