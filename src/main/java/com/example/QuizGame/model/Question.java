package com.example.QuizGame.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {

        this.answers = answers;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getQuestionText() {

        return questionText;
    }

    public void setQuestionText(String questionText) {

        this.questionText = questionText;
    }

}