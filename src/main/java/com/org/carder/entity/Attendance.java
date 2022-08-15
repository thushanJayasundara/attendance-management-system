package com.org.carder.entity;

import com.org.carder.constant.AttendanceEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private User empNumber;
    @Enumerated
    private AttendanceEnum attendance;
}
