package com.example.mynote.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mynote.model.Note;
import com.example.mynote.model.Response;
import com.example.mynote.repositories.NoteRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/notes")
public class NoteController {
    NoteRepository noteRepo = new NoteRepository();

    @GetMapping("/{id}")
    public Response getNoteById(@PathVariable int id) {
        Note note = noteRepo.getNoteById(id);
        if (note != null) {
            return new Response("Note found", note, 200);
        } else {
            return new Response("Note not found", null, 404);
        }
    }

    @GetMapping("/getList")
    public Response getNoteByUserId(@RequestParam int userId, @RequestParam String status) {
        ArrayList<Note> notes = noteRepo.getNoteByStatus(userId, status);
        if (notes != null && !notes.isEmpty()) {
            return new Response("Notes found", notes, 200);
        } else {
            return new Response("No notes found", null, 404);
        }
    }

    @PostMapping("/createNote")
    public Response createNote(@RequestBody Map<String, String> param) {
        String title = param.get("title");
        String content = param.get("content");
        String createdAt = LocalDate.now().toString();
        Note note = new Note(0, title, content, createdAt, "new", null);
        int userId = Integer.parseInt(param.get("userId"));
        boolean success = noteRepo.createNote(note, userId);
        if (success) {
            return new Response("Note created successfully", note, 200);
        } else {
            return new Response("Note creation failed", null, 400);
        }
    }

    @PutMapping("/{id}")
    public Response updateNote(@PathVariable String id, @RequestBody String title, @RequestBody String content) {
        Note note = new Note();
        note.setId(Integer.parseInt(id));
        note.setTitle(title);
        note.setContent(content);
        boolean success = noteRepo.updateNote(note);
        if (success) {
            Note newNote = noteRepo.getNoteById(Integer.parseInt(id));
            return new Response("Note updated successfully", newNote, 200);
        } else {
            return new Response("Note update failed", null, 400);
        }
    }

    @PutMapping("/{id}")
    public Response markAsActive(@PathVariable String id) {
        boolean success = noteRepo.updateNoteStatus(Integer.parseInt(id), "active", null);
        if (success) {
            return new Response("Note marked as active", null, 200);
        } else {
            return new Response("Failed to mark note as active", null, 400);
        }
    }

    @PutMapping("/{id}")
    public Response markAsCompleted(@PathVariable String id) {
        String completedAt = LocalDate.now().toString();
        boolean success = noteRepo.updateNoteStatus(Integer.parseInt(id), "completed", completedAt);
        if (success) {
            return new Response("Note marked as completed", null, 200);
        } else {
            return new Response("Failed to mark note as completed", null, 400);
        }
    }

    @DeleteMapping("/{id}")
    public Response deleteNote(@PathVariable String id) {
        boolean success = noteRepo.deleteNote(Integer.parseInt(id));
        if (success) {
            return new Response("Note deleted successfully", null, 200);
        } else {
            return new Response("Note deletion failed", null, 400);
        }
    }
}