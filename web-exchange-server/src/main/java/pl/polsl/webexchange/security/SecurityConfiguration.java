package pl.polsl.webexchange.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.polsl.webexchange.WebExchangeProperties;
import pl.polsl.webexchange.user.Role;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final WebExchangeProperties webExchangeProperties;
    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/auth/login").permitAll()
                .antMatchers("/api/auth/refresh").authenticated()
                .antMatchers("/api/register/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/currencies/**").permitAll()
                .antMatchers("/api/currencyRates/**").permitAll()
                .antMatchers("/api/currencies/configuration").hasAuthority(Role.ROLE_ADMIN.name())
                .antMatchers("/api/**").hasAuthority(Role.ROLE_USER.name())
                .anyRequest().permitAll()
                .and()
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userDetailsService, webExchangeProperties.getJwtConfig().getSecret()))
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
