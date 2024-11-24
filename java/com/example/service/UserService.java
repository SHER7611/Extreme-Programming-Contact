package com.example.service;

import com.example.dao.UserDao;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public void save(User user) {
		userDao.save(user);
	}

	public void del(Long id) {
		userDao.deleteById(id);
	}

	public User findById(Long id) {
		return userDao.findById(id).orElse(null);
	}

	public Page<User> findPage(Integer pageNum, Integer pageSize, String name) {
		Sort sort = Sort.by(Sort.Direction.DESC, "create_time");
		PageRequest request = PageRequest.of(pageNum - 1, pageSize, sort);
		return userDao.findNameLike(name, request);
	}

	public Page<User> findByCollect(Integer pageNum, Integer pageSize, String collect) {
		Sort sort = Sort.by(Sort.Direction.DESC, "create_time");
		PageRequest request = PageRequest.of(pageNum - 1, pageSize, sort);
		return userDao.findByCollect(collect, request);
	}

	/**
	 * 批量导入用户，不删除原有数据。
	 */
	@Transactional
	public void importUsers(List<User> users) {
		for (User user : users) {
			// 检查邮箱是否存在，避免重复数据
			User existingUser = userDao.findByEmail(user.getEmail());

			if (existingUser != null) {
				// 仅更新需要修改的字段，避免不必要的数据库操作
				boolean isUpdated = false;

				if (!existingUser.getName().equals(user.getName())) {
					existingUser.setName(user.getName());
					isUpdated = true;
				}
				if (!existingUser.getAge().equals(user.getAge())) {
					existingUser.setAge(user.getAge());
					isUpdated = true;
				}
				if (!existingUser.getSex().equals(user.getSex())) {
					existingUser.setSex(user.getSex());
					isUpdated = true;
				}
				if (user.getCollect() != null && !existingUser.getCollect().equals(user.getCollect())) {
					existingUser.setCollect(user.getCollect());
					isUpdated = true;
				}

				// 如果有更新的字段，才执行保存操作
				if (isUpdated) {
					userDao.save(existingUser);
				}
			} else {
				// 如果是新用户，直接添加到数据库，默认收藏状态为“否”
				if (user.getCollect() == null) {
					user.setCollect("否");
				}
				userDao.save(user);
			}
		}
	}
}
