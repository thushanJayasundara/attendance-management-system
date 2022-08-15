package com.org.carder.DTOs;

import com.org.carder.constant.AttendanceEnum;
import com.org.carder.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDTO {

    private String id;
    private UserDTO user;
    private String date;
    private AttendanceEnum attendance;
    private CommonStatus commonStatus;

}
