package com.ssms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssms.entity.Department;
import com.ssms.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
	//public Optional<User> findByUsername(String username);
	public List<User> findByDepartment(Department department);
	public List<User> findByManager(User user);
}
