package com.org.carder.DTOs;


import com.org.carder.constant.CommonStatus;
import com.org.carder.constant.ComplainStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplainDTO {
    private String id;
    private String complainTitle;
    private String complainDescription;
    private String complainReply;
    private ComplainStatus complainStatus;
    private CommonStatus commonStatus;
    private DepartmentDTO departmentDTO;
    private LocalDateTime createOn;
    private LocalDateTime repliedOn;
    private UserDTO complainUser;
    private UserDTO repliedUser;
}
