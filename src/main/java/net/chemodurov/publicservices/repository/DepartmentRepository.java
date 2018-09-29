package net.chemodurov.publicservices.repository;

import net.chemodurov.publicservices.model.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {
    Department findByCode(Integer code);
    Department findByName(String name);
}
