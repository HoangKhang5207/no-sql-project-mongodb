package vn.hoangkhang.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.hoangkhang.laptopshop.domain.dto.ProductCriteriaDTO;
import vn.hoangkhang.laptopshop.service.EmailService;
import vn.hoangkhang.laptopshop.service.ProductMongoService;
import vn.hoangkhang.laptopshop.service.UploadService;
import vn.hoangkhang.laptopshop.service.UserMongoService;
import vn.hoangkhang.laptopshop.domain.CartDetailMongo;
import vn.hoangkhang.laptopshop.domain.CartMongo;
import vn.hoangkhang.laptopshop.domain.OrderMongo;
import vn.hoangkhang.laptopshop.domain.ProductMongo;
import vn.hoangkhang.laptopshop.domain.ReviewMongo;
import vn.hoangkhang.laptopshop.domain.UserMongo;

@Controller
public class ItemController {

    private final ProductMongoService productMongoService;
    private final UserMongoService userMongoService;
    private final UploadService uploadService;
    private final EmailService emailService;

    public ItemController(ProductMongoService productMongoService,
            UserMongoService userMongoService, UploadService uploadService, EmailService emailService) {
        this.productMongoService = productMongoService;
        this.userMongoService = userMongoService;
        this.uploadService = uploadService;
        this.emailService = emailService;
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
        List<ProductMongo> productList = this.productMongoService.getAllProductsWithoutPagination();

        long cntApple = productList.stream().filter(p -> p.getFactory().equals("APPLE")).count();
        long cntAsus = productList.stream().filter(p -> p.getFactory().equals("ASUS")).count();
        long cntAcer = productList.stream().filter(p -> p.getFactory().equals("ACER")).count();
        long cntHP = productList.stream().filter(p -> p.getFactory().equals("HP")).count();
        long cntLenovo = productList.stream().filter(p -> p.getFactory().equals("LENOVO")).count();
        long cntDell = productList.stream().filter(p -> p.getFactory().equals("DELL")).count();

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
        model.addAttribute("cntProductsSold", this.productMongoService.countTheNumberOfProductsSold(productId));

        model.addAttribute("cntOneStar", ratingOneStar);
        model.addAttribute("cntTwoStar", ratingTwoStar);
        model.addAttribute("cntThreeStar", ratingThreeStar);
        model.addAttribute("cntFourStar", ratingFourStar);
        model.addAttribute("cntFiveStar", ratingFiveStar);

        model.addAttribute("cntApple", cntApple);
        model.addAttribute("cntAsus", cntAsus);
        model.addAttribute("cntAcer", cntAcer);
        model.addAttribute("cntHP", cntHP);
        model.addAttribute("cntLenovo", cntLenovo);
        model.addAttribute("cntDell", cntDell);

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

        UserMongo user = this.userMongoService.getUserById(id);

        CartMongo cart = this.userMongoService.fetchCartByUser(currentUser);

        List<CartDetailMongo> cartDetails = cart == null ? new ArrayList<CartDetailMongo>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetailMongo cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }

        model.addAttribute("user", user);
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
            @RequestParam("receiverPhone") String receiverPhone) throws MessagingException {
        UserMongo currentUser = new UserMongo();// null
        HttpSession session = request.getSession(false);
        String id = (String) session.getAttribute("id");
        currentUser.setId(id);

        String email = (String) session.getAttribute("email");

        OrderMongo order = this.productMongoService.handlePlaceOrder(currentUser, session, receiverName,
                receiverAddress, receiverPhone);

        this.emailService.sendOrderConfirmationEmail(email, order);

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
            @RequestParam("reviewFile") MultipartFile[] files,
            HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            ProductMongo product = this.productMongoService.getProductById(id);
            model.addAttribute("product", product);
            return "client/product/review";
        }

        if (files != null && !files[0].getOriginalFilename().isEmpty() && files.length > 0) {
            List<String> fileNames = this.uploadService.handleUploadMultipleFiles(files, "review");
            newReview.setImages(fileNames);
        } else {
            newReview.setImages(new ArrayList<String>());
        }

        HttpSession session = request.getSession(false);

        this.productMongoService.handleAddProductReview(newReview, id, session);

        return "redirect:/order-history";
    }

    @GetMapping("/review-detail/{id}")
    public String getReviewDetailPage(Model model, @PathVariable("id") String reviewId,
            @RequestParam("productId") String productId) {

        ProductMongo product = this.productMongoService.getProductById(productId);
        ReviewMongo review = this.productMongoService.getReviewById(reviewId);

        model.addAttribute("product", product);
        model.addAttribute("review", review);

        return "client/product/review-detail";
    }

    @GetMapping("/products")
    public String getProductPage(Model model, ProductCriteriaDTO productCriteriaDTO, HttpServletRequest request) {
        int page = 1;
        try {
            if (productCriteriaDTO.getPage().isPresent()) {
                // convert from String to int
                page = Integer.parseInt(productCriteriaDTO.getPage().get());
            } else {
                // page = 1
            }
        } catch (Exception e) {
            // page = 1
        }

        int size = 6;
        int skip = (page - 1) * size;
        int totalPages = 0;

        List<ProductMongo> products = this.productMongoService
                .getAllProductWithFilter(productCriteriaDTO).size() > 0 ? this.productMongoService
                        .getAllProductWithFilter(productCriteriaDTO) : new ArrayList<ProductMongo>();

        totalPages = (int) Math.ceil((double) products.size() / size);

        products = products.stream().skip(skip).limit(size).toList();

        String qs = request.getQueryString();
        if (qs != null && !qs.isBlank()) {
            // remove page
            qs = qs.replace("page=" + page, "");
        }

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("queryString", qs);
        return "client/product/show";
    }
}
