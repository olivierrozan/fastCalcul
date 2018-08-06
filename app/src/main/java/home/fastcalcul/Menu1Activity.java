package home.fastcalcul;

import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Menu1Activity extends Fragment {

    private Button startAButton, startBButton, highScoreButton, exitButton;

    private GridView liste;
    SharedPreferences sharedPreferences;
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
            highScoreDialog();
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

        AlertDialog alertDialog = new AlertDialog.Builder(getContext(), R.style.MyDialogTheme)
                .setTitle(R.string.confirmQuit)
                .setMessage("")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void highScoreDialog() {

        // custom dialog
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.activity_high_score_list);
        dialog.setTitle("High Score");

        sharedPreferences = getContext().getSharedPreferences("PREFS", MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        Map<String, Integer> map = new HashMap<String, Integer>();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            map.put(entry.getKey(), (Integer) entry.getValue());
        }

        Map<String, Integer> sortedMap = sortByValue( map );

        hs_back = (Button) dialog.findViewById(R.id.hs_back);

        liste = (GridView) dialog.findViewById(R.id.gridView1);

        ListAdapter adapter = new ListAdapter(getContext(), sortedMap);
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

    private static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

//        int i = 0;
        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
//            if (i < 5) {
                sortedMap.put(entry.getKey(), entry.getValue());
//                i++;
//            }
        }

        return sortedMap;
    }
}