package vn.hoangkhang.laptopshop.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "order_detail")
public class OrderDetailMongo {

    @Id
    private String id;
    private double price;
    private long quantity;
    private ProductMongo product;

    public OrderDetailMongo() {
    }

    public OrderDetailMongo(String id, double price, long quantity, ProductMongo product) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.product = product;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public ProductMongo getProduct() {
        return product;
    }

    public void setProduct(ProductMongo product) {
        this.product = product;
    }

}
