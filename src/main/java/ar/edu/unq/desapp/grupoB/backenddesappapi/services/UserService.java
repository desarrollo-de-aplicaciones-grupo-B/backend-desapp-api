package ar.edu.unq.desapp.grupoB.backenddesappapi.services;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoB.backenddesappapi.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;

    @Transactional
    public User save(User user){
        return this.userRepository.save(user);
    }

    @Transactional
    public User findByID(Integer id) {
        return this.userRepository.findById(id).get();
    }

    @Transactional
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Transactional
    public void deleteById(Integer id) {
        this.userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(User userUpdate, Integer userId){
        Optional<User> userFound = userRepository.findById(userId);

        User user = userFound.get();
        user.setName(userUpdate.getName());
        user.setLastname(userUpdate.getLastname());
        user.setEmail(userUpdate.getEmail());
        user.setAddress(userUpdate.getAddress());
        user.setCvu(userUpdate.getCvu());
        user.setUserWallet(userUpdate.getUserWallet());
    }

}
