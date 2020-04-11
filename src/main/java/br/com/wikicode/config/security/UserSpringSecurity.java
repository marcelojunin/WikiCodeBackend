package br.com.wikicode.config.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.wikicode.domain.enums.Profile;

public class UserSpringSecurity implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public UserSpringSecurity(String id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public UserSpringSecurity(String id, String username, String password, Set<String> profiles) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = profiles
								.stream()
								.map(profile -> new SimpleGrantedAuthority(profile))
								.collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	public String getId() {
		return id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
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
	
	public boolean hasRole(Profile profile) {
		return getAuthorities().contains(new SimpleGrantedAuthority(profile.getDescription()));
	}

}