package ar.edu.unq.desapp.grupoB.backenddesappapi.webservices;


import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoB.backenddesappapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserRestService {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAll(){
        return userService.findAll();
    }

    @PostMapping
    public void saveUser(@RequestBody User user){userService.save(user); }

    @GetMapping(value = "/{id}")
    public User getById(@PathVariable("id") Integer id){
        return userService.findByID(id);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        userService.deleteById(id);
    }

    @PutMapping
    public void updateUser(@RequestBody User user){
        userService.updateUser(user);
    }

}
