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
    @SuppressWarnings({"unchecked,rawtypes"})
    public Predicate toPredicate(Root<Segment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        return switch (criteria.getOperation()) {
            case "<" -> criteriaBuilder.lessThan(root.get(criteria.getKey()), (Comparable) criteria.getValue());
            case ">" -> criteriaBuilder.greaterThan(root.get(criteria.getKey()), (Comparable) criteria.getValue().toString());
            case "=" -> criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
            default -> null;
        };


    }
}
