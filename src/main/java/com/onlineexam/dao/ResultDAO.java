package com.onlineexam.dao;

import com.onlineexam.model.Question;
import com.onlineexam.model.Result;
import com.onlineexam.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ResultDAO {

    // Saves the result and every answer together. Either all is saved, or nothing (transaction).
    public boolean saveResult(int studentId, int examId, int correct, int incorrect, int unanswered,
                              int marks, double percentage, List<Question> questions, List<String> chosen) {
        String resultSql = "INSERT INTO results (student_id, exam_id, correct_count, incorrect_count, "
                + "unanswered_count, marks_obtained, percentage) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String answerSql = "INSERT INTO answers (result_id, question_id, selected_option, is_correct) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement ps = con.prepareStatement(resultSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, studentId);
                ps.setInt(2, examId);
                ps.setInt(3, correct);
                ps.setInt(4, incorrect);
                ps.setInt(5, unanswered);
                ps.setInt(6, marks);
                ps.setDouble(7, percentage);
                ps.executeUpdate();

                int resultId;
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    keys.next();
                    resultId = keys.getInt(1);
                }

                try (PreparedStatement pa = con.prepareStatement(answerSql)) {
                    for (int i = 0; i < questions.size(); i++) {
                        String selected = chosen.get(i);
                        boolean isCorrect = selected != null
                                && selected.equals(questions.get(i).getCorrectOption());
                        pa.setInt(1, resultId);
                        pa.setInt(2, questions.get(i).getQuestionId());
                        pa.setString(3, selected);
                        pa.setBoolean(4, isCorrect);
                        pa.addBatch();
                    }
                    pa.executeBatch();
                }
                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                System.out.println("Save result failed: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Save result failed: " + e.getMessage());
            return false;
        }
    }

    // All past results of one student (newest first). JOIN gives the exam title.
    public List<Result> getResultsByStudent(int studentId) {
        List<Result> list = new ArrayList<>();
        String sql = "SELECT r.*, e.exam_title FROM results r "
                + "JOIN exams e ON r.exam_id = e.exam_id "
                + "WHERE r.student_id = ? ORDER BY r.attempted_on DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Result r = new Result();
                    r.setResultId(rs.getInt("result_id"));
                    r.setExamId(rs.getInt("exam_id"));
                    r.setExamTitle(rs.getString("exam_title"));
                    r.setCorrectCount(rs.getInt("correct_count"));
                    r.setIncorrectCount(rs.getInt("incorrect_count"));
                    r.setUnansweredCount(rs.getInt("unanswered_count"));
                    r.setMarksObtained(rs.getInt("marks_obtained"));
                    r.setPercentage(rs.getDouble("percentage"));
                    r.setAttemptedOn(rs.getString("attempted_on"));
                    list.add(r);
                }
            }
        } catch (SQLException e) {
            System.out.println("Fetch results failed: " + e.getMessage());
        }
        return list;
    }
}