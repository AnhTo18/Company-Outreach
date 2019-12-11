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
package org.springframework.samples.outreach.owner;

import org.hamcrest.Matcher;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.outreach.company.Company;
import org.springframework.samples.outreach.company.CompanyRepository;
import org.springframework.samples.outreach.owner.*;
import org.springframework.samples.outreach.prize.Prize;
import org.springframework.samples.outreach.prize.PrizeRepository;
import org.springframework.samples.outreach.subscription.Subscription;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.ws.rs.Produces;

/**
 * Owner Controller for Consumers and Companies Logic Actions
 * 
 * @author creimers
 * @author kschrock
 */
@RestController
public class OwnerController {

	@Autowired
	OwnerRepository ownersRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	PrizeRepository prizeRepository;

	private final Logger logger = LoggerFactory.getLogger(OwnerController.class);

	/**
	 * This method creates and adds a prize to the Prize Repository. THIS IS A POST
	 * METHOD, Path = /prize/add
	 * 
	 * @return HashMap<String, String> This returns JSON data of "verify", "Added".
	 */
	@RequestMapping(value = "/addPrize", method = RequestMethod.POST)
	public HashMap<String, String> createPrize(@RequestBody Prize newprize) {
		HashMap<String, String> map = new HashMap<>();
		System.out.println(this.getClass().getSimpleName() + " - Create new Prize method is invoked.");
		// print to the console
		prizeRepository.save(newprize);
		// save the prize
		prizeRepository.flush();
		// update the repos
		map.put("verify", "Added");
		// update return value

		return map;

	}

	/**
	 * This method finds all the Prize objects within the Prize Repository. THIS IS
	 * A GET METHOD, Path = /prize FOR TESTING PURPOSES ONLY(?)
	 * 
	 * @return List<Prize> This returns the list of prizes within the Repository.
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/getAll/Prizes")
	public List<Prize> getAllCompanies() {
		logger.info("Entered into Controller Layer");
		List<Prize> results = prizeRepository.findAll();
		logger.info("Number of Records Fetched:" + results.size());
		return results;
	}

	/**
	 * This method subscribes a user to a company. THIS IS A POST METHOD, Path =
	 * /owners/subscribe
	 * 
	 * @return HashMap<String, String> This returns JSON data of "verify",
	 *         "Subscribed".
	 */
	@RequestMapping(value = "/owners/subscribe/{username}/{companyUsername}", method = RequestMethod.POST)
	public HashMap<String, String> subscribeToCompany(@PathVariable("companyUsername") String cmpUserName,
			@PathVariable("username") String username) {
		HashMap<String, String> map = new HashMap<>();
		// create hash map for return value
		System.out.println(this.getClass().getSimpleName() + " - Subscribe method is invoked.");
		// print to the console

		Owner currentOwner = ownersRepository.findOwnerByUsername(username);
		// find the current Owner in the repo
		Company company = companyRepository.findCompanyByUsername(cmpUserName);
		// find the company in the Repo
		for (Subscription subscription : company.getSubscriptions()) {
			// iterate through all the subscriptions
			if (subscription.getOwner().getId() == currentOwner.getId()) {
				// this checks the User if they are already subscripted
				map.put("verify", "Already Subscribed");
				return map;
			}
		}
		Owner owner = ownersRepository.findById(currentOwner.getId()).get();

		Subscription subscription = new Subscription();
		// this creates a new subscription
		subscription.setCompany(company);
		// this sets the subscription Company
		subscription.setOwner(owner);
		// this sets the subscription Owner
		owner.getSubscriptions().add(subscription);
		// this adds the subscription to Owner

		company.getSubscriptions().add(subscription);
		// this adds the subscription to Company
		ownersRepository.save(owner);
		// save to Repo
		companyRepository.save(company);
		// save to Repo
		/* end subscription logic */
		map.put("verify", "Subscribed");
		// update return value
		return map;

	}
	
	/**
	 * This method is for creating a Prize IS A POST METHOD, Path =
	 * /redeem/{companyName}/{prizeName}/{username}/{Quantity}
	 * 
	 * @param String username, String companyName, String Quantity
	 * @return Hash map
	 */
	@RequestMapping(value = "/redeem/{companyName}/{prizeName}/{username}/{Quantity}", method = RequestMethod.POST)
	public HashMap<String, String> redeemPrizes(@PathVariable("companyName") String companyName,
			@PathVariable("prizeName") String prizeName, @PathVariable("username") String username,
			@PathVariable("Quantity") String Quantity) {

		username = username.toString().trim();
		// given usersname
		HashMap<String, String> map = new HashMap<>();
		// create hash map for return value
		int quantity = Integer.parseInt(Quantity);
		// quantity to buy
		int pointsCost = prizeRepository.findPrizeByPrizename(prizeName).getCost();
		// this finds the point cost of the prize found in the Repository
		Owner currentUser = ownersRepository.findOwnerByUsername(username);
		// find the user in the repository

		if (username.toString().trim().equals(currentUser.getUsername())) {
			// this matches the given username to the current User Username

			for (Subscription subscription : currentUser.getSubscriptions()) {
				// iterate through all subscriptions for the current User
				if (subscription.getCompany().getCompanyName().trim().equals(companyName.trim())) {
					// this checks current Company Name matches the given company name.
					double totalCost = 0;
					// total cost of the prize redemption
					double discount = 1;
					// discount
					if (subscription.getCompany().getPaidStatus() == true) {
						// this checks the paid status of the current Company Subscription of the User
						discount = prizeRepository.findPrizeByPrizename(prizeName).getDiscount();
						// this finds the discount for the current Prize since they paid
						discount = discount * quantity;
						// apply for all the items redeeming
						totalCost = pointsCost * quantity - discount;
						// get the total cost by subtracting the points off from the total
					}
					if (subscription.getCompany().getPaidStatus() == false) {
						// this checks the paid status of the current Company Subscription of the User
						totalCost = pointsCost * quantity;
						// since the paid status is false, no discount
						// and find toal cost
					}
					System.out.println(totalCost);
					// print to the console log
					String output = "Not Enough " + subscription.getCompany().getCompanyName() + " Points";
					if (subscription.getpoints() - totalCost < 0) {
						// this checks if the user can purchase the prize
						map.put("verify", output);
						// return message of not Enough COMPANYNAME points
						return map;
					}
					if (prizeRepository.findPrizeByPrizename(prizeName).getQty() - quantity < 0) {
						// this checks if there are any prizes lefts
						map.put("verify", "Not Enough Prizes Left");
						return map;
					}
					System.out.println(subscription.getpoints() - totalCost + "MATH");
					double leftOverPoints = subscription.setPoints(subscription.getpoints() - totalCost);
					// this finds the points left over after purchase
					System.out.println(leftOverPoints + "Left Over");
					// output to console
					subscription.setPoints(leftOverPoints);
					// update the subscriptions left over points
					String userAddress = "You have " + quantity + " " + prizeName + ". Being sent to "
							+ currentUser.getAddress();
					// send userAddress
					map.put("verify", userAddress);
					ownersRepository.flush();
					// updates changes
					companyRepository.flush();
					// updates changes

					int origQty = prizeRepository.findPrizeByPrizename(prizeName).getQty();
					// find the original prize quantity
					prizeRepository.findPrizeByPrizename(prizeName).setQty(origQty - quantity);
					// update the prize quantity
					prizeRepository.flush();
					// update change to qty

					return map;
				}

			}

		}

		map.put("verify", "NotFound");
		return map;

	}

	/**
	 * This method creates and add a User to the Owners Repository. THIS IS A POST
	 * METHOD, Path = /owners/add
	 * 
	 * @return HashMap<String, String> This returns JSON data of "verify", "Added".
	 */
	@RequestMapping(value = "/owners/add", method = RequestMethod.POST)
	public HashMap<String, String> createEmployee(@RequestBody Owner newUser) {
		HashMap<String, String> map = new HashMap<>();
		// create hash map for return value

		if (ownersRepository.findOwnerByUsername(newUser.getUsername()) != null) {
			// user is already found in the repo
			map.put("verify", "Already Exists");
			// update json return value for the map
			return map;
		}
		if (ownersRepository.findOwnerByUsername(newUser.getUsername()) == null) {
			// this checks for duplicates. It will not add anything if the user exists
			ownersRepository.save(newUser);
			// user is not found in the repo, so the we save it to the repo
			map.put("verify", "Added");
			// update json return value for the map
		}
		System.out.println(this.getClass().getSimpleName() + " - Create new User method is invoked.");
		// print to the console
		ownersRepository.flush();
		// update the ownersRepository
		return map;

	}

	/**
	 * This method adds the given points to the given user. This searches through
	 * the Repository to find the user and give them the given amount of points to
	 * them and updating the Repository. THIS IS A POST METHOD, Path =
	 * /owners/addpoints/{points}/{username}
	 * 
	 * @param int    Points
	 * @param String Username
	 * @return HashMap<String, String> This returns JSON data of "verify", "Added"
	 *         || "verify", "NotFound".
	 */
	@RequestMapping(value = "/owners/addpoints/{points}/{company}/{username}", method = RequestMethod.POST)
	public HashMap<String, String> addPoints(@PathVariable("points") int points,
			@PathVariable("company") String company, @PathVariable("username") String username) {
		username = username.toString().trim();
		// given usersname
		HashMap<String, String> map = new HashMap<>();
		// create hashmap for return value
		Owner currentUser = ownersRepository.findOwnerByUsername(username);
		// find the user in the repository

		if (username.toString().trim().equals(currentUser.getUsername())) {
			// match the given username to the current owner
			for (Subscription subscription : currentUser.getSubscriptions()) {
				// iterate through all the subscriptions for the Current User
				if (subscription.getCompany().getCompanyName().trim().equals(company.trim())) {
					// match the current Company to to the given company
					double temp = 0;
					// temp variable
					double currentPoints;
					// current Points for the current Company

					try {
						currentPoints = subscription.getpoints();
						// get the current points and give it to current Points
					} catch (NumberFormatException e) {
						currentPoints = 0;
						// not found
					}
					temp = currentPoints + points;
					// add total points
					subscription.setPoints(temp);
					// update the companys current Points
					map.put("verify", "addedPoints!!");
					// update return value
					ownersRepository.flush();
					// updates changes
					companyRepository.flush();
					return map;
				}
			}

			ownersRepository.flush(); // updates changes
			return map;

		}

		map.put("verify", "NotFound");
		return map;

	}

