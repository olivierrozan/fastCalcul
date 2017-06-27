package home.fastcalcul;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuModeAActivity extends AppCompatActivity {

    private Button level1Button, level2Button, level3Button, level4Button, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_mode_a);

        level1Button = (Button) findViewById(R.id.level1);
        level2Button = (Button) findViewById(R.id.level2);
        level3Button = (Button) findViewById(R.id.level3);
        level4Button = (Button) findViewById(R.id.level4);
        backButton = (Button) findViewById(R.id.back);

        level1Button.setOnClickListener(level1Listener);
        level2Button.setOnClickListener(level2Listener);
        level3Button.setOnClickListener(level3Listener);
        level4Button.setOnClickListener(level4Listener);
        backButton.setOnClickListener(backListener);
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }

    /**
     * level1Listener
     * Starts the appli Mode A (Choose 1 level)
     */
    View.OnClickListener level1Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("mode", "1");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    /**
     * level2Listener
     * Starts the appli Mode A (Choose 1 level)
     */
    View.OnClickListener level2Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("mode", "2");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    /**
     * level3Listener
     * Starts the appli Mode A (Choose 1 level)
     */
    View.OnClickListener level3Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("mode", "3");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    /**
     * level4Listener
     * Starts the appli Mode A (Choose 1 level)
     */
    View.OnClickListener level4Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("mode", "4");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    /**
     * backListener
     * Starts the appli Mode A (Choose 1 level)
     */
    View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
        }
    };
}
