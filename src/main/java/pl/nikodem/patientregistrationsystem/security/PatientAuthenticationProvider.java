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
import pl.nikodem.patientregistrationsystem.service.PatientService;

@Component
public class PatientAuthenticationProvider implements AuthenticationProvider {
    private final PatientService patientService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PatientAuthenticationProvider(PatientService patientService, PasswordEncoder passwordEncoder) {
        this.patientService = patientService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails patient = patientService.loadUserByUsername(username);

        if (patient != null) {
            if (passwordEncoder.matches(password, patient.getPassword()))
                return new UsernamePasswordAuthenticationToken(username, password, ApplicationUserRole.PATIENT.getAuthorities());
            else throw new BadCredentialsException("Incorrect password for user " + patient.getUsername());
        }
        else throw new UsernameNotFoundException("Username " + username + " not found");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
