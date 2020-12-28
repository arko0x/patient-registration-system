package pl.nikodem.patientregistrationsystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.nikodem.patientregistrationsystem.service.DoctorDetailsService;

@Component
public class DoctorAuthenticationProvider implements AuthenticationProvider {
    private final DoctorDetailsService doctorService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DoctorAuthenticationProvider(DoctorDetailsService doctorService, PasswordEncoder passwordEncoder) {
        this.doctorService = doctorService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails doctor = doctorService.loadUserByUsername(username);

        if (doctor != null) {
            if (passwordEncoder.matches(password, doctor.getPassword()))
                return new UsernamePasswordAuthenticationToken(username, password, ApplicationUserRole.DOCTOR.getAuthorities());
            else throw new BadCredentialsException("Incorrect password for user " + doctor.getUsername());
        }
        else throw new UsernameNotFoundException("Username " + username + " not found");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
