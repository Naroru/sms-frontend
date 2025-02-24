package com.example.smsfrontend.specification;

import com.example.smsfrontend.proxy.segment.Segment;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class SegmentSpecification implements Specification<Segment> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Segment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
/*
    @Override
    public Predicate toPredicate(Root<Segment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        switch (criteria.getOperation()){
            case "<":
                return criteriaBuilder.lessThan(root.get(criteria.getKey()),criteria.getValue().toString());


        }


    }*/
}
