package com.example.t07_backend_notification_soa.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AsignationProgramsDto {
    private Integer id;
    private Integer id_programs;
    private Integer id_activities;
    private Date date_asignation;
    private String direction;
}
