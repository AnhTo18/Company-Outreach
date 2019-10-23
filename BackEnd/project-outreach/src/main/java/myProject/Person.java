package myProject;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;

@Entity
@Table(name = "user")
class Person {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private Integer id;

	    @Column(name = "first_name")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String firstName;

	    @Column(name = "last_name")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String lastName;

	    @Column(name = "address")
	    @NotFound(action = NotFoundAction.IGNORE) 
	    String address;

	    @Column(name = "telephone")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String telephone;

	    public Integer getId() {
	        return id;
	        //Getter for ID of User
	    }

	    public void setId(Integer id) {
	        this.id = id;
	        //Setter for ID of User
	    }

	    public boolean isNew() {
	        return this.id == null;
	    }

	    public String getFirstName() {
	        return this.firstName;
	        //Getter for FirstName of User
	    }

	    public void setFirstName(String firstName) {
	        this.firstName = firstName;
	        //Setter for FirstName of User
	    }

	    public String getLastName() {
	        return this.lastName;
	        //Getter for LastName of User
	    }

	    public void setLastName(String lastName) {
	        this.lastName = lastName;
	      //Setter for LastName of User
	    }

	    public String getAddress() {
	        return this.address;
	        //Getter for Address of User
	    }

	    public void setAddress(String address) {
	        this.address = address;
	        //Setter for Address
	    }

	    public String getTelephone() {
	        return this.telephone;
	        //Getter for Telephone Number
	    }

	    public void setTelephone(String telephone) {
	        this.telephone = telephone;
	        //Setter for Telephone Number
	    }

	    @Override
	    public String toString() {
	        return new ToStringCreator(this)

	                .append("id", this.getId())
	                .append("new", this.isNew())
	                .append("lastName", this.getLastName())
	                .append("firstName", this.getFirstName())
	                .append("address", this.address)
	                .append("telephone", this.telephone).toString();
	    }
	
	
}