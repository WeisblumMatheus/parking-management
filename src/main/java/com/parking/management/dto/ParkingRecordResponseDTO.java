package com.parking.management.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ParkingRecordResponseDTO {

    public ParkingRecordResponseDTO() {
    }

    private Long id;
    private String licensePlate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss",shape = JsonFormat.Shape.STRING)
    private LocalDateTime entryTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }
}
