package com.mdev.controller;

//import com.mdev.office.entity.User;

import com.mdev.entity.User;
import com.mdev.entity.UserRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
//@Controller
@RequestMapping("/api")
public class UserController {

    private List<User> users = new ArrayList<>();

    @GetMapping
    public List<User> getAll(){
        return users;
    }

    @GetMapping("/user/{id}")
    public User byId(@PathVariable Long id){

        return users.get(0);
    }

    @PostMapping
    public User create(@RequestBody User user){

        users.add(user);
        return user;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        users.removeIf(user -> user.getId().equals(id));
    }

//    @GetMapping
//    public Principal retrievePrincipal(Principal principal){
//        return principal;
//    }
}
