package com.muhardin.endy.belajar.switchuser.belajarswitchuser.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;

import javax.sql.DataSource;

@Slf4j
@Configuration @EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class KonfigurasiSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    private static final String SQL_LOGIN
            = "select p.username, p.hashed_password as password, true as enabled " +
            "from pengguna p where p.username = ?";

    private static final String SQL_ROLE
            = "select p.username, r.nama as authority from s_role r " +
            "inner join pengguna p on p.id_role = r.id "
            + "where p.username = ?";

    @Bean
    public SwitchUserFilter switchUserFilter() {
        try {
            SwitchUserFilter filter = new SwitchUserFilter();
            filter.setUserDetailsService(userDetailsService());
            filter.setSwitchUserUrl("/switchuser/form");
            filter.setExitUserUrl("/switchuser/exit");
            filter.setTargetUrl("/transaksi/list");
            return filter;
        } catch (RuntimeException e) {
            log.info("Unable to switch user filter: {}", e.getMessage());
        }
        return null;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(13);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        try {
            auth
                    .jdbcAuthentication()
                    .dataSource(dataSource)
                    .usersByUsernameQuery(SQL_LOGIN)
                    .authoritiesByUsernameQuery(SQL_ROLE)
                    .passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            log.info("Unable to login SQL: {}", e.getMessage());
        }
    }

    @Override
    protected void configure(HttpSecurity http) {
        try {
            http.authorizeRequests(authorize -> authorize
                .mvcMatchers("/switchuser/exit")
                    .hasAuthority(SwitchUserFilter.ROLE_PREVIOUS_ADMINISTRATOR)
                .mvcMatchers("/switchuser/select", "/switchuser/form")
                    .hasAuthority("Administrator")
                .anyRequest().authenticated()
            )
            .addFilterAfter(switchUserFilter(), FilterSecurityInterceptor.class)
            .logout().permitAll()
            .and().formLogin()
            .defaultSuccessUrl("/transaksi/list", true);
        } catch (Exception e) {
            log.info("Someting wrong in configure: {}", e.getMessage());
        }
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/js/*", "/img/*", "/css/*");
    }
}
