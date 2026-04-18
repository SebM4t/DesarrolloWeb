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
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    var rutas = rutaService.getRutas(); 

    http.authorizeHttpRequests(request -> {
        // 1. ABRIR RUTAS TÉCNICAS (Esto es lo que te falta)
        // loginProcessingUrl debe ser accesible para que Spring reciba los datos
        request.requestMatchers("/login", "/loginProcessing", "/registro/**", "/error/**").permitAll();
        request.requestMatchers("/css/**", "/js/**", "/img/**", "/webjars/**").permitAll();
        request.requestMatchers("/").permitAll(); 

        // 2. REGLAS DINÁMICAS
        for (Ruta ruta : rutas) {
            // Saltamos la raíz y el login para que no se bloqueen por accidente
            if (ruta.getRuta().equals("/") || ruta.getRuta().equals("/login")) continue;

            if (ruta.isRequiereRol() && ruta.getRol() != null) {
                // IMPORTANTE: Usar hasAuthority porque tu Service ya pone "ROLE_"
                request.requestMatchers(ruta.getRuta()).hasAuthority("ROLE_" + ruta.getRol().getRol());
            } else {
                request.requestMatchers(ruta.getRuta()).permitAll();
            }
        }
        
        request.anyRequest().authenticated();
    });

    http.formLogin(form -> form
            .loginPage("/login")
            .loginProcessingUrl("/login") // Este POST debe coincidir con el th:action del HTML
            .defaultSuccessUrl("/", true)
            .failureUrl("/login?error=true")
            .permitAll()
    );

    // Desactiva CSRF momentáneamente solo para probar si este es el bloqueo
    http.csrf(csrf -> csrf.disable()); 

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
