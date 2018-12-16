package me.module.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	@Autowired
    private AuthenticationProvider securityProvider;
	
	@Override
    protected UserDetailsService userDetailsService() {
        //自定义用户信息类
        return this.userDetailsService;
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeRequests()
		    .antMatchers("/hello").hasRole("USER")
			.antMatchers("/user").hasRole("ADMIN")
			.antMatchers("/login","/").permitAll()
			.and()
			.formLogin()
			.loginPage("/login") //如果没配置 则用默认登陆页
			//.successForwardUrl("/hello")
			.successHandler(new AuthenticationSuccessHandler() {
	            @Override
	            public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication arg2)
	                    throws IOException, ServletException {
	                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	                if (principal != null && principal instanceof UserDetails) {
	                    UserDetails user = (UserDetails) principal;
	                    System.out.println("loginUser:"+user.getUsername());
	                    //维护在session中
	                    arg0.getSession().setAttribute("userDetail", user);
	                    arg1.sendRedirect("/hello");
	                } 
	            }
	        })
			//.failureForwardUrl("/fail")
			.failureHandler(new AuthenticationFailureHandler() {
	            @Override
	            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
	                    throws IOException, ServletException {
	                System.out.println("error"+authenticationException.getMessage());
	                response.sendRedirect("/login");
	            }
	        })
			.and()
			.logout()//如果没配置 则会报EOF异常
			.permitAll()
			.and().csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
//		auth.inMemoryAuthentication()
//		.withUser("admin").password("admin").roles("ADMIN","USER")
//		.and()
//		.withUser("test").password("test").roles("USER");
		
		//使用数据库验证
		auth.authenticationProvider(securityProvider);
		
	}

}
