package com.bignerdranch.popquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private int questionIndex;
    private Question[] questions;
    private View mQuestionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Question questionOne = new Question(R.string.question_one, true);
        Question questionTwo = new Question(R.string.question_two, false);
        Question questionThree = new Question(R.string.question_three, false);
        Question questionFour = new Question(R.string.question_four, true);
        Question questionFive = new Question(R.string.question_five, true);

        questions = new Question[]
                {questionOne, questionTwo, questionThree, questionFour, questionFive};
        questionIndex = 0;

        updateQuestion();

        mTrueButton = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button)findViewById(R.id.false_button);
        mNextButton = (Button)findViewById(R.id.next_button);
        mPrevButton = (Button)findViewById(R.id.prev_button);
        mQuestionTextView = findViewById(R.id.question_text);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerAndToast(questionIndex, true);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerAndToast(questionIndex, false);
            }
        });

        View.OnClickListener incrementIndexAndUpdateListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionIndex = (questionIndex + 1) % questions.length;
                updateQuestion();
            }
        };

        mNextButton.setOnClickListener(incrementIndexAndUpdateListener);
        mQuestionTextView.setOnClickListener(incrementIndexAndUpdateListener);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionIndex = (questionIndex == 0 ? 0 : (questionIndex - 1) % questions.length);
                updateQuestion();
            }
        });
    }

    private void updateQuestion() {
        ((TextView)findViewById(R.id.question_text)).setText(questions[questionIndex].getQuestionId());
    }

    private void checkAnswerAndToast(int questionIndex, boolean selection) {
        if (questions[questionIndex].isAnswer() == selection) {
            Toast.makeText(MainActivity.this, R.string.correct_toast ,Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, R.string.incorrect_toast ,Toast.LENGTH_SHORT).show();
        }
    }
}
