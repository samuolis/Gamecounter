package com.example.android.gamecounter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;


import static com.example.android.gamecounter.R.id.change;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    final Context c = this;
    private Button mButton2;
    public String Team1;
    public String Team2;

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
                                Team1=userInputDialogEditText.getText().toString();
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
                                Team2=userInputDialogEditText.getText().toString();
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
        Team1=preferences.getString("Team1", "Team 1");
        Team2=preferences.getString("Team2", "Team 2");
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
        editor.apply();
    }



    int scoreB = 0;
    int scoreAB = 0;

    public void displayB(View view) {
        if (scoreA!=21)
            scoreB = scoreB + 1;
        if (scoreB>21)
            scoreB=21;
        displayForTeamB(scoreB);
    }

    public void displayForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.TeamBScore);
        scoreView.setText(String.valueOf(score));
        scoreAB = scoreA + scoreB;
        if(scoreB==21)
            Toast.makeText(this, "Winner is "+ Team2, Toast.LENGTH_LONG).show();
        if (scoreB == 21||scoreA==21) {
            displayForTeamChange("Game Over");
        } else {
            if (scoreAB % 7 == 0 && scoreAB != 0 && scoreB != 21) {
                displayForTeamChange("Changing Sides");
            } else displayForTeamChange("Lets Play");
        }
    }

    int scoreA = 0;

    public void displayA(View view) {
        if (scoreB!=21)
            scoreA = scoreA + 1;
        if (scoreA>21)
            scoreA=21;
        displayForTeamA(scoreA);
    }

    public void displayForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.TeamAScore);
        scoreView.setText(String.valueOf(score));
        scoreAB = scoreA + scoreB;
        if (scoreA== 21)
            Toast.makeText(this, "Winner is "+ Team1, Toast.LENGTH_LONG).show();
        if (scoreA == 21||scoreB==21) {
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
        scoreB = 0;
        scoreA = 0;
        scoreAB = 0;
        displayForTeamChange("Lets Play");
        displayForTeamA(scoreA);
        displayForTeamB(scoreB);
    }



}







