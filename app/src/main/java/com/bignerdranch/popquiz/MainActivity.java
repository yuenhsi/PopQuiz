package com.bignerdranch.popquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private int questionIndex;
    private Question[] questions;
    private View mQuestionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called.");
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
        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mPrevButton = (ImageButton)findViewById(R.id.prev_button);
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

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called.");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called.");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called.");
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
