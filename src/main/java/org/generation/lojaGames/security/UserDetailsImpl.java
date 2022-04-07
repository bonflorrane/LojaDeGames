package org.generation.lojaGames.security;

import java.util.Collection;
import java.util.List;

import org.generation.lojaGames.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails{
	
	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;
	private List<GrantedAuthority> authorities; //informar o tipo de autorização que o usuário tem. Usuário adm

	public UserDetailsImpl(Usuario usuario) {//método construtor
		this.userName = usuario.getUsuario(); // this busca a variavel criada aqui = getusuario indo para model usuário
		this.password = usuario.getSenha();

	}
	
	public UserDetailsImpl() {
	} // testar a segurança, usando só usuario ou senha

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


}
