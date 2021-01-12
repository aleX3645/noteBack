package com.SirLex.NoteAppRestApi.repository;

import com.SirLex.NoteAppRestApi.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note,Long> {
}
