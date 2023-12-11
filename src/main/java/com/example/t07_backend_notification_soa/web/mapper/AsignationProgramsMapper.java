package com.example.t07_backend_notification_soa.web.mapper;

import com.example.t07_backend_notification_soa.domain.dto.ActivitiesDto;
import com.example.t07_backend_notification_soa.domain.dto.AsignationProgramsDto;
import com.example.t07_backend_notification_soa.domain.dto.ProgramsDto;
import com.example.t07_backend_notification_soa.domain.model.AsignationPrograms;
import com.example.t07_backend_notification_soa.service.AsignationProgramsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AsignationProgramsMapper {
    private final AsignationProgramsService asignationProgramsService;

    public AsignationProgramsMapper(AsignationProgramsService asignationProgramsService) {
        this.asignationProgramsService = asignationProgramsService;
    }

    public ProgramsDto obtenerPrograms(Integer id){
        log.info("Notification mapper obtenerPrograms -> " + id);
        ResponseEntity<ProgramsDto> programs = asignationProgramsService.validarPrograma(id)
                .map(programas -> ResponseEntity.ok().body(programas))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build()).block();
        return programs.getBody();
    }
    public ActivitiesDto obtenerActivities(Integer id){
        log.info("Notification mapper obtenerPrograms -> " + id);
        ResponseEntity<ActivitiesDto> actividad = asignationProgramsService.validarActividades(id)
                .map(actividades -> ResponseEntity.ok().body(actividades))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build()).block();
        return actividad.getBody();
    }

    public AsignationPrograms crearAsignacion(AsignationProgramsDto asignationProgramsDto){
        log.info("Notification mapper createnotification -> " + asignationProgramsDto.toString());
        ProgramsDto programsDto = obtenerPrograms(asignationProgramsDto.getId_programs());
        ActivitiesDto activitiesDto = obtenerActivities(asignationProgramsDto.getId_activities());
        log.info("Notification mapper obtenerentidades -> " + activitiesDto.toString());

        AsignationPrograms asignation = new AsignationPrograms();
        asignation.setId_programs(asignationProgramsDto.getId_programs());
        asignation.setId_activities(asignationProgramsDto.getId_activities());
        asignation.setDate_asignation(asignationProgramsDto.getDate_asignation());
        asignation.setDirection(asignationProgramsDto.getDirection());
        asignation.setActive("A");
        return  asignation;
    }
}
