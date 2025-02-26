package hu.fitness.service;

import hu.fitness.converter.ClientConverter;
import hu.fitness.converter.ProgramConverter;
import hu.fitness.domain.Client;
import hu.fitness.domain.Login;
import hu.fitness.domain.Program;
import hu.fitness.dto.ClientList;
import hu.fitness.dto.ClientRead;
import hu.fitness.dto.ClientSave;
import hu.fitness.dto.ProgramRead;
import hu.fitness.exception.ClientNotFoundException;
import hu.fitness.exception.LoginNotFoundException;
import hu.fitness.repository.ClientRepository;
import hu.fitness.repository.LoginRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ClientRead updateClient(int id, @Valid ClientSave clientSave) {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException();
        }
        if (!loginRepository.existsById(clientSave.getLoginId())) {
            throw new LoginNotFoundException();
        }
        Login login = loginRepository.getReferenceById(clientSave.getLoginId());
        Client client = ClientConverter.convertSaveToModel(id, clientSave, login);
        clientRepository.save(client);
        return ClientConverter.convertModelToRead(client);
    }

    public List<ProgramRead> getProgramsByClientId(Integer id) {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException();
        }
        List<Program> programs = clientRepository.findProgramsByClientId(id);
        List<ProgramRead> programReads = new ArrayList<>();
        for (Program program : programs) {
            programReads.add(ProgramConverter.convertModelToRead(program));
        }
        return programReads;
    }
}
