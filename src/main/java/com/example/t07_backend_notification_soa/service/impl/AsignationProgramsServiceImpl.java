package com.example.t07_backend_notification_soa.service.impl;

import com.example.t07_backend_notification_soa.domain.dto.*;
import com.example.t07_backend_notification_soa.domain.mapper.AsignacionProgramsMapper;
import com.example.t07_backend_notification_soa.exception.NotFoundException;
import com.example.t07_backend_notification_soa.repository.AsignationProgramsRepository;
import com.example.t07_backend_notification_soa.service.AsignationProgramsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.t07_backend_notification_soa.domain.mapper.AsignacionProgramsMapper.toModel;

@Service
@Slf4j
@RequiredArgsConstructor
public class AsignationProgramsServiceImpl implements AsignationProgramsService {
    private final AsignationProgramsRepository asignationProgramsRepository;
    @Override
    public Mono<Void> create(ProgramsBulkDto dto) {
        List<Mono<Void>> saveMonos = dto.getId_activities().stream()
                .map(res -> {
                    AsignationProgramsResponseDto asignacion = AsignationProgramsResponseDto.builder()
                            .id_programs(dto.getId_programs())
                            .id_activities(res.getId_activities())
                            .direction(dto.getDirection())
                            .date_asignation(dto.getDate_asignation())
                            .build();
                    return asignationProgramsRepository.save(toModel(asignacion)).then();
                })
                .collect(Collectors.toList());

        return Mono.when(saveMonos);
    }


    @Override
    public Flux<AsignationProgramsResponseDto> listAsignation() {
        return asignationProgramsRepository.findAll()
                .map(AsignacionProgramsMapper::toDtoAsignation);
    }

    @Override
    public Mono<AsignationProgramsResponseDto> update(Integer id, AsignationProgramsDto request) {
        return this.asignationProgramsRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(("El id: " + id + " no fue encontrado"))))
                .flatMap(asignation -> asignationProgramsRepository.save(toModel(asignation.getId(),request)))
                .map(AsignacionProgramsMapper::toDtoAsignation);
    }

    @Override
    public Mono<Void> delete(Integer id) {
        return asignationProgramsRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(("El id: " + id + " no fue encontrado"))))
                .flatMap(asignation -> {
                    asignation.setActive("I");
                    return asignationProgramsRepository.save(asignation);
                })
                .doOnSuccess(unused -> log.info("Se elimino el usuario con id: {}" ,id))
                .then();
    }

    @Override
    public Mono<AsignationProgramsResponseDto> activar(Integer id) {
        return asignationProgramsRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(("El id: " + id + " no fue encontrado"))))
                .flatMap(asignation -> {
                    asignation.setActive("A");
                    return asignationProgramsRepository.save(asignation);
                })
                .map(AsignacionProgramsMapper::toDtoAsignation);
    }

    @Override
    public Mono<ProgramsDto> validarPrograma(Integer id) {
        log.info("Notification validar service ->" + id);
        WebClient webClient = WebClient.create("http://localhost:8085/v1/programs/find/" + id);
        ClientResponse response = webClient.get()
                .exchange()
                .block();
        log.info("webclient funcionario service ->" + response.bodyToMono(ProgramsDto.class));
        if (response.statusCode().value() == 200) {
            return webClient.get().retrieve().bodyToMono(ProgramsDto.class);
        }else {
            throw new NotFoundException("funcionario no existente");
        }
    }

    @Override
    public Mono<ActivitiesDto> validarActividades(Integer id) {
        log.info("Notification validar service ->" + id);
        WebClient webClient = WebClient.create("http://localhost:8084/ms-soa/" + id);
        ClientResponse response = webClient.get()
                .exchange()
                .block();
        log.info("webclient funcionario service ->" + response.bodyToMono(ActivitiesDto.class));
        if (response.statusCode().value() == 200) {
            return webClient.get().retrieve().bodyToMono(ActivitiesDto.class);
        }else {
            throw new NotFoundException("funcionario no existente");
        }
    }

    public Flux<AsignationProgramsResponseDto> listarPorEstado(String active) {
        return asignationProgramsRepository.findByActive(active);
    }
}
