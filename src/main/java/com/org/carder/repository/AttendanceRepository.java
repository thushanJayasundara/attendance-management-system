package com.org.carder.repository;

import com.org.carder.entity.Attendance;


import com.org.carder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {


     Attendance getAttendanceByDateAndAndEmployee(LocalDate date, User employee);

     List<Attendance> getAllByDate(LocalDate date);


     @Query("select a from Attendance a where a.date >= :fourDaysAgoDate")
     List<Attendance> findAllByDate(@Param("fourDaysAgoDate") LocalDate fourDaysAgoDate);
}
