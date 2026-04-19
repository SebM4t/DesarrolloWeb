package com.DesarrolloWeb.demo;

import com.DesarrolloWeb.demo.domain.Ruta;
import com.DesarrolloWeb.demo.service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final RutaService rutaService;

    public SecurityConfig(RutaService rutaService) {
        this.rutaService = rutaService;
    }

@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) 
            throws Exception {
        var rutas = rutaService.getRutas();
        http.authorizeHttpRequests(request -> {
                
                for (Ruta ruta : rutas) {
                    if (ruta.isRequiereRol()){
                        request.requestMatchers(ruta.getRuta()).hasRole(ruta.getRol().getRol());
                    } else {
                        request.requestMatchers(ruta.getRuta()).permitAll();
                    }
                }
                request.anyRequest().authenticated();
        });
http.formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/",true)
                .failureUrl("/login?error=true")
                .permitAll()
        ).logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()   
        ).exceptionHandling(ex -> ex.accessDeniedPage("/acceso_denegado")
        ).sessionManagement(session -> session
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
  
        );
        return http.build();
        
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build,
            @Lazy PasswordEncoder passwordEncoder,
            @Lazy UserDetailsService userDetailsService)
            throws Exception {
        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

}
