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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        long temp;
        temp = Double.doubleToLongBits(totalPrice);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((receiverAddress == null) ? 0 : receiverAddress.hashCode());
        result = prime * result + ((receiverName == null) ? 0 : receiverName.hashCode());
        result = prime * result + ((receiverPhone == null) ? 0 : receiverPhone.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((orderDetails == null) ? 0 : orderDetails.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderMongo other = (OrderMongo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (Double.doubleToLongBits(totalPrice) != Double.doubleToLongBits(other.totalPrice))
            return false;
        if (receiverAddress == null) {
            if (other.receiverAddress != null)
                return false;
        } else if (!receiverAddress.equals(other.receiverAddress))
            return false;
        if (receiverName == null) {
            if (other.receiverName != null)
                return false;
        } else if (!receiverName.equals(other.receiverName))
            return false;
        if (receiverPhone == null) {
            if (other.receiverPhone != null)
                return false;
        } else if (!receiverPhone.equals(other.receiverPhone))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (orderDetails == null) {
            if (other.orderDetails != null)
                return false;
        } else if (!orderDetails.equals(other.orderDetails))
            return false;
        return true;
    }
}
