package com.org.carder.entity;

import com.org.carder.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "vacateList")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacateList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "vacateUser",referencedColumnName = "empNumber")
    private User vacateUser;

    @Enumerated
    private CommonStatus commonStatus;
}
