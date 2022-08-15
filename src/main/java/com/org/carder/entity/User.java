package com.org.carder.entity;

import com.org.carder.constant.CommonStatus;
import com.org.carder.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long empNumber;
    @Column
    private String name;
    @Column
    private String nic;
    @Column
    private String password;
    @Column
    private String mobile;
    @Enumerated
    private UserRole userRole;
    @Enumerated
    private CommonStatus commonStatus;

    @ManyToOne
    @JoinColumn(name = "department_id",referencedColumnName = "id")
    private Department department;

}
