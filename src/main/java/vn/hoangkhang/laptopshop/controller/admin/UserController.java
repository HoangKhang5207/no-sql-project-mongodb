package vn.hoangkhang.laptopshop.controller.admin;

import java.util.ArrayList;
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
import vn.hoangkhang.laptopshop.domain.OrderMongo;
import vn.hoangkhang.laptopshop.domain.UserMongo;
import vn.hoangkhang.laptopshop.service.UploadService;
import vn.hoangkhang.laptopshop.service.UserMongoService;
import vn.hoangkhang.laptopshop.util.RoleUtil;

@Controller
public class UserController {

    private final UserMongoService userMongoService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    // DI: Dependency Injection
    public UserController(UserMongoService userMongoService, UploadService uploadService,
            PasswordEncoder passwordEncoder) {
        this.userMongoService = userMongoService;
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
        Pageable pageable = PageRequest.of(page - 1, 5);

        Page<UserMongo> pageUser = this.userMongoService.getAllUsers(pageable);
        List<UserMongo> users = pageUser.getContent();

        model.addAttribute("users", users);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageUser.getTotalPages());
        return "admin/user/show";
    }

    // Rendering Create User Page
    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new UserMongo());
        return "admin/user/create";
    }

    // Handling Create User
    @PostMapping("/admin/user/create")
    public String createUserPage(Model model,
            @Valid @ModelAttribute("newUser") UserMongo hkhang,
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

        if (hkhang.getRole().getName().equals("USER")) {
            hkhang.getRole().setId(RoleUtil.USER.getId());
            hkhang.getRole().setDescription(RoleUtil.USER.getDescription());
        } else {
            hkhang.getRole().setId(RoleUtil.ADMIN.getId());
            hkhang.getRole().setDescription(RoleUtil.ADMIN.getDescription());
        }

        hkhang.setCart(null);
        hkhang.setOrders(new ArrayList<OrderMongo>());

        // save to db
        this.userMongoService.handleSaveUser(hkhang);

        return "redirect:/admin/user";
    }

    // Rendering User Details by Id
    @GetMapping("/admin/user/{userId}")
    public String getUserDetailPage(Model model, @PathVariable String userId) {
        model.addAttribute("userId", userId);
        UserMongo user = this.userMongoService.getUserById(userId);
        model.addAttribute("user", user);
        return "admin/user/user_detail";
    }

    // Rendering Update User Page by Id
    @GetMapping("/admin/user/update/{userId}")
    public String getUpdateUserPage(Model model, @PathVariable String userId) {
        UserMongo updateUser = this.userMongoService.getUserById(userId);
        model.addAttribute("updateUser", updateUser);
        return "admin/user/update";
    }

    // Handling Update User
    @PostMapping("/admin/user/update")
    public String updateUserPage(Model model,
            @ModelAttribute("updateUser") UserMongo hkhang,
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

        this.userMongoService.handleUpdateUser(hkhang);

        return "redirect:/admin/user";
    }

    // Rendering Delete User Confirmation
    @GetMapping("/admin/user/delete/{userId}")
    public String getDeleteUserPage(Model model, @PathVariable String userId) {
        model.addAttribute("userId", userId);
        model.addAttribute("newUser", new UserMongo());
        return "admin/user/delete";
    }

    // Handling Delete User
    @PostMapping("/admin/user/delete")
    public String deleteUserPage(@ModelAttribute("newUser") UserMongo user) {
        this.userMongoService.handleDeleteUser(user.getId());

        return "redirect:/admin/user";
    }
}
