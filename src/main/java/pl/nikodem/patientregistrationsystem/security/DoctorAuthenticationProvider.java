package pl.nikodem.patientregistrationsystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.nikodem.patientregistrationsystem.doctor.DoctorService;

@Component
public class DoctorAuthenticationProvider implements AuthenticationProvider {
    private final DoctorService doctorService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DoctorAuthenticationProvider(DoctorService doctorService, PasswordEncoder passwordEncoder) {
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
                if (doctor.isEnabled() && doctor.isAccountNonLocked()) {
                    return new UsernamePasswordAuthenticationToken(username, password, ApplicationUserRole.DOCTOR.getAuthorities());
                }
                else if (!doctor.isEnabled())
                    throw new DisabledException("Account disabled");
                else
                    throw new LockedException("Account is locked");
            else throw new BadCredentialsException("Incorrect password for user " + doctor.getUsername());
        }
        else throw new UsernameNotFoundException("Username " + username + " not found");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
