package com.purplecloud.userinfro.controller;

import com.purplecloud.userinfro.bean.UserInform;
import com.purplecloud.userinfro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-info")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/get-user-by-id")
    public UserInform getUserById(@RequestParam("id") int id) {
        UserInform userById = userService.getUserById(id);
        userById.setOnline(userService.isOnline(id));
        return userById;
    }
    @RequestMapping("/hartBeat")
    public void hartBeat(@RequestParam("id") int id) {
        userService.hartBeat(id);
    }
    @RequestMapping("/insertUserInform")
    public void insertUserInform(@RequestParam("id") int id, @RequestParam("email") String email){
        UserInform userInform = new UserInform();
        userInform.setId(id);
        userInform.setEmail(email);
        userInform.setAvatar("avatar.jpg");
        userService.insertUserInform(userInform);
    }
}
