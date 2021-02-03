package com.SirLex.NoteAppRestApi.service;

import com.SirLex.NoteAppRestApi.model.Note;
import com.SirLex.NoteAppRestApi.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    User register(User user);

    List<User> getAll();

    User findByUsername(String username);

    User findById(Long id);

    Set<Note> getAllNotesForUsername(String username);

    Note getNoteByNoteIdUser(User user, Long noteId) throws Exception;

    void updateNoteByNoteId(Long noteId, String text) throws Exception;

    User getUserByJwt();

    void delete(Long id);
}
