package home.fastcalcul;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView TSum, TRandomOperand, TGoodAnswers, TBadAnswers, TCountdown;
    private Integer sum, randomOperand, numberOfGoodAnswers, numberOfBadAnswers, buttonIndexWithGoodAnswer, totalDialog;
    private Button[] listButtons;
    private boolean play;
    private Integer level;
    private long timer, timeWhenPaused;
    private CountDownTimer countDownTimer;
    private Animation goodAnim, badAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    private void init() {
        numberOfGoodAnswers = 0;
        numberOfBadAnswers = 0;
        play = true;
        level = 1;
        sum = 10;
        timer = 30000;
        timeWhenPaused = 0;
        totalDialog = 0;

        initCountDownTimer();

        TGoodAnswers.setText("Correct " + String.valueOf(numberOfGoodAnswers));
        TBadAnswers.setText("Errors " + String.valueOf(numberOfBadAnswers));
        TSum.setText(String.valueOf(sum));

        goodAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        badAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        newCalcul();
    }

    private void initCountDownTimer() {
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
                finishDialog("Time passed");
            }
        }.start();
    }

    /**
     * newCalcul
     * Generates random numbers for buttons, marker and randomOperand
     */
    private void newCalcul() {
        Random r = new Random();

        randomOperand = r.nextInt(sum - 1) + 1;
        buttonIndexWithGoodAnswer = r.nextInt(3);

        TSum.setText(String.valueOf(sum));
        TRandomOperand.setText(String.valueOf(randomOperand));

        int nb = sum - randomOperand;

        List<Integer> listNumbers = new ArrayList<>();

        for (int i = 1; i < sum - 1; i++) {
            if (i != nb) {
                listNumbers.add(i);
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
    private final View.OnClickListener fabListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog();
        }
    };

    /**
     * clickListener
     */
    private final View.OnClickListener clickListener = new View.OnClickListener() {

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

                    if (numberOfBadAnswers == 10) {
                        timeWhenPaused = timer;
                        countDownTimer.cancel();
                        finishDialog("Too much wrong answers !!!");
                    }
                }

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

                randomOperand = r.nextInt(sum - 1) + 1;
                buttonIndexWithGoodAnswer = r.nextInt(3);

                TSum.setText(String.valueOf(sum));
                TRandomOperand.setText(String.valueOf(randomOperand));

                newCalcul();
            }
        }
    };

    @Override
    public void onBackPressed() {
        dialog();
    }

    private void dialog() {

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

    private void beginDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.begin_dialog);
        //dialog.setTitle("Start");
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
            }
        });

        dialog.show();
    }

    private void finishDialog(String text) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.finish_dialog);
        dialog.setTitle(text);
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

        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonNO = (Button) dialog.findViewById(R.id.dialogButtonNO);

        if (numberOfBadAnswers == 10) {
            totalDialog = 0;

            Window window = dialog.getWindow();
            WindowManager.LayoutParams wlp = window.getAttributes();

            wlp.y = -100;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);
        } else {
            totalDialog = numberOfGoodAnswers <= numberOfBadAnswers ? 0 : numberOfGoodAnswers - numberOfBadAnswers;
        }

        TotalScore.setText(String.valueOf(totalDialog));

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
            }
        });

        dialog.show();
    }
}
