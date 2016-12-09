package com.bignerdranch.popquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class CheatActivity extends AppCompatActivity {

    Button mConfirmationButton;

    private boolean cheated;
    public static final String IS_ANSWER_TRUE_EXTRA = "isAnswerTrue";
    public static final String DID_I_CHEAT_EXTRA = "didICheat";
    public static final String CHEATED = "cheated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(CHEATED)) {
                Intent returnIntent = new Intent().putExtra(DID_I_CHEAT_EXTRA, true);
                setResult(Activity.RESULT_OK, returnIntent);
            }
        }
        mConfirmationButton = (Button)findViewById(R.id.cheat_confirmation_button);
        mConfirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        CheatActivity.this,
                        String.valueOf(getIntent().getBooleanExtra(IS_ANSWER_TRUE_EXTRA, false)),
                        Toast.LENGTH_SHORT).show();
                Intent returnIntent = new Intent().putExtra(DID_I_CHEAT_EXTRA, true);
                setResult(Activity.RESULT_OK, returnIntent);
                cheated = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mConfirmationButton.getWidth() / 2;
                    int cy = mConfirmationButton.getHeight() / 2;
                    float radius = mConfirmationButton.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mConfirmationButton, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mConfirmationButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                } else {
                    mConfirmationButton.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(CHEATED, cheated);
    }
}
