package com.org.carder.controller.viewController;

import com.org.carder.DTOs.DepartmentDTO;
import com.org.carder.service.AttendanceService;
import com.org.carder.service.DepartmentService;
import com.org.carder.service.UserService;
import com.org.carder.service.VacateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class ViewController {

    private final DepartmentService departmentService;
    private final UserService userService;
    private final VacateService vacateService;
    private final AttendanceService attendanceService;


    @Autowired
    public ViewController(DepartmentService departmentService, UserService userService, VacateService vacateService, AttendanceService attendanceService) {
        this.departmentService = departmentService;
        this.userService = userService;
        this.vacateService = vacateService;
        this.attendanceService = attendanceService;
    }

    @GetMapping({"/", "/login"})
    public String login() {
        return "login";
    }

    @GetMapping("/reg")
    public String register(Model model) {
        List<DepartmentDTO> departmentDTOS = departmentService.getAll();
        model.addAttribute("departments", departmentDTOS);
        return "register";
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/dash")
    public String dash(Model model) {
        model.addAttribute("userCount", String.valueOf(userService.getUserCount()));
        model.addAttribute("vacateCount",String.valueOf(vacateService.getVacateEmp()));
        model.addAttribute("attendance", String.valueOf(attendanceService.getTodayAbsentCount()));
        return "dashbord";
    }

    @GetMapping("/uReg")
    public String userReg(Model model) {
        List<DepartmentDTO> departmentDTOS = departmentService.getAll();
        model.addAttribute("departments", departmentDTOS);
        return "userRegistration";
    }

    @GetMapping("/dReg")
    public String depReg() {
        return "department";
    }

    @GetMapping("/leave")
    public String comReplay() {
        return "leave";
    }

    @GetMapping("/attendanceRepo")
    public String attendanceRepo() {
        return "attenAndRepo";
    }

    @GetMapping("/holiday")
    public String complain() {
        return "holiday";
    }

}
