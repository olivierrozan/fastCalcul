package home.fastcalcul;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rozan_000 on 10/07/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private Context mContext;
    private onViewClick listener;

    List<String> listeAndroid = new ArrayList<>();

    public ListAdapter (Context context){
        this.mContext = context;

        listeAndroid.add("nom 1");
        listeAndroid.add("nom 2");
        listeAndroid.add("nom 3");
        listeAndroid.add("nom 4");
        listeAndroid.add("nom 5");

    }

    public void setListener(onViewClick listener) {
        this.listener = listener;
    }

    public interface onViewClick{
        void onViewClick(Bundle bundle);
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.high_score_adapter_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.name.setText(listeAndroid.get(position));
    }

    @Override
    public int getItemCount() {
        return listeAndroid.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView image;
        public ListViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.adapter_name);
            image =(ImageView) itemView.findViewById(R.id.imageView);

            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    image.setVisibility(View.VISIBLE);
                }
            });

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("nomSelect", name.getText().toString());
                    listener.onViewClick(bundle);
                }
            });
        }
    }
}
