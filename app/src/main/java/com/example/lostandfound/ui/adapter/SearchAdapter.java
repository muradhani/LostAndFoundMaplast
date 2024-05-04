package com.example.lostandfound.ui.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostandfound.R;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<AutocompletePrediction> predictionsList;

    public SearchAdapter(List<AutocompletePrediction> predictionsList) {
        this.predictionsList = predictionsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AutocompletePrediction prediction = predictionsList.get(position);
        holder.bind(prediction);
    }

    @Override
    public int getItemCount() {
        return predictionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
        }

        public void bind(AutocompletePrediction prediction) {
            textView.setText(prediction.getPrimaryText(null).toString());
        }
    }
}