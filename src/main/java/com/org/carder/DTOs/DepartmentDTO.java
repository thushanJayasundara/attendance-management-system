package com.org.carder.DTOs;


import com.org.carder.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
    private String id;
    private String departmentTitle;
    private String departmentDescription;
    private String departmentContactNumber;
    private CommonStatus commonStatus;
}
