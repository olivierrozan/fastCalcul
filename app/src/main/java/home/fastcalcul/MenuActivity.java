package home.fastcalcul;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button startAButton, startBButton, exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        startAButton = (Button) findViewById(R.id.level1);
        startBButton = (Button) findViewById(R.id.level2);
        exitButton = (Button) findViewById(R.id.quit);

        startAButton.setOnClickListener(startAListener);
        startBButton.setOnClickListener(startBListener);
        exitButton.setOnClickListener(exitListener);
    }

    /**
     * startAListener
     * Starts the appli Mode A (Choose 1 level)
     */
    View.OnClickListener startAListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            Intent intent = new Intent(getApplicationContext(), MenuModeAActivity.class);
            startActivity(intent);
        }
    };

    /**
     * startBListener
     * Starts the appli Mode B (All levels)
     */
    View.OnClickListener startBListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
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
        //showLocationDialog();
    }

    private void dialog() {

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Menu Option");

        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonNO = (Button) dialog.findViewById(R.id.dialogButtonNO);

        // if button is clicked, close the custom dialog
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // if button is clicked, close the custom dialog
        dialogButtonNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showLocationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this, R.style.MyDialogTheme);
        builder.setTitle(getString(R.string.dialog_title));
        //builder.setMessage(getString(R.string.dialog_title));
        builder.setCancelable(false);

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }
}
