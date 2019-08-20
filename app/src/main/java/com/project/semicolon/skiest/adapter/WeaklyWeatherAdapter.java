package com.project.semicolon.skiest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.semicolon.skiest.model.FakeWeaklyData;
import com.project.semicolon.skiest.R;
import com.project.semicolon.skiest.databinding.WeaklyWeatherListItem;

import java.util.List;

public class WeaklyWeatherAdapter extends RecyclerView.Adapter<WeaklyWeatherAdapter.ViewHolder> {
    private List<FakeWeaklyData> list;
    private Context context;

    public WeaklyWeatherAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<FakeWeaklyData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        WeaklyWeatherListItem weaklyWeatherListItem = WeaklyWeatherListItem.inflate(layoutInflater, parent, false);
        return new ViewHolder(weaklyWeatherListItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));

    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;

        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private WeaklyWeatherListItem binding;

        public ViewHolder(WeaklyWeatherListItem binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(FakeWeaklyData weatherList) {
            binding.dateText.setText(weatherList.getDate());
            binding.maxTemp.setText((weatherList.getMax()) + context.getString(R.string.celsius_degree));
            binding.minTemp.setText(weatherList.getMin() + context.getString(R.string.celsius_degree));

        }
    }
}
