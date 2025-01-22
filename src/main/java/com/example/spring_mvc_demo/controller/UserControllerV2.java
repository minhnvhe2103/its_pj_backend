package com.example.spring_mvc_demo.controller;

import com.example.spring_mvc_demo.model.User;
import com.example.spring_mvc_demo.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/usersv2")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserControllerV2 {
    UserService userService;

//    @RequestMapping( method = RequestMethod.GET)
//    @ResponseBody
//    public List<User> getAllUser1() {
//        return userService.getAllUserV2();
//    }
//
//    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
//    @ResponseBody
//    public User getUserById(@PathVariable Long userId) {
//        return userService.getUserById(userId);
//    }
//
//    @RequestMapping(method = RequestMethod.POST)
//    @ResponseBody
//    public User addUser(@RequestBody User user) {
//        return userService.addUser(user);
//    }


}

