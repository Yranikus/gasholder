package com.example.gasholder.configuration;


import com.example.gasholder.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDao userDao;

    @Override
    public void configure(WebSecurity web) throws Exception {
        StrictHttpFirewall firewall = new StrictHttpFirewall();

        firewall.setAllowUrlEncodedSlash(true);
        firewall.setAllowBackSlash(true);
        firewall.setAllowUrlEncodedPercent(true);
        firewall.setAllowUrlEncodedPeriod(true);
        firewall.setAllowSemicolon(true);

        // Allow UTF-8 values
        Pattern allowed = Pattern.compile("[\\p{IsAssigned}&&[^\\p{IsControl}]]*");
        firewall.setAllowedHeaderValues((header) -> {
            String parsed = new String(header.getBytes(ISO_8859_1), UTF_8);
            return allowed.matcher(parsed).matches();
        });

        web.httpFirewall(firewall);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/rest/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/map").hasRole("обыватель")
                .antMatchers("/update/**").hasRole("aдмин")
                .antMatchers("/resources/css/{style}.css").permitAll()
                .antMatchers("/resources/css/{style}.png").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/l").permitAll().and()
                .formLogin(form -> form.loginPage("/l").defaultSuccessUrl("/").loginProcessingUrl("/login")
                .usernameParameter("login").passwordParameter("password")).logout().deleteCookies("JSESSIONID", "workshop").permitAll()
                .and().csrf().disable();
    }


    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailServiceImpl(userDao)).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }


}
