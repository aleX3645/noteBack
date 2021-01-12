package com.SirLex.NoteAppRestApi.dto;

import com.SirLex.NoteAppRestApi.model.Note;
import com.SirLex.NoteAppRestApi.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private List<NoteDto> notes;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setNotes(toNoteList(notes));
        return user;
    }

    public static UserDto fromUser(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setNotes(NoteDto.fromNoteSet(user.getNotes()));
        return userDto;
    }

    private Set<Note> toNoteList(List<NoteDto> notes){
        Set<Note> res = new HashSet<>();
        for(NoteDto n:notes){
            res.add(n.toNote());
        }
        return res;
    }
}
