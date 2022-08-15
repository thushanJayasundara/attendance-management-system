package com.org.carder.entity;

import com.org.carder.constant.AttendanceEnum;
import com.org.carder.constant.CommonStatus;
import com.org.carder.utill.LocalDateAttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "attendUser",referencedColumnName = "empNumber")
    private User employee;
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate date;
    @Enumerated
    private AttendanceEnum attendance;
    @Enumerated
    private CommonStatus commonStatus;
}
