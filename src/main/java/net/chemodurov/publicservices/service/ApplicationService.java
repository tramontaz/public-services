package net.chemodurov.publicservices.service;

import net.chemodurov.publicservices.model.Application;
import net.chemodurov.publicservices.model.Department;
import net.chemodurov.publicservices.repository.ApplicationRepository;
import net.chemodurov.publicservices.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository, DepartmentRepository departmentRepository) {
        this.applicationRepository = applicationRepository;
        this.departmentRepository = departmentRepository;
    }

    public Long getTotalrecordsCount() {
        return applicationRepository.count();
    }

    public Application save(Application application) {
        Department department;
        if (application != null && application.getStateService() != null &&
                application.getStateService().getDepCode() != null){
            department = departmentRepository.findByCode(application.getStateService().getDepCode());

        } else department = null;
        if (application != null) {
            application.setDepartment(department);
        }
        return applicationRepository.save(application);
    }

    public List<Application> findAll() {
        return applicationRepository.findAll();
    }
}