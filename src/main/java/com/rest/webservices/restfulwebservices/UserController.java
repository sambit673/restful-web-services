package com.rest.webservices.restfulwebservices;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {

	@Autowired
	private UserDaoComponent service;

	@GetMapping("/users")
	public List<User> allUsers() {

		return service.findAll();

	}

	@GetMapping("/users/{id}")
	public User oneUser(@PathVariable int id) {

		User user = service.findOne(id);
		if (user == null)
			throw new UserNotFoundException("id - " + id);

		// all-users,server path,users
		// retrieveAllUsers
	    

		return user;
	}

	@PostMapping("/users")
	public ResponseEntity<Object> adduser(@Valid @RequestBody User user) {

		User savedUser = service.save(user);

		// created
		// user/{id} // savedUser.getId()
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();

	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {

		User user = service.deleteById(id);

		if (user == null)
			throw new UserNotFoundException("id - " + id);

	}

}
