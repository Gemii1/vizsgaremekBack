package hu.fitness.service;

import hu.fitness.converter.LoginConverter;
import hu.fitness.domain.Login;
import hu.fitness.dto.LoginRead;
import hu.fitness.dto.LoginSave;
import hu.fitness.exception.LoginNotFoundException;
import hu.fitness.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    public LoginRead readLogin(int id) {
        if (!loginRepository.existsById(id)) {
            throw new LoginNotFoundException();
        }
        Login login = loginRepository.getReferenceById(id);
        return LoginConverter.convertModelToRead(login);
    }

    public List<LoginRead> listLogins() {
        List<LoginRead> contactList;
        List<Login> logins = loginRepository.findAll();
        contactList = LoginConverter.convertModelsToList(logins);
        return contactList;
    }

    public LoginRead createLogin(LoginSave loginSave) {
        Login login = LoginConverter.convertSaveToModel(loginSave);
        Login savedLogin = loginRepository.save(login);
        return LoginConverter.convertModelToRead(savedLogin);
    }

    public LoginRead deleteLogin(int id) {
        if (!loginRepository.existsById(id)) {
            throw new LoginNotFoundException();
        }
        Login login = loginRepository.getReferenceById(id);
        loginRepository.delete(login);
        return LoginConverter.convertModelToRead(login);
    }
}
