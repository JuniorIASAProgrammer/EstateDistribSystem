package com.realestate.serviceuser.api;

import com.realestate.serviceuser.api.dto.UserDto;
import com.realestate.serviceuser.repo.model.User;
import com.realestate.serviceuser.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("EstateMarketplace/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll(){
        final List<User> userList = userService.getAll();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/getById/id={id}")
    public ResponseEntity<User> getById(@PathVariable long id) {
        try {
            final User user = userService.getById(id);
            return ResponseEntity.ok(user);
        }
        catch (IllegalArgumentException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getByEmail/email={email}")
    public ResponseEntity<Long> getByEmail(@PathVariable String email) {
        try {
            final User user = userService.getByEmail(email);
            return ResponseEntity.ok(user.getId());
        }
        catch (IllegalArgumentException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody UserDto user) {
        try {
            long id = userService.create(user);
            String location = String.format("/user/%d", id);
            return ResponseEntity.created(URI.create(location)).build();
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody UserDto user, @PathVariable long id) {
        try{
            userService.update(user, id);
            return new ResponseEntity<>("User info updated", HttpStatus.OK);
        }
        catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/block/userId={id}")
    public ResponseEntity<String> block(@PathVariable long id) {
        try {
            userService.blockById(id);
            return new ResponseEntity<>("User blocked", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @PatchMapping("/unblock/userId={id}")
    public ResponseEntity<String> unblock(@PathVariable long id) {
        try {
            userService.unblockById(id);
            return new ResponseEntity<>("User unblocked", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id){
        userService.delete(id);
        return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
    }
}
