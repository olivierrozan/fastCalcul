package home.fastcalcul;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
            new AlertDialog.Builder(MenuActivity.this)
                    //.setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Lightning Mental Arithmetic")
                    .setMessage("Are you sure you want to quit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    };

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MenuActivity.this)
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Lightning Mental Arithmetic")
                .setMessage("Are you sure you want to quit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
