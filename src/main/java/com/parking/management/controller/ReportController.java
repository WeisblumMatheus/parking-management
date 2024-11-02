package com.parking.management.controller;

import com.parking.management.service.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/v1/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/gerar-relatorio-dia")
    public ResponseEntity<String> gerarRelatorioDia() throws IOException {
        String csv = reportService.gerarRelatorioDia();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio_dia.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);
    }

    @GetMapping("/gerar-relatorio-mes")
    public ResponseEntity<String> gerarRelatorioMes() throws IOException {
        String csv = reportService.gerarRelatorioMes();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio_mes.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);
    }

    @GetMapping("/gerar-relatorio-ano")
    public ResponseEntity<String> gerarRelatorioAno() throws IOException {
        String csv = reportService.gerarRelatorioAno();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio_ano.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);
    }

    @GetMapping("/gerar-relatorio-todos-anos")
    public ResponseEntity<String> gerarRelatorioTodosAnos() throws IOException {
        String csv = reportService.gerarRelatorioTodosAnos();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio_todos_anos.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);
    }
}
