package com.parking.management.controller;

import com.parking.management.dto.ParkingRecordResponseDTO;
import com.parking.management.model.ParkingRecord;
import com.parking.management.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/parking")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingRecord> getParkingRecord(@PathVariable Long id) {
        Optional<ParkingRecord> parkingRecord = parkingService.getParkingRecord(id);
        return parkingRecord.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ParkingRecordResponseDTO> createParkingRecord(@RequestBody ParkingRecord parkingRecord) {
        ParkingRecord createdParkingRecord = parkingService.createParkingRecord(parkingRecord);
        ParkingRecordResponseDTO responseDTO = convertToDTO(createdParkingRecord);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingRecord> updateParkingRecord(@PathVariable Long id) {
        Optional<ParkingRecord> updatedParkingRecord = parkingService.updateParkingRecord(id);
        return updatedParkingRecord.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private ParkingRecordResponseDTO convertToDTO(ParkingRecord parkingRecord) {
        ParkingRecordResponseDTO dto = new ParkingRecordResponseDTO();
        dto.setId(parkingRecord.getId());
        dto.setLicensePlate(parkingRecord.getLicensePlate());
        dto.setEntryTime(parkingRecord.getEntryTime());
        return dto;
    }


}
