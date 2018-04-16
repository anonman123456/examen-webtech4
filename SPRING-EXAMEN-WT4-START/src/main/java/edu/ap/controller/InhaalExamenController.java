package edu.ap.controller;

import edu.ap.spring.model.InhaalExamen;
import edu.ap.spring.redis.RedisService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InhaalExamenController {

	private RedisService service;

	@Autowired
	public void setRedisService(RedisService service) {
		this.service = service;
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity newInhaalExamen(@RequestParam("student") String student, @RequestParam("exam") String examen,
			@RequestParam("reason") String reden) {
		LocalDateTime datum = LocalDateTime.now();
		String key = student + examen + reden;

		// validatie bestaat al?
		Map<Object, Object> exists = service.hgetAll(key);
		if (exists.size() == 0) {
			// schrijf naar database
			Map<String, String> map = new HashMap<>();
			map.put("student", student);
			map.put("exam", examen);
			map.put("reason", reden);
			map.put("date", datum.toString());
			service.hset(student + examen + reden, map);
			return ResponseEntity.ok(HttpStatus.OK);
		} else {
			return ResponseEntity.ok(HttpStatus.I_AM_A_TEAPOT);
		}
	}
}
