package com.org.carder.DTOs;



import com.org.carder.constant.CommonStatus;
import com.org.carder.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String empNumber;
    private String name;
    private String nic;
    private String password;
    private String mobile;
    private CommonStatus commonStatus;
    private UserRole userRole;
    private DepartmentDTO departmentDTO;
}
