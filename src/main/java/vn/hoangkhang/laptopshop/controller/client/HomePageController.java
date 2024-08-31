package vn.hoangkhang.laptopshop.controller.client;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.hoangkhang.laptopshop.domain.Order;
import vn.hoangkhang.laptopshop.domain.Product;
import vn.hoangkhang.laptopshop.domain.User;
import vn.hoangkhang.laptopshop.domain.dto.RegisterDTO;
import vn.hoangkhang.laptopshop.service.OrderService;
import vn.hoangkhang.laptopshop.service.ProductService;
import vn.hoangkhang.laptopshop.service.UserService;

@Controller
public class HomePageController {

    private final ProductService productService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final OrderService orderService;

    public HomePageController(ProductService productService, UserService userService,
            PasswordEncoder passwordEncoder, OrderService orderService) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> pageProduct = this.productService.getAllProducts(pageable);
        List<Product> products = pageProduct.getContent();
        model.addAttribute("products", products);
        return "client/homepage/show";
    }

    // Rendering Register Page
    @GetMapping("/register")
    public String getRegisterPge(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    // Handling Register
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registerUser") RegisterDTO registerUser,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }

        User user = userService.registerDTOToUser(registerUser);

        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setRole(userService.getRoleByName("USER"));

        // save to db
        userService.handleSaveUser(user);

        return "redirect:/login";
    }

    // Rendering Login Page
    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "client/auth/login";
    }

    // Rendering Access Denied Page
    @GetMapping("/access-denied")
    public String getDenyPage(Model model) {
        return "client/auth/deny";
    }

    @GetMapping("/order-history")
    public String getOrderHistoryPage(Model model, HttpServletRequest request) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        List<Order> orders = this.orderService.fetchOrderByUser(currentUser);
        model.addAttribute("orders", orders);

        return "client/cart/order-history";
    }

}
