package com.slokam.healthcare.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.slokam.healthcare.entity.Patient;
import com.slokam.healthcare.pojo.PatientSearchPojo;
import com.slokam.healthcare.util.StringUtils;

@Repository
public class PatientCriteriaRepo {

	 @Autowired
	 private EntityManager em;   //like session
	
	   public List<Patient> patientFullSearch(PatientSearchPojo searchPojo) {
		   CriteriaBuilder cb=em.getCriteriaBuilder();
		   CriteriaQuery<Patient> cq=cb.createQuery(Patient.class);
		   Root<Patient> root=cq.from(Patient.class);
		   List<Predicate> predicateList=new ArrayList<>();
		   if(searchPojo!=null) {
			   if(StringUtils.blankCheck(searchPojo.getName())) {
				   predicateList.add(cb.like(root.get("name"), searchPojo.getName()+"%"));
			   }
			   if(searchPojo.getPhone()!=null) {
				   predicateList.add((cb.equal(root.get("phone"), searchPojo.getPhone())));
			   }
			   if(searchPojo.getFromdate()!=null) {
				   predicateList.add(cb.greaterThanOrEqualTo(root.get("regDate"), searchPojo.getFromdate()));
			   }
			   if(searchPojo.getTodate()!=null) {
				   predicateList.add(cb.lessThanOrEqualTo(root.get("regDate"), searchPojo.getTodate()));
			   }
			   if(searchPojo.getStartingAge()!=null) {
				   predicateList.add(cb.greaterThanOrEqualTo(root.get("age"), searchPojo.getStartingAge()));
			   }
			   if(searchPojo.getEndingAge()!=null) {
				   predicateList.add(cb.lessThanOrEqualTo(root.get("age"), searchPojo.getEndingAge()));
			   }
		   }
		   cq.where(predicateList.toArray(new Predicate[predicateList.size()]));
		   TypedQuery<Patient> patientQuery=em.createQuery(cq);
	       return patientQuery.getResultList();
	    }
}
