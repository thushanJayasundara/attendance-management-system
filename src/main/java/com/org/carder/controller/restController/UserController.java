package com.org.carder.controller.restController;

import com.org.carder.DTOs.UserDTO;
import com.org.carder.service.UserService;
import com.org.carder.utill.CommonResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save-update")
    public CommonResponse saveUpdate(@RequestBody UserDTO userDTO) {
        return userService.saveUpdate(userDTO);
    }

    @GetMapping("/find-by-emp-num/{empNumber}")
    public CommonResponse findByEmpNum(@PathVariable final String empNumber){
        return userService.findByEmpNum(empNumber);
    }

    @DeleteMapping("/delete-by-emp-num/{empNumber}")
    public CommonResponse deleteUserByEmpNum(@PathVariable final String empNumber){
        return userService.deleteUserByEmpNum(empNumber);
    }

    @GetMapping("/get-all")
    public CommonResponse findAll(){
        return userService.findAll();
    }

    @GetMapping("/getCount")
    public long getCount(){
        return userService.getUserCount();
    }
   
}
