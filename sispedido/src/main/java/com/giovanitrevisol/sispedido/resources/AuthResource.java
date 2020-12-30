package com.giovanitrevisol.sispedido.resources;

import com.giovanitrevisol.sispedido.dto.EmailDTO;
import com.giovanitrevisol.sispedido.security.JWTUtil;
import com.giovanitrevisol.sispedido.security.UserSS;
import com.giovanitrevisol.sispedido.services.AuthService;
import com.giovanitrevisol.sispedido.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {


    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;

    @PostMapping(value = "/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }


    @PostMapping(value = "/forgot")
     public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
       service.sendNewPassword(objDto.getEmail());
       return ResponseEntity.noContent().build();
    }
}
