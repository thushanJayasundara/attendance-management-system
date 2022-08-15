package com.org.carder.DTOs.insertDTO;


import com.org.carder.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveInsertDTO {
    private String id;
    private String leaveEmpNumber;
    private String reason;
    private CommonStatus commonStatus;
    private String leaveDate;
}
