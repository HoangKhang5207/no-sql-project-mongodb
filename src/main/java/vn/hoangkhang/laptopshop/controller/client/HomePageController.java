package vn.hoangkhang.laptopshop.controller.client;

import java.util.ArrayList;
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
import vn.hoangkhang.laptopshop.domain.OrderMongo;
import vn.hoangkhang.laptopshop.domain.ProductMongo;
import vn.hoangkhang.laptopshop.domain.RoleMongo;
import vn.hoangkhang.laptopshop.domain.UserMongo;
import vn.hoangkhang.laptopshop.domain.dto.RegisterDTO;
import vn.hoangkhang.laptopshop.service.ProductMongoService;
import vn.hoangkhang.laptopshop.service.UserMongoService;
import vn.hoangkhang.laptopshop.util.RoleUtil;

@Controller
public class HomePageController {

    private final ProductMongoService productMongoService;
    private final UserMongoService userMongoService;
    private final PasswordEncoder passwordEncoder;

    public HomePageController(UserMongoService userMongoService, ProductMongoService productMongoService,
            PasswordEncoder passwordEncoder) {
        this.userMongoService = userMongoService;
        this.productMongoService = productMongoService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        int size = 12;
        int skip = 0;
        List<ProductMongo> products = this.productMongoService.getAllTheBestSellingProducts(size, skip);
        model.addAttribute("products", products);
        model.addAttribute("productService", this.productMongoService);
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

        UserMongo user = this.userMongoService.registerDTOToUser(registerUser);

        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        user.setRole(new RoleMongo(RoleUtil.USER.getId(), "USER", RoleUtil.USER.getDescription()));
        user.setAvatar("default-avatar.jpg");

        user.setCart(null);
        user.setOrders(new ArrayList<OrderMongo>());

        // save to db
        this.userMongoService.handleSaveUser(user);

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
        UserMongo currentUser = new UserMongo();// null
        HttpSession session = request.getSession(false);
        String id = (String) session.getAttribute("id");
        currentUser.setId(id);

        model.addAttribute("productService", this.productMongoService);
        List<OrderMongo> orders = this.userMongoService.fetchOrderByUser(currentUser);
        model.addAttribute("orders", orders);

        return "client/cart/order-history";
    }
}
