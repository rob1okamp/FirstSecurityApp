package ru.amang.SpringSecurity.FirstSecurityApp.config;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.amang.SpringSecurity.FirstSecurityApp.services.PersonDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

//    protected void configure(HttpSecurity http) throws Exception{
//        http.csrf(csrf->csrf.disable())
//                .authorizeHttpRequests(authorize->authorize
//                        .requestMatchers("/auth/login","/error").permitAll()
//                        .anyRequest().authenticated())
//                        .formLogin(formLogin->formLogin.loginPage("/auth/login")
//                        .loginProcessingUrl("process_login")
//                        .defaultSuccessUrl("/hello",true)
//                        .failureUrl("auth/login?error").permitAll());
//    }

    protected void configure (AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(personDetailsService);

    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(authorize->authorize
                        .requestMatchers("/auth/login","/error").permitAll()
                        .anyRequest().authenticated())
                        .formLogin(formLogin->formLogin.loginPage("/auth/login")
                        .loginProcessingUrl("process_login")
                        .defaultSuccessUrl("/hello",true)
                        .failureUrl("auth/login?error").permitAll());
        return http.build();
    }


    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
