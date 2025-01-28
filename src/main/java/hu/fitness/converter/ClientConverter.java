package hu.fitness.converter;

import hu.fitness.domain.Client;
import hu.fitness.domain.Login;
import hu.fitness.dto.ClientList;
import hu.fitness.dto.ClientRead;
import hu.fitness.dto.ClientSave;

import java.util.ArrayList;
import java.util.List;

public class ClientConverter {
    public static List<ClientList> convertModelsToLists(List<Client> clients) {
        List<ClientList> clientLists = new ArrayList<>();
        for (Client client : clients) {
            clientLists.add(convertModelToList(client));
        }
        return clientLists;
    }

    private static ClientList convertModelToList(Client client) {
        ClientList clientList = new ClientList();
        clientList.setId(client.getId());
        clientList.setName(client.getName());
        clientList.setBirthDate(client.getBirthDate());
        clientList.setGender(client.getGender());
        clientList.setPhoneNumber(client.getPhoneNumber());
        clientList.setLogin(LoginConverter.convertModelToRead(client.getLogin()));
        return clientList;
    }

    public static Client convertSaveToModel(ClientSave clientSave, Login login) {
        Client client = new Client();
        client.setName(clientSave.getName());
        client.setBirthDate(clientSave.getBirthDate());
        client.setGender(clientSave.getGender());
        client.setPhoneNumber(clientSave.getPhone());
        client.setLogin(login);
        return client;
    }

    public static ClientRead convertModelToRead(Client client) {
        ClientRead clientRead = new ClientRead();
        clientRead.setId(client.getId());
        clientRead.setName(client.getName());
        clientRead.setBirthDate(client.getBirthDate());
        clientRead.setGender(client.getGender());
        clientRead.setPhoneNumber(client.getPhoneNumber());
        clientRead.setLogin(LoginConverter.convertModelToRead(client.getLogin()));
        return clientRead;
    }

    public static Client convertSaveToModel(int id, ClientSave clientSave, Login login) {
        Client client = new Client();
        client.setId(id);
        client.setName(clientSave.getName());
        client.setBirthDate(clientSave.getBirthDate());
        client.setGender(clientSave.getGender());
        client.setPhoneNumber(clientSave.getPhone());
        client.setLogin(login);
        return client;
    }
}
