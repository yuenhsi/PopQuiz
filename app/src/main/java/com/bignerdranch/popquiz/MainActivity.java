package com.bignerdranch.popquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private Button mCheatButton;
    private int questionIndex;
    private Question[] questions;
    private View mQuestionTextView;
    private boolean cheated;

    public static final String CHEATED = "cheated";
    public static final String TAG = "MainActivity";
    public static final String QUESTION_INDEX = "index";
    public static final int REQUESTCODE_CHEAT = 0;

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
        if (savedInstanceState != null) {
            questionIndex = savedInstanceState.getInt(QUESTION_INDEX);
            cheated = savedInstanceState.getBoolean(CHEATED);
        } else {
            questionIndex = 0;
            cheated = false;
        }
        updateQuestion();

        mTrueButton = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button)findViewById(R.id.false_button);
        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mPrevButton = (ImageButton)findViewById(R.id.prev_button);
        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mQuestionTextView = findViewById(R.id.question_text);

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CheatActivity.class).putExtra(
                        CheatActivity.IS_ANSWER_TRUE_EXTRA, questions[questionIndex].isAnswer());
                startActivityForResult(i, REQUESTCODE_CHEAT);
            }
        });

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerAndToast(questionIndex, true);
                if (cheated) {
                    Toast.makeText(MainActivity.this, R.string.cheated_toast,Toast.LENGTH_SHORT).show();
                }
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerAndToast(questionIndex, false);
                if (cheated) {
                    Toast.makeText(MainActivity.this, R.string.cheated_toast,Toast.LENGTH_SHORT).show();
                }
            }
        });

        View.OnClickListener incrementIndexAndUpdateListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cheated = false;
                questionIndex = (questionIndex + 1) % questions.length;
                updateQuestion();
            }
        };

        mNextButton.setOnClickListener(incrementIndexAndUpdateListener);
        mQuestionTextView.setOnClickListener(incrementIndexAndUpdateListener);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cheated = false;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUESTCODE_CHEAT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getBooleanExtra(CheatActivity.DID_I_CHEAT_EXTRA, false)) {
                    cheated = true;
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState called.");
        savedInstanceState.putInt(QUESTION_INDEX, questionIndex);
        savedInstanceState.putBoolean(CHEATED, cheated);
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
