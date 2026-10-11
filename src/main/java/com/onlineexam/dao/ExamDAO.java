package com.onlineexam.dao;

import com.onlineexam.model.Exam;
import com.onlineexam.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExamDAO {

    public boolean addExam(Exam e) {
        String sql = "INSERT INTO exams (exam_title, subject_id, duration_minutes, total_marks, marks_per_question) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getExamTitle());
            ps.setInt(2, e.getSubjectId());
            ps.setInt(3, e.getDurationMinutes());
            ps.setInt(4, e.getTotalMarks());
            ps.setInt(5, e.getMarksPerQuestion());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Add exam failed: " + ex.getMessage());
            return false;
        }
    }

    public List<Exam> getAllExams() {
        List<Exam> list = new ArrayList<>();
        String sql = "SELECT * FROM exams ORDER BY exam_id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Exam e = new Exam();
                e.setExamId(rs.getInt("exam_id"));
                e.setExamTitle(rs.getString("exam_title"));
                e.setSubjectId(rs.getInt("subject_id"));
                e.setDurationMinutes(rs.getInt("duration_minutes"));
                e.setTotalMarks(rs.getInt("total_marks"));
                e.setMarksPerQuestion(rs.getInt("marks_per_question"));
                list.add(e);
            }
        } catch (SQLException ex) {
            System.out.println("Fetch exams failed: " + ex.getMessage());
        }
        return list;
    }
}