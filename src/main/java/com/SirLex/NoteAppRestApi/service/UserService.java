package com.SirLex.NoteAppRestApi.service;

import com.SirLex.NoteAppRestApi.model.Note;
import com.SirLex.NoteAppRestApi.model.User;

import java.util.List;

public interface UserService {
    User register(User user);

    List<User> getAll();

    User findByUsername(String username);

    User findById(Long id);

    List<Note> getAllNotesForUsername(String username);

    Note getNoteByNoteIdUsername(String username, Long noteId);

    void updateNoteByUsernameNoteId(String username, Long noteId, String text);

    void delete(Long id);
}
