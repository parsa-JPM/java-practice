package com.example.interview_practice.mockmvc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private record UserDTO(String name, int age) {
    }

    private List<UserDTO> userDTO = List.of(
            new UserDTO("Parsa", 27),
            new UserDTO("Fati", 29));

    @GetMapping("/user")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userDTO);
    }


}
