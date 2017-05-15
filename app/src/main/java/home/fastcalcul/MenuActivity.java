package home.fastcalcul;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private Button startButton, exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        startButton = (Button) findViewById(R.id.start);
        exitButton = (Button) findViewById(R.id.quit);

        startButton.setOnClickListener(startListener);
        exitButton.setOnClickListener(exitListener);
    }

    /**
     * startListener
     * Starts the appli
     */
    View.OnClickListener startListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    };

    /**
     * exitListener
     * Quits the appli
     */
    View.OnClickListener exitListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog();
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
        text.setText("Are you sure you want to quit?");

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
