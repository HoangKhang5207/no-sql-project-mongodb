package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Get All Products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

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
}
