/**
 *
 */
package com.practice.flyway;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * Gets the Employee list.
	 *
	 * @return the list of all the employees in the database
	 */
	@GetMapping(path="/employees")
	public List<Employee> getEmployees() {
		logger.info("START - getEmployees of EmployeeController");
		return (List<Employee>) employeeRepository.findAll();

	}

	/**
	 * Gets the Employee details from the database based on the employeeId.
	 *
	 * @param id the employeeId
	 * @return the employee details if found otherwise returns an error message
	 */
	@GetMapping("/employee/{id}")
	public ResponseEntity<Object> retrieveEmployee(@PathVariable long id) {
		logger.info("START - retrieveEmployee of EmployeeController");
		try{
			Optional<Employee> employee = employeeRepository.findById(id);
			return new ResponseEntity<>(employee.get(),HttpStatus.OK);
		} catch(Exception e){
			return new ResponseEntity<>("Employee with id "+id+" could not be found",HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Deletes the Employee from the database based on the employeeId.
	 *
	 * @param id the employeeId
	 * @return the message of deletion along with appropriate HTTP Status Codes
	 */
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable long id) {
		logger.info("START - deleteEmployee of EmployeeController");
		try{
			employeeRepository.deleteById(id);
			return new ResponseEntity<>("Employee with id "+id+" is successfully deleted",HttpStatus.OK);
		} catch(Exception e){
			return new ResponseEntity<>("Employee with id "+id+" could not be deleted",HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Adds the Employee to the database
	 *
	 * @param the employee object
	 * @return the details of the employee added or the error message if the employee is not added
	 */
	@PostMapping("/employees")
	public ResponseEntity<Object> createEmployee(@RequestBody Employee employee) {
		logger.info("START - createEmployee of EmployeeController");
		try{
			Employee newEmployee = employeeRepository.save(employee);
			return new ResponseEntity<>(newEmployee,HttpStatus.OK);
		} catch (Exception e){
			return new ResponseEntity<>("Employee could not be added",HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Updates the Employee object details to the database
	 *
	 * @param the employee object and the employeeId
	 * @return the details of the employee updated or the error message if the employee is not updated
	 */
	@PutMapping("/employee/{id}")
	public ResponseEntity<Object> updateEmployee(@RequestBody Employee employee, @PathVariable long id) {
		logger.info("START - updateEmployee of EmployeeController");
		Optional<Employee> currentEmployee = employeeRepository.findById(id);

		if (!currentEmployee.isPresent()){
			return new ResponseEntity<>("Employee with id: "+id+" not found in the database",HttpStatus.BAD_REQUEST);
		}
		employee.setEmployeeId(id);
		employeeRepository.save(employee);
		return new ResponseEntity<>(employee,HttpStatus.OK);
	}
}
