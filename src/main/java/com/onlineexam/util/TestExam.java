package com.onlineexam.util;

import com.onlineexam.dao.ExamDAO;
import com.onlineexam.model.Exam;

public class TestExam {
    public static void main(String[] args) {
        Exam e = new Exam();
        e.setExamTitle("Java Test 1");
        e.setSubjectId(1);
        e.setDurationMinutes(10);
        e.setTotalMarks(5);
        e.setMarksPerQuestion(1);

        System.out.println("Exam added: " + new ExamDAO().addExam(e));
    }
}