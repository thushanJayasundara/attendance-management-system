package com.org.carder.entity;

import com.org.carder.constant.CommonStatus;
import com.org.carder.utill.LocalDateAttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "holiday")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String reason;
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate holiday;
    @Enumerated
    private CommonStatus commonStatus;
}
