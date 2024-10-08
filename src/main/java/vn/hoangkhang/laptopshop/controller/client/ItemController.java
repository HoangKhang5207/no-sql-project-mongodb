package vn.hoangkhang.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.hoangkhang.laptopshop.domain.dto.ProductCriteriaDTO;
import vn.hoangkhang.laptopshop.service.ProductMongoService;
import vn.hoangkhang.laptopshop.service.UserMongoService;
import vn.hoangkhang.laptopshop.domain.CartDetailMongo;
import vn.hoangkhang.laptopshop.domain.CartMongo;
// import vn.hoangkhang.laptopshop.domain.Product;
import vn.hoangkhang.laptopshop.domain.ProductMongo;
import vn.hoangkhang.laptopshop.domain.ReviewMongo;
// import vn.hoangkhang.laptopshop.domain.Product_;
import vn.hoangkhang.laptopshop.domain.UserMongo;

@Controller
public class ItemController {

    private final ProductMongoService productMongoService;
    private final UserMongoService userMongoService;

    public ItemController(ProductMongoService productMongoService,
            UserMongoService userMongoService) {
        this.productMongoService = productMongoService;
        this.userMongoService = userMongoService;
    }

    @GetMapping("/product/{productId}")
    public String getProductDetail(Model model, @PathVariable String productId,
            @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                // convert from String to int
                page = Integer.parseInt(pageOptional.get());
            } else {
                // page = 1
            }
        } catch (Exception e) {
            // page = 1
        }

        ProductMongo product = this.productMongoService.getProductById(productId);

        int size = 10;
        int skip = (page - 1) * size;
        int totalPages = (int) Math.ceil((double) product.getReviews().size() / size);

        long ratingOneStar = product.getReviews().stream().filter(rv -> rv.getRating() == 1).count();
        long ratingTwoStar = product.getReviews().stream().filter(rv -> rv.getRating() == 2).count();
        long ratingThreeStar = product.getReviews().stream().filter(rv -> rv.getRating() == 3).count();
        long ratingFourStar = product.getReviews().stream().filter(rv -> rv.getRating() == 4).count();
        long ratingFiveStar = product.getReviews().stream().filter(rv -> rv.getRating() == 5).count();

        product.getReviews().sort((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()));

        model.addAttribute("product", product);
        model.addAttribute("reviews", product.getReviews().stream().skip(skip).limit(size).toList());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute("cntOneStar", ratingOneStar);
        model.addAttribute("cntTwoStar", ratingTwoStar);
        model.addAttribute("cntThreeStar", ratingThreeStar);
        model.addAttribute("cntFourStar", ratingFourStar);
        model.addAttribute("cntFiveStar", ratingFiveStar);
        return "client/product/detail";
    }

    @PostMapping("/add-product-to-cart/{productId}")
    public String addProductToCart(@PathVariable String productId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        String email = (String) session.getAttribute("email");

        this.productMongoService.handleAddProductToCart(productId, email, session, 1L);
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        UserMongo currentUser = new UserMongo();
        HttpSession session = request.getSession(false);
        String id = (String) session.getAttribute("id");
        currentUser.setId(id);

        CartMongo cart = this.userMongoService.fetchCartByUser(currentUser);

        List<CartDetailMongo> cartDetails = cart == null ? new ArrayList<CartDetailMongo>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetailMongo cartDetail : cartDetails) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("cart", cart);

        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartDetail(@PathVariable String id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String cartDetailId = id;
        this.productMongoService.handleRemoveCartDetail(cartDetailId, session);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String getCheckOutPage(Model model, HttpServletRequest request) {
        UserMongo currentUser = new UserMongo();// null
        HttpSession session = request.getSession(false);
        String id = (String) session.getAttribute("id");
        currentUser.setId(id);

        CartMongo cart = this.userMongoService.fetchCartByUser(currentUser);

        List<CartDetailMongo> cartDetails = cart == null ? new ArrayList<CartDetailMongo>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetailMongo cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);

        return "client/cart/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String getCheckoutPage(@ModelAttribute("cart") CartMongo cart, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        List<CartDetailMongo> cartDetails = cart == null ? new ArrayList<CartDetailMongo>() : cart.getCartDetails();
        this.productMongoService.handleUpdateCartBeforeCheckout(cartDetails, session);
        return "redirect:/checkout";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(
            HttpServletRequest request,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone) {
        UserMongo currentUser = new UserMongo();// null
        HttpSession session = request.getSession(false);
        String id = (String) session.getAttribute("id");
        currentUser.setId(id);

        this.productMongoService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);

        return "redirect:/thanks";
    }

    @GetMapping("/thanks")
    public String getThanksPage() {
        return "client/cart/thanks";
    }

    @PostMapping("/add-product-from-view-detail")
    public String handleAddProductFromViewDetail(
            @RequestParam("id") String id,
            @RequestParam("quantity") Long quantity,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        String email = (String) session.getAttribute("email");
        this.productMongoService.handleAddProductToCart(id, email, session, quantity);
        return "redirect:/product/" + id;
    }

    @GetMapping("/review-product/{id}")
    public String getReviewPage(Model model, @PathVariable String id,
            @RequestParam("orderId") String orderId) {
        ProductMongo product = this.productMongoService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("orderId", orderId);
        model.addAttribute("newReview", new ReviewMongo());
        return "client/product/review";
    }

    @PostMapping("/add-review-product")
    public String handleAddReview(Model model,
            @Valid @ModelAttribute("newReview") ReviewMongo newReview,
            BindingResult bindingResult,
            @RequestParam("productId") String id,
            HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            ProductMongo product = this.productMongoService.getProductById(id);
            model.addAttribute("product", product);
            return "client/product/review";
        }

        HttpSession session = request.getSession(false);

        this.productMongoService.handleAddProductReview(newReview, id, session);

        return "redirect:/order-history";
    }

    // @GetMapping("/products")
    // public String getProductPage(Model model, ProductCriteriaDTO
    // productCriteriaDTO, HttpServletRequest request) {
    // int page = 1;
    // try {
    // if (productCriteriaDTO.getPage().isPresent()) {
    // // convert from String to int
    // page = Integer.parseInt(productCriteriaDTO.getPage().get());
    // } else {
    // // page = 1
    // }
    // } catch (Exception e) {
    // // page = 1
    // // TODO: handle exception
    // }

    // Pageable pageable = PageRequest.of(page - 1, 3);

    // // Sort By Price
    // if (productCriteriaDTO.getSort() != null &&
    // productCriteriaDTO.getSort().isPresent()) {
    // String sort = productCriteriaDTO.getSort().get();
    // if (sort.equals("gia-tang-dan")) {
    // pageable = PageRequest.of(page - 1, 3, Sort.by(Product_.PRICE).ascending());
    // } else if (sort.equals("gia-giam-dan")) {
    // pageable = PageRequest.of(page - 1, 3, Sort.by(Product_.PRICE).descending());
    // }
    // }

    // Page<Product> prs = this.productService.getAllProductsWithSpec(pageable,
    // productCriteriaDTO);

    // List<Product> products = prs.getContent().size() > 0 ? prs.getContent() : new
    // ArrayList<Product>();

    // String qs = request.getQueryString();
    // if (qs != null && !qs.isBlank()) {
    // // remove page
    // qs = qs.replace("page=" + page, "");
    // }

    // model.addAttribute("products", products);
    // model.addAttribute("currentPage", page);
    // model.addAttribute("totalPages", prs.getTotalPages());
    // model.addAttribute("queryString", qs);
    // return "client/product/show";
    // }
}
