package com.student.config;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.student.entity.User;





public class CustomUserDetails implements UserDetails{

	 private String login;
	    private String password;
	    private Collection<? extends GrantedAuthority> grantedAuthorities;

	    public static CustomUserDetails fromUserEntityToCustomUserDetails(User user) {
	        CustomUserDetails c = new CustomUserDetails();
	        c.login = user.getLogin();
	        c.password = user.getPassword();
	        c.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
	        //System.out.println("***********"+user.getRole()+"*************");
	        return c;
	    }

	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return grantedAuthorities;
	    }

	    @Override
	    public String getPassword() {
	        return password;
	    }

	    @Override
	    public String getUsername() {
	        return login;
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }

}
