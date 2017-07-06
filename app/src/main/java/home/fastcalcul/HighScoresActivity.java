package home.fastcalcul;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HighScoresActivity extends AppCompatActivity {

    private EditText TName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_scores_dialog);

        TName = (EditText) findViewById(R.id.high_score_name);

        TName.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String name = String.valueOf(TName.getText());
                    Log.d("AAA", name);
                    finish();
                    return true;
                }
                return false;
            }
        });

    }
}
