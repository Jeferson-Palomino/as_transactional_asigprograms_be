package com.example.t07_backend_notification_soa.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.Date;


@Table(name = "asignationprograms")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AsignationPrograms {
    @Id
    private Integer id;
    private Integer id_programs;
    private Integer id_activities;
    private LocalDate date_asignation;
    private String direction;
    private  String name_programs;
    private String name_activities;
    private String active;

}
