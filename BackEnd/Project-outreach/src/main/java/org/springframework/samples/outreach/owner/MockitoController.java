package org.springframework.samples.outreach.owner;



import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.List;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyList;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.test.context.jdbc.Sql;
//import org.junit.Before;




@RunWith(MockitoJUnitRunner.class)
public class MockitoController {
	
	@InjectMocks
	private OwnerController OwnerController;
	
	@Mock
	private OwnerRepository OwnerRepository;
	 
	Owners current = new Owners();
	
	@Before(value= "")
	public void init() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		// set EmployeeDAO mock object
		OwnerController = mock(OwnerController.class);
 
        // create an user object
		
		current.setId(12);
		current.setAddress("Mountain View, CA");
		current.setFirstName("Google");
		current.setLastName("Lastname");
		current.setUsername("googler");
		current.setPassord("data");
		current.setPoints("111");
		
		OwnerController.deleteEmployeeById(12);
		
		System.out.println("HELLO");
		System.out.println(current);
 
	}
	
	@Test
    public void deleteEmployeeTest() {
		
		Owners user = new Owners();
		user.setId(12);
		
		Owners user2 = new Owners();
		user2.setId(11);
		user2.setFirstName("KORDELL");
		
		when(OwnerRepository.findAll()).thenReturn(Stream.of(user, user2).collect(Collectors.toList()));
		
		java.util.List<Owners> results = OwnerController.getAllOwners();
				
		System.out.println(results);
		OwnerRepository.deleteById(11);
		try {
			OwnerController.deleteEmployeeById(11);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		OwnerRepository.deleteAll();
		OwnerController.deleteAll();
		OwnerController.deleteAll();
		OwnerRepository.flush();
		try {
			OwnerRepository.deleteAll();
			OwnerController.deleteAll();
			OwnerRepository.flush();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
		 System.out.println("HELLO");
		
        System.out.println(results);
       
        System.out.println("HELLO");
        
        System.out.println("HELLO Test");
		System.out.println("------------------------------------------\n\n\n");
		
     //   assertEquals("REMOVED", status);
    }
	
	
	
	//This creates a user and fills in the parameters
	//Then this checks the creating a user into the DB by returning a string.
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
		System.out.println(user);
		//System.out.println(OwnerController.createEmployee(user));
		
		assertEquals("New Owner Kordell Saved".trim(),OwnerController.createEmployee(user).trim());
		System.out.println("Creating User Test");
		System.out.println("------------------------------------------\n\n\n");
	}
	
	//This Creates 2 null users, with no parameters created.
	//There should be 2 users total in the DB. 
	@Test
	public void getUsers() {
		Owners user = new Owners();
		
		Owners user2 = new Owners();
		
		when(OwnerRepository.findAll()).thenReturn(Stream.of(user, user2).collect(Collectors.toList()));
		
		assertEquals(2,OwnerController.getAllOwners().size());
		
		System.out.println("Get Users Test");
		System.out.println("------------------------------------------\n\n\n");
	}
	
	
	//This Creates a user, with username = Username and password = Password
	//This shows to logins, first one matches and passes
	//the second one does not match and fails.
	@Test
	public void loginTest() {
		Owners user = new Owners();
		user.setFirstName("Kordell");
		user.setLastName("Schrock");
		user.setPoints("10");
		user.setPassord("Password");
		user.setUsername("Username");
		user.setAddress("Address");
		user.setTelephone("555-555-5555");
		user.setId(117);
		when(OwnerRepository.findAll()).thenReturn(Stream.of(user).collect(Collectors.toList()));
		System.out.println("Login 1"); //SHOULD BE TURE
		System.out.println(OwnerController.loginOwner("Username", "Password"));
		
		assertEquals("{verify=true}".trim(),OwnerController.loginOwner("Username", "Password").toString().trim());
		
		System.out.println("Login 2");//SHOULD BE FALSE
		assertEquals("{verify=false}".trim(),OwnerController.loginOwner("Wrong", "Wrong").toString().trim());
		System.out.println(OwnerController.loginOwner("Wrong", "Wrong"));
	
		System.out.println("Login Test");
		System.out.println("------------------------------------------\n\n\n");
		
	}
	
	//THIS TESTS GET ALL FUNCTION
	@Test
	public void getAllAccountTest() {
		java.util.List<Owners>list = new ArrayList<Owners>();
		Owners acctOne = new Owners();
		Owners acctTwo = new Owners();
		Owners acctThree = new Owners();

		list.add(acctOne);
		list.add(acctTwo);
		list.add(acctThree);

		when(OwnerRepository.findAll()).thenReturn(list);

		java.util.List<Owners> acctList = OwnerController.getAllOwners();

		assertEquals(3, acctList.size());
		verify(OwnerRepository, times(1)).findAll();
		System.out.println("GET ALL Test");
		System.out.println("------------------------------------------\n\n\n");
	}
	
//	@Test
//	public void getAccountByIdTest() {
//		Mockito.when(OwnerRepository.findById((1)).thenReturn((new Owners())));
//
//		Optional<Owners> acct = OwnerController.findOwnerById(1);
//
////		assertEquals("jDoe", acct.getUserID());
////		assertEquals("123456", acct.getPassword());
////		assertEquals("jDoe@gmail.com", acct.getEmail());
//	}

	 
	@Test
	public void deleteAllUsers() {
		Owners user = new Owners();
		
		
		Owners user2 = new Owners();
		
		Owners newBook = new Owners();
		
		String current = "New Owner " + user.getFirstName() + " Saved";
		
        //when(OwnerController.createEmployee(user)).thenReturn(current);
        
     //   Mockito.when(OwnerController.createEmployee(user)).thenReturn(current);
		
		//when(OwnerRepository.findAll()).thenReturn(Stream.of(user, user2).collect(Collectors.toList()));
		//when(OwnerRepository.save(user)).thenReturn((Owners) OwnerController.getAllOwners());
		
		//OwnerRepository.deleteAll();
		
		//System.out.println(OwnerController.findOwnerById(1));
	
		System.out.println(OwnerController.getAllOwners().size());
		
		//assertEquals(0 ,OwnerController.getAllOwners().size());
		
		System.out.println("Delete All Test");
		System.out.println("------------------------------------------\n\n\n");
	
	}
	
//	 @Test
//	  @Sql("data.sql")
//	  void whenInitializedByDbUnit_thenFindsByName() {
//	    Optional<Owners> user = OwnerController.findOwnerById(2);
//	    assertThat(user).isNotNull();
//	  }

}
