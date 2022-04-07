package org.generation.lojaGames.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override // para criar mais uma forma de login (usuario em memória  e usuario padrão) // auth é alias apelido para a clase AuthenticationManagerBuilder
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{ // protected a função está acessivel a todos os níeveis de segurança, todos os pacotes de segurança tem acesso ao usuario em memoria
	auth.userDetailsService(userDetailsService);//
	auth.inMemoryAuthentication()//caso queira logar com usuario in Memory também consegue
	.withUser("root")//identificando usuario
	.password(passwordEncoder().encode("root"))//identificando senha
	.authorities("ROLE_USER"); // indica o que  root /root significa na aplicação// é um usuario/senha na minha aplicação. Criando um usuario em memoria para ter um usuario sem cadastro sem acesso ao banco de dados, é para teste.
    
	}
	
	@Bean // = autowired, gestão de dependência, escopo local. Bean escopo global.
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();//encriptação de senha
	}
	
	@Override//permitindo a liberação de rotas para usuário
	protected void configure(HttpSecurity http) throws Exception{//configurando endpoints da aplicação (configuração por http)
			http.authorizeHttpRequests() //  autoriza que as requisições cheguem na aplicação 
			.antMatchers("/usuarios/logar").permitAll()// antMatches independente de onde a requisição vier permitir 
			.antMatchers("/usuarios/cadastrar").permitAll()//antmatcher qq metodo que vier é permitido
			.antMatchers("/usuarios/all").permitAll()
			.antMatchers("/usuarios/atualizar").permitAll()
			.antMatchers(HttpMethod.OPTIONS).permitAll()// qualquer método que vier na aplicação terá uma resposta que pode logar ou que primeiro vc precisa se cadastrar
			.anyRequest().authenticated()// qq outra requisição irá precisar de autenticação
			.and().httpBasic()	//httpBasic dizendo que usa verbo e metodos http
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // toda requição terá começo, meio e fim. deslogando o usuário e o token não valerá mais
			.and().cors() // auxiliar do cross origin. permitindo de qq rota
			.and().csrf().disable();//por padrão não deixa deletar e atualizar
	}
	
}

