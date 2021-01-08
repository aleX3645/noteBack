package com.SirLex.NoteAppRestApi.security.Jwt;

import com.SirLex.NoteAppRestApi.model.Role;
import com.SirLex.NoteAppRestApi.model.Status;
import com.SirLex.NoteAppRestApi.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUserFactory {
    public void JwtFactory(){

    }

    public static JwtUser create(User user){
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getNotes(),
                user.getStatus().equals(Status.ACTIVE),
                user.getUpdated(),
                mapToGrantedAuthorities(user.getRoles())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles){
        return roles.stream()
                .map(role ->
                    new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
