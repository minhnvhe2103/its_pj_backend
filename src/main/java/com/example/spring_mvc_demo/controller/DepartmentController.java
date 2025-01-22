package com.example.spring_mvc_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.spring_mvc_demo.dto.DepartmentDTO;
import com.example.spring_mvc_demo.service.DepartmentService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Controller
@RequestMapping("/departments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentController {
    DepartmentService departmentService;
    @GetMapping
    public String getAllDepartments(Model model) {
        model.addAttribute("departments", new DepartmentDTO());
        return "redirect:/users1";
    }


}
