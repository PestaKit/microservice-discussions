/**
File which containts interface ReportRepository (no method)
*/
package io.pestakit.discussions.repositories;

import io.pestakit.discussions.entities.ReportEntity;
import org.springframework.data.repository.CrudRepository;

public interface ReportRepository extends CrudRepository<ReportEntity, Integer>{

}
