package net.chemodurov.publicservices.repository;

import net.chemodurov.publicservices.model.Application;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends MongoRepository<Application, String> {
}
