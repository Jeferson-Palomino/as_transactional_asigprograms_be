package com.example.t07_backend_notification_soa.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActivitiesDto {
    private Integer id;
    private String name;
    private  String type_pronacej;
    private String type_soa;
}
