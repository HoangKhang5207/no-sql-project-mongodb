package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.repository.RoleRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
}
