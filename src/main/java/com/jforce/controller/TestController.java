package com.jforce.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jforce.entity.Loggins;
import com.jforce.entity.User;
import com.jforce.repository.LogginsRepository;
import com.jforce.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/test")
public class TestController {

	@Autowired
	private UserRepository userRepository;

//	@Autowired
	private LogginsRepository logginsRepository ;

//Test only
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER')")
	public String userAccess() {
		return "User Content. user can login and logout";
	}

	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

	@GetMapping("/user/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<User> getUserDetails(@PathVariable("id") Integer id) {
		Optional<User> _user = userRepository.findById(id);
		if (_user.isPresent()) {
			return new ResponseEntity<>(_user.get(), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/user/{id}/loggin")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Boolean> getUserLogout(@PathVariable("id") Integer id) {
		Optional<User> _user = userRepository.findById(id);
		if (_user.isPresent()) {
			if (logginsRepository.findIfLoginToday(id).isEmpty()) {
				Loggins _log = new Loggins(id, LocalDate.now(), LocalTime.now());
				Loggins _x = logginsRepository.save(_log);
				return new ResponseEntity<>(true, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(true, HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/user/{id}/logout")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Boolean> getUserLoggin(@PathVariable("id") Integer id) {
		Optional<User> _user = userRepository.findById(id);
		if (_user.isPresent()) {
			List<Loggins> _loggins = logginsRepository.findIfLoginToday(id);
			if (_loggins.size() == 0) { // If 0 means not yet loggin true so he can loggin
				return new ResponseEntity<>(true, HttpStatus.OK);
			} else if (_loggins.size() == 1) {
				Loggins _l = logginsRepository.findById(_loggins.get(0).getId()).orElse(null);
				_l.setLogout(LocalTime.now());
				logginsRepository.save(_l);
				return new ResponseEntity<>(false, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

//	@GetMapping("/user/report/{id}")
//	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//	public ResponseEntity<?> getUserReport(@PathVariable("id") Integer id) {
//		Optional<User> _user = userRepository.findById(id);
//		if (_user.isPresent()) {
//			List<Loggins> _loggins = logginsRepository.findByLoggins(id);
//			return new ResponseEntity<>(_loggins, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>("No Such User", HttpStatus.BAD_REQUEST);
//		}
//	}

	@GetMapping("/user/report/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> getUserReport(@PathVariable("id") Integer id,
			@RequestParam(required = false) String date) {

		Optional<User> _user = userRepository.findById(id);
		if (_user.isPresent()) {
			try {
				List<Loggins> loggins = new ArrayList<Loggins>();

				if (date == null) {
					System.out.println("Default is Called");
					loggins = logginsRepository.findByLoggins(id);
//					logginsRepository.findAll().forEach(loggins::add);
				} else {
					// 2023-02-03 added date
					System.out.println("By Date Called");
					logginsRepository.findByDate(id, LocalDate.parse(date)).forEach(loggins::add);
				}
				if (loggins.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
				return new ResponseEntity<>(loggins, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> adminAccess() {
		List<User> _user = userRepository.findAll();
		return new ResponseEntity<>(_user, HttpStatus.OK);
	}

}