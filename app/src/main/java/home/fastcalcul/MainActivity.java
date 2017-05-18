package home.fastcalcul;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView TSum, TrandomOperand, TGoodAnswers, TBadAnswers, TCountdown;
    private Integer sum, randomOperand, numberOfGoodAnswers, numberOfBadAnswers, buttonIndexWithGoodAnswer;
    private Button[] listButtons;
    private FloatingActionButton fab;
    private boolean play;
    private Integer level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TSum = (TextView) findViewById(R.id.sum);
        TrandomOperand = (TextView) findViewById(R.id.randomOperand);
        TGoodAnswers = (TextView) findViewById(R.id.goodPoints);
        TBadAnswers = (TextView) findViewById(R.id.badPoints);
        TCountdown = (TextView) findViewById(R.id.countdown);

        listButtons = new Button[3];

        listButtons[0] = (Button) findViewById(R.id.choice1);
        listButtons[1] = (Button) findViewById(R.id.choice2);
        listButtons[2] = (Button) findViewById(R.id.choice3);
        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);



        for (Button b: listButtons) {
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

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                TCountdown.setText("Timer: " + millisUntilFinished / 1000);

                if (millisUntilFinished <= 5000) {
                    TCountdown.setTextColor(Color.RED);
                }

            }

            public void onFinish() {
                TCountdown.setText("0");
                play = false;
                finishDialog();
            }
        }.start();

        TGoodAnswers.setText("Correct " + String.valueOf(numberOfGoodAnswers));
        TBadAnswers.setText("Errors " + String.valueOf(numberOfBadAnswers));
        TSum.setText(String.valueOf(sum));

        newCalcul();
    }

    /**
     * newCalcul
     * Generates random numbers for buttons, marker and randomOperand
     */
    public void newCalcul() {
        Random r = new Random();

        randomOperand = r.nextInt(sum - 1) + 1;
        buttonIndexWithGoodAnswer = r.nextInt(3);

        TSum.setText(sum.toString());
        TrandomOperand.setText(String.valueOf(randomOperand));

        int nb = Integer.valueOf(sum) - Integer.valueOf(randomOperand);

        List<Integer> listNumbers = new ArrayList<Integer>();

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
    View.OnClickListener fabListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog();
        }
    };

    /**
     * clickListener
     *
     */
    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick (View view) {

            Random r = new Random();
            Button b = (Button)view;

            if (play) {
                if (Integer.valueOf(((Button) view).getText().toString()) + randomOperand == sum) {
                    numberOfGoodAnswers++;
                    TGoodAnswers.setText("Correct " + numberOfGoodAnswers.toString());
                } else {
                    numberOfBadAnswers++;
                    TBadAnswers.setText("Errors " + numberOfBadAnswers.toString());
                }

                if (numberOfGoodAnswers >= 5 && numberOfGoodAnswers < 9) {
                    level = 2;
                } else if(numberOfGoodAnswers >= 10 && numberOfGoodAnswers < 14) {
                    level = 3;
                } else if(numberOfGoodAnswers >= 15) {
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

                TSum.setText(sum.toString());
                TrandomOperand.setText(String.valueOf(randomOperand));

                newCalcul();
            }
        }
    };

    @Override
    public void onBackPressed() {
        dialog();
    }

    private void dialog() {

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Menu Option");

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
            }
        });

        dialog.show();
    }

    private void beginDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.begin_dialog);
        dialog.setTitle("Start");
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

    private void finishDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.finish_dialog);
        dialog.setTitle("Game Over");
        dialog.setCancelable(false);

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.textDialog);
        text.setText("Restart?");

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
}
