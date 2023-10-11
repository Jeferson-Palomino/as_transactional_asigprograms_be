package com.example.t07_backend_notification_soa.web;

import com.example.t07_backend_notification_soa.domain.dto.AsignationProgramsDto;
import com.example.t07_backend_notification_soa.domain.model.AsignationPrograms;
import com.example.t07_backend_notification_soa.service.AsignationProgramsService;
import com.example.t07_backend_notification_soa.web.mapper.AsignationProgramsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignation/programs")
@Slf4j
public class AsignationProgramsController {
    private final AsignationProgramsService asignationProgramsService;
    private final AsignationProgramsMapper asignationProgramsMapper;

    public AsignationProgramsController(AsignationProgramsService asignationProgramsService, AsignationProgramsMapper asignationProgramsMapper) {
        this.asignationProgramsService = asignationProgramsService;
        this.asignationProgramsMapper = asignationProgramsMapper;
    }
    @PostMapping
    @CrossOrigin(origins="http://localhost:4200")
    public AsignationPrograms asignarProgramas(@RequestBody AsignationProgramsDto asignationProgramsDto){
        AsignationPrograms asignationPrograms = asignationProgramsMapper.crearAsignacion(asignationProgramsDto);
        return  asignationProgramsService.create(asignationPrograms);
    }
    @GetMapping
    @CrossOrigin(origins="http://localhost:4200")
    public List<AsignationPrograms> listar(){
        return  asignationProgramsService.listAsignation();
    }
}
