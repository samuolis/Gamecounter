package com.example.android.gamecounter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.gamecounter.R.id.change;

public class MainActivity extends AppCompatActivity {

    final Context c = this;
    public String Team1;
    public String Team2;
    public int finalScore1 = 0;
    public int finalScore2 = 0;
    int scoreB = 0;
    int scoreAB = 0;
    int timeout1 = 2;
    int timeout2 = 2;
    int scoreA = 0;
    private Button mButton;
    private Button mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.openUserInputDialog);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);
                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialogBox, int id) {
                                Team1 = userInputDialogEditText.getText().toString();
                                mButton.setText(userInputDialogEditText.getText());
                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });


                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });
        mButton2 = (Button) findViewById(R.id.openUserInputDialog2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);

                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                Team2 = userInputDialogEditText.getText().toString();
                                mButton2.setText(userInputDialogEditText.getText());
                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        scoreA = preferences.getInt("ScoreA", 0);
        scoreB = preferences.getInt("ScoreB", 0);
        Team1 = preferences.getString("Team1", "Team 1");
        Team2 = preferences.getString("Team2", "Team 2");
        finalScore1 = preferences.getInt("finalA", 0);
        finalScore2 = preferences.getInt("finalB", 0);
        displayForTeamA(scoreA);
        displayForTeamB(scoreB);
        mButton.setText(Team1);
        mButton2.setText(Team2);

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("ScoreA", scoreA);
        editor.putInt("ScoreB", scoreB);
        editor.putString("Team1", Team1);
        editor.putString("Team2", Team2);
        editor.putInt("finalA", finalScore1);
        editor.putInt("finalB", finalScore2);
        editor.apply();
    }

    public void displayB(View view) {
        if (scoreA != 21)
            scoreB = scoreB + 1;
        if (scoreB > 21) {
            resetFromJava();
            scoreB = 1;

        }
        if (scoreB == 21) {
            finalScore2 = finalScore2 + 1;
            Toast.makeText(this, "Winner is " + Team2, Toast.LENGTH_LONG).show();

        }
        displayForTeamB(scoreB);

    }

    public void displayForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.TeamBScore);
        scoreView.setText(String.valueOf(score));
        TextView finalView2 = (TextView) findViewById(R.id.final2);
        finalView2.setText(String.valueOf(finalScore2));
        scoreAB = scoreA + scoreB;
        if (scoreB == 21)
            if (scoreB == 21 || scoreA == 21) {
                displayForTeamChange("Game Over");
            } else {
                if (scoreAB % 7 == 0 && scoreAB != 0 && scoreB != 21) {
                    displayForTeamChange("Changing Sides");
                } else displayForTeamChange("Lets Play");
            }
    }

    public void displayA(View view) {
        if (scoreB != 21)
            scoreA = scoreA + 1;
        if (scoreA > 21) {
            resetFromJava();
            scoreA = 1;

        }
        if (scoreA == 21) {

            finalScore1 = finalScore1 + 1;

            Toast.makeText(this, "Winner is " + Team1, Toast.LENGTH_LONG).show();


        }
        displayForTeamA(scoreA);

    }

    public void displayForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.TeamAScore);
        scoreView.setText(String.valueOf(score));
        TextView finalView1 = (TextView) findViewById(R.id.final1);
        finalView1.setText(String.valueOf(finalScore1));
        scoreAB = scoreA + scoreB;
        if (scoreA == 21)
            if (scoreA == 21 || scoreB == 21) {
                displayForTeamChange("Game Over");

            } else {
                if (scoreAB % 7 == 0 && scoreAB != 0 && scoreB != 21) {
                    displayForTeamChange("Changing Sides");
                } else displayForTeamChange("Lets Play");
            }
    }


    public void displayForTeamChange(String score) {
        TextView scoreView = (TextView) findViewById(change);
        scoreView.setText(String.valueOf(score));

    }

    public void displayReset(View view) {
        finalScore2 = 0;
        finalScore1 = 0;
        resetFromJava();
    }

    private void resetFromJava() {
        scoreB = 0;
        scoreA = 0;
        scoreAB = 0;
        timeout1 = 2;
        timeout2 = 2;
        displayForTeamChange("Lets Play");
        displayForTeamA(scoreA);
        displayForTeamB(scoreB);
        Button timeButton2 = (Button) findViewById(R.id.timeoutCount2);
        timeButton2.setText("+" + timeout2);
        Button timeButton1 = (Button) findViewById(R.id.timeoutCount1);
        timeButton1.setText("+" + timeout1);
    }

    public void timeoutsCounting1(View view) {
        timeout1 = timeout1 - 1;
        if (timeout1 <= 0) {
            timeout1 = 0;
            Button timeButton1 = (Button) findViewById(R.id.timeoutCount1);
            timeButton1.setText("" + timeout1);
        } else {

            Button timeButton1 = (Button) findViewById(R.id.timeoutCount1);
            timeButton1.setText("+" + timeout1);
        }

    }

    public void timeoutsCounting2(View view) {
        timeout2 = timeout2 - 1;
        if (timeout2 <= 0) {
            timeout2 = 0;
            Button timeButton2 = (Button) findViewById(R.id.timeoutCount2);
            timeButton2.setText("" + timeout2);
        } else {

            Button timeButton2 = (Button) findViewById(R.id.timeoutCount2);
            timeButton2.setText("+" + timeout2);
        }
    }


}