package home.fastcalcul;

import android.app.Dialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Menu1Activity extends Fragment {

    private Button startAButton, startBButton, highScoreButton, exitButton;

    private GridView liste;
    SharedPreferences sharedPreferences;
    List<String> scoresList = new ArrayList<>();
    public Button hs_back;

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
            //getActivity().finish();
            highScoreDialog();
//            Intent intent = new Intent(getActivity(), HighScoreListActivity.class);
//            startActivity(intent);
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
            //getActivity().finish();
        }
    };

    private void dialog() {

        // custom dialog
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Menu Option");

        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonNO = (Button) dialog.findViewById(R.id.dialogButtonNO);

        // if button is clicked, close the custom dialog
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
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

    private void highScoreDialog() {

        // custom dialog
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.activity_high_score_list);
        dialog.setTitle("High Score");

        sharedPreferences = getContext().getSharedPreferences("PREFS", MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();

        if (scoresList.isEmpty()) {
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                Log.d("AAA", entry.getKey() + ": " + entry.getValue().toString());
                scoresList.add(entry.getKey() + "," + entry.getValue().toString());
            }
        }

        Collections.sort(scoresList);

        hs_back = (Button) dialog.findViewById(R.id.hs_back);

        liste = (GridView) dialog.findViewById(R.id.gridView1);

        ListAdapter adapter = new ListAdapter(getContext(), scoresList);
        liste.setAdapter(adapter);

        // if button is clicked, close the custom dialog
        hs_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
