package hu.fitness.controller;

import hu.fitness.dto.ClientList;
import hu.fitness.dto.ClientRead;
import hu.fitness.dto.ClientSave;
import hu.fitness.dto.ClientUpdate;
import hu.fitness.dto.ProgramRead;
import hu.fitness.service.ClientService;
import hu.fitness.service.ProgramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/client")
@Tag(name = "Client Test Functions", description = "Manage Clients")
public class ClientController {


    @Autowired
    private ClientService clientService;
    @Autowired
    private ProgramService programService;

    @CrossOrigin
    @GetMapping("/")
    @Operation(summary = "List all Clients")
    public List<ClientList> readClientList() {
        return clientService.listClients();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    @Operation(summary = "Get Client by id")
    public ClientRead readClient(@PathVariable int id) {
        return clientService.readClient(id);
    }

    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    @Operation(summary = "Create Client")
    public ClientRead createClient(@Valid @RequestBody ClientSave clientSave) {
        return clientService.createClient(clientSave);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Client by id")
    public ClientRead deleteClient(@PathVariable int id) {
        return clientService.deleteClient(id);
    }

    @CrossOrigin
    @PatchMapping("/{id}")
    @Operation(summary = "Update Client by id")
    public ClientRead updateClient(@PathVariable int id, @Valid @RequestBody ClientUpdate clientUpdate) {
        return clientService.updateClientSelected(id, clientUpdate);
    }

    @CrossOrigin
    @GetMapping("/{id}/program-count")
    @Operation(summary = "Count how many Programs the Client registered for")
    public ResponseEntity<Integer> countPrograms(@PathVariable int id) {
        return new ResponseEntity<>(programService.countPrograms(id),HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/{id}/program-list")
    @Operation(summary = "List joined Programs by id")
    public List<ProgramRead> readJoinedPrograms(@PathVariable int id) {
        return clientService.getProgramsByClientId(id);
    }
}
