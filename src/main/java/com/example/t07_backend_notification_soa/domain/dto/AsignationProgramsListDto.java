package com.example.t07_backend_notification_soa.domain.dto;

import com.example.t07_backend_notification_soa.domain.model.AsignationPrograms;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsignationProgramsListDto {
    private ProgramsDto programsDto;
    private ActivitiesDto activitiesDto;
    private AsignationPrograms asignationPrograms;

}
