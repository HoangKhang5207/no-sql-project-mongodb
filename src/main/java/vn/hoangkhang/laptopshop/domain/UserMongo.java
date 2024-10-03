package vn.hoangkhang.laptopshop.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Document(collection = "users")
public class UserMongo {

    @Id
    private String id;

    @NotNull
    @Email(message = "Email không hợp lệ", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotNull
    @Size(min = 3, message = "Fullname phải có tối thiểu 3 ký tự")
    private String fullName;

    @NotNull
    @Size(min = 2, message = "Password phải có tối thiểu 2 ký tự")
    private String password;

    private String address;
    private String avatar;
    private String phone;
    private RoleMongo role;
    private CartMongo cart;
    private List<OrderMongo> orders;

    public UserMongo() {
    }

    public UserMongo(String id, String address, String avatar, String email, String fullName, String password,
            String phone, RoleMongo role, CartMongo cart, List<OrderMongo> orders) {
        this.id = id;
        this.address = address;
        this.avatar = avatar;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.cart = cart;
        this.orders = orders;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public RoleMongo getRole() {
        return role;
    }

    public void setRole(RoleMongo role) {
        this.role = role;
    }

    public CartMongo getCart() {
        return cart;
    }

    public void setCart(CartMongo cart) {
        this.cart = cart;
    }

    public List<OrderMongo> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderMongo> orders) {
        this.orders = orders;
    }
}
