package net.chemodurov.publicservices.service;

import lombok.RequiredArgsConstructor;
import net.chemodurov.publicservices.model.StateService;
import net.chemodurov.publicservices.repository.StateServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StateServiceService {
    private final StateServiceRepository repository;

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

    public StateService findById(String id) {
        Optional optional = repository.findById(id);
        return (StateService) optional.get();
    }
}
