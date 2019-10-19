/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Simple JavaBean domain object representing an owner.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @ModifiedBy Tanmay Ghosh
 */

@Entity

@Table(name = "owners")
public class Owners {

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
    private String address;
    
   /* @Column(name = "city")
    @NotFound(action = NotFoundAction.IGNORE)
    private String city;*/

    @Column(name = "telephone")
    @NotFound(action = NotFoundAction.IGNORE)
    private String telephone;
    
   /* @Column(name = "password")
    @NotFound(action = NotFoundAction.IGNORE)
    private String password;

    
    @Column(name = "favFood")
    @NotFound(action = NotFoundAction.IGNORE)
    private String favFood;

    
    @Column(name = "email")
    @NotFound(action = NotFoundAction.IGNORE)
    private String email;
    
    @Column(name = "username")
    @NotFound(action = NotFoundAction.IGNORE)
    private String username;*/
    
  //  @JsonPropertyOrder(value = {"user", "pass"}, alphabetic = true)

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return this.id == null;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
   /* public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getfavFood() {
        return this.favFood;
    }

    public void setfavFood(String favFood) {
        this.favFood = favFood;
    }

    public String getemail() {
        return this.email;
    }

    public void setemail(String email) {
        this.email = email;
    }
    
    public String getusername() {
        return this.username;
    }

    public void setusername(String username) {
        this.username = username ;
    }*/

    @Override
    public String toString() {
        return new ToStringCreator(this)

                .append("id", this.getId())
                .append("address", this.getAddress())
                .append("new", this.isNew())
                .append("firstName", this.getFirstName())
                .append("lastName", this.getLastName())
                .append("telephone", this.getTelephone()).toString();
                /*.append("city", this.getCity())
                .append("password", this.getPassword())
                .append("favFood", this.getfavFood())
                .append("email", this.getemail())
                .append("username", this.getusername()).toString();*/
                
    }
}
