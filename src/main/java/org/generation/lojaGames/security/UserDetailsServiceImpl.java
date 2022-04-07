package org.generation.lojaGames.security;

import java.util.Optional;

import org.generation.lojaGames.model.Usuario;
import org.generation.lojaGames.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service 
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername (String userName) throws UsernameNotFoundException{// busca pelo usuário no banco de dados
		Optional<Usuario>usuario = usuarioRepository.findByUsuario(userName);
		usuario.orElseThrow(()-> new UsernameNotFoundException(userName + "Este usuário não foi encontrado"));
		return usuario.map(UserDetailsImpl::new).get();//map é usado pois pode vir uma única resposta ou um array de usuários. :: atribui no UserDetailsImpl (o userName e password)
	}
	
	
}
	