package com.org.carder.controller.restController;

import com.org.carder.service.VacateService;
import com.org.carder.utill.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vacateController")
public class VacateController {

    private final VacateService vacateService;

    public VacateController(VacateService vacateService) {
        this.vacateService = vacateService;
    }

    @PostMapping("/")
    public CommonResponse generateVacateList(){
        return vacateService.generateVacateList();
    }

    @GetMapping("/")
    public CommonResponse getVacateList(){
        return vacateService.getVacateList();
    }

    @GetMapping("/getCount")
    public CommonResponse getVacateListCount(){
        return vacateService.getVacateList();
    }


}
