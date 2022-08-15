package com.org.carder.entity;

import com.org.carder.constant.CommonStatus;
import com.org.carder.constant.ComplainStatus;
import com.org.carder.utill.LocalDateTimeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "complain")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Complain implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String ComplainTitle;
    @Column
    private String ComplainDescription;
    @Enumerated
    private CommonStatus commonStatus;
    @Enumerated
    private ComplainStatus complainStatus;
    @Column
    private String complainReply;
    @ManyToOne
    @JoinColumn(name = "complainedDepartment",referencedColumnName = "id")
    private Department department;
    @ManyToOne
    @JoinColumn(name = "complainUser",referencedColumnName = "empNumber")
    private User complainUser;
    @ManyToOne
    @JoinColumn(name = "repliedUser",referencedColumnName = "empNumber")
    private User repliedUser;
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdOn;
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime repliedOn;

}
