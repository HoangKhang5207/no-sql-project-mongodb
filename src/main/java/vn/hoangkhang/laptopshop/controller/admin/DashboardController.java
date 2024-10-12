package vn.hoangkhang.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.hoangkhang.laptopshop.service.ProductMongoService;
import vn.hoangkhang.laptopshop.service.UserMongoService;

@Controller
public class DashboardController {

    private final UserMongoService userMongoService;
    private final ProductMongoService productMongoService;

    public DashboardController(UserMongoService userMongoService, ProductMongoService productMongoService) {
        this.userMongoService = userMongoService;
        this.productMongoService = productMongoService;
    }

    @GetMapping("/admin")
    public String dashboard(Model model) {
        model.addAttribute("countUsers", this.userMongoService.countUsers());
        model.addAttribute("countProducts", this.userMongoService.countProducts());
        model.addAttribute("countOrders", this.userMongoService.countOrders());
        model.addAttribute("countReviews", this.productMongoService.countReviews());
        return "admin/dashboard/show";
    }
}