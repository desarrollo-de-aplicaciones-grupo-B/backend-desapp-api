package ar.edu.unq.desapp.grupoB.backenddesappapi.webservices;


import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.UserResponseDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.UserUpdateDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoB.backenddesappapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserRestService {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public void saveUser(@RequestBody User user){
        userService.save(user);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(userService.findByID(id));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        userService.deleteById(id);
    }

    @PostMapping(value = "/{id}")
    public void updateUser(@RequestBody User userUpdateDTO, @PathVariable ("id") Integer id){
        userService.updateUser(userUpdateDTO,id);
    }

}
