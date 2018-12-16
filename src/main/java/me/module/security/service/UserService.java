package me.module.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.module.security.dao.UserRepository;
import me.module.security.entity.SysUser;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public SysUser findByUsername(String userName) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(userName);
	}

}
