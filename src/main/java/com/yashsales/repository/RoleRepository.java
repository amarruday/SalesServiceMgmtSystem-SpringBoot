package com.yashsales.repository;

import com.yashsales.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	public Role findByRolename(String rolename);
}
