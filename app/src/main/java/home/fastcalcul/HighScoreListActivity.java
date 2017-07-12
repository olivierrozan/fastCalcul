package home.fastcalcul;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HighScoreListActivity extends AppCompatActivity {

    private GridView liste;
    SharedPreferences sharedPreferences;
    List<String> scoresList = new ArrayList<>();
    private Button hs_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score_list);

        sharedPreferences = getBaseContext().getSharedPreferences("PREFS", MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("AAA", entry.getKey() + ": " + entry.getValue().toString());
            scoresList.add(entry.getKey() + "," + entry.getValue().toString());
        }

        Collections.sort(scoresList);

        hs_back = (Button) findViewById(R.id.hs_back);
        hs_back.setOnClickListener(clickListener);

        liste = (GridView) findViewById(R.id.gridView1);

        ListAdapter adapter = new ListAdapter(this, scoresList);
        liste.setAdapter(adapter);

        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        ((TextView) v.findViewById(R.id.adapter_name))
                                .getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        goToMenu();
    }

    /**
     * clickListener
     */
    public final View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            goToMenu();
        }
    };

    private void goToMenu() {
        finish();
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }
}
