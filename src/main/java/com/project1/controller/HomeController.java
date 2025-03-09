package com.project1.controller;

import com.project1.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "Hello World!!!";
    }

    //@RequestMapping(value = "/user", method = RequestMethod.GET)
    @GetMapping("/user")
    public User user() {
        User user = new User();
        user.setId("1");
        user.setName("Indrajit");
        user.setEmailId("indrajit6479@gmail.com");
        return user;
    }

    @GetMapping("/{id}")
    public String pathVariable(@PathVariable String id) {
        return String.format("Path variable is: %s", id);
    }

    @GetMapping("/{id}/{id2}")
    public String pathVariable2(@PathVariable String id, @PathVariable("id2") String name) {
        return String.format("Path variable id is %s and name is %s", id, name);
    }
}
