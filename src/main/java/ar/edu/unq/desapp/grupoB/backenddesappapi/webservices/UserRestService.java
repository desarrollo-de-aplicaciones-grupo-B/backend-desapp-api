package ar.edu.unq.desapp.grupoB.backenddesappapi.webservices;


import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoB.backenddesappapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public void saveUser(@RequestBody User user){
        userService.save(user);
    }

    @GetMapping(value = "/{username}")
    public User getUserByName(@RequestParam(value = "username")String username){ return userService.findUserByName(username); }

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

    @PostMapping(value = "/{id}/newTrading")
    public void openTrading(@RequestBody Integer cryptoId, Double cryptoAmount, Double cotization, Double operationAmount, @PathVariable("id")Integer id) {
        userService.openTrading(id,cryptoId,cryptoAmount,cotization,operationAmount);
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
    public void cancel(@PathVariable("id")Integer userId, @PathVariable("tradingId")Integer tradingId){
        userService.cancel(userId,tradingId);
    }


}
