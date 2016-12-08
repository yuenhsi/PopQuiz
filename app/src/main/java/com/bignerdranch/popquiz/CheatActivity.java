package com.bignerdranch.popquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(CHEATED, cheated);
    }
}
