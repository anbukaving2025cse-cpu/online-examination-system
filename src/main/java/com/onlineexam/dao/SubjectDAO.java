package com.onlineexam.dao;

import com.onlineexam.model.Subject;
import com.onlineexam.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    // CREATE: add a new subject
    public boolean addSubject(Subject subject) {
        String sql = "INSERT INTO subjects (subject_name, description) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, subject.getSubjectName());
            ps.setString(2, subject.getDescription());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Add subject failed: " + e.getMessage());
            return false;
        }
    }

    // READ: get all subjects
    public List<Subject> getAllSubjects() {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM subjects ORDER BY subject_id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Subject s = new Subject();
                s.setSubjectId(rs.getInt("subject_id"));
                s.setSubjectName(rs.getString("subject_name"));
                s.setDescription(rs.getString("description"));
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Fetch subjects failed: " + e.getMessage());
        }
        return list;
    }

    // UPDATE: change name and description of a subject
    public boolean updateSubject(int id, String newName, String newDescription) {
        String sql = "UPDATE subjects SET subject_name = ?, description = ? WHERE subject_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setString(2, newDescription);
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Update subject failed: " + e.getMessage());
            return false;
        }
    }

    // DELETE: remove a subject (its exams and questions are removed too)
    public boolean deleteSubject(int id) {
        String sql = "DELETE FROM subjects WHERE subject_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Delete subject failed: " + e.getMessage());
            return false;
        }
    }
}