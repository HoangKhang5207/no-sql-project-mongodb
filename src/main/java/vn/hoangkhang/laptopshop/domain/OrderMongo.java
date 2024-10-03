package vn.hoangkhang.laptopshop.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class OrderMongo {

    @Id
    private String id;
    private double totalPrice;
    private String receiverAddress;
    private String receiverName;
    private String receiverPhone;
    private String status;
    private List<OrderDetailMongo> orderDetails;

    public OrderMongo() {
    }

    public OrderMongo(String id, double totalPrice, String receiverAddress, String receiverName, String receiverPhone,
            String status, List<OrderDetailMongo> orderDetails) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.receiverAddress = receiverAddress;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.status = status;
        this.orderDetails = orderDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderDetailMongo> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailMongo> orderDetails) {
        this.orderDetails = orderDetails;
    }

}
