package com.youngchayoungcha.tastynote.web;

import com.youngchayoungcha.tastynote.web.dto.NoteResponseDTO;
import com.youngchayoungcha.tastynote.service.NoteService;
import com.youngchayoungcha.tastynote.web.dto.NoteRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        // TODO Security Context Holder에서 member Id 불러올 것.
        NoteResponseDTO note = noteService.createNote(1L, noteDto.getTitle());
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @GetMapping(value = "/{noteId}")
    public ResponseEntity<NoteResponseDTO> getNote(@PathVariable(value = "noteId") Long noteId) {
        NoteResponseDTO note = noteService.getNote(noteId);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @PatchMapping(value = "/{noteId}")
    public ResponseEntity<NoteResponseDTO> modifyNote(@PathVariable(value = "noteId") Long noteId, @RequestBody @Valid NoteResponseDTO noteDto) {
        // TODO 권한 체크해야 함
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
        // TODO Security Context Holder에서 불러올 것.
        List<NoteResponseDTO> notes = noteService.getMemberNotes(1L);

        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

}
