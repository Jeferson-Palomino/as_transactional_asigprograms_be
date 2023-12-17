package com.example.t07_backend_notification_soa.service.impl;

import com.example.t07_backend_notification_soa.domain.dto.*;
import com.example.t07_backend_notification_soa.domain.mapper.AsignacionProgramsMapper;
import com.example.t07_backend_notification_soa.domain.model.AsignationPrograms;
import com.example.t07_backend_notification_soa.domain.report.AsignationProgramsReportDto;
import com.example.t07_backend_notification_soa.exception.NotFoundException;
import com.example.t07_backend_notification_soa.repository.AsignationProgramsRepository;
import com.example.t07_backend_notification_soa.service.AsignationProgramsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.t07_backend_notification_soa.domain.mapper.AsignacionProgramsMapper.toModel;

@Service
@Slf4j
@RequiredArgsConstructor
public class AsignationProgramsServiceImpl implements AsignationProgramsService {
    private final AsignationProgramsRepository asignationProgramsRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;
    @Override
    public Mono<Void> create(ProgramsBulkDto dto) {
        List<Mono<Void>> saveMonos = dto.getActivities().stream()
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
    public Flux<AsignationProgramsReportDto> listAsignation() {
        Flux<AsignationPrograms> asignation = asignationProgramsRepository.findAll().sort(Comparator.comparing(AsignationPrograms::getId).reversed());
        return asignation.flatMap(dataasugnation -> {
            Mono<ProgramsDto> programs = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8085/v1/programs/find/"+ dataasugnation.getId_programs())
                    .retrieve()
                    .bodyToMono(ProgramsDto.class);

            Mono<ActivitiesDto> activities = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8084/ms-soa/" + dataasugnation.getId_activities())
                    .retrieve()
                    .bodyToMono(ActivitiesDto.class);
            return programs.zipWith(activities).map(data ->{
                ProgramsDto programsDto = data.getT1();
                ActivitiesDto activitiesDto = data.getT2();
                AsignationProgramsReportDto asignationProgramsReportDto = new AsignationProgramsReportDto();
                asignationProgramsReportDto.setId(dataasugnation.getId());
                asignationProgramsReportDto.setId_programs(dataasugnation.getId_programs());
                asignationProgramsReportDto.setId_activities(dataasugnation.getId_activities());
                asignationProgramsReportDto.setName_programs(programsDto.getName());
                asignationProgramsReportDto.setName_activities(activitiesDto.getName());
                asignationProgramsReportDto.setDate_asignation(dataasugnation.getDate_asignation());
                asignationProgramsReportDto.setDirection(dataasugnation.getDirection());
                return asignationProgramsReportDto;
            });
        });

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

    public Flux<AsignationProgramsReportDto> listarPorEstado(String active) {
        Flux<AsignationPrograms> asignation = asignationProgramsRepository.findAll()
                .sort(Comparator.comparing(AsignationPrograms::getId).reversed())
                .filter((activ)-> activ.getActive().equals(active));
        return asignation.flatMap(dataasignation ->{
            Mono<ProgramsDto> programs = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8085/v1/programs/find/"+ dataasignation.getId_programs())
                    .retrieve()
                    .bodyToMono(ProgramsDto.class);

            Mono<ActivitiesDto> activities = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8084/ms-soa/" + dataasignation.getId_activities())
                    .retrieve()
                    .bodyToMono(ActivitiesDto.class);
            return programs.zipWith(activities).map(data ->{
                ProgramsDto programsDto = data.getT1();
                ActivitiesDto activitiesDto = data.getT2();
                AsignationProgramsReportDto asignationProgramsReportDto = new AsignationProgramsReportDto();
                asignationProgramsReportDto.setId(dataasignation.getId());
                asignationProgramsReportDto.setId_programs(dataasignation.getId_programs());
                asignationProgramsReportDto.setId_activities(dataasignation.getId_activities());
                asignationProgramsReportDto.setName_programs(programsDto.getName());
                asignationProgramsReportDto.setName_activities(activitiesDto.getName());
                asignationProgramsReportDto.setDate_asignation(dataasignation.getDate_asignation());
                asignationProgramsReportDto.setDirection(dataasignation.getDirection());
                return asignationProgramsReportDto;
            });
        });
    }

    public Mono<ResponseEntity<Resource>> exportAsignationReport(String nameprograms) {
        Flux<AsignationProgramsReportDto> asignationReportDtoFlux = listAsignation().filter(programs ->
            programs.getName_programs().equals(nameprograms)
        );

        return asignationReportDtoFlux.collectList()
                .flatMap(asignationReportDtos -> {
                    try {
                        final HashMap<String, Object> parameters = new HashMap<>();
                        parameters.put("dataAsignation", new JRBeanCollectionDataSource(asignationReportDtos));
                        return Mono.just(jasperReport("asignacion.jasper", "asignationprograms", parameters));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Mono.error(e);
                    }
                })
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }



    public ResponseEntity<Resource> jasperReport(String reportPath, String outputFileName, HashMap<String, Object> parameters) throws JRException {

        try {
            final File file = ResourceUtils.getFile("classpath:"+reportPath);
            final File imgLogo = ResourceUtils.getFile("classpath:image/logo.png");
            final JasperReport report = (JasperReport) JRLoader.loadObject(file);
            parameters.put("logo", new FileInputStream(imgLogo));

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
            byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
            StringBuilder stringBuilder = new StringBuilder().append("InvoicePDF:");
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                    .filename(stringBuilder.append(outputFileName).toString())
                    .build();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(contentDisposition);
            return ResponseEntity.ok().contentLength((long) reporte.length)
                    .contentType(MediaType.APPLICATION_PDF)
                    .headers(headers).body(new ByteArrayResource(reporte));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
