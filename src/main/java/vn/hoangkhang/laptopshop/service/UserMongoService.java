package vn.hoangkhang.laptopshop.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.hoangkhang.laptopshop.domain.CartDetailMongo;
import vn.hoangkhang.laptopshop.domain.CartMongo;
import vn.hoangkhang.laptopshop.domain.OrderMongo;
import vn.hoangkhang.laptopshop.domain.UserMongo;
import vn.hoangkhang.laptopshop.domain.dto.RegisterDTO;
import vn.hoangkhang.laptopshop.repository.ProductMongoRepository;
import vn.hoangkhang.laptopshop.repository.UserMongoRepository;

@Service
public class UserMongoService {
    private final UserMongoRepository userMongoRepository;
    private final ProductMongoRepository productMongoRepository;

    public UserMongoService(UserMongoRepository userMongoRepository, ProductMongoRepository productMongoRepository) {
        this.userMongoRepository = userMongoRepository;
        this.productMongoRepository = productMongoRepository;
    }

    // Get All Users
    public Page<UserMongo> getAllUsers(Pageable pageable) {
        return this.userMongoRepository.findAll(pageable);
    }

    // Save User
    public UserMongo handleSaveUser(UserMongo user) {
        user.setId(UUID.randomUUID().toString());
        return this.userMongoRepository.save(user);
    }

    // View Detail User
    public UserMongo getUserById(String userId) {
        return this.userMongoRepository.findById(userId).orElse(null);
    }

    // Update User
    public UserMongo handleUpdateUser(UserMongo updateUser) {
        UserMongo user = getUserById(updateUser.getId());

        if (user == null)
            return null;

        user.setFullName(updateUser.getFullName());
        user.setAddress(updateUser.getAddress());
        user.setPhone(updateUser.getPhone());
        user.setAvatar(updateUser.getAvatar());
        return this.userMongoRepository.save(user);
    }

    // Delete User By Id
    public void handleDeleteUser(String userId) {
        this.userMongoRepository.deleteById(userId);
    }

    // Mapping from RegisterDTO object to User object
    public UserMongo registerDTOToUser(RegisterDTO registerDTO) {
        UserMongo user = new UserMongo();
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;
    }

    // Check Email Exists
    public boolean checkEmailExists(String email) {
        return this.userMongoRepository.existsByEmail(email);
    }

    // Get User by Email
    public UserMongo getUserByEmail(String email) {
        return this.userMongoRepository.findByEmail(email);
    }

    public Long countUsers() {
        return this.userMongoRepository.count();
    }

    public Long countProducts() {
        return this.productMongoRepository.count();
    }

    public Long countOrders() {
        return 0L;
    }

    public CartDetailMongo findByCartAndProduct(String cartId, String productId) {
        Optional<UserMongo> user = this.userMongoRepository.findCartDetailByCartAndProduct(cartId,
                productId);

        return user.isPresent() ? user.get().getCart().getCartDetails().get(0) : null;
    }

    public CartDetailMongo findCartDetailById(String cartDetailId) {
        Optional<UserMongo> user = this.userMongoRepository.findCartDetailById(cartDetailId);

        return user.isPresent() ? user.get().getCart().getCartDetails().get(0) : null;
    }

    public CartMongo fetchCartByUser(UserMongo userRequest) {
        Optional<UserMongo> user = this.userMongoRepository.findById(userRequest.getId());

        return user.isPresent() ? user.get().getCart() : null;
    }

    public List<OrderMongo> fetchOrderByUser(UserMongo userRequest) {
        Optional<UserMongo> user = this.userMongoRepository.findById(userRequest.getId());

        return user.isPresent() ? user.get().getOrders() : null;
    }
}
