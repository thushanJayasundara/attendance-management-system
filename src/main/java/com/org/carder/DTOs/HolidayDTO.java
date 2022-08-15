package com.org.carder.DTOs;

import com.org.carder.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayDTO {

    private String id;
    private String reason;
    private String holiday;
    private CommonStatus commonStatus;
}
