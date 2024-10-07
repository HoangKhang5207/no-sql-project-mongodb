package vn.hoangkhang.laptopshop.domain;

import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReviewMongo() {

    }

    public ReviewMongo(String id, UserMongo user, int rating, String content, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.rating = rating;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
