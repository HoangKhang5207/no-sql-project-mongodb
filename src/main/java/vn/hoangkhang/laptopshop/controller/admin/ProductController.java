package vn.hoangkhang.laptopshop.controller.admin;

import java.util.ArrayList;
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
// import vn.hoangkhang.laptopshop.domain.Product;
import vn.hoangkhang.laptopshop.domain.ProductMongo;
import vn.hoangkhang.laptopshop.domain.ReviewMongo;
import vn.hoangkhang.laptopshop.service.ProductMongoService;
import vn.hoangkhang.laptopshop.service.UploadService;

@Controller
public class ProductController {

    private final UploadService uploadService;
    private final ProductMongoService productMongoService;

    public ProductController(UploadService uploadService,
            ProductMongoService productMongoService) {
        this.uploadService = uploadService;
        this.productMongoService = productMongoService;
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
        Pageable pageable = PageRequest.of(page - 1, 5);
        // pageNumber: số trang từ phía client gửi lên server (nhưng với phía backend
        // bắt đầu từ 0).
        // pageSize: số lượng phần tử muốn lấy.

        Page<ProductMongo> pageProduct = productMongoService.getAllProducts(pageable);
        List<ProductMongo> products = pageProduct.getContent(); // convert Page to List

        model.addAttribute("products", products);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageProduct.getTotalPages());
        return "admin/product/show";
    }

    // Rendering Create Product Page
    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new ProductMongo());
        return "admin/product/create";
    }

    // Handling Create Product
    @PostMapping("/admin/product/create")
    public String createProductPage(Model model,
            @Valid @ModelAttribute("newProduct") ProductMongo product,
            BindingResult bindingResult,
            @RequestParam("productFile") MultipartFile file) {

        if (bindingResult.hasErrors()) {
            return "admin/product/create";
        }

        String productImage = uploadService.handleUploadFile(file, "product");
        product.setImage(productImage);

        product.setReviews(new ArrayList<ReviewMongo>());

        // save to db
        productMongoService.handleSaveProduct(product);

        return "redirect:/admin/product";
    }

    // Rendering Product Details by Id
    @GetMapping("/admin/product/{productId}")
    public String getProductDetailPage(Model model, @PathVariable String productId) {
        ProductMongo product = productMongoService.getProductById(productId);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }

    // Rendering Update Product Page by Id
    @GetMapping("/admin/product/update/{productId}")
    public String getUpdateProductPage(Model model, @PathVariable String productId) {
        ProductMongo product = productMongoService.getProductById(productId);
        model.addAttribute("updateProduct", product);
        return "admin/product/update";
    }

    // Handling Update Product
    @PostMapping("/admin/product/update")
    public String updateProductPage(@Valid @ModelAttribute("updateProduct") ProductMongo product,
            BindingResult bindingResult, @RequestParam("productFile") MultipartFile file) {

        // validate
        if (bindingResult.hasErrors()) {
            return "admin/product/update";
        }

        if (!file.isEmpty()) {
            String productImage = uploadService.handleUploadFile(file, "product");
            product.setImage(productImage);
        }

        product.setReviews(new ArrayList<ReviewMongo>());

        productMongoService.handleUpdateProduct(product);

        return "redirect:/admin/product";
    }

    // Rendering Delete User Confirmation
    @GetMapping("/admin/product/delete/{productId}")
    public String getDeleteProductPage(Model model, @PathVariable String productId) {
        model.addAttribute("productId", productId);
        model.addAttribute("newProduct", new ProductMongo());
        return "admin/product/delete";
    }

    // Handling Delete Product
    @PostMapping("/admin/product/delete")
    public String deleteProductPage(@ModelAttribute("newProduct") ProductMongo product) {
        productMongoService.handleDeleteProduct(product.getId());
        return "redirect:/admin/product";
    }
}
