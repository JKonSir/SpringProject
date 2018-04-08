package org.mycompany.repository;

import org.mycompany.model.IntegrationLogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyRepository extends MongoRepository<IntegrationLogEntity, String> {

    List<IntegrationLogEntity> findByTimeBetween(long from, long to);

}
