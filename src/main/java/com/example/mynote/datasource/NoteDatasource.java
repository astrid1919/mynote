package com.example.mynote.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import com.example.mynote.model.Note;

public class NoteDatasource {
    private static final String URL = "jdbc:mysql://root:vuSzugqsRRoyVomJqRxPBfQqhAxvpQPY@ballast.proxy.rlwy.net:20988/railway";
    private Connection connection;

    public NoteDatasource() {
        try {
            connection = DriverManager.getConnection(URL, "root", "vuSzugqsRRoyVomJqRxPBfQqhAxvpQPY");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Note getNoteById(int id) {
        try {
            var statement = connection.createStatement();
            var resultSet = statement.executeQuery(
                "SELECT id, title, content, createdAt FROM tbNote WHERE id = " + id
            );
            if (resultSet.next()) {
                return new Note(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("content"),
                    resultSet.getString("createdAt")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();    
        }
        return null;
    }

    public ArrayList<Note> getNoteByUserId(int userId) {
        try {
            var statement = connection.createStatement();
            var resultSet = statement.executeQuery(
                "SELECT id, title, content, createdAt FROM tbNote WHERE userId = " + userId
            );
            var notes = new ArrayList<Note>();
            while (resultSet.next()) {
                notes.add(new Note(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("content"),
                    resultSet.getString("createdAt")
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
                "INSERT INTO tbNote (title, content, createdAt, userId) VALUES ('" 
                + note.getTitle() + "', '" 
                + note.getContent() + "', '" 
                + note.getCreatedAt() + "', " 
                + userId + ")"
            );
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();    
        }
        return false;
    }

}
