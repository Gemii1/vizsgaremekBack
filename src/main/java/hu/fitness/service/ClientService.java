package hu.fitness.service;

import hu.fitness.converter.ClientConverter;
import hu.fitness.domain.Client;
import hu.fitness.domain.Login;
import hu.fitness.dto.ClientList;
import hu.fitness.dto.ClientRead;
import hu.fitness.dto.ClientSave;
import hu.fitness.dto.ClientUpdate;
import hu.fitness.enumeration.Qualification;
import hu.fitness.exception.ClientNotFoundException;
import hu.fitness.exception.InvalidInputException;
import hu.fitness.exception.LoginNotFoundException;
import hu.fitness.repository.ClientRepository;
import hu.fitness.repository.LoginRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private LoginRepository loginRepository;

    public List<ClientList> listClients() {
        List<ClientList> clientList = new ArrayList<>();
        List<Client> clients = clientRepository.findAll();
        clientList = ClientConverter.convertModelsToLists(clients);
        return clientList;
    }

    public ClientRead createClient(ClientSave clientSave) {
        if (!loginRepository.existsById(clientSave.getLoginId())) {
            throw new LoginNotFoundException();
        }
        Login login = loginRepository.getReferenceById(clientSave.getLoginId());
        Client client = ClientConverter.convertSaveToModel(clientSave, login);
        Client savedClient = clientRepository.save(client);
        return ClientConverter.convertModelToRead(savedClient);
    }

    public ClientRead readClient(Integer id) {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException();
        }
        Client client = clientRepository.getReferenceById(id);
        return ClientConverter.convertModelToRead(client);
    }

    public ClientRead deleteClient(int id) {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException();
        }
        ClientRead clientRead = ClientConverter.convertModelToRead(clientRepository.getReferenceById(id));
        clientRepository.deleteById(id);
        return clientRead;
    }

    public ClientRead updateClientSelected(int id, ClientUpdate clientUpdate) {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException();
        }
        Client client = clientRepository.getReferenceById(id);
        switch(clientUpdate.getSelected()){
            case NAME:
                client.setName((String) clientUpdate.getValue());
                break;
            case BIRTH_DATE:
                client.setBirthDate(LocalDate.parse((String) clientUpdate.getValue()));
                break;
            case PHONE_NUMBER:
                client.setPhoneNumber((String) clientUpdate.getValue());
                break;
            default:
                throw new InvalidInputException();
        }
        clientRepository.save(client);
        return ClientConverter.convertModelToRead(client);
    }
}
