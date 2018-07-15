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

import static android.content.Context.MODE_PRIVATE;

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
    public String beginDialogTitle;

    private EditText TName;

    private HighScores highScore;

    private static final String PREFS = "PREFS";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getBaseContext().getSharedPreferences(PREFS, 0);

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

        beginDialogTitle = "";

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
        Boolean enableSameUnits = false;

        switch(mode) {
            case "1":
                sum = 10;
                break;
            case "2":
                sum = 100;
                enableSameUnits = true;
                break;
            case "3":
                sum = r.nextInt(100 - 50) + 50;
                enableSameUnits = true;
                break;
            case "4":
                sum = r.nextInt(1000 - 100) + 100;
                enableSameUnits = true;
                break;
            case "5":
                if (numberOfGoodAnswers < 10) {
                    sum = 10;
                } else if (numberOfGoodAnswers >= 10 && numberOfGoodAnswers < 20) {
                    sum = 100;
                    enableSameUnits = true;
                } else if (numberOfGoodAnswers >= 20 && numberOfGoodAnswers < 30) {
                    sum = r.nextInt(100 - 50) + 50;
                    enableSameUnits = true;
                } else if (numberOfGoodAnswers >= 30) {
                    sum = r.nextInt(1000 - 101) + 101;
                    enableSameUnits = true;
                }

                break;
            default:
                break;
        }

        randomOperand = r.nextInt(sum - 1) + 1;
        // Index of the correct answer in the list
        buttonIndexWithGoodAnswer = r.nextInt(3);

        TSum.setText(String.valueOf(sum));
        TRandomOperand.setText(String.valueOf(randomOperand));

        // Good answer
        int nb = sum - randomOperand;

        List<String> listNumbers = new ArrayList<String>();
        String sGood = String.valueOf(nb);

        // Adding wrong answers to list
        // total:100, opA:27, good:73
        // => 0 10 20 30 40 50 60 70 80
        // => 3 13 23 33 43 53 63 73 83
        if (enableSameUnits) {
            for (int i = 10; i < sum - 10; i += 10) {
                String s = String.valueOf(i);
                s = s.replace(s.charAt(s.length() - 1), sGood.charAt(sGood.length() - 1));

                if (!s.equals(sGood)) {
                    listNumbers.add(s);
                }
            }
        } else {
            for (int i = 1; i < sum; i ++) {
                String s = String.valueOf(i);

                if (!s.equals(sGood)) {
                    listNumbers.add(s);
                }
            }
        }

        Collections.shuffle(listNumbers);
        // The good answer is inserted into the list randomly
        listNumbers.add(buttonIndexWithGoodAnswer, sGood);

        // The buttons contain the list elements respectively
        for (int i = 0; i < 3; i++) {
            listButtons[i].setText(String.valueOf(listNumbers.get(i)));
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

                    TGoodAnswers.setText("Correct " + String.valueOf(numberOfGoodAnswers));
                } else {
                    numberOfBadAnswers++;

                    TBadAnswers.setVisibility(View.VISIBLE);
                    TBadAnswers.startAnimation(badAnim);

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
