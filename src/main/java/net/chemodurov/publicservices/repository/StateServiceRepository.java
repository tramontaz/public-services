package net.chemodurov.publicservices.repository;

import net.chemodurov.publicservices.model.StateService;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateServiceRepository extends MongoRepository<StateService, String> {
    StateService findByDepCode(Integer code);
    StateService findByName(String name);
}
