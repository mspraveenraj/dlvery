package com.project.dlvery.searchspecification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;


public class GenericSpecification<T> implements Specification<T> {

    private static final long serialVersionUID = 1900581010229669687L;

    private List<SearchCriteria> list;

    public GenericSpecification() {
        this.list = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        //create a new predicate list
        List<Predicate> predicates = new ArrayList<>();

        //add add criteria to predicates
        for (SearchCriteria criteria : list) {
   
            if (criteria.getValue()!= null && criteria.getOperation().equals(SearchOperation.USERNAME)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        criteria.getValue().toString().toLowerCase() + "%"));
            } else if (criteria.getValue()!= null && criteria.getOperation().equals(SearchOperation.FIRSTNAME)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        criteria.getValue().toString().toLowerCase() + "%"));
                
            } else if (criteria.getValue()!= null && criteria.getOperation().equals(SearchOperation.TEAMNAME)) {
                predicates.add(builder.equal(
                        root.get("team").get("id"), criteria.getValue()));
                
            } else if (criteria.getValue()!= null && criteria.getOperation().equals(SearchOperation.EMAIL)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        "%" +criteria.getValue().toString().toLowerCase() + "%"));
                
            }
           
        }
        
        return builder.and(predicates.toArray(new Predicate[0]));
    }

}