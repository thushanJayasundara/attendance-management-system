package com.org.carder.DTOs;

import com.org.carder.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveDTO {
    private String id;
    private UserDTO leaveUser;
    private String reason;
    private CommonStatus commonStatus;
    private String leaveDate;
}
