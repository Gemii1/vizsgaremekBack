package hu.fitness.controller;


import hu.fitness.auth.PermissionCollector;
import hu.fitness.converter.LoginConverter;
import hu.fitness.domain.Login;
import hu.fitness.dto.LoginRead;
import hu.fitness.dto.LoginRequest;
import hu.fitness.service.LoginService;
import hu.fitness.token.JWTTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication and Authorization Controller")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;
    private LoginService loginService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider, LoginService loginService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.loginService = loginService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    public ResponseEntity<LoginRead> login(@RequestBody final LoginRequest loginRequest) {
        authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        Login login = loginService.findLoginByEmail(loginRequest.getEmail());
        PermissionCollector collector = new PermissionCollector(login);
        HttpHeaders jwtHeader = getJWTHeader(collector);
        LoginRead loginRead = LoginConverter.convertModelToRead(login);
        return new ResponseEntity<>(loginRead, jwtHeader,HttpStatus.OK);
    }

    private HttpHeaders getJWTHeader(PermissionCollector collector) {
        HttpHeaders jwtHeader = new HttpHeaders();
        jwtHeader.set("JWT_Token",jwtTokenProvider.generateJwtToken(collector));
        return jwtHeader;
    }

    public void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
