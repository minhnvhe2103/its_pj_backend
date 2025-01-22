package com.example.spring_mvc_demo.controller;

import com.example.spring_mvc_demo.dto.LoginDTO;
import com.example.spring_mvc_demo.dto.UserDTO;
import com.example.spring_mvc_demo.dto.response.LoginResponse;
import com.example.spring_mvc_demo.helper.exception.ApiResponse;
import com.example.spring_mvc_demo.model.User;
import com.example.spring_mvc_demo.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

//    @GetMapping
//    public List<User> getAllUser(){
//        return userService.getAllUserByHQL();
//    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @PostMapping()
    public User addUser(@RequestBody User user){
        return userService.addUser1(user);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser1(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errors);
        }
        userService.addUserJSON(userDTO);
        return ResponseEntity.ok("User added successfully!");
    }

    @PostMapping("/login")
    ApiResponse<LoginResponse> authenticate(@RequestBody LoginDTO request){
        var result  = userService.auth(request);

        return ApiResponse.<LoginResponse>builder()
                .data(result)
                .build();
    }




}
