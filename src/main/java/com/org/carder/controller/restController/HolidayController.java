package com.org.carder.controller.restController;


import com.org.carder.DTOs.HolidayDTO;
import com.org.carder.service.HolidayService;
import com.org.carder.utill.CommonResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/holiday")
public class HolidayController {

    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @PostMapping("/")
    public CommonResponse saveUpdateHoliday(@RequestBody HolidayDTO holidayDTO){
        return holidayService.saveUpdate(holidayDTO);
    }

    @GetMapping("/find-by-date/{date}")
    public CommonResponse findByDate(@PathVariable final String date){ return holidayService.findByDate(date); }

    @DeleteMapping("/delete-by-date/{date}")
    public CommonResponse deleteByDate(@PathVariable final String date){ return holidayService.deleteByDate(date); }

    @GetMapping("/get-all")
    public CommonResponse findAll(){ return holidayService.findAll(); }

}
