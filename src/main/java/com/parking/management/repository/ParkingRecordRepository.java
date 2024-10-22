package com.parking.management.repository;

import com.parking.management.model.ParkingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRecordRepository extends JpaRepository<ParkingRecord, Long> {

}
