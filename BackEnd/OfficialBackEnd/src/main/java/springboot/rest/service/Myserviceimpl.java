package springboot.rest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.rest.dao.Mydaorepository;
import springboot.rest.model.Employee;


@Service
public class Myserviceimpl implements Myservice {

	@Autowired
	Mydaorepository dao;

	@Override
	public List<Employee> getEmployees() {
		return dao.findAll();
		//finds all the employees/users
	}
	@Override
	public Optional<Employee> getEmployeeById(int empid) {
		return dao.findById(empid);
		//finds the correct user/employee by id number
	}
	@Override
	public Employee addNewEmployee(Employee emp) {
		return dao.save(emp);
		//adds a new employee
	}
	@Override
	public Employee updateEmployee(Employee emp) {
		return dao.save(emp);
		//updates the current employee by id number
	}
	@Override
	public void deleteEmployeeById(int empid) {
		dao.deleteById(empid);
		//delete the current employee by id number
	}
	@Override
	public void deleteAllEmployees() {
		dao.deleteAll();
		//deletes all the accounts in the table
	}
	
	public void create(Employee user) {
        dao.save(user);
    }
}