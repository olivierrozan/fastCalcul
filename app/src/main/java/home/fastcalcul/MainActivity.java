package home.fastcalcul;

import android.app.Dialog;
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

    private TextView TMarkerNumber, TrandomNumber, TGoodAnswers, TBadAnswers, TCountdown;
    private Integer markerNumber, randomNumber, numberOfGoodAnswers, numberOfBadAnswers, buttonIndexWithGoodAnswer;
    private Button[] listButtons;
    private Button resetButton;
    private FloatingActionButton fab;
    private boolean play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TMarkerNumber = (TextView) findViewById(R.id.marker);
        TrandomNumber = (TextView) findViewById(R.id.randomNumber);
        TGoodAnswers = (TextView) findViewById(R.id.goodPoints);
        TBadAnswers = (TextView) findViewById(R.id.badPoints);
        TCountdown = (TextView) findViewById(R.id.countdown);

        listButtons = new Button[3];

        listButtons[0] = (Button) findViewById(R.id.choice1);
        listButtons[1] = (Button) findViewById(R.id.choice2);
        listButtons[2] = (Button) findViewById(R.id.choice3);
        resetButton = (Button) findViewById(R.id.reset);
        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        init();

        for (Button b: listButtons) {
            b.setOnClickListener(clickListener);
        }

        resetButton.setOnClickListener(resetListener);
        fab.setOnClickListener(fabListener);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                dialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/


    private void init() {
        numberOfGoodAnswers = 0;
        numberOfBadAnswers = 0;
        play = true;

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                TCountdown.setText("Timer: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                TCountdown.setText("0");
                play = false;
            }
        }.start();

        Random r = new Random();
        markerNumber = r.nextInt(1000);

        TGoodAnswers.setText("Correct " + String.valueOf(numberOfGoodAnswers));
        TBadAnswers.setText("Errors " + String.valueOf(numberOfBadAnswers));
        TMarkerNumber.setText(String.valueOf(markerNumber));

        newCalcul();
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
     * resetListener
     */
    View.OnClickListener resetListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            init();
        }
    };

    /**
     * clickListener
     *
     */
    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick (View view) {
            /*MaterialRippleLayout.on(view)
                    .rippleColor(Color.BLACK)
                    .create();*/

            Button b = (Button)view;
            String buttonText = b.getText().toString();

            if (play) {
                if (Integer.valueOf(((Button) view).getText().toString()) + randomNumber == markerNumber) {
                    numberOfGoodAnswers++;
                    TGoodAnswers.setText("Correct " + numberOfGoodAnswers.toString());
                /*Snackbar.make(findViewById(android.R.id.content), buttonText + ": Correct", Snackbar.LENGTH_LONG)
                        .show();*/
                } else {
                    numberOfBadAnswers++;
                    TBadAnswers.setText("Errors " + numberOfBadAnswers.toString());
                /*Snackbar.make(findViewById(android.R.id.content), buttonText + ": Loser", Snackbar.LENGTH_LONG)
                        .show();*/
                }

                newCalcul();

            }
        }
    };

    public void newCalcul() {
        Random r = new Random();

        randomNumber = r.nextInt(markerNumber - 1) + 1;
        buttonIndexWithGoodAnswer = r.nextInt(3);

        TMarkerNumber.setText(markerNumber.toString());
        TrandomNumber.setText(String.valueOf(randomNumber));

        int nb = Integer.valueOf(markerNumber) - Integer.valueOf(randomNumber);

        List<Integer> listNumbers = new ArrayList<Integer>();

        for (int i = 1; i < markerNumber - 1; i++) {
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
}
