package org.mycompany.repository;

import org.mycompany.model.IntegrationLogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyRepository extends MongoRepository<IntegrationLogEntity, String> {
}
