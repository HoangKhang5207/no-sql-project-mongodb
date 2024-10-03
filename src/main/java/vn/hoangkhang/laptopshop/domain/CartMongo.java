package vn.hoangkhang.laptopshop.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "carts")
public class CartMongo {

    @Id
    private String id;
    private int sum;
    List<CartDetailMongo> cartDetails;

    public CartMongo() {
    }

    public CartMongo(String id, int sum, List<CartDetailMongo> cartDetails) {
        this.id = id;
        this.sum = sum;
        this.cartDetails = cartDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public List<CartDetailMongo> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<CartDetailMongo> cartDetails) {
        this.cartDetails = cartDetails;
    }

}
