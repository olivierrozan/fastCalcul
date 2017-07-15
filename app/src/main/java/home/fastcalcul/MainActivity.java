package home.fastcalcul;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import home.fastcalcul.Models.HighScores;

public class MainActivity extends AppCompatActivity {

    private TextView TSum, TRandomOperand, TGoodAnswers, TBadAnswers, TCountdown;
    public Integer sum, randomOperand, numberOfGoodAnswers, numberOfBadAnswers, buttonIndexWithGoodAnswer/*, totalDialog*/;
    private Button[] listButtons;
    public boolean play;
    public Integer level;
    private long timer, timeWhenPaused;
    private CountDownTimer countDownTimer;
    private Animation goodAnim, badAnim;
    public String mode;
    public String beginDialogTitle/*, score_name*/;

    private EditText TName;

    private HighScores highScore;

    private static final String PREFS = "PREFS";
    private static final String PREFS_SCORE = "PREFS_SCORE";
    private static final String PREFS_NAME = "PREFS_NAME";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getBaseContext().getSharedPreferences(PREFS, MODE_PRIVATE);
//
//        //objectif : sauvegarder 1 seule fois le nom et l'age de l'utilisateur
//
//        //pour cela, on commence par regarder si on a déjà des éléments sauvegardés
//        if (sharedPreferences.contains(PREFS_SCORE) && sharedPreferences.contains(PREFS_NAME)) {
//
//            int score = sharedPreferences.getInt(PREFS_SCORE, 0);
//            String name = sharedPreferences.getString(PREFS_NAME, null);
//            Toast.makeText(this, name + "    " + score, Toast.LENGTH_SHORT).show();
//
//        } else {
//            //si aucun utilisateur n'est sauvegardé, on ajouter [24,florent]
//            sharedPreferences
//                    .edit()
//                    .putInt(PREFS_SCORE, 24)
//                    .putString(PREFS_NAME, "florent")
//                    .apply();
//
//            Toast.makeText(this, "Sauvegardé, relancez l'application pour voir le résultat", Toast.LENGTH_SHORT).show();
//        }

        //sharedPreferences.edit().clear().apply();

        mode = (String) getIntent().getExtras().get("mode");

        TSum = (TextView) findViewById(R.id.sum);
        TRandomOperand = (TextView) findViewById(R.id.randomOperand);
        TGoodAnswers = (TextView) findViewById(R.id.goodPoints);
        TBadAnswers = (TextView) findViewById(R.id.badPoints);
        TCountdown = (TextView) findViewById(R.id.countdown);

        listButtons = new Button[3];

        listButtons[0] = (Button) findViewById(R.id.choice1);
        listButtons[1] = (Button) findViewById(R.id.choice2);
        listButtons[2] = (Button) findViewById(R.id.choice3);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        for (Button b : listButtons) {
            b.setOnClickListener(clickListener);
        }

        fab.setOnClickListener(fabListener);

