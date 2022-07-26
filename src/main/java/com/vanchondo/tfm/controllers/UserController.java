package com.vanchondo.tfm.controllers;

import com.vanchondo.tfm.dtos.security.CurrentUserDTO;
import com.vanchondo.tfm.dtos.users.DeleteUserDTO;
import com.vanchondo.tfm.dtos.users.UpdateUserDTO;
import com.vanchondo.tfm.dtos.users.UserDTO;
import com.vanchondo.tfm.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PutMapping(value = "")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UpdateUserDTO user, @RequestAttribute("currentUser") CurrentUserDTO currentUser){
        UserDTO dto = userService.updateUser(user, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping(value = "")
    public void deleteUser(@Valid @RequestBody DeleteUserDTO user, @RequestAttribute("currentUser") CurrentUserDTO currentUser){
        userService.deleteUser(user, currentUser);
    }

    @GetMapping(value="/available")
    public ResponseEntity available(@RequestParam(value = "username", required = false) String username, @RequestParam(value="email", required = false) String email) {
        if (userService.available(username, email)) {
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }
}
