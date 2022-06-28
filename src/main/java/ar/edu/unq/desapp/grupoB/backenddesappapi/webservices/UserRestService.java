package ar.edu.unq.desapp.grupoB.backenddesappapi.webservices;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoB.backenddesappapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserRestService {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAll(){
        return userService.findAll();
    }

    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody User user){
        this.userService.save(user);
        return ResponseEntity.ok().body("The user was registered");
    }

    @GetMapping(value = "/{id}")
    public User getById(@PathVariable("id") Integer id){
        return userService.findByID(id);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        userService.deleteById(id);
    }

    @PostMapping(value = "/{id}")
    public void updateUser(@RequestBody User user, @PathVariable ("id") Integer id){
        userService.updateUser(user,id);
    }
}
