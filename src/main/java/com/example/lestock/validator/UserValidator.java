package com.example.lestock.validator;
import com.example.lestock.controller.dto.errors.FieldErrorDTO;
import com.example.lestock.dao.UserDAO;
import com.example.lestock.exceptions.DuplicateRecordException;
import com.example.lestock.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserValidator {
    private final UserDAO userDAO;

    public void validateUser(User user){
        if(isUserSaved(user)){
            FieldErrorDTO emailErrorDTO = new FieldErrorDTO(
                    "email",
                    "User with this email already exists",
                    "UniquenessBreak"
            );
            FieldErrorDTO userNameErrorDTO = new FieldErrorDTO(
                    "userName",
                    "User with this user name already exists",
                    "UniquenessBreak"
            );
            throw new DuplicateRecordException("User already exists", List.of(emailErrorDTO, userNameErrorDTO));
        }
    }

    private boolean isUserSaved(User user){
        Optional<User> userOptional = userDAO.findByEmailAndUserName(user.getEmail(),  user.getUserName());

        if(user.getId() == null){
            return userOptional.isPresent();
        }

        return userOptional.map(User::getId)
                .stream()
                .anyMatch(id -> !id.equals(user.getId()));
    }
}