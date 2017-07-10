package home.fastcalcul;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class HighScoreListActivity extends AppCompatActivity implements ListAdapter.onViewClick {

    private TextView textWelcome;
    private RecyclerView liste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score_list);

        textWelcome = (TextView) findViewById(R.id.textWelcome);

        textWelcome.setText("Welcome");
        liste = (RecyclerView) findViewById(R.id.listView);

        ListAdapter adapter = new ListAdapter(this);
        liste.setAdapter(adapter);
        adapter.setListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        liste.setLayoutManager(manager);
    }

    @Override
    public void onViewClick(Bundle bundle) {
//        textWelcome.setText(getString(R.string.welcome_msg, bundle.getString("nomSelect"), getString(R.string.app_name)));
    }
}
