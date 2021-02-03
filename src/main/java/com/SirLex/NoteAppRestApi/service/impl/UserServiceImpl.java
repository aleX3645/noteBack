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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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

        User registeredUser = userRepository.save(user);

        log.info("In register: user - {} successfully registered",registeredUser.getUsername());
        return registeredUser;
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
    public Set<Note> getAllNotesForUsername(String username) {
        User user = findByUsername(username);

        Set<Note> notes = user.getNotes();

        log.info("In getAllNotesForUserById: found {} notes by user id {}", notes.size(), user.getId());
        return notes;
    }

    @Override
    public Note getNoteByNoteIdUser(User user, Long noteId) throws Exception{
        Set<Note> notes = user.getNotes();
        Note note;
        try {
            note = findNoteById(notes, noteId);
        }catch (Exception exception){
            throw new Exception("invalid note id");
        }

        log.info("In getNoteByNoteIdUserId: found node by id {} for user id {}", note.getId(), user.getId());
        return note;
    }

    @Override
    public void updateNoteByNoteId(Long noteId, String text) throws Exception{
        User user = getUserByJwt();
        Note note = getNoteByNoteIdUser(user, noteId);
        note.setText(text);
        userRepository.flush();

        log.info("In delete: note with id {} had updated for user {}", noteId,user.getUsername());
    }

    @Override
    public User getUserByJwt() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return findByUsername(userDetails.getUsername());
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("In delete: user by id {} deleted", id);
    }

    Note findNoteById(Set<Note> notes, Long noteId){

        for(Note n:notes){
            if(n.getId() == noteId){
                return n;
            }
        }
        return  null;
    }
}
