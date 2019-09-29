package springboot.rest.service;

import java.util.List;
import java.util.Optional;

import springboot.rest.model.Employee;

public interface Myservice {

	public List<Employee> getEmployees();
	public Optional<Employee> getEmployeeById(int empid);
	public Employee addNewEmployee(Employee emp);
	public Employee updateEmployee(Employee emp);
	public void deleteEmployeeById(int empid);
	public void deleteAllEmployees();

}