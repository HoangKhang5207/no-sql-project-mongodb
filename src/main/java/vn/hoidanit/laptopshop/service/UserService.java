package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;
import vn.hoidanit.laptopshop.repository.RoleRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
            ProductRepository productRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    // Get All Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get User By Email
    public List<User> getAllUsersByEmail(String email) {
        return userRepository.findAllByEmail(email);
    }

    // Save User
    public User handleSaveUser(User user) {
        User khang = userRepository.save(user);
        System.out.println(khang);
        return khang;
    }

    // View Detail User
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    // Update User
    public User handleUpdateUser(User updateUser) {
        User user = getUserById(updateUser.getId());
        if (user != null) {
            user.setFullName(updateUser.getFullName());
            user.setAddress(updateUser.getAddress());
            user.setPhone(updateUser.getPhone());
            user.setAvatar(updateUser.getAvatar());
            return userRepository.save(user);
        }
        return null;
    }

    // Delete User By Id
    public void handleDeleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    // Get Role by Name
    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    // Mapping from RegisterDTO object to User object
    public User registerDTOToUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;
    }

    // Check Email Exists
    public boolean checkEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    // Get User by Email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Long countUsers() {
        return this.userRepository.count();
    }

    public Long countProducts() {
        return this.productRepository.count();
    }

    public Long countOrders() {
        return this.orderRepository.count();
    }
}
