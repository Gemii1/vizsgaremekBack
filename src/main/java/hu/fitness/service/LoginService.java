package hu.fitness.service;

import hu.fitness.auth.PermissionCollector;
import hu.fitness.converter.ClientConverter;
import hu.fitness.converter.LoginConverter;
import hu.fitness.converter.TrainerConverter;
import hu.fitness.domain.Login;
import hu.fitness.exception.ClientNotFoundException;
import hu.fitness.exception.LoginNotFoundException;
import hu.fitness.exception.TrainerNotFoundException;
import hu.fitness.repository.ClientRepository;
import hu.fitness.repository.LoginRepository;
import hu.fitness.repository.TrainerRepository;
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
    private final ClientRepository clientRepository;
    private final TrainerRepository trainerRepository;


    @Autowired
    public LoginService(LoginRepository loginRepository, ClientRepository clientRepository, TrainerRepository trainerRepository) {
        this.loginRepository = loginRepository;
        this.clientRepository = clientRepository;
        this.trainerRepository = trainerRepository;
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

    public Object getUserDetailsByEmail(String email) {
        Login login = findLoginByEmail(email);
        Integer id = login.getId();
        String role = login.getRole();
        return switch (role) {
            case "CLIENT" -> ClientConverter.convertModelToRead(clientRepository.getByLoginId(id)
                    .orElseThrow(ClientNotFoundException::new));
            case "TRAINER" -> TrainerConverter.convertModelToRead(trainerRepository.getByLoginId(id)
                    .orElseThrow(TrainerNotFoundException::new));
            default -> LoginConverter.convertModelToRead(login);
        };
    }
}