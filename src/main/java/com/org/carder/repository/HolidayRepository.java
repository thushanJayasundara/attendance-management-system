package com.org.carder.repository;

import com.org.carder.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface HolidayRepository extends JpaRepository<Holiday,Long> {

    Boolean existsByHoliday(LocalDate holiday);

    Holiday findByHoliday(LocalDate holiday);
}
