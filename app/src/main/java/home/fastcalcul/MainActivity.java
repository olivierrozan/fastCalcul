package home.fastcalcul;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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
    private boolean play;

    View.OnClickListener mOnClickListener;

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

        init();

        for (Button b: listButtons) {
            b.setOnClickListener(clickListener);
        }

        resetButton.setOnClickListener(resetListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);

                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

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
}
