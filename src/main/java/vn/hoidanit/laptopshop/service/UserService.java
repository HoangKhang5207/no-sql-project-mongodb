package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String handleHello() {
        return "Hello from Service";
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
        User user = userRepository.findById(updateUser.getId()).orElse(null);
        if (user != null) {
            user.setFullName(updateUser.getFullName());
            user.setAddress(updateUser.getAddress());
            user.setPhone(updateUser.getPhone());
            return userRepository.save(user);
        }
        return null;
    }

    // Delete User By Id
    public void handleDeleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