        beginDialog();
    }

    public void init() {
        numberOfGoodAnswers = 0;
        numberOfBadAnswers = 0;
        play = true;
        level = 1;
        timer = 5000;
        timeWhenPaused = 0;
        //totalDialog = 0;
        beginDialogTitle = "";
        //score_name = "";
        initCountDownTimer();

        highScore = new HighScores();

        TGoodAnswers.setText("Correct " + String.valueOf(numberOfGoodAnswers));
        TBadAnswers.setText("Errors " + String.valueOf(numberOfBadAnswers));

        goodAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        badAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        newCalcul();
    }

    public void initCountDownTimer() {
        countDownTimer = new CountDownTimer(timer, 1000) {

            public void onTick(long millisUntilFinished) {
                String time = String.valueOf(millisUntilFinished / 1000);
                TCountdown.setText(time);
                TCountdown.setTextColor(Color.WHITE);
                timer = millisUntilFinished;

                if (millisUntilFinished <= 6000) {
                    TCountdown.setTextColor(Color.RED);
                }
            }

            public void onFinish() {
                TCountdown.setText("0");
                play = false;
                timeoutDialog();
            }
        }.start();
    }

    /**
     * newCalcul
     * Generates random numbers for buttons, marker and randomOperand
     */
    public void newCalcul() {
        Random r = new Random();

        switch(mode) {
            case "1":
                sum = 10;
                break;
            case "2":
                sum = 100;
                break;
            case "3":
                sum = r.nextInt(100 - 10) + 10;
                break;
            case "4":
                sum = r.nextInt(1000 - 100) + 100;
                break;
            case "5":
                if (numberOfGoodAnswers == 10) {
                    level = 2;
                } else if (numberOfGoodAnswers == 20) {
                    level = 3;
                } else if (numberOfGoodAnswers == 30) {
                    level = 4;
                }

                switch (level) {
                    case 1:
                        sum = 10;
                        break;
                    case 2:
                        sum = 100;
                        break;
                    case 3:
                        sum = r.nextInt(100 - 10) + 10;
                        break;
                    case 4:
                        sum = r.nextInt(1000 - 100) + 100;
                        break;
                }
                break;
            default:
                break;
        }

        randomOperand = r.nextInt(sum - 1) + 1;
        buttonIndexWithGoodAnswer = r.nextInt(3);

        TSum.setText(String.valueOf(sum));
        TRandomOperand.setText(String.valueOf(randomOperand));


        randomOperand = r.nextInt(sum - 1) + 1;
        buttonIndexWithGoodAnswer = r.nextInt(3);

        TSum.setText(String.valueOf(sum));
        TRandomOperand.setText(String.valueOf(randomOperand));

        int nb = sum - randomOperand;

        List<String> listNumbers = new ArrayList<>();

        for (int i = 1; i < sum - 1; i++) {
            if (i != nb) {
                listNumbers.add(String.valueOf(i));
            }
        }

        Collections.shuffle(listNumbers);

        listButtons[buttonIndexWithGoodAnswer].setText(String.valueOf(nb));

        if (buttonIndexWithGoodAnswer == 0) {
            listButtons[1].setText(String.valueOf(listNumbers.get(0)));
            listButtons[2].setText(String.valueOf(listNumbers.get(1)));
        } else if (buttonIndexWithGoodAnswer == 1) {
            listButtons[0].setText(String.valueOf(listNumbers.get(0)));
            listButtons[2].setText(String.valueOf(listNumbers.get(1)));
        } else if (buttonIndexWithGoodAnswer == 2) {
            listButtons[0].setText(String.valueOf(listNumbers.get(0)));
            listButtons[1].setText(String.valueOf(listNumbers.get(1)));
        }
    }

    /**
     * fabListener
     */
    public final View.OnClickListener fabListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog();
        }
    };

    /**
     * clickListener
     */
    public final View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            Random r = new Random();

            if (play) {
                // If the good answer is chosen
                if (Integer.valueOf(((Button) view).getText().toString()) + randomOperand == sum) {
                    numberOfGoodAnswers++;

                    TGoodAnswers.setVisibility(View.VISIBLE);
                    TGoodAnswers.startAnimation(goodAnim);

                    // Adds 10 seconds to timer every 10 good answers
//                    if (numberOfGoodAnswers % 10 == 0) {
//                        countDownTimer.cancel();
//                        timer += 30000;
//                        initCountDownTimer();
//                    }

                    TGoodAnswers.setText("Correct " + String.valueOf(numberOfGoodAnswers));
                } else {
                    numberOfBadAnswers++;

                    TBadAnswers.setVisibility(View.VISIBLE);
                    TBadAnswers.startAnimation(badAnim);

//                    countDownTimer.cancel();
//                    // Removes 1 second to timer for each wrong answer
//                    timer -= 1000;
//                    initCountDownTimer();

                    TBadAnswers.setText("Errors " + String.valueOf(numberOfBadAnswers));

                    if (numberOfBadAnswers >= 10) {
                        numberOfBadAnswers = 10;
                        play = false;
                        timeWhenPaused = timer;
                        countDownTimer.cancel();
                        finishDialog();
                    }
                }

                newCalcul();
            }
        }
    };

    @Override
    public void onBackPressed() {
        dialog();
    }

    public void dialog() {

        timeWhenPaused = timer;
        countDownTimer.cancel();

        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme)
                .setTitle("Menu Option")
                .setMessage("All progression will be lost. Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        timer = timeWhenPaused;
                        initCountDownTimer();
                    }
                })
                .show();
    }

    public void beginDialog() {

        switch(mode) {
            case "1":
                beginDialogTitle = "10";
                break;
            case "2":
                beginDialogTitle = "100";
                break;
            case "3":
                beginDialogTitle = "Between 10 and 100";
                break;
            case "4":
                beginDialogTitle = "Between 100 and 1000";
                break;
            case "5":
                beginDialogTitle = "All levels";
            default:
                break;
        }

        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme)
                .setTitle(beginDialogTitle)
                .setMessage("Start?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        init();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                    }
                })
                .show();
    }

    public void finishDialog() {

        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme)
                .setTitle("Too much wrong answers!!!")
                .setMessage("Restart?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        init();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                    }
                })
                .show();
    }

    public void timeoutDialog() {

        int totalDialog = numberOfGoodAnswers <= numberOfBadAnswers ? 0 : numberOfGoodAnswers - numberOfBadAnswers;
        highScore.setScore(totalDialog);

        String content = "Good answers \t" + numberOfGoodAnswers;
        content += "\n\nErrors \t" + numberOfBadAnswers;
        content += "\n\nTotal \t" + highScore.getScore();

        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme)
                .setTitle("Out of time!!!")
                .setMessage(content)
                .setCancelable(false)
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        init();
                    }
                })
                .setNeutralButton("Save score", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        highScoreDialog();
                    }
                })
                .setNegativeButton("Back to menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                    }
                })
                .show();

        TextView message = (TextView) alertDialog.findViewById(android.R.id.message);
        message.setTextSize(20);

        Button button1 = (Button) alertDialog.findViewById(android.R.id.button1);
        button1.setTextSize(13);

        Button button2 = (Button) alertDialog.findViewById(android.R.id.button2);
        button2.setTextSize(13);

        Button button3 = (Button) alertDialog.findViewById(android.R.id.button3);
        button3.setTextSize(13);

    }

    public void highScoreDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.high_scores_dialog);

        dialog.setTitle("Enter your name");
        dialog.setCancelable(false);

        TName = (EditText) dialog.findViewById(R.id.high_score_name);
        TName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });

        // Submit when pressing Enter key
        TName.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    highScore.setName(String.valueOf(TName.getText()));
                    dialog.dismiss();

                    afterSaveScoreDialog();
                    return true;
                }
                return false;
            }
        });

        dialog.show();
    }

    public void afterSaveScoreDialog() {
        sharedPreferences
                .edit()
                .putInt(highScore.getName(), highScore.getScore())
                .apply();

        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme)
                .setTitle("Restart?")
                .setMessage("")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        init();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                    }
                })
                .show();
    }
}
