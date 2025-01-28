package hu.fitness.controller;

import hu.fitness.dto.LoginRead;
import hu.fitness.dto.LoginSave;
import hu.fitness.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login")
@Tag(name = "Login Test Functions", description = "Manage Login Information")
public class LoginController {

    @Autowired
    LoginService loginService;

    @CrossOrigin
    @GetMapping("/{id}")
    @Operation(summary = "Get Login Information by id")
    public LoginRead readLogin(@PathVariable int id) {
        return loginService.readLogin(id);
    }

    @CrossOrigin
    @GetMapping("/")
    @Operation(summary = "List all Login Information")
    public List<LoginRead> readLoginList() {
        return loginService.listLogins();
    }

    @CrossOrigin
    @PostMapping("/")
    @Operation(summary = "Create Login Information")
    public LoginRead createLogin(@RequestBody @Valid LoginSave loginSave) {
        return loginService.createLogin(loginSave);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Login Information by id")
    public LoginRead deleteLogin(@PathVariable int id) {
        return loginService.deleteLogin(id);
    }

}
