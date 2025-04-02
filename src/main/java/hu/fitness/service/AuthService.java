package hu.fitness.service;

import java.io.InputStream;
import hu.fitness.auth.PermissionCollector;
import hu.fitness.converter.ClientConverter;
import hu.fitness.converter.LoginConverter;
import hu.fitness.converter.TrainerConverter;
import hu.fitness.domain.Client;
import hu.fitness.domain.FileEntity;
import hu.fitness.domain.Login;
import hu.fitness.domain.Trainer;
import hu.fitness.dto.ClientRead;
import hu.fitness.dto.ClientRegisterRequest;
import hu.fitness.dto.TrainerRead;
import hu.fitness.dto.TrainerRegisterRequest;
import hu.fitness.exception.*;
import hu.fitness.repository.ClientRepository;
import hu.fitness.repository.FileRepository;
import hu.fitness.repository.LoginRepository;
import hu.fitness.repository.TrainerRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class AuthService implements UserDetailsService {


    private final LoginRepository loginRepository;
    private final ClientRepository clientRepository;
    private final TrainerRepository trainerRepository;
    private final PasswordEncoder passwordEncoder;
    private final TrainerService trainerService;
    private final FileRepository fileRepository;


    @Autowired
    public AuthService(LoginRepository loginRepository, ClientRepository clientRepository, TrainerRepository trainerRepository, PasswordEncoder passwordEncoder, TrainerService trainerService, FileRepository fileRepository) {
        this.loginRepository = loginRepository;
        this.clientRepository = clientRepository;
        this.trainerRepository = trainerRepository;
        this.passwordEncoder = passwordEncoder;
        this.trainerService = trainerService;
        this.fileRepository = fileRepository;
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

    public ClientRead registerClient(ClientRegisterRequest request) {
        if (loginRepository.existsByEmail(request.getEmail())) {
            throw new EmailTakenException();
        }

        Login login = new Login();
        login.setEmail(request.getEmail());
        login.setPassword(passwordEncoder.encode(request.getPassword()));
        login.setRole("CLIENT");

        Login savedLogin = loginRepository.save(login);

        Client client = new Client();
        client.setLogin(savedLogin);
        client.setName(request.getName());
        client.setBirthDate(request.getBirthDate());
        client.setGender(request.getGender());
        client.setPhoneNumber(request.getPhone());
        client.setPrograms();

        Client savedClient = clientRepository.save(client);

        return ClientConverter.convertModelToRead(savedClient);
    }

    public TrainerRead registerTrainer(TrainerRegisterRequest request) {
        if (loginRepository.existsByEmail(request.getEmail())) {
            throw new EmailTakenException();
        }

        Login login = new Login();
        login.setEmail(request.getEmail());
        login.setPassword(passwordEncoder.encode(request.getPassword()));
        login.setRole("TRAINER");

        Login savedLogin = loginRepository.save(login);

        Trainer trainer = new Trainer();
        trainer.setLogin(savedLogin);
        trainer.setName(request.getName());
        trainer.setPhoneNumber(request.getPhoneNumber());
        trainer.setBirthDate(request.getBirthDate());
        trainer.setGender(request.getGender());
        trainer.setQualification(request.getQualification());
        trainer.setRating(0.0);

        FileEntity fileEntity;
        try {
            InputStream inputStream = getClass().getResourceAsStream("/images/default_image.jpg");
            assert inputStream != null;
            byte[] defaultImage = IOUtils.toByteArray(inputStream);
            fileEntity = new FileEntity("default_image.jpg", "image/jpeg", defaultImage);
        } catch (IOException e) {
            throw new FailedSaveException();
        }

        fileEntity = fileRepository.save(fileEntity);

        trainer.setFileEntity(fileEntity);

        Trainer savedTrainer = trainerRepository.save(trainer);

        return TrainerConverter.convertModelToRead(savedTrainer);
    }
}