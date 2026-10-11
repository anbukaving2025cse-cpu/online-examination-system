package com.onlineexam.util;

import com.onlineexam.dao.QuestionDAO;
import com.onlineexam.model.Question;

public class TestQuestion {
    public static void main(String[] args) {
        Question q = new Question();
        q.setExamId(1);   // 1 = Java Test 1 (use your exam_id if different)
        q.setQuestionText("Which keyword is used to create an object in Java?");
        q.setOptionA("class");
        q.setOptionB("new");
        q.setOptionC("this");
        q.setOptionD("static");
        q.setCorrectOption("B");

        System.out.println("Question added: " + new QuestionDAO().addQuestion(q));
    }
}
