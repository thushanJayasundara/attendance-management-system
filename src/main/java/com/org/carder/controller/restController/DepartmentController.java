package com.org.carder.controller.restController;

import com.org.carder.DTOs.DepartmentDTO;
import com.org.carder.service.DepartmentService;
import com.org.carder.utill.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    
    @PostMapping("/save-update")
    public CommonResponse saveUpdate(@RequestBody DepartmentDTO dto){
        return departmentService.saveUpdate(dto);
    }

    @GetMapping("/find-by-title/{depTitle}")
    public CommonResponse findDepartmentByTitle(@PathVariable final String depTitle){
        return departmentService.findDepartmentByTitle(depTitle);
    }

    @DeleteMapping("/delete-by-title/{depTitle}")
    public CommonResponse deleteDepartmentByTitle(@PathVariable final String depTitle){
        return departmentService.deleteDepartmentByTitle(depTitle);
    }

    @GetMapping("/get-all")
    public CommonResponse findAll(){
        return departmentService.findAll();
    }

    @GetMapping("/getCount")
    public long getCount(){
        return departmentService.getDepartmentCount();
    }
}
