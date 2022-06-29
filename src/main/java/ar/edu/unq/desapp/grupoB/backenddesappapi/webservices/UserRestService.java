package ar.edu.unq.desapp.grupoB.backenddesappapi.webservices;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoB.backenddesappapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


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
    public ResponseEntity<String> register(@RequestBody User user){
        userService.save(user);
        return ResponseEntity.ok().body("The user was registered");
    }

    @PostMapping(value = "/{id}")
    public void updateUser(@RequestBody User user, @PathVariable ("id") Integer id){
        userService.updateUser(user,id);
    }

    @GetMapping()
    public Optional<User> userByName(@RequestParam ("name") String name){
        return userService.findUserByName(name);
    }
}
