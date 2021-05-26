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

    	System.out.println("Starting...");
        //create a new predicate list
        List<Predicate> predicates = new ArrayList<>();

        //add add criteria to predicates
        for (SearchCriteria criteria : list) {
        	System.out.println(list.size());
        	list.forEach((l) -> System.out.println(l));
            if (criteria.getValue()!= null && criteria.getOperation().equals(SearchOperation.USERNAME)) {
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        criteria.getValue().toString().toLowerCase() + "%"));
               System.out.println("1st if...");
            } else if (criteria.getValue()!= null && criteria.getOperation().equals(SearchOperation.FIRSTNAME)) {
            	System.out.println("2nd if...");
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        criteria.getValue().toString().toLowerCase() + "%"));
                
            } else if (criteria.getValue()!= null && criteria.getOperation().equals(SearchOperation.TEAMNAME)) {
            	System.out.println("3rd if...");
                predicates.add(builder.equal(
                        root.get("team").get("id"), criteria.getValue()));
                
            } else if (criteria.getValue()!= null && criteria.getOperation().equals(SearchOperation.EMAIL)) {
            	System.out.println("4th if...");
                predicates.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        "%" +criteria.getValue().toString().toLowerCase() + "%"));
                
            }
            System.out.println("loop over...");
        }
        System.out.println("outside loop");
        predicates.forEach((pred) -> System.out.println(pred));

        return builder.and(predicates.toArray(new Predicate[0]));
    }

}