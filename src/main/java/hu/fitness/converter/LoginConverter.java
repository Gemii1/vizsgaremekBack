package hu.fitness.converter;

import hu.fitness.domain.Login;
import hu.fitness.dto.LoginSave;
import hu.fitness.dto.LoginRead;

import java.util.ArrayList;
import java.util.List;

public class LoginConverter {

    public static LoginRead convertModelToRead(Login login) {
        LoginRead loginRead = new LoginRead();
        loginRead.setLoginId(login.getId());
        loginRead.setEmail(login.getEmail());
        loginRead.setPassword(login.getPassword());
        return loginRead;
    }

    public static List<LoginRead> convertModelsToList(List<Login> logins) {
        List<LoginRead> loginReads = new ArrayList<>();
        for (Login login : logins) {
            loginReads.add(convertModelToRead(login));
        }
        return loginReads;
    }

    public static Login convertSaveToModel(LoginSave loginSave) {
        Login login = new Login();
        login.setEmail(loginSave.getEmail());
        login.setPassword(loginSave.getPassword());
        return login;
    }
}
