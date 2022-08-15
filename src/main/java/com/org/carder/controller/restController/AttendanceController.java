package com.org.carder.controller.restController;


import com.org.carder.DTOs.AttendanceDTO;
import com.org.carder.service.AttendanceService;
import com.org.carder.utill.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendanceController")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/")
    public CommonResponse markAttendance(AttendanceDTO attendanceDTO){
        return attendanceService.markAttendance(attendanceDTO);
    }
}
