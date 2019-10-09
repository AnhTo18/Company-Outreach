package myProject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyController {
	
	@Autowired
	MyDatabase db;
	
	@GetMapping("/person/{id}")
	Person getPerson(@PathVariable Integer id) {
		return db.findOne(id);
	}
	@RequestMapping("/persons")
	List<Person> hello() {
		return db.findAll();
		//the returns all the people in the DataBase
	}
	
	@PostMapping("/person")
	Person createPerson(@RequestBody Person p) {
		db.save(p);
		return p;
		//this creates a person in the DataBase and saves it
	}
	
	
	@PutMapping("/person/{id}")
	Person updatePerson(@RequestBody Person p, @PathVariable Integer id) {
		Person old_p = db.findOne(id);
		old_p.setAddress(p.address);
		db.save(old_p);
		return old_p;
		//this updates the person's address in the DataBase
	}
	
	@DeleteMapping("/person/{id}")
	String deletePerson(@PathVariable Integer id) {
		db.delete(id);
		return "deleted " + id;
		//this deletes the person in the DataBase
	}

}


