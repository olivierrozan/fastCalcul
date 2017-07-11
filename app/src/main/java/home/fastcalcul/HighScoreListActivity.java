package home.fastcalcul;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HighScoreListActivity extends AppCompatActivity {

    private TextView textWelcome;
    private GridView liste;
    SharedPreferences sharedPreferences;
    List<String> scoresList = new ArrayList<>();

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

        textWelcome = (TextView) findViewById(R.id.textWelcome);
        textWelcome.setText("Welcome");

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
}
