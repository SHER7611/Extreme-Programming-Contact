package com.example.dao;

import com.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

	@Query(value = "select * from user where name like %?1%", nativeQuery = true)
	Page<User> findNameLike(String name, PageRequest pageRequest);

	// 收藏
	@Query(value = "select * from user where collect = ?1", nativeQuery = true)
	Page<User> findByCollect(String collect, PageRequest pageRequest);

	@Query(value = "select * from user where email = ?1", nativeQuery = true)
	User findByEmail(String email);
}
