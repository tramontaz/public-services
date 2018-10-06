package net.chemodurov.publicservices.repository;

import lombok.RequiredArgsConstructor;
import net.chemodurov.publicservices.model.Application;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaginatedRepository {
    private final MongoTemplate mongoTemplate;

    public List<Application> paginatedSearchRequest(String searchParam, int page, int lenght) {
        Query query = new Query();
        if ("".equals(searchParam) || searchParam == null) {
            query.with(PageRequest.of(page/lenght, lenght));
            return mongoTemplate.find(query, Application.class);
        } else {
            query.addCriteria(Criteria.where("declarant.surname").regex(searchParam)).with(PageRequest.of(page/lenght, lenght));
            return mongoTemplate.find(query, Application.class);
        }
    }
}