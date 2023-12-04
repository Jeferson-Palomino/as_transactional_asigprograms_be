package com.example.t07_backend_notification_soa.domain.dto;

import lombok.*;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AsignationProgramsResponseDto {
    private Integer id;
    private Integer id_programs;
    private Integer id_activities;
    private LocalDate date_asignation;
    private String direction;
    private  String name_programs;
    private String name_activities;
    private String active;
}
