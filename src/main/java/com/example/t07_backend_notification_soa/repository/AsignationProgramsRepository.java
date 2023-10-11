package com.example.t07_backend_notification_soa.repository;

import com.example.t07_backend_notification_soa.domain.model.AsignationPrograms;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignationProgramsRepository extends CrudRepository<AsignationPrograms,Long> {

}