	/**
	 * This method finds all the Owner objects within the Owner Repository. THIS IS
	 * A GET METHOD, Path = /owners
	 * 
	 * @return List<Owners> This returns the list of owners within the Repository.
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/owners")
	public List<Owner> getAllOwners() {
		logger.info("Entered into Controller Layer");
		List<Owner> results = ownersRepository.findAll();
		logger.info("Number of Records Fetched:" + results.size());
		return results;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/owners/{username}/{password}/paid")
	public boolean userPaysApp(@PathVariable("username") String username, @PathVariable("password") String password) {
		username = username.toString().trim();
		// given usersname
		password = password.toString().trim();
		// given password
		Owner currentUser = ownersRepository.findOwnerByUsername(username);
		// find the user in the repository

		if (currentUser.getUsername().trim().equals(username) && currentUser.getpassword().trim().equals(password)) {
			// match the given username and password to the current owner
			currentUser.setPaid("true");
			ownersRepository.flush();
			// update repo
			return true;
			// return value

		}

		return false;
		// return false

	}

	/**
	 * This method is for a User to Pay company. This takes a username and password
	 * then changes the paidstatus of this user's company. IS A GET METHOD, Path =
	 * /owners/{username}/{password}/{company}/paid
	 * 
	 * @param String username, String password
	 * @return boolean
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/owners/{username}/{password}/{company}/paid")
	public boolean userPaysCompany(@PathVariable("username") String username, @PathVariable("password") String password,
			@PathVariable("company") String company) {
		username = username.toString().trim();
		// given usersname
		password = password.toString().trim();
		// given password
		Owner currentUser = ownersRepository.findOwnerByUsername(username);
		// find the user in the repository
		if (currentUser.getUsername().trim().equals(username) && currentUser.getpassword().trim().equals(password)) {
			// match the given username and password to the current owner
			for (Subscription subscription : currentUser.getSubscriptions()) {
				// iterate through all current user subscriptions
				if (subscription.getCompany().getCompanyName().trim().equals(company.trim())) {
					// if the current company is the same as given company, set paid status to true.
					subscription.getCompany().setPaidStatus(true);
					ownersRepository.flush();
					// update repo
					companyRepository.flush();
					// update repo
					return true;
				}
			}

		}

		return false;

	}

	/**
	 * This method finds the given Owner and return the current Subscriptions json
	 * array objects. Each Object contains CompanyName, Company Id, and Current
	 * Company Points . IS A GET METHOD, Path = /owners/{username}/findSubscriptions
	 * 
	 * @param String Username
	 * @return Json Array Object
	 */
	@RequestMapping(method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/owners/{username}/findSubscriptions")
	public ResponseEntity<String> findUserSubscriptions(@PathVariable("username") String username)
			throws JSONException {
		username = username.toString().trim();
		Owner currentUser = ownersRepository.findOwnerByUsername(username);
		// this finds the current Owner in Repo
		JSONArray currentSubscriptionArray = new JSONArray();
		// this creates Json array for the subscriptions

		if (currentUser.getUsername().trim().equals(username)) {
			// compare the given username to the current Owner object
			for (Subscription subscription : currentUser.getSubscriptions()) {
				// this iterates through all the subscriptions for the current Owner
				JSONObject companyObject = new JSONObject();
				// create companyObject for each iteration
				companyObject.put("CompanyId", subscription.getCompany().getId());
				// add the Company Id for each iteration
				companyObject.put("Company", subscription.getCompany().getCompanyName());
				// add the Company Name for each iteration
				companyObject.put("CompanyUserPoints", subscription.getpoints());
				// add the Current Company Points for this Current Owner Object each iteration
				currentSubscriptionArray.put(companyObject);
				// add the Current Company Object to the Current Json Subscripton Array
				
			}

		}

		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		// this changes the send type of Spring to send Json data.

		System.out.println(currentSubscriptionArray);
		// output to console log, to make sure everything is correct
		JSONObject subscriptionObject = new JSONObject();
		// create return json object
		subscriptionObject.put("UserSubscriptions", currentSubscriptionArray);
		// name the json array to UserSubscriptions and add the Current Json
		// Subscirption Array

		System.out.println(this.getClass().getSimpleName() + " - Check subscriptions method is invoked.");

		return new ResponseEntity<String>(subscriptionObject.toString(), httpHeaders, HttpStatus.OK);
		// this returns the json object

	}

	/**
	 * This method finds the given Id Owner object within the Owner Repository. THIS
	 * IS A GET METHOD, Path = /owners/{username}
	 * 
	 * @param String Username
	 * @return Owners This returns the single owner by id within the Repository.
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/owners/{username}")
	public Owner findOwnerById(@PathVariable("username") String username) {
		logger.info("Entered into Controller Layer");
		username = username.toString().trim();
		Owner currentUser = ownersRepository.findOwnerByUsername(username);
		// this finds the current Owner in Repo
		if (currentUser.getUsername().trim().equals(username)) {
			// match the given usrename to the current Owner's username
			return currentUser;
			// if matches then return the current Owner Object

		}
		return null; // NOT FOUND
	}

	/**
	 * This method tries to Login with the given Username and Password. It does this
	 * by searching through all Owner Objects within the Owner Repository. THIS IS A
	 * GET METHOD, Path = /owners/login/{username}/{password}
	 * 
	 * @param String Username
	 * @param String Password
	 * @return Map<String, String> This returns "verify", "true" || "verify",
	 *         "false".
	 */
	@RequestMapping(value = "/owners/login/{username}/{password}", method = RequestMethod.GET)
	public Map<String, String> loginOwner(@PathVariable("username") String username,
			@PathVariable("password") String password) {
		username = username.toString().trim();
		password = password.toString().trim();
		Owner currentUser = ownersRepository.findOwnerByUsername(username);
		// this finds the current Owner in Repo
		HashMap<String, String> map = new HashMap<>();
		// this is the return json data
		String currentUsername = currentUser.getUsername().toString().trim();
		// current Username to match
		String currentPassword = currentUser.getpassword().toString().trim();
		// current Password to match
		if (username.equals(currentUsername) && password.equals(currentPassword)) {
			// this compares the inputs if they are correct.
			map.put("verify", "true");
			// correctly logins
			return map;
		}

		map.put("verify", "false");
		// unsuccessful login
		return map;
	}

	/**
	 * This method deletes all the Owner objects within the Owner Repository. THIS
	 * IS A POST METHOD, Path = /owners/deleteall
	 * 
	 * @return void
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/owners/deleteall")
	public void deleteAll() {
		System.out.println(this.getClass().getSimpleName() + " - Delete all employees is invoked.");
		//print to the console
		ownersRepository.deleteAll();
		//delete all the owners in the repository
	}

	/**
	 * This method deletes the ID Owner object within the Owner Repository. THIS IS
	 * A POST METHOD, Path = /owners/delete/{id}
	 * 
	 * @param int ID
	 * @return void
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/owners/delete/{id}")
	public void deleteEmployeeById(@PathVariable int id) throws Exception {
		System.out.println(this.getClass().getSimpleName() + " - Delete employee by id is invoked.");
		//print to the console

		Owner emp = (Owner) ownersRepository.findById(id).get();
		//find the owner by ID
		ownersRepository.deleteById(id);
		//delete owner by ID
	}

}
