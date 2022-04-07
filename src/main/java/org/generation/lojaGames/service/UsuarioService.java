package org.generation.lojaGames.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.lojaGames.model.UsuarioLogin;
import org.generation.lojaGames.model.Usuario;
import org.generation.lojaGames.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {
	
	@Autowired // para importar as coisas do usuarioRepository
	private UsuarioRepository usuarioRepository;
	
	public Optional<Usuario>cadastrarUsuario(Usuario usuario){ //A função recebe um objeto do tipo usuário //é publico para  o usuario conseguir ter a rota de cadastro, ser acessado pelo controller
		
		if(usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) { //é criado uma validação para cadastrar o usuário. tem um usuario igual a este no banco de dados?
		return Optional.empty(); // se estiver vai retomar um objeto vazio não deixando eu recadastrar
		}
		
		usuario.setSenha(criptografarSenha(usuario.getSenha()));//dentro de objeto usuario, a senha será pegada, e  antes de mandar pro banco de dados a senha será criptografada
		
		return Optional.of(usuarioRepository.save(usuario));// não está presente no banco, então será salvo/cadastrado
	}
	public Optional <UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin){
		Optional<Usuario>usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario()); // verifica se o usuario existe
		if(usuario.isPresent()) {
			if(compararSenhas(usuarioLogin.get().getSenha(),usuario.get().getSenha())) {// comparando a senha digitada com a senha do banco de dados
				usuarioLogin.get().setId(usuario.get().getId());// pego(get) os valores do objeto usuário e seto para usuarioLogin
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				usuarioLogin.get().setToken(geradorBasicToken(usuarioLogin.get().getUsuario(),usuarioLogin.get().getSenha()));
				usuarioLogin.get().setSenha(usuario.get().getSenha());
				return usuarioLogin;
			}
		}
		return Optional.empty();//retorna vazio se o usuário não existir no banco de dados
	}
	
	public Optional<Usuario> atualizarUsuario(Usuario usuario){//se o id estiver presente, ele busca 
        if(usuarioRepository.findById(usuario.getId()).isPresent()) {
        	Optional<Usuario>buscarUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());// busca o objeto usuário
        	if(buscarUsuario.isPresent()) {
        		if(buscarUsuario.get().getId()!= usuario.getId())// verificando se o id que trouxe no banco é o mesmo que o id pesquisado
        			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe",null);//http estatus exige que retorne alguma coisa que será o null
        	}
        	usuario.setSenha(criptografarSenha(usuario.getSenha()));//(caso mude a senha) antes de salvar no banco de dados a senha é criptografada
        	return Optional.of(usuarioRepository.save(usuario));// salva no banco
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado",null);
    }
	

	private boolean compararSenhas(String senhaDigitada, String senhaDoBanco ) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(senhaDigitada, senhaDoBanco);
	}
	
	private String geradorBasicToken(String usuario, String senha) {
		String token = usuario + ":" + senha;
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII"))); //passando para binário todas as letras do teclado e depois transformando novamente.
	    return "Basic "+ new String(tokenBase64);//basic é necessário pq estamos usando o BasicSecurity
	}
	
	private String criptografarSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); //BCrypt é responsavel por encriptografar
		return encoder.encode(senha);
	}
	
}
	

