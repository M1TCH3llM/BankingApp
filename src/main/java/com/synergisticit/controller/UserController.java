package com.synergisticit.controller;

import java.security.Principal;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.security.Principal;

import com.synergisticit.domain.User;
import com.synergisticit.service.RoleService;
import com.synergisticit.service.UserService;
import com.synergisticit.validation.UserValidators;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserValidators userValidator;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/page")
    public String userPage(@RequestParam(value = "editId", required = false) Long editId,
                           Model model,
                           Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User formUser;

        if (editId != null) {
            formUser = userService.findUserById(editId.longValue()); // load from DB
            if (formUser == null) {
                formUser = new User(); // fallback
            }
        } else {
            formUser = new User();
        }

        if (auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            model.addAttribute("users", userService.findAll());
        } else {
            User loggedIn = userService.findByUsername(principal.getName());
            model.addAttribute("users", Collections.singletonList(loggedIn));
        }

        model.addAttribute("user", formUser);
        model.addAttribute("roles", roleService.findAll());
        return "userForm";
    }

    @PostMapping("/page")
    public String saveUser(@Valid @ModelAttribute("user") User user,
                           BindingResult result,
                           Model model,
                           RedirectAttributes ra) {

        if (result.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            model.addAttribute("roles", roleService.findAll());
            return "userForm";
        }
        
        boolean isUpdate = (user.getUserId() != null && user.getUserId() != 0);
        userService.saveUser(user); 

        ra.addFlashAttribute("msg", isUpdate ? "User updated" : "User created");
        return "redirect:/user/page";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes ra) {
        userService.deleteUserById(id);
        ra.addFlashAttribute("msg", "User deleted.");
        return "redirect:/user/page";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }
}
