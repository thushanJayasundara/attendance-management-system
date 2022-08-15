package com.org.carder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "leave")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String reason;

    @Column
    private LocalDate fromDate;

    @Column
    private LocalDate toDate;

    @ManyToOne
    @JoinColumn(name = "leaveUser",referencedColumnName = "empNumber")
    private User empNumber;
}
