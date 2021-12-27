package com.realestate.serviceuser.service;

import com.realestate.serviceuser.api.dto.UserDto;
import com.realestate.serviceuser.api.enums.BanEnum;
import com.realestate.serviceuser.repo.RoleRepo;
import com.realestate.serviceuser.repo.UserRepo;
import com.realestate.serviceuser.repo.model.Role;
import com.realestate.serviceuser.repo.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    public List<User> getAll(){
        return userRepo.findAll();
    }

    public User getById(long id) throws IllegalArgumentException{
        final Optional<User> maybeUser = userRepo.findOneById(id);
        if (maybeUser.isEmpty()) throw new IllegalArgumentException("User not found");
        else return maybeUser.get();
    }

    public User getByEmail(String email) throws IllegalArgumentException{
        final Optional<User> maybeUser = userRepo.findOneByEmail(email);
        if (maybeUser.isEmpty()) throw new IllegalArgumentException("User not found");
        else return maybeUser.get();
    }

    public long create(UserDto newUser) {
        String name = newUser.getName();
        String surname = newUser.getSurname();
        String email = newUser.getEmail();
        String password = newUser.getPassword();
        String repeatPassword = newUser.getRepeatPassword();
        String phone = newUser.getPhone();
        Role role = roleRepo.findByName(newUser.getRole());

        Pattern p = Pattern.compile("^\\d{10}$");
        Matcher m = p.matcher(phone);
        if (!m.matches()){ throw new IllegalArgumentException("Wrong phone number"); }

        if (Objects.equals(password, repeatPassword)) {
            User user = new User(name, surname, email, password, phone, role);
            User savedUser = userRepo.save(user);
            log.info("New user {} was saved", savedUser.getEmail());
            return savedUser.getId();
        }
        else throw new IllegalArgumentException("Incorrect password. Please, try again");
    }

    public void update(UserDto userDto, long id) throws IllegalArgumentException {
        String name = userDto.getName();
        String surname = userDto.getSurname();
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        String phone = userDto.getPhone();

        final Optional<User> maybeUser = userRepo.findById(id);
        if (maybeUser.isEmpty()) throw new IllegalArgumentException("User not found");
        User user = maybeUser.get();
        if (name != null && !name.isBlank()) user.setName(name);
        if (surname != null && !surname.isBlank()) user.setSurname(surname);
        if (email != null && !email.isBlank()) user.setEmail(email);
        if (password != null && !password.isBlank()) user.setPassword(password);
        if (phone != null && !phone.isBlank()) user.setPhone(phone);
        try {
            userRepo.save(user);
        }
        catch (Exception e){
            throw new IllegalArgumentException("Email already used");
        }
    }

    public void blockById(long id) {
        Optional<User> maybeUser = userRepo.findById(id);
        if (maybeUser.isEmpty()) throw new IllegalArgumentException("User not found");
        User user = maybeUser.get();
        user.setBan(BanEnum.YES);
        userRepo.save(user);
    }

    public void unblockById(long id) {
        Optional<User> maybeUser = userRepo.findById(id);
        if (maybeUser.isEmpty()) throw new IllegalArgumentException("User not found");
        User user = maybeUser.get();
        user.setBan(BanEnum.NO);
        userRepo.save(user);
    }

    public void delete(long id){
        userRepo.deleteById(id);
    }
}
