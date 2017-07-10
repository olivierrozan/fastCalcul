package home.fastcalcul;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
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
            int score = sharedPreferences.getInt(PREFS_SCORE, 0);
            String name = sharedPreferences.getString(PREFS_NAME, null);
            Toast.makeText(this, name + "    " + score, Toast.LENGTH_SHORT).show();
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
        timer = 30000;
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

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Menu Option");
        dialog.setCancelable(false);

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.textDialog);
        text.setText("All progression will be lost. Are you sure you want to exit ?");

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButton2 = (Button) dialog.findViewById(R.id.dialogButtonNO);

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        // if button is clicked, close the custom dialog
        dialogButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                timer = timeWhenPaused;
                initCountDownTimer();
            }
        });

        dialog.show();
    }

    public void beginDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.begin_dialog);

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

        dialog.setTitle(beginDialogTitle);
        dialog.setCancelable(false);

        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonNO = (Button) dialog.findViewById(R.id.dialogButtonNO);

        // if button is clicked, close the custom dialog
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                init();
            }
        });

        // if button is clicked, close the custom dialog
        dialogButtonNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
    }

    public void finishDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.finish_dialog);
        dialog.setTitle("Too much wrong answers!!!");
        dialog.setCancelable(false);

        // set the custom dialog components - text, image and button

        TextView TotalScore = (TextView) dialog.findViewById(R.id.totalDialog);

        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonNO = (Button) dialog.findViewById(R.id.dialogButtonNO);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.y = -100;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        //totalDialog = 0;
        highScore.setScore(0);
        TotalScore.setText(String.valueOf(highScore.getScore()));

        // Restart
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                init();
            }
        });

        // Back to menu
        dialogButtonNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
    }

    public void timeoutDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.timeout_dialog);
        dialog.setTitle("Out of time!!!");
        dialog.setCancelable(false);

        // set the custom dialog components - text, image and button
        TextView good = (TextView) dialog.findViewById(R.id.total_good_title);
        TextView bad = (TextView) dialog.findViewById(R.id.total_bad_title);
        TextView goodScore = (TextView) dialog.findViewById(R.id.total_good_score);
        TextView badScore = (TextView) dialog.findViewById(R.id.total_bad_score);
        TextView TotalScore = (TextView) dialog.findViewById(R.id.totalDialog);

        good.setText("Good answers");
        goodScore.setText(String.valueOf(numberOfGoodAnswers));
        bad.setText("Errors");
        badScore.setText(String.valueOf(numberOfBadAnswers));

        Button noHighScore = (Button) dialog.findViewById(R.id.no_highScore);
        Button saveHighScore = (Button) dialog.findViewById(R.id.highScore);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.y = -100;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        int totalDialog = numberOfGoodAnswers <= numberOfBadAnswers ? 0 : numberOfGoodAnswers - numberOfBadAnswers;
        highScore.setScore(totalDialog);

        TotalScore.setText(String.valueOf(totalDialog));

        // no highscore, redirects to back/restart dialog
        noHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                saveScoreDialog();
            }
        });

        // highscore, redirects to highscore input dialog, then to back/restart dialog
        saveHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                dialog.dismiss();
                highScoreDialog();

            }
        });

        dialog.show();
    }

    public void highScoreDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.high_scores_dialog);

        dialog.setTitle("Enter your name");
        dialog.setCancelable(false);

        final EditText TName = (EditText) dialog.findViewById(R.id.high_score_name);

        TName.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //score_name = String.valueOf(TName.getText());
                    highScore.setName(String.valueOf(TName.getText()));
                    dialog.dismiss();

                    saveScoreDialog();
                    return true;
                }
                return false;
            }
        });

        dialog.show();
    }

    public void saveScoreDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.score_saved_dialog);

        dialog.setTitle("Restart");
        dialog.setCancelable(false);

        Button back = (Button) dialog.findViewById(R.id.score_back);
        Button restart = (Button) dialog.findViewById(R.id.score_restart);


        sharedPreferences
                .edit()
                .putInt(highScore.getName(), highScore.getScore())
                .apply();

        // Restart
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                init();
            }
        });

        // Back to menu
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
    }
}
