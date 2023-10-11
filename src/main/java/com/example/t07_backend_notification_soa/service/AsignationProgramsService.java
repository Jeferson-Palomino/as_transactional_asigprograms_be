package com.example.t07_backend_notification_soa.service;

import com.example.t07_backend_notification_soa.domain.dto.ActivitiesDto;
import com.example.t07_backend_notification_soa.domain.dto.ProgramsDto;
import com.example.t07_backend_notification_soa.domain.model.AsignationPrograms;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AsignationProgramsService {
    AsignationPrograms create(AsignationPrograms asignationPrograms);
    List<AsignationPrograms> listAsignation();
    public Mono<ProgramsDto> validarPrograma(Integer id);
    public Mono<ActivitiesDto> validarActividades(Integer id);
}
