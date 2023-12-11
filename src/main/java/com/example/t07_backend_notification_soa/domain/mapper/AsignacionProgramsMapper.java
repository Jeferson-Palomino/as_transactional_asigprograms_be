package com.example.t07_backend_notification_soa.domain.mapper;

import com.example.t07_backend_notification_soa.domain.dto.AsignationProgramsDto;
import com.example.t07_backend_notification_soa.domain.dto.AsignationProgramsResponseDto;
import com.example.t07_backend_notification_soa.domain.model.AsignationPrograms;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AsignacionProgramsMapper {
    public static AsignationPrograms toModel(AsignationProgramsResponseDto dto) {
        return new AsignationPrograms(
                dto.getId(),
                dto.getId_programs(),
                dto.getId_activities(),
                dto.getDate_asignation(),
                dto.getDirection(),
                dto.getActive()
        );
    }
    public static AsignationPrograms toModel(Integer id, AsignationProgramsDto dto) {
        return new AsignationPrograms(
                id,
                dto.getId_activities(),
                dto.getId_programs(),
                dto.getDate_asignation(),
                dto.getDirection(),
                dto.getActive()
        );
    }
    public static AsignationProgramsResponseDto toDtoAsignation(AsignationPrograms dto) {
        return new AsignationProgramsResponseDto(
                dto.getId(),
                dto.getId_activities(),
                dto.getId_programs(),
                dto.getDate_asignation(),
                dto.getDirection(),
                dto.getActive()
        );
    }
}
