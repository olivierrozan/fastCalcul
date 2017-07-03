package home.fastcalcul;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MenuModeAActivity extends Fragment {

    private Button level1Button, level2Button, level3Button, level4Button, backButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_menu_mode_a, container, false);

        level1Button = (Button) view.findViewById(R.id.level1);
        level2Button = (Button) view.findViewById(R.id.level2);
        level3Button = (Button) view.findViewById(R.id.level3);
        level4Button = (Button) view.findViewById(R.id.level4);
        backButton = (Button) view.findViewById(R.id.back);

        level1Button.setOnClickListener(level1Listener);
        level2Button.setOnClickListener(level2Listener);
        level3Button.setOnClickListener(level3Listener);
        level4Button.setOnClickListener(level4Listener);
        backButton.setOnClickListener(backListener);

        return view;
    }

    /**
     * level1Listener
     * Starts the appli Mode A (Choose 1 level)
     */
    View.OnClickListener level1Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
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
            //finish();
            Intent intent = new Intent(getActivity(), MainActivity.class);
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
            //finish();
            Intent intent = new Intent(getActivity(), MainActivity.class);
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
            //finish();
            Intent intent = new Intent(getActivity(), MainActivity.class);
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
            //finish();
            ((MenuActivity)getActivity()).setViewPager(0);
        }
    };
}