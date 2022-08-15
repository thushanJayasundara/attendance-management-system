package com.org.carder.DTOs;

import com.org.carder.constant.AttendanceEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDTO {

    private String id;
    private String empNumber;
    private AttendanceEnum attendance;


}
