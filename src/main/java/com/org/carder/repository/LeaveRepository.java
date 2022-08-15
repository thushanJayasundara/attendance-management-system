package com.org.carder.repository;

import com.org.carder.entity.Leave;
import com.org.carder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave,Long> {

    @Query("select l from Leave l where l.leaveUser.empNumber =?1 and l.leaveDate = ?2")
    Leave getLeaveByLeaveUserAndFromDate(Long empNumber,LocalDate leaveDate);

    List<Leave> getLeavesByLeaveDate(LocalDate localDate);


}
