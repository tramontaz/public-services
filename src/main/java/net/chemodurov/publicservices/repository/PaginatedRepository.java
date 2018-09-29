package net.chemodurov.publicservices.repository;

import net.chemodurov.publicservices.model.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaginatedRepository {
    private final ApplicationRepository repository;

    @Autowired
    public PaginatedRepository(ApplicationRepository repository) {
        this.repository = repository;
    }

    public List<Application> pageableRequest(int from, int to) {
        Pageable pageableRequest = new PageRequest(from, to);
        Page<Application> pages = repository.findAll(pageableRequest);
        return pages.getContent();
    }
}