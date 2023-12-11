package com.example.t07_backend_notification_soa.domain.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsignationProgramsReportDto {
    private Integer id;
    private String name_programs;
    private String name_activities;
    private LocalDate date_asignation;
    private String direction;
}
