package com.example.dao;

import com.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long>{
	//收藏
	@Query(value = "select * from user where collect = '是'",nativeQuery = true)
	Page<User> findByCollect(String collect, PageRequest pageRequest);

	@Query(value = "select*from user where name like %?1%", nativeQuery = true)
	Page<User> findNameLike(String name, PageRequest pageRequest);
}
