package hu.fitness.controller;

import hu.fitness.dto.ClientRead;
import hu.fitness.dto.ProgramRead;
import hu.fitness.dto.ProgramSave;
import hu.fitness.service.ProgramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/program")
@Tag(name = "Program Functions", description = "Manage Programs")
public class ProgramController {

    @Autowired
    ProgramService programService;

    @CrossOrigin
    @GetMapping("/{id}")
    @Operation(summary = "Get Program Information by id")
    public ProgramRead readProgram(@PathVariable int id){
        return programService.readProgram(id);
    }

    @CrossOrigin
    @GetMapping("/")
    @Operation(summary = "List all Programs")
    public List<ProgramRead> listPrograms(){
        return programService.listPrograms();
    }

    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    @Operation(summary = "Create Program")
    public ProgramRead createProgram(@RequestBody final ProgramSave programSave){
        return programService.createProgram(programSave);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Program by id")
    public ProgramRead deleteProgram(@PathVariable int id){
        return programService.deleteProgram(id);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    @Operation(summary = "Update Program by id")
    public ProgramRead updateProgram(@PathVariable int id, @RequestBody final ProgramSave programSave){
        return programService.updateProgram(id,programSave);
    }

    @CrossOrigin
    @PostMapping("/{programId}/clients/{clientId}")
    @Operation(summary = "Add Client to Program by id")
    public ResponseEntity<String> registerClientToProgram(@PathVariable int clientId, @PathVariable int programId) {
        programService.addClientToProgram(clientId,programId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/{programId}/clients/{clientId}")
    @Operation(summary = "Remove Client from Program by id")
    public ResponseEntity<String> removeClientFromProgram(@PathVariable int clientId, @PathVariable int programId) {
        programService.removeClientFromProgram(clientId,programId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @CrossOrigin
    @GetMapping("/{id}/client-count")
    @Operation(summary = "Count how many Clients registered for Program")
    public ResponseEntity<Integer> countClients(@PathVariable int id) {
        return new ResponseEntity<>(programService.countClients(id), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/{id}/client-list")
    @Operation(summary = "List joined Clients by id")
    public List<ClientRead> listClients(@PathVariable int id) {
        return programService.getClientsByProgramId(id);
    }
}
