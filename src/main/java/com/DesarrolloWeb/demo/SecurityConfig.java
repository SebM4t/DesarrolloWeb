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
        var rutas = rutaService.getRutas(); //recupera la tabla de rutas completa
        http.authorizeHttpRequests(request -> {
            for (Ruta ruta : rutas) {
                if (ruta.isRequiereRol()) {
                    request.requestMatchers(ruta.getRuta()).hasRole(ruta.getRol().getRol());
                }else{
                    request.requestMatchers(ruta.getRuta()).permitAll();
                }
            }
        
                request.anyRequest().authenticated();
        }
        );
       
        
        http.formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true").permitAll()
        ).logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID").permitAll()
        ).exceptionHandling(ex -> ex.accessDeniedPage("/acceso_denegado")
        ).sessionManagement(session -> session.maximumSessions(1).maxSessionsPreventsLogin(false)
        );

        return http.build();
    }

//    public static final String[] PUBLIC_URLS = {
//        "/", "/index", "/fav/**", "/consultas/**", "/js/**", "/webjars/**", "/login",
//        "/registro/**"
//    };
//
//    public static final String[] USUARIO_URLS = {
//        "/facturar/carrito"
//    };
//
//    public static final String[] VENDEDOR_O_ADMIN_URLS = {
//        "/producto/listado", "/categoria/listado"
//    };
//    public static final String[] ADMIN_URLS = {
//        "/producto/**", "/categoria/**"
//    };
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http)
//            throws Exception {
//        http.authorizeHttpRequests(request -> request
//                .requestMatchers(PUBLIC_URLS).permitAll()
//                .requestMatchers(USUARIO_URLS).hasRole("USUARIO")
//                .requestMatchers(VENDEDOR_O_ADMIN_URLS).hasAnyRole("VENDEDOR", "ADMIN")
//                .requestMatchers(ADMIN_URLS).hasRole("ADMIN")
//                .anyRequest().authenticated()
//        ).formLogin(form -> form
//                .loginPage("/login")
//                .loginProcessingUrl("/perform_login")
//                .defaultSuccessUrl("/", true)
//                .failureUrl("/login?error=true")
//                .permitAll()
//        ).logout(logout -> logout
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout=true")
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .permitAll()
//        ).exceptionHandling(ex -> ex.accessDeniedPage("/acceso_denegado")
//        ).sessionManagement(session -> session
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(false)
//        );
//
//        return http.build();
//    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService users(PasswordEncoder passwordEncoder) {
//        UserDetails andres = User.builder()
//                .username("andres")
//                .password(passwordEncoder().encode("123"))
//                .roles("ADMIN")
//                .build();
//        UserDetails laura = User.builder()
//                .username("laura")
//                .password(passwordEncoder().encode("456"))
//                .roles("VENDEDOR")
//                .build();
//        UserDetails carlos = User.builder()
//                .username("carlos")
//                .password(passwordEncoder().encode("789"))
//                .roles("USUARIO")
//                .build();
//        return new InMemoryUserDetailsManager(andres, laura, carlos);
//
//    }
    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build,
            @Lazy PasswordEncoder passwordEncoder,
            @Lazy UserDetailsService userDetailsService)
            throws Exception {
        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

}
