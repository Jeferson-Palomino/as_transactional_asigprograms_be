package com.example.t07_backend_notification_soa.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;

@Entity
@Table(name = "asignationprograms")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AsignationPrograms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer id_programs;
    private Integer id_activities;
    private Date date_asignation;
    private String direction;
    private  String name_programs;
    private String name_activities;
    private String active;

}
