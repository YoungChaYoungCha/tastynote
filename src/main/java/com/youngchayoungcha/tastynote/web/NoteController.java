package com.youngchayoungcha.tastynote.web;

import com.youngchayoungcha.tastynote.domain.Member;
import com.youngchayoungcha.tastynote.util.CurrentLoginInfoUtils;
import com.youngchayoungcha.tastynote.web.dto.NoteResponseDTO;
import com.youngchayoungcha.tastynote.service.NoteService;
import com.youngchayoungcha.tastynote.web.dto.NoteRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "notes")
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteResponseDTO> addNote(@RequestBody @Valid NoteRequestDTO noteDto){
        NoteResponseDTO note = noteService.createNote(noteDto.getTitle());
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @GetMapping(value = "/{noteId}")
    public ResponseEntity<NoteResponseDTO> getNote(@PathVariable(value = "noteId") Long noteId) {
        NoteResponseDTO note = noteService.getNote(noteId);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @PatchMapping(value = "/{noteId}")
    public ResponseEntity<NoteResponseDTO> modifyNote(@PathVariable(value = "noteId") Long noteId, @RequestBody @Valid NoteResponseDTO noteDto) {
        NoteResponseDTO note = noteService.updateNote(noteId, noteDto.getTitle());
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{noteId}")
    public ResponseEntity deleteNote(@PathVariable(value = "noteId") Long noteId){
        noteService.deleteNote(noteId);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<NoteResponseDTO>> getMemberNotes(){
        List<NoteResponseDTO> notes = noteService.getMemberNotes(CurrentLoginInfoUtils.getLoginMemberId());

        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

}
