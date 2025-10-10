package com.example.lestock.validator;
import com.example.lestock.controller.dto.errors.FieldErrorDTO;
import com.example.lestock.exceptions.DuplicateRecordException;
import com.example.lestock.model.User;
import com.example.lestock.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserValidator {
    private final UserService userService;

    public void validateUser(User user){
        if(isUserSaved(user)){
            FieldErrorDTO fieldErrorDTO = new FieldErrorDTO(
                    "email",
                    "User with this email already exists",
                    "UniqunessBreak"
            );
            throw new DuplicateRecordException("User already exists", List.of(fieldErrorDTO));
        }
    }

    private boolean isUserSaved(User user){
        Optional<User> userOptional = userService.findUserByEmail(user.getEmail());

        if(user.getId() == null){
            return userOptional.isPresent();
        }

        return userOptional.map(User::getId)
                .stream()
                .anyMatch(id -> !id.equals(user.getId()));
    }
}
