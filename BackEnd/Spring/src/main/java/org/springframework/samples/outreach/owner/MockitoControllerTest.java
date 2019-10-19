package org.springframework.samples.outreach.owner;



import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.acl.Owner;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
//import org.junit.Before;



@RunWith(MockitoJUnitRunner.class)
public class MockitoControllerTest {
	
	@InjectMocks
	private OwnerController OwnerController;
	
	@Mock
	private OwnerRepository OwnerRepository;
	 
	
	@Before(value= "")
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testingUser() {
		Owners user = new Owners();
		user.setId(117);
		user.setFirstName("Kordell");
		user.setLastName("Schrock");
		user.setPoints("10");
		user.setPassord("Password");
		user.setUsername("Username");
		user.setAddress("Address");
		user.setTelephone("555-555-5555");
	//	System.out.println(user);
		
	//	System.out.println(OwnerController.createEmployee(user));
	}
	
	@Test
	public void getUsers() {
		Owners user = new Owners();
		
		Owners user2 = new Owners();
		
		when(OwnerRepository.findAll()).thenReturn(Stream.of(user, user2).collect(Collectors.toList()));
		
		assertEquals(2,OwnerController.getAllOwners().size());
	}
	@Test
	public void findById() {
		Owners user = new Owners();
		user.setId(117);
		
//		when(OwnerRepository.findById(117)).thenReturn(Stream.of(user).collect(Collectors.toList()));
//		
//		assertEquals(1,OwnerController.getAllOwners().size());
	}


}
