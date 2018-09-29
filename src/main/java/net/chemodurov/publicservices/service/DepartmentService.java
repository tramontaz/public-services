package net.chemodurov.publicservices.service;

import net.chemodurov.publicservices.model.Department;
import net.chemodurov.publicservices.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository repository;

    @Autowired
    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    public Department saveDepartment(Department department) {
        return repository.save(department);
    }

    public List<Department> getAll() {
        return repository.findAll();
    }

    public Department findDepartmentByCode(Integer code) {
        return repository.findByCode(code);
    }

    public Department findDepartmentByName(String name) {
        return repository.findByName(name);
    }
}
