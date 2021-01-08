package com.SirLex.NoteAppRestApi.service.impl;

import com.SirLex.NoteAppRestApi.model.Note;
import com.SirLex.NoteAppRestApi.model.Role;
import com.SirLex.NoteAppRestApi.model.Status;
import com.SirLex.NoteAppRestApi.model.User;
import com.SirLex.NoteAppRestApi.repository.RoleRepository;
import com.SirLex.NoteAppRestApi.repository.UserRepository;
import com.SirLex.NoteAppRestApi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        user.setStatus(Status.ACTIVE);

        User registredUser = userRepository.save(user);

        log.info("In register: user - {} successfully registered",registredUser.getUsername());
        return registredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        log.info("In getAll: {} - users founded", users.size());
        return  users;
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        log.info("In findByUsername: {} - found by username", user.getUsername());
        return user;
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        log.info("In findByUsername: {} - found by id {}", user.getUsername(), user.getId());
        return user;
    }

    @Override
    public List<Note> getAllNotesForUsername(String username) {
        User user = findByUsername(username);

        List<Note> notes = user.getNotes();

        log.info("In getAllNotesForUserById: found {} notes by user id {}", notes.size(), user.getId());
        return notes;
    }

    @Override
    public Note getNoteByNoteIdUsername(String username, Long noteId) {
        User user = findByUsername(username);

        List<Note> notes = user.getNotes();
        Note note = findNoteById(notes, noteId);

        log.info("In getNoteByNoteIdUserId: found node by id {} for user id {}", note.getId(), user.getId());
        return note;
    }

    @Override
    public void updateNoteByUsernameNoteId(String username, Long noteId, String text) {
        Note note = getNoteByNoteIdUsername(username, noteId);
        note.setText(text);

        log.info("In delete: note with id {} was updated for user {}", noteId,username);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("In delete: user by id {} deleted", id);
    }

    Note findNoteById(List<Note> notes, Long noteId){
        List<Note> result = new ArrayList<Note>();
        notes.forEach(note ->{
            if(note.getId() == noteId)
                result.add(note);
        });

        return result.get(0);
    }
}
