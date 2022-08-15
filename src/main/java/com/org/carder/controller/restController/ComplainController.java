package com.org.carder.controller.restController;

import com.org.carder.DTOs.ComplainInsertDTO;
import com.org.carder.constant.ComplainStatus;
import com.org.carder.service.ComplainService;
import com.org.carder.utill.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;

@RestController
@RequestMapping("/complain")
public class ComplainController {

    private final ComplainService complainService;

    @Autowired
    public ComplainController(ComplainService complainService) {
        this.complainService = complainService;
    }

    @PostMapping("/fetch-complain")
    public CommonResponse fetchComplain(@RequestBody ComplainInsertDTO dto){
        return complainService.fetchComplain(dto);
    }

    @GetMapping("/getComplain-by-userEmpNum/{userEmpNum}")
    public CommonResponse getUserByEmpNum(@PathVariable String userEmpNum){
        return complainService.findComplainByUserEmpNum(userEmpNum);
    }

    @PostMapping("/replay-complain")
    public CommonResponse replayComplain(@RequestBody ComplainInsertDTO dto){ return complainService.replayComplain(dto); }

    @GetMapping("/getCount")
    public long getCount(){
        return complainService.getComplainCount();
    }

    @GetMapping("/complainFilter/{depTitle}")
    public CommonResponse getComplainOnFilter(@PathVariable String depTitle, @PathVariable ComplainStatus status){
        return new CommonResponse(true,null, new ArrayList<>(Collections.singletonList(complainService.getComplainByDepartment(depTitle))));
    }

    @GetMapping("/getComplainById/{complainId}")
    public CommonResponse findComplainById(@PathVariable String complainId){
        return complainService.findComplainById(complainId);
    }
}
