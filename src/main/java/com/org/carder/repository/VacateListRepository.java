package com.org.carder.repository;

import com.org.carder.entity.User;
import com.org.carder.entity.VacateList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacateListRepository extends JpaRepository<VacateList,Long> {

    Boolean existsByVacateUser(User user);

}
