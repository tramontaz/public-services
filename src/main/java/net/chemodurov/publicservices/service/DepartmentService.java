package net.chemodurov.publicservices.service;

import lombok.RequiredArgsConstructor;
import net.chemodurov.publicservices.model.Department;
import net.chemodurov.publicservices.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository repository;

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
