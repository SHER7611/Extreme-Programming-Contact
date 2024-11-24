package com.example.controller;

import com.example.common.Result;
import com.example.entity.User;
import com.example.service.Userservice;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private Userservice userservice;

	@PostMapping
	public Result add(@RequestBody User user) {
		userservice.save(user);
		return Result.success();
	}

	@PutMapping
	public Result update(@RequestBody User user) {
		userservice.save(user);
		return Result.success();
	}

	@DeleteMapping("/{id}")
	public Result delete(@PathVariable Long id) {
		userservice.del(id);
		return Result.success();
	}

	@GetMapping("/{id}")
	public Result<User> findById(@PathVariable Long id) {

		return Result.success(userservice.findById(id));
	}

	@GetMapping("/page")
	public Result<Page<User>> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
									   @RequestParam(defaultValue = "10") Integer pageSize,
									   @RequestParam(required = false) String name) {

		return Result.success(userservice.findPage(pageNum, pageSize, name));
	}

	@GetMapping("/collect")
	public Result<Page<User>> findPageByCollect(@RequestParam(defaultValue = "1") Integer pageNum,
												@RequestParam(defaultValue = "10") Integer pageSize,
												@RequestParam(required = false) String collect) {

		return Result.success(userservice.findByCollect(pageNum, pageSize, collect));
	}
}
