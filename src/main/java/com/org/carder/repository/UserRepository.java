package com.org.carder.repository;

import com.org.carder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmpNumber(Long eNumber);

    Boolean existsByEmpNumber(Long eNumber);

}
