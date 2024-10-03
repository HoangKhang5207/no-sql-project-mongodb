package vn.hoangkhang.laptopshop.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Document(collection = "products")
public class ProductMongo {

    @Id
    private String id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    @DecimalMin(value = "0", inclusive = false, message = "Price phải lớn hơn 0")
    private Double price;
    private String image;

    @NotNull
    @NotBlank(message = "detailDesc không được để trống")
    private String detailDesc;

    @NotBlank(message = "shortDesc không được để trống")
    private String shortDesc;

    @Min(value = 1, message = "Số lượng cần lớn hơn hoặc bằng 1")
    private long quantity;
    private long sold;
    private String factory;
    private String target;
    private List<ReviewMongo> reviews;

    public ProductMongo() {

    }

    public ProductMongo(String id, String name, Double price, String image, String detailDesc, String shortDesc,
            long quantity, long sold, String factory, String target, List<ReviewMongo> reviews) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.detailDesc = detailDesc;
        this.shortDesc = shortDesc;
        this.quantity = quantity;
        this.sold = sold;
        this.factory = factory;
        this.target = target;
        this.reviews = reviews;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Long getSold() {
        return sold;
    }

    public void setSold(long sold) {
        this.sold = sold;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<ReviewMongo> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewMongo> reviews) {
        this.reviews = reviews;
    }
}
