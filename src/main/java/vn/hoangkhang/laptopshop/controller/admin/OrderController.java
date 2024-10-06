package vn.hoangkhang.laptopshop.controller.admin;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.hoangkhang.laptopshop.domain.OrderDetailMongo;
import vn.hoangkhang.laptopshop.domain.OrderMongo;
import vn.hoangkhang.laptopshop.domain.dto.AggregatedOrders;
import vn.hoangkhang.laptopshop.service.UserMongoService;

@Controller
public class OrderController {
    private final UserMongoService userMongoService;

    public OrderController(UserMongoService userMongoService) {
        this.userMongoService = userMongoService;
    }

    @GetMapping("/admin/order")
    public String getDashboard(Model model, @RequestParam("page") Optional<String> pageOptional) {
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

        int size = 5;
        int skip = (page - 1) * size;

        AggregatedOrders pageOrder = this.userMongoService.fetchAllOrders();
        List<OrderMongo> orders = pageOrder.getOrdersWithPagination(skip, size);

        model.addAttribute("orders", orders);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageOrder.getTotalPages(size));
        return "admin/order/show";
    }

    @GetMapping("/admin/order/{id}")
    public String getOrderDetailPage(Model model, @PathVariable String id) {
        AggregatedOrders aggOrders = this.userMongoService.fetchAllOrders();
        OrderMongo order = aggOrders.findOrderById(id);
        List<OrderDetailMongo> orderDetails = order == null ? new ArrayList<OrderDetailMongo>()
                : order.getOrderDetails();
        model.addAttribute("order", order);
        model.addAttribute("id", id);
        model.addAttribute("totalPrices", order == null ? 0 : order.getTotalPrice());
        model.addAttribute("orderDetails", orderDetails);
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getUpdateOrderPage(Model model, @PathVariable String id) {
        AggregatedOrders aggOrders = this.userMongoService.fetchAllOrders();
        OrderMongo currentOrder = aggOrders.findOrderById(id);
        model.addAttribute("newOrder", currentOrder);
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    public String handleUpdateOrder(@ModelAttribute("newOrder") OrderMongo order) {
        this.userMongoService.updateOrder(order);
        return "redirect:/admin/order";
    }
}
