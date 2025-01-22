package com.example.spring_mvc_demo.controller;

import com.example.spring_mvc_demo.dto.UserDTO;
import com.example.spring_mvc_demo.dto.UserUpdateRequest;
import com.example.spring_mvc_demo.dto.response.UserListResponse;
import com.example.spring_mvc_demo.dto.response.UserResponse;
import com.example.spring_mvc_demo.model.User;
import com.example.spring_mvc_demo.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users1")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserViewController {

    UserService userService;

//    @GetMapping
//    public String getAllUsers(Model model) {
//        List<User> users = userService.getAllUserHQL();
//        model.addAttribute("users", users);
//        return "userList";
//    }
//
//    @GetMapping
//    public ResponseEntity<Page<User>> getAllUsers(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "id") String sortBy,
//            @RequestParam(defaultValue = "ASC") String direction) {
//        Page<User> users = userService.getAllUserHQL(page, size, sortBy, direction);
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }

    @GetMapping
    public String getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction,
            Model model) {
        Page<UserListResponse> users = userService.getAllUserHQL(page, size, sortBy, direction);

        model.addAttribute("users", users.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("totalItems", users.getTotalElements());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        model.addAttribute("size", size);
        return "userList";
    }

//    @GetMapping("/filter")
//    public String listUsers(@RequestParam(value = "name", required = false) String name, Model model) {
//        List<User> users;
//        if (name != null && !name.isEmpty()) {
//            users = userService.filterUser(name);
//        } else {
//            return "redirect:/users1";
//        }
//        model.addAttribute("users", users);
//        return "userList";
//    }

    @GetMapping("/filter")
    public String listUsers(@RequestParam(value = "name", required = false) String name, Model model) {
        List<UserListResponse> users;
        if (name != null && !name.isEmpty()) {
            users = userService.filterUserByName(name);
        } else {
            return "redirect:/users1";
        }
        model.addAttribute("users", users);
        return "userList";
    }

//    @GetMapping("/users1/filter")
//    public String filterUsers(
//            @RequestParam String name,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "userId") String sortBy,
//            @RequestParam(defaultValue = "ASC") String direction,
//            Model model) {
//        Page<User> users = userService.getUsersByName(name, page, size, sortBy, direction);
//
//        model.addAttribute("users", users.getContent());
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", users.getTotalPages());
//        model.addAttribute("totalItems", users.getTotalElements());
//        model.addAttribute("sortBy", sortBy);
//        model.addAttribute("direction", direction);
//        model.addAttribute("size", size);
//        model.addAttribute("filterName", name);
//
//        return "userList";
//    }






    @GetMapping("/detail/{id}")
    public String showUserDetail(@PathVariable("id") Long userId, Model model) {
        UserResponse userDTO = userService.viewUserDetail(userId);
        model.addAttribute("userDTO", userDTO);
        return "userDetailView";  // Tên view của bạn (ví dụ userDetailView.html)
    }


    @GetMapping("/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "createUser";
    }


    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("userDTO") UserDTO userDTO,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "createUser";
        }
        userService.addUserJSON(userDTO);
        return "redirect:/users1";
    }

    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long userId, Model model) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return "redirect:/users1";
        }
        UserUpdateRequest userDTO = new UserUpdateRequest(
                user.getEmail(),
                user.getPhoneNumber(),
                user.getFirstName(),
                user.getLastName()
        );
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("idUser", userId);
        return "editUser";
    }
    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long userId,
                           @Valid @ModelAttribute("userDTO") UserUpdateRequest userUpdateRequest,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "editUser";
        }
        userService.updateUser(userId, userUpdateRequest);
        return "redirect:/users1";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return "redirect:/users1";
    }

}
