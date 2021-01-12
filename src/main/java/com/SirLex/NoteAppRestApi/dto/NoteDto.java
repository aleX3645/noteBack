package com.SirLex.NoteAppRestApi.dto;

import com.SirLex.NoteAppRestApi.model.Note;
import com.SirLex.NoteAppRestApi.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoteDto {
    private Long id;
    private String text;

    public Note toNote(){
        Note note = new Note();
        note.setId(id);
        note.setText(text);
        return note;
    }

    public static NoteDto fromNote(Note note){
        NoteDto noteDto = new NoteDto();
        noteDto.setId(note.getId());
        noteDto.setText(note.getText());
        return noteDto;
    }

    public static List<NoteDto> fromNoteSet(Set<Note> notes){
        List<NoteDto> res = new ArrayList<>();
        for(Note n:notes){
            res.add(fromNote(n));
        }
        return res;
    }
}
