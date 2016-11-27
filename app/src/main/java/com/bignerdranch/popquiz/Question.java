package com.bignerdranch.popquiz;

public class Question {

    private int mQuestionId;
    private boolean mAnswer;

    public Question(int questionId, boolean answer) {
        this.mQuestionId = questionId;
        this.mAnswer = answer;
    }

    public int getQuestionId() {
        return mQuestionId;
    }

    public void setQuestionId(int questionId) {
        mQuestionId = questionId;
    }

    public boolean isAnswer() {
        return mAnswer;
    }

    public void setAnswer(boolean answer) {
        mAnswer = answer;
    }
}
