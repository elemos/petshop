package br.com.metaway.petshop.controller;

import br.com.metaway.petshop.Security.TokenService;
import br.com.metaway.petshop.Utils;
import br.com.metaway.petshop.controller.dto.AuthRq;
import br.com.metaway.petshop.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthRq authRq){

        var usernamePassword = new UsernamePasswordAuthenticationToken(authRq.login(), authRq.ps());
        System.out.println("Credentials" + usernamePassword.getCredentials().toString());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        System.out.println("RetornoAuth" + auth.isAuthenticated());
        System.out.println(auth.getName());
        System.out.println(auth.toString());
        System.out.println(((User) auth.getPrincipal()).getPs());
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(token);
    }

}
