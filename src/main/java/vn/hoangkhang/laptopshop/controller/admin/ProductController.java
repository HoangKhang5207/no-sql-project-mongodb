package vn.hoangkhang.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoangkhang.laptopshop.domain.Product;
import vn.hoangkhang.laptopshop.service.ProductService;
import vn.hoangkhang.laptopshop.service.UploadService;

@Controller
public class ProductController {

    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    // Get List of Products
    @GetMapping("/admin/product")
    public String getProductPage(Model model, @RequestParam("page") Optional<String> pageOptional) {
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

        // client: page = 1, limit = 10
        // database: offset + limit
        Pageable pageable = PageRequest.of(page - 1, 4);
        // pageNumber: số trang từ phía client gửi lên server (nhưng với phía backend bắt đầu từ 0).
        // pageSize: số lượng phần tử muốn lấy.

        Page<Product> pageProduct = productService.getAllProducts(pageable);
        List<Product> products = pageProduct.getContent(); // convert Page to List
        model.addAttribute("products", products);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageProduct.getTotalPages());
        return "admin/product/show";
    }

    // Rendering Create Product Page
    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    // Handling Create Product
    @PostMapping("/admin/product/create")
    public String createProductPage(Model model,
            @Valid @ModelAttribute("newProduct") Product product,
            BindingResult bindingResult,
            @RequestParam("productFile") MultipartFile file) {

        if (bindingResult.hasErrors()) {
            return "admin/product/create";
        }

        String productImage = uploadService.handleUploadFile(file, "product");
        product.setImage(productImage);

        // save to db
        productService.handleSaveProduct(product);
        return "redirect:/admin/product";
    }

    // Rendering Product Details by Id
    @GetMapping("/admin/product/{productId}")
    public String getProductDetailPage(Model model, @PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }

    // Rendering Update Product Page by Id
    @GetMapping("/admin/product/update/{productId}")
    public String getUpdateProductPage(Model model, @PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        model.addAttribute("updateProduct", product);
        return "admin/product/update";
    }

    // Handling Update Product
    @PostMapping("/admin/product/update")
    public String updateProductPage(@Valid @ModelAttribute("updateProduct") Product product,
            BindingResult bindingResult, @RequestParam("productFile") MultipartFile file) {

        // validate
        if (bindingResult.hasErrors()) {
            return "admin/product/update";
        }

        if (!file.isEmpty()) {
            String productImage = uploadService.handleUploadFile(file, "product");
            product.setImage(productImage);
        }

        productService.handleUpdateUser(product);

        return "redirect:/admin/product";
    }

    // Rendering Delete User Confirmation
    @GetMapping("/admin/product/delete/{productId}")
    public String getDeleteProductPage(Model model, @PathVariable Long productId) {
        model.addAttribute("productId", productId);
        model.addAttribute("newProduct", new Product());
        return "admin/product/delete";
    }

    // Handling Delete Product
    @PostMapping("/admin/product/delete")
    public String deleteProductPage(@ModelAttribute("newProduct") Product product) {
        productService.handleDeleteProduct(product.getId());
        return "redirect:/admin/product";
    }
}
