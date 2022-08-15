package com.org.carder.entity;


import com.org.carder.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "department")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String departmentTitle;
    @Column
    private String departmentDescription;
    @Column
    private String departmentContactNumber;
    @Enumerated
    private CommonStatus commonStatus;

}
