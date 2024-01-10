package com.ecommerce.admin.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AdminConfiguration {
    @Bean
    public UserDetailsService userDetailsService(){
        return new AdminServiceConfig();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder
                = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/**", "/static/**").permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        // tới form đăng nhập
                        .loginPage("/login")
                        // xử lí đăng nhập ở trang này
                        .loginProcessingUrl("/do-login")
                        // đăng nhập thành công sẽ qua index
                        .defaultSuccessUrl("/index", true)
                        //cho phép tất cả mọi người (cả người dùng chưa đăng nhập)
                        // truy cập trang "/login" và "/do-login" mà không cần xác thực
                        .permitAll()
                )
                .logout(logout -> logout
                        // phiên làm việc của người dùng sẽ bị hủy sau khi họ đăng xuất
                        .invalidateHttpSession(true)
                        // hông tin xác thực (authentication) của người dùng sẽ bị xóa sau khi đăng xuất.
                        .clearAuthentication(true)
                        //: Đây là URL mà ứng dụng của bạn sẽ sử dụng để xử lý yêu cầu đăng xuất
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        //y là URL mà người dùng sẽ được chuyển đến sau khi đăng xuất thành công
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .authenticationManager(authenticationManager)
        ;
        return http.build();
    }

}
