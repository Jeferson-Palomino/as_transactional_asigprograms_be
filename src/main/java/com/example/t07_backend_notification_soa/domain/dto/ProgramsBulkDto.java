package com.example.t07_backend_notification_soa.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class ProgramsBulkDto {
    private Integer id_programs;
    private List<ProgramsAsignationDto> id_activities;
    private LocalDate date_asignation;
    private String direction;
    @Getter
    @Setter
    public static  class ProgramsAsignationDto {
        private Integer id_activities;
    }
}
