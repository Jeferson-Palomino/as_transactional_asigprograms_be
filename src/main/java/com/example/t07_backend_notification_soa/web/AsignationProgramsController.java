package com.example.t07_backend_notification_soa.web;

import com.example.t07_backend_notification_soa.domain.dto.AsignationProgramsDto;
import com.example.t07_backend_notification_soa.domain.dto.AsignationProgramsListDto;
import com.example.t07_backend_notification_soa.domain.dto.AsignationProgramsResponseDto;
import com.example.t07_backend_notification_soa.domain.dto.ProgramsBulkDto;
import com.example.t07_backend_notification_soa.domain.report.AsignationProgramsReportDto;
import com.example.t07_backend_notification_soa.repository.AsignationProgramsRepository;
import com.example.t07_backend_notification_soa.service.AsignationProgramsService;
import com.example.t07_backend_notification_soa.service.impl.AsignationProgramsServiceImpl;
import com.example.t07_backend_notification_soa.web.mapper.AsignationProgramsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping( "/api/asignation/programs")
@Slf4j
public class AsignationProgramsController {
    private final AsignationProgramsService asignationProgramsService;
    private  final AsignationProgramsServiceImpl asignationProgramsServiceImpl;
    private final AsignationProgramsMapper asignationProgramsMapper;
    private final AsignationProgramsRepository asignationProgramsRepository;

    public AsignationProgramsController(AsignationProgramsService asignationProgramsService, AsignationProgramsServiceImpl asignationProgramsServiceImpl, AsignationProgramsMapper asignationProgramsMapper, AsignationProgramsRepository asignationProgramsRepository) {
        this.asignationProgramsService = asignationProgramsService;
        this.asignationProgramsServiceImpl = asignationProgramsServiceImpl;
        this.asignationProgramsMapper = asignationProgramsMapper;
        this.asignationProgramsRepository = asignationProgramsRepository;
    }
    @PostMapping
    @CrossOrigin(origins="http://localhost:4200")
    public Mono<Void> asignarProgramas(@RequestBody ProgramsBulkDto dto){
        return  asignationProgramsService.create(dto);
    }
    @GetMapping
    @CrossOrigin(origins="http://localhost:4200")
    public Flux<AsignationProgramsReportDto> listar(){
        return  asignationProgramsService.listAsignation();
    }
    @CrossOrigin(origins="http://localhost:4200")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Integer id) {
        return this.asignationProgramsService.delete(id);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping(value = "/{id}/activar", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<AsignationProgramsResponseDto> activarEntidad(@PathVariable Integer id) {
        return this.asignationProgramsService.activar(id);
    }
    @CrossOrigin(origins="http://localhost:4200")
    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<AsignationProgramsResponseDto> update(@PathVariable Integer id, @RequestBody AsignationProgramsDto dto) {
        return this.asignationProgramsService.update(id, dto);
    }
    @CrossOrigin(origins="http://localhost:4200")
    @GetMapping("/{activo}")
    public Flux<AsignationProgramsReportDto> listarPorEstado(@PathVariable String activo) {
        return asignationProgramsService.listarPorEstado(activo);
    }

    @CrossOrigin(origins="http://localhost:4200")
    @GetMapping("/report/{nameprograms}")
    public Mono<ResponseEntity<Resource>> exportAsignationPrograms(@PathVariable String nameprograms){
        return asignationProgramsServiceImpl.exportAsignationReport(nameprograms);
    }
}
