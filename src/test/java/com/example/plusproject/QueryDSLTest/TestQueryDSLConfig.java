package com.example.plusproject.QueryDSLTest;

import com.example.plusproject.queryDSL.repository.QueryDSLRepository;
import com.example.plusproject.queryDSL.repository.QueryDSLRepositoryImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestQueryDSLConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public QueryDSLRepository queryDSLRepository() {
        return new QueryDSLRepositoryImpl(jpaQueryFactory());
    }
}
