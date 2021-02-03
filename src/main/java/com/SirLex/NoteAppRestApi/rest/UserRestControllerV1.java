package com.SirLex.NoteAppRestApi.rest;

import com.SirLex.NoteAppRestApi.dto.AuthenticationRequestDto;
import com.SirLex.NoteAppRestApi.dto.UpdateNoteRequestDto;
import com.SirLex.NoteAppRestApi.dto.UserDto;
import com.SirLex.NoteAppRestApi.model.User;
import com.SirLex.NoteAppRestApi.service.UserService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestControllerV1 {
    private final UserService userService;

    @Autowired
    public UserRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/api/v1/getUserInfo/")
    public ResponseEntity<UserDto> getUserInfo(){
        User user = userService.getUserByJwt();
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/api/v1/updateNoteById/{id}")
    public HttpStatus updateNote(@PathVariable("id") Long id,
                                       @RequestBody UpdateNoteRequestDto requestDto) {
        try{
            userService.updateNoteByNoteId(id,requestDto.getText());
        }catch (Exception exception){
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }
}
