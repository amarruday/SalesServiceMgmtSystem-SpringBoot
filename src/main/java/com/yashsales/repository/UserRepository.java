package com.yashsales.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yashsales.entity.Department;
import com.yashsales.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
	//public Optional<User> findByUsername(String username);
	public List<User> findByDepartment(Department department);
	public List<User> findByManager(User user);
}
