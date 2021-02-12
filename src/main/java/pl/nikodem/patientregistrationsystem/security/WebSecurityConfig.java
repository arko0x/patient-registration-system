package pl.nikodem.patientregistrationsystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static pl.nikodem.patientregistrationsystem.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PatientAuthenticationProvider patientAuthenticationProvider;
    private final DoctorAuthenticationProvider doctorAuthenticationProvider;

    @Autowired
    public WebSecurityConfig(PatientAuthenticationProvider patientAuthenticationProvider, DoctorAuthenticationProvider doctorAuthenticationProvider) {
        this.patientAuthenticationProvider = patientAuthenticationProvider;
        this.doctorAuthenticationProvider = doctorAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(patientAuthenticationProvider);
        auth.authenticationProvider(doctorAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/patient/register", "/patient/register/**").anonymous()
                .antMatchers("/doctor/register", "/doctor/register/**").anonymous()
                .antMatchers("/doctor/**").hasRole(DOCTOR.name())
                .antMatchers("/patient/**").hasRole(PATIENT.name())
                .antMatchers("/").permitAll()
                .and()
                .formLogin();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
