package me.module.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import me.module.security.entity.SysUser;

public interface UserRepository extends JpaRepository<SysUser,Long> {

	SysUser findByUsername(String userName);

} 
