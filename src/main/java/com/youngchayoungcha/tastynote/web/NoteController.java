package com.youngchayoungcha.tastynote.web;

import com.youngchayoungcha.tastynote.web.dto.NoteDTO;
import com.youngchayoungcha.tastynote.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "notes")
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteDTO> addNote(@RequestBody String title){
        // TODO Security Context Holder에서 member Id 불러올 것.
        System.out.println(title);
        NoteDTO note = noteService.createNote(1L, title);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @GetMapping(value = "/{noteId}")
    public ResponseEntity<NoteDTO> getNote(@PathVariable(value = "noteId") Long noteId) {
        NoteDTO note = noteService.getNote(noteId);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @PatchMapping(value = "/{noteId}")
    public ResponseEntity<NoteDTO> modifyNote(@PathVariable(value = "noteId") Long noteId, @RequestBody String title) {
        NoteDTO note = noteService.updateNote(noteId, title);
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{noteId}")
    public ResponseEntity deleteNote(@PathVariable(value = "noteId") Long noteId){
        noteService.deleteNote(noteId);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<NoteDTO>> getMemberNotes(){
        // TODO Security Context Holder에서 불러올 것.
        List<NoteDTO> notes = noteService.getMemberNotes(1L);

        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

}
