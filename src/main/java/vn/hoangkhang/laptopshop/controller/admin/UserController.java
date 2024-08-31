package vn.hoangkhang.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoangkhang.laptopshop.domain.User;
import vn.hoangkhang.laptopshop.service.UploadService;
import vn.hoangkhang.laptopshop.service.UserService;

@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    // DI: Dependency Injection
    public UserController(UserService userService, UploadService uploadService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    // Get List of Users
    @GetMapping("/admin/user")
    public String getUserPage(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                // convert from String to int
                page = Integer.parseInt(pageOptional.get());
            } else {
                // page = 1
            }
        } catch (Exception e) {
            // page = 1
        }
        Pageable pageable = PageRequest.of(page - 1, 4);

        Page<User> pageUser = this.userService.getAllUsers(pageable);
        List<User> users = pageUser.getContent();

        model.addAttribute("users", users);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageUser.getTotalPages());
        return "admin/user/show";
    }

    // Rendering Create User Page
    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    // Handling Create User
    @PostMapping("/admin/user/create")
    public String createUserPage(Model model,
            @Valid @ModelAttribute("newUser") User hkhang,
            BindingResult bindingResult,
            @RequestParam("khangFile") MultipartFile file) {
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>" + error.getField() + " - " + error.getDefaultMessage());
        }

        // validate
        if (bindingResult.hasErrors()) {
            return "admin/user/create";
        }

        String avatar = uploadService.handleUploadFile(file, "avatar");
        String hashPassword = passwordEncoder.encode(hkhang.getPassword());

        hkhang.setAvatar(avatar);
        hkhang.setPassword(hashPassword);
        hkhang.setRole(userService.getRoleByName(hkhang.getRole().getName()));

        // save to db
        userService.handleSaveUser(hkhang);
        return "redirect:/admin/user";
    }

    // Rendering User Details by Id
    @GetMapping("/admin/user/{userId}")
    public String getUserDetailPage(Model model, @PathVariable Long userId) {
        model.addAttribute("userId", userId);
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "admin/user/user_detail";
    }

    // Rendering Update User Page by Id
    @GetMapping("/admin/user/update/{userId}")
    public String getUpdateUserPage(Model model, @PathVariable Long userId) {
        User updateUser = userService.getUserById(userId);
        model.addAttribute("updateUser", updateUser);
        return "admin/user/update";
    }

    // Handling Update User
    @PostMapping("/admin/user/update")
    public String updateUserPage(Model model,
            @ModelAttribute("updateUser") User hkhang,
            // BindingResult bindingResult,
            @RequestParam("khangFile") MultipartFile file) {

        if (!file.isEmpty()) {
            String avatar = uploadService.handleUploadFile(file, "avatar");
            hkhang.setAvatar(avatar);
        }

        // validate
        // if (bindingResult.hasErrors()) {
        // return "admin/user/update";
        // }

        userService.handleUpdateUser(hkhang);
        return "redirect:/admin/user";
    }

    // Rendering Delete User Confirmation
    @GetMapping("/admin/user/delete/{userId}")
    public String getDeleteUserPage(Model model, @PathVariable Long userId) {
        model.addAttribute("userId", userId);
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }

    // Handling Delete User
    @PostMapping("/admin/user/delete")
    public String deleteUserPage(@ModelAttribute("newUser") User user) {
        userService.handleDeleteUser(user.getId());
        return "redirect:/admin/user";
    }
}
