package com.parking.management.repository;

import com.parking.management.model.ParkingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<ParkingRecord, Long> {
    @Query("SELECT p FROM ParkingRecord p WHERE p.entryTime BETWEEN :start AND :end")
    List<ParkingRecord> findByEntryTimeBetween(LocalDateTime start, LocalDateTime end);
}