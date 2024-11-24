package com.example.controller;

import com.example.common.Result;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public Result add(@RequestBody User user) {
		userService.save(user);
		return Result.success("新增成功");
	}

	@PutMapping
	public Result update(@RequestBody User user) {
		userService.save(user);
		return Result.success("更新成功");
	}

	@DeleteMapping("/{id}")
	public Result delete(@PathVariable Long id) {
		userService.del(id);
		return Result.success("删除成功");
	}

	@GetMapping("/{id}")
	public Result<User> findById(@PathVariable Long id) {
		User user = userService.findById(id);
		if (user == null) {
			return Result.error("404", "用户不存在");
		}
		return Result.success(user);
	}

	@GetMapping("/page")
	public Result<Page<User>> findPage(
			@RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(required = false) String name) {
		Page<User> page = userService.findPage(pageNum, pageSize, name);
		return Result.success(page);
	}

	@GetMapping("/collect")
	public Result<Page<User>> findByCollect(
			@RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(required = false) String collect) {
		Page<User> page = userService.findByCollect(pageNum, pageSize, collect);
		return Result.success(page);
	}

	@PostMapping("/import")
	public Result<String> importUsers(@RequestBody List<User> users) {
		if (users == null || users.isEmpty()) {
			return Result.error("400", "导入失败，数据为空");
		}

		try {
			userService.importUsers(users);
			return Result.success("导入成功");
		} catch (Exception e) {
			return Result.error("500", "导入失败: " + e.getMessage());
		}
	}
}
