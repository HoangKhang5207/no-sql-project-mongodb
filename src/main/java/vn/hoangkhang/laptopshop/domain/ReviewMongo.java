package vn.hoangkhang.laptopshop.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Min;

@Document(collection = "reviews")
public class ReviewMongo {

    @Id
    private String id;
    private UserMongo user;

    @Min(value = 1, message = "trường này là bắt buộc")
    private int rating;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private String orderId;

    public ReviewMongo() {

    }

    public ReviewMongo(String id, UserMongo user, int rating, String content, Date createdAt,
            Date updatedAt, String orderId) {
        this.id = id;
        this.user = user;
        this.rating = rating;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.orderId = orderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserMongo getUser() {
        return user;
    }

    public void setUser(UserMongo user) {
        this.user = user;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
