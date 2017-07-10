package home.fastcalcul;

import android.app.Dialog;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Menu1Activity extends Fragment {

    private Button startAButton, startBButton, highScoreButton, exitButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.activity_menu1, container, false);

        startAButton = (Button) view.findViewById(R.id.modeA);
        startBButton = (Button) view.findViewById(R.id.modeB);
        highScoreButton = (Button) view.findViewById(R.id.highScoreButton);
        exitButton = (Button) view.findViewById(R.id.quit);

        startAButton.setOnClickListener(startAListener);
        startBButton.setOnClickListener(startBListener);
        highScoreButton.setOnClickListener(highScoreListener);
        exitButton.setOnClickListener(exitListener);

        return view;
    }

    /**
     * startAListener
     * Starts the appli Mode A (Choose 1 level)
     */
    View.OnClickListener startAListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //finish();
            ((MenuActivity)getActivity()).setViewPager(1);
        }
    };

    /**
     * startBListener
     * Starts the appli Mode B (All levels)
     */
    View.OnClickListener startBListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().finish();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("mode", "5");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    /**
     * HighScoreListener
     * HighScore
     */
    View.OnClickListener highScoreListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().finish();
            Intent intent = new Intent(getActivity(), HighScoreListActivity.class);
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
            //dialog();
            getActivity().finish();
        }
    };


//    private void dialog() {
//
//        // custom dialog
//        final Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.custom_dialog);
//        dialog.setTitle("Menu Option");
//
//        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
//        Button dialogButtonNO = (Button) dialog.findViewById(R.id.dialogButtonNO);
//
//        // if button is clicked, close the custom dialog
//        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //finish();
//            }
//        });
//
//        // if button is clicked, close the custom dialog
//        dialogButtonNO.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//    }
}
