package com.SirLex.NoteAppRestApi.dto;

import com.SirLex.NoteAppRestApi.model.Note;
import com.SirLex.NoteAppRestApi.model.Status;
import com.SirLex.NoteAppRestApi.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private List<Note> notes;
    private Status status;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setNotes(notes);
        user.setStatus(status);
        return user;
    }

    public static AdminUserDto fromUser(User user){
        AdminUserDto userDto = new AdminUserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setNotes(user.getNotes());
        userDto.setStatus(user.getStatus());
        return userDto;
    }
}
