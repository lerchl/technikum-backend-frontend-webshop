package at.technikumwien.webshop.controller;

import at.technikumwien.webshop.dto.LoginDTO;
import at.technikumwien.webshop.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationContoller {

    private final AuthenticationService authenticationService;

    // /////////////////////////////////////////////////////////////////////////
    // Init
    // /////////////////////////////////////////////////////////////////////////

    public AuthenticationContoller(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        var jwt = authenticationService.login(loginDTO.getUsername(), loginDTO.getPassword());
        HttpCookie cookie = ResponseCookie.from("jwt", jwt)
                                          .httpOnly(true)
                                          .path("/")
                                          .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }
}
