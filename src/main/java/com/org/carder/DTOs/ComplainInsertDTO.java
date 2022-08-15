package com.org.carder.DTOs;

import com.org.carder.constant.CommonStatus;
import com.org.carder.constant.ComplainStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplainInsertDTO {
    private String id;
    private String complainTitle;
    private String complainDescription;
    private String complainReply;
    private ComplainStatus complainStatus;
    private CommonStatus commonStatus;
    private String departmentTitle;
    private String complainUserEmpNum;
    private String repliedUserEmpNum;
}
