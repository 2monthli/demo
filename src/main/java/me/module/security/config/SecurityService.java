package me.module.security.config;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import me.module.security.service.UserService;

/**
 * 用户身份认证服务类
 * 
*/
@Service("userDetailsService")
public class SecurityService implements UserDetailsService {
	@Resource
	UserService userService;
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		try {
			UserDetails userDetails = userService.findByUsername(name);
            if(userDetails != null) {
                return userDetails;
            } else {
                throw new UsernameNotFoundException("该用户不存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
	}

}
