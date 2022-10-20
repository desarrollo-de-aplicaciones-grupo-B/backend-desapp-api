package ar.edu.unq.desapp.grupoB.backenddesappapi.webservices;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.CreateTransactionDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.RegisterDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.security.JwtRequest;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.security.JwtResponse;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.Utils.security.JwtTokenUtil;
import ar.edu.unq.desapp.grupoB.backenddesappapi.services.JwtUserDetailsService;
import ar.edu.unq.desapp.grupoB.backenddesappapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserRestService {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/allUser")
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.ok().body(userService.findAll());
    }

    @PostMapping(path = "/register")
    public void register(@RequestBody RegisterDTO user) {
         userService.save(user);
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<?> login(@RequestBody JwtRequest authenticationRequest) {
        return ResponseEntity.ok(userService.authenticate(authenticationRequest));
    }

    @PostMapping(value = "/{id}/newTrading")
    public void openTrading(@RequestBody CreateTransactionDTO trading, @PathVariable("id")Integer id) {
        userService.openTrading(id, trading);
    }

    @PutMapping(value ="/{id}/buy/{tradingId}")
    public void buy(@PathVariable("id")Integer buyerId, @PathVariable("tradingId")Integer tradingId){
        userService.buy(buyerId,tradingId);
    }

    @PutMapping(value="/{id}/confirmTransfer/{tradingId}")
    public void confirmTransfer(@PathVariable("id")Integer buyerId, @PathVariable("tradingId")Integer tradingId){
        userService.confirmTransfer(buyerId,tradingId);
    }

    @PutMapping(value="/{id}/confirmReception/{tradingId}")
    public void confirmReception(@PathVariable("id")Integer sellerId, @PathVariable("tradingId")Integer tradingId){
        userService.confirmReception(sellerId,tradingId);
    }

    @PutMapping(value="/{id}/cancel/{tradingId}")
    public void cancel(@PathVariable("id")Integer sellerId, @PathVariable("tradingId")Integer tradingId){
        userService.cancel(sellerId,tradingId);
    }


}
