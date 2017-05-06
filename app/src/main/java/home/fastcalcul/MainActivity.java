package home.fastcalcul;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Set;

import static android.R.id.undo;

public class MainActivity extends AppCompatActivity {

    private TextView TMarkerNumber, TrandomNumber, TGoodAnswers, TBadAnswers, TCountdown;
    private Integer markerNumber, randomNumber, numberOfGoodAnswers, numberOfBadAnswers, buttonIndexWithGoodAnswer, countdown;
    private Button[] listButtons;
    private Button resetButton;

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

    private void init() {
        numberOfGoodAnswers = 0;
        numberOfBadAnswers = 0;
        countdown = 20;

        Random r = new Random();
        markerNumber = r.nextInt(1000);

        TGoodAnswers.setText("Correct " + String.valueOf(numberOfGoodAnswers));
        TBadAnswers.setText("Errors " + String.valueOf(numberOfBadAnswers));
        TCountdown.setText(String.valueOf(countdown));
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
            Button b = (Button)view;
            String buttonText = b.getText().toString();

            if (countdown > 1) {
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

                countdown--;
                TCountdown.setText(String.valueOf(countdown));
                newCalcul();

            } else {
                TCountdown.setText("STOP");
            }

        }
    };

    public void newCalcul() {
        Random r = new Random();

        randomNumber = r.nextInt(markerNumber - 1) + 1;
        buttonIndexWithGoodAnswer = r.nextInt(3);

        TMarkerNumber.setText("Marker " + markerNumber.toString());
        TrandomNumber.setText(String.valueOf(randomNumber));
        TCountdown.setText(String.valueOf(countdown));

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
