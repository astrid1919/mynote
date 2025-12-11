package com.example.mynote.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import com.example.mynote.model.Note;

public class NoteRepository {
    private static final String URL = "jdbc:mysql://localhost:3306/mynoteDB";
    private Connection connection;

    public NoteRepository() {
        try {
            connection = DriverManager.getConnection(URL, "root", "123456789");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Note getNoteById(int id) {
        try {
            var statement = connection.createStatement();
            var resultSet = statement.executeQuery(
                "SELECT id, title, content, createdAt, status, completedAt FROM tbNote WHERE id = " + id
            );
            if (resultSet.next()) {
                return new Note(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("content"),
                    resultSet.getString("createdAt"),
                    resultSet.getString("status"),
                    resultSet.getString("completedAt")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();    
        }
        return null;
    }

    public ArrayList<Note> getNoteByStatus(int userId, String status) {
        try {
            var statement = connection.createStatement();
            String query = "SELECT id, title, content, createdAt, status, completedAt FROM tbNote WHERE userId = " + userId;
            if (status != null && !status.isEmpty()) {
                query += " AND status = '" + status + "'";
            }
            var resultSet = statement.executeQuery(query);
            var notes = new ArrayList<Note>();
            while (resultSet.next()) {
                notes.add(new Note(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("content"),
                    resultSet.getString("createdAt"),
                    resultSet.getString("status"),
                    resultSet.getString("completedAt")
                ));
            }
            return notes;
        } catch (Exception e) {
            e.printStackTrace();    
        }
        return null;
    }

    public boolean createNote(Note note, int userId) {
        try {
            var statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(
                "INSERT INTO tbNote (title, content, createdAt, userId, status, completedAt) VALUES ('" 
                + note.getTitle() + "', '" 
                + note.getContent() + "', '" 
                + note.getCreatedAt() + "', " 
                + userId + ", '" 
                + note.getStatus() + "', '" 
                + note.getCompletedAt() + "')"
            );
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();    
        }
        return false;
    }

    public boolean updateNote(Note note) {
        try {
            var statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(
                "UPDATE tbNote SET title = '" + note.getTitle() + "', content = '" + note.getContent() + "' WHERE id = " + note.getId()
            );
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();    
        }
        return false;
    }

    public boolean deleteNote(int id) {
        try {
            var statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(
                "UPDATE tbNote SET status = 'deleted' WHERE id = " + id
            );
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();    
        }
        return false;
    }

    public boolean updateNoteStatus(int noteId, String status, String completedAt) {
        try {
            var statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(
                "UPDATE tbNote SET status = '" + status + "', completedAt = '" + completedAt + "' WHERE id = " + noteId
            );
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();    
        }
        return false;
    }

}
