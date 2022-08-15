package com.org.carder.controller.restController;

import com.org.carder.DTOs.insertDTO.LeaveInsertDTO;
import com.org.carder.service.LeaveService;
import com.org.carder.utill.CommonResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leave")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping("/")
    public CommonResponse saveLeave(@RequestBody LeaveInsertDTO leaveInsertDTO){return leaveService.saveUpdate(leaveInsertDTO);}

    @GetMapping("/")
    public CommonResponse getAllLeave(){
        return leaveService.getAllLeave();
    }

    @GetMapping("/today-leave")
    public CommonResponse getTodayLeave(){
        return leaveService.getTodayLeave();
    }


    @PostMapping("/advance-search/{leaveDate}/{empNumber}")
    public CommonResponse advanceSearch(@PathVariable final String leaveDate,
                                        @PathVariable final String empNumber){
        return leaveService.advanceSearch(leaveDate, empNumber);
    }

}
