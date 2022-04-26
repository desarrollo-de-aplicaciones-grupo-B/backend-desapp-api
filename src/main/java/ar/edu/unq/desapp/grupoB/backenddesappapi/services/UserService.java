package ar.edu.unq.desapp.grupoB.backenddesappapi.services;

import ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO.UserResponseDTO;
import ar.edu.unq.desapp.grupoB.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoB.backenddesappapi.repositories.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;
import javax.transaction.Transactional;
import java.util.ArrayList;
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
    public UserResponseDTO findByID(Integer id) {

        User foundUser = this.userRepository.findById(id).get();
        ModelMapper modelMapper = new ModelMapper();
        UserResponseDTO userResponseDTO = modelMapper.map(foundUser,UserResponseDTO.class);

        return userResponseDTO;

    }

    @Transactional
    public List<UserResponseDTO> findAll() {
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> usersDTO = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();

        users.forEach((user) -> usersDTO.add(mapper.map(user,UserResponseDTO.class)));

        return usersDTO;

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
