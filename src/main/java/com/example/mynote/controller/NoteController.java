package com.example.mynote.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mynote.datasource.NoteDatasource;
import com.example.mynote.model.Note;
import com.example.mynote.model.Response;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/notes")
public class NoteController {
    NoteDatasource noteDatasource = new NoteDatasource();

    @GetMapping("/{id}")
    public Response getNoteById(@PathVariable int id) {
        Note note = noteDatasource.getNoteById(id);
        if (note != null) {
            return new Response("Note found", note, 200);
        } else {
            return new Response("Note not found", null, 404);
        }
    }

    @GetMapping("/getList")
    public Response getNoteByUserId(@RequestParam int userId) {
        ArrayList<Note> notes = noteDatasource.getNoteByUserId(userId);
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
        Note note = new Note(0, title, content, createdAt);
        int userId = Integer.parseInt(param.get("userId"));
        boolean success = noteDatasource.createNote(note, userId);
        if (success) {
            return new Response("Note created successfully", note, 200);
        } else {
            return new Response("Note creation failed", null, 400);
        }
    }
    
}
