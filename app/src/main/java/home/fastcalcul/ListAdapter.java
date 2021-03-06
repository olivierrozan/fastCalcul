package home.fastcalcul;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by rozan_000 on 10/07/2017.
 */

public class ListAdapter extends BaseAdapter {
    private Context context;
    private final Map<String, Integer> mobileValues;
    List<String> scoresList = new ArrayList<>();

    public ListAdapter(Context context, Map<String, Integer> map) {
        this.context = context;
        this.mobileValues = map;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = inflater.inflate(R.layout.high_score_adapter_list, null);

            for (Map.Entry<String, ?> entry : mobileValues.entrySet()) {
                scoresList.add(entry.getKey() + "," + entry.getValue().toString());
            }

            String n = scoresList.get(position).split(",")[0];
            String s = scoresList.get(position).split(",")[1];

            Integer rank = position + 1;

            TextView name = (TextView) gridView
                    .findViewById(R.id.adapter_name);
            name.setText(rank + ". " + n);

            TextView score = (TextView) gridView
                    .findViewById(R.id.adapter_score);
            score.setText(s);

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return mobileValues.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
