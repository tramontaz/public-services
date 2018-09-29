package net.chemodurov.publicservices.service;

import net.chemodurov.publicservices.model.StateService;
import net.chemodurov.publicservices.repository.StateServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateServiceService {
    private final StateServiceRepository repository;

    @Autowired
    public StateServiceService(StateServiceRepository repository) {
        this.repository = repository;
    }

    public StateService saveDepartment(StateService stateService) {
        return repository.save(stateService);
    }

    public List<StateService> getAll() {
        return repository.findAll();
    }

    public StateService findServiceByCode(Integer depCode) {
        return repository.findByDepCode(depCode);
    }

    public StateService findServiceByName(String name) {
        return repository.findByName(name);
    }
}
