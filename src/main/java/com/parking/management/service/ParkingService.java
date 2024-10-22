package com.parking.management.service;

import com.parking.management.model.ParkingRecord;
import com.parking.management.repository.ParkingRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ParkingService {

    private final ParkingRecordRepository parkingRecordRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingService.class);

    public ParkingService(ParkingRecordRepository parkingRecordRepository) {
        this.parkingRecordRepository = parkingRecordRepository;
    }

    public Optional<ParkingRecord> getParkingRecord(Long id){
        LOGGER.info("Fetching parking record with id {}", id);
        Optional<ParkingRecord> parkingRecord = parkingRecordRepository.findById(id);
        if (parkingRecord.isEmpty()) {
            LOGGER.error("No parking record found with id {}", id);
        }
        return parkingRecord;
    }

    public ParkingRecord createParkingRecord(ParkingRecord parkingRecord) {
        LOGGER.info("Creating parking record for license plate {}", parkingRecord.getLicensePlate());
        return parkingRecordRepository.save(parkingRecord);
    }

    public Optional<ParkingRecord> updateParkingRecord(Long id) {
        LOGGER.info("Updating parking record with id {}", id);
        return parkingRecordRepository.findById(id)
                .map(record -> {
                    record.setExitTime(LocalDateTime.now());
                    record.setAmountPaid(calculateAmountPaid(record.getEntryTime(), record.getExitTime()));
                    record.setPaid(true);
                    return parkingRecordRepository.save(record);
                });
    }

    public BigDecimal calculateAmountPaid(LocalDateTime entryTime, LocalDateTime exitTime) {
        long durationInHours = Duration.between(entryTime, exitTime).toHours();
        double ratePerHour = 5.0;
        if (durationInHours > 3) {
            ratePerHour = 4.0;
        } else if (durationInHours < 0.5) {
            return BigDecimal.valueOf(0.0);
        }
        return BigDecimal.valueOf(durationInHours * ratePerHour);
    }
}
