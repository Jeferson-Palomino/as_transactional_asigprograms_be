package com.example.t07_backend_notification_soa.repository;

import com.example.t07_backend_notification_soa.domain.dto.AsignationProgramsListDto;
import com.example.t07_backend_notification_soa.domain.dto.AsignationProgramsResponseDto;
import com.example.t07_backend_notification_soa.domain.model.AsignationPrograms;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AsignationProgramsRepository extends ReactiveCrudRepository<AsignationPrograms,Integer> {}
