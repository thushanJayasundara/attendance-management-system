package com.org.carder.DTOs;

import com.org.carder.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacateListDTO {
    private String id;
    private UserDTO employee;
    private CommonStatus commonStatus;
}
