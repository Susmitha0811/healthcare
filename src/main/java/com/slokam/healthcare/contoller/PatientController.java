package com.slokam.healthcare.contoller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slokam.healthcare.entity.Patient;
import com.slokam.healthcare.pojo.PatientSearchPojo;
import com.slokam.healthcare.service.IPatientService;

@RestController
@RequestMapping("patient")
public class PatientController {

	@Autowired
	private IPatientService patientService;
	
	@PostMapping("/register")
	public ResponseEntity<Patient> registerPatient(@RequestBody Patient patient){
		
		patientService.patientRegistration(patient);
		return new ResponseEntity<Patient>(patient,HttpStatus.CREATED);
	}
	
	@PostMapping("/search")
	public ResponseEntity<List<Patient>> patientSearch(@RequestBody PatientSearchPojo searchPojo){
		List<Patient> list=patientService.patientSearch(searchPojo);
		return ResponseEntity.ok(list);
	}
	@GetMapping("/all")
	public ResponseEntity<List<Patient>> getAllPatients(){
		List<Patient> list=patientService.getAllPatients();
		return ResponseEntity.ok(list);
	}
	@GetMapping("/byId/{id}")
	public ResponseEntity<Patient> getById(@PathVariable Integer id){
		Patient patient =patientService.getById(id);
		return ResponseEntity.ok(patient);
	}
	@GetMapping("/even")
	public ResponseEntity<List<Patient>> getEvenId(){
		List<Patient> list=patientService.getAllPatients();
		List<Patient> elist=list.stream().filter(p->p.getId()%2==0).collect(Collectors.toList());
		return ResponseEntity.ok(elist);
	}
	@GetMapping("/age")
	public ResponseEntity<List<Patient>> getAboveAge(){
		return ResponseEntity.ok(patientService.getAllPatients().stream().filter(p->p.getAge()>=25).collect(Collectors.toList()));
	}
	@GetMapping("/name")
	public ResponseEntity<List<Patient>> getByName(){
		return ResponseEntity.ok(patientService.getAllPatients().stream().filter(p->p.getName().equals("three")).collect(Collectors.toList()));
	}
	@GetMapping("/allNames")
	public ResponseEntity<List<String>> getAllNames(){
		return ResponseEntity.ok(patientService.getAllPatients().stream().map(m->m.getName()).collect(Collectors.toList()));
	}
	@GetMapping("/allName")
	public ResponseEntity<List<Patient>> getAllName(){
		return ResponseEntity.ok(patientService.getAllPatients().stream().filter(p->nullCheck(p)).collect(Collectors.toList()));
	}
	public boolean nullCheck(Patient patient) {
		boolean result=false;
		if(Objects.nonNull(patient)&& patient.getId()!=null && patient.getId()%2==0) {
			result=true;
		}
		return result;
	}
}
