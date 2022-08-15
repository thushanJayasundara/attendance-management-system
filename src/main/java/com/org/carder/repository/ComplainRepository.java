package com.org.carder.repository;

import com.org.carder.entity.Complain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplainRepository extends JpaRepository<Complain,Long> {

    @Query("select com from Complain com where com.complainUser.empNumber=?1")
    List<Complain> findComplainsByComplainUser(Long complainUser);


}
