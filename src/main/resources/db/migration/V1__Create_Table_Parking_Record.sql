CREATE TABLE parking_lot.parking_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    license_plate VARCHAR(255) NOT NULL,
    entry_time DATETIME(6) NOT NULL,
    exit_time DATETIME(6) NULL,
    amount_paid DECIMAL(38, 2) NULL,
    paid BIT NULL
);