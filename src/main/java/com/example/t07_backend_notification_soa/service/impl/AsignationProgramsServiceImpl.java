package com.example.t07_backend_notification_soa.service.impl;

import com.example.t07_backend_notification_soa.domain.dto.ActivitiesDto;
import com.example.t07_backend_notification_soa.domain.dto.ProgramsDto;
import com.example.t07_backend_notification_soa.domain.model.AsignationPrograms;
import com.example.t07_backend_notification_soa.exception.NotFoundException;
import com.example.t07_backend_notification_soa.repository.AsignationProgramsRepository;
import com.example.t07_backend_notification_soa.service.AsignationProgramsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class AsignationProgramsServiceImpl implements AsignationProgramsService {
    private final AsignationProgramsRepository asignationProgramsRepository;
    @Override
    public AsignationPrograms create(AsignationPrograms asignationPrograms) {
        return asignationProgramsRepository.save(asignationPrograms);
    }

    @Override
    public List<AsignationPrograms> listAsignation() {
        return (List<AsignationPrograms>) asignationProgramsRepository.findAll();
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
}
