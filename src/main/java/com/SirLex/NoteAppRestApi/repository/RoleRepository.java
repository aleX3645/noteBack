package com.SirLex.NoteAppRestApi.repository;

import com.SirLex.NoteAppRestApi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
