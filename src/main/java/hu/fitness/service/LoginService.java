package hu.fitness.service;

import hu.fitness.auth.PermissionCollector;
import hu.fitness.domain.Login;
import hu.fitness.exception.LoginNotFoundException;
import hu.fitness.repository.LoginRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class LoginService implements UserDetailsService {

    private final LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public List<String> findPermissionsByUser(Integer id) {
        return loginRepository.findPermissionsByUser(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loginRepository.findLoginByEmail(username)
                .map(PermissionCollector::new)
                .orElseThrow(() -> new UsernameNotFoundException("Felhasználó nem található: " + username));
    }


    public Login findLoginByEmail(final String email) {
        return loginRepository.findLoginByEmail(email)
                .orElseThrow(LoginNotFoundException::new);
    }
}