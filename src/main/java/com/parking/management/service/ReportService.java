package com.parking.management.service;

import com.opencsv.CSVWriter;
import com.parking.management.model.ParkingRecord;
import com.parking.management.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public String gerarRelatorioDia() throws IOException {
        LocalDate today = LocalDate.now();
        List<ParkingRecord> records = reportRepository.findByEntryTimeBetween(today.atStartOfDay(), today.plusDays(1).atStartOfDay());
        return saveAndReturnCSV(records, "relatorio_dia.csv");
    }

    public String gerarRelatorioMes() throws IOException {
        YearMonth thisMonth = YearMonth.now();
        List<ParkingRecord> records = reportRepository.findByEntryTimeBetween(thisMonth.atDay(1).atStartOfDay(), thisMonth.plusMonths(1).atDay(1).atStartOfDay());
        return saveAndReturnCSV(records, "relatorio_mes.csv");
    }

    public String gerarRelatorioAno() throws IOException {
        LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
        LocalDate startOfNextYear = startOfYear.plusYears(1);
        List<ParkingRecord> records = reportRepository.findByEntryTimeBetween(startOfYear.atStartOfDay(), startOfNextYear.atStartOfDay());
        return saveAndReturnCSV(records, "relatorio_ano.csv");
    }

    public String gerarRelatorioTodosAnos() throws IOException {
        List<ParkingRecord> records = reportRepository.findAll();
        return saveAndReturnCSV(records, "relatorio_todos_anos.csv");
    }

    private String saveAndReturnCSV(List<ParkingRecord> records, String fileName) throws IOException {
        String csvContent = generateCSVContent(records);

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(csvContent);
        }

        return csvContent;
    }

    private String generateCSVContent(List<ParkingRecord> records) {
        StringWriter writer = new StringWriter();
        BigDecimal totalAmountPaid = BigDecimal.valueOf(0.0);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try (CSVWriter csvWriter = new CSVWriter(writer)) {
            csvWriter.writeNext(new String[]{"Relatório de Estacionamento"});
            csvWriter.writeNext(new String[]{"Data e Hora de Geração:", LocalDateTime.now().format(dateFormatter)});
            csvWriter.writeNext(new String[]{"", ""});

            csvWriter.writeNext(new String[]{"ID", "Placa", "Hora de Entrada", "Hora de Saída", "Valor Pago", "Pago?"});
            csvWriter.writeNext(new String[]{"", "", "", "", "", ""});

            for (ParkingRecord record : records) {
                BigDecimal amountPaid = record.getAmountPaid() != null ? record.getAmountPaid() : BigDecimal.valueOf(0.0);
                totalAmountPaid = totalAmountPaid.add(amountPaid);

                csvWriter.writeNext(new String[]{
                        String.valueOf(record.getId()),
                        record.getLicensePlate(),
                        record.getEntryTime().format(dateFormatter),
                        record.getExitTime() != null ? record.getExitTime().format(dateFormatter) : "",
                        formatCurrency(amountPaid),
                        record.getPaid() ? "Sim" : "Não"
                });
            }

            csvWriter.writeNext(new String[]{"", "", "", "", "Total Pago:", formatCurrency(totalAmountPaid)});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return writer.toString();
    }

    private String formatCurrency(BigDecimal amount) {
        return String.format("R$ %.2f", amount);
    }

}