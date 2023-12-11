package com.example.t07_backend_notification_soa.service;

import com.example.t07_backend_notification_soa.domain.dto.*;
import com.example.t07_backend_notification_soa.domain.model.AsignationPrograms;
import com.example.t07_backend_notification_soa.domain.report.AsignationProgramsReportDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AsignationProgramsService {
    Mono<Void> create(ProgramsBulkDto dto);
    Flux<AsignationProgramsReportDto> listAsignation();
    Mono<AsignationProgramsResponseDto> update(Integer id , AsignationProgramsDto request);
    Mono<Void> delete(Integer id);
    Mono<AsignationProgramsResponseDto> activar(Integer id);
    public Mono<ProgramsDto> validarPrograma(Integer id);
    public Mono<ActivitiesDto> validarActividades(Integer id);

    Flux<AsignationProgramsListDto> listarPorEstado(String activo);
}
