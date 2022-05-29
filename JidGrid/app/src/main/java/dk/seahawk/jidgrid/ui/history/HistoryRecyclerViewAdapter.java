package dk.seahawk.jidgrid.ui.history;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import dk.seahawk.jidgrid.util.LocationHistory.PlaceholderItem;
import dk.seahawk.jidgrid.databinding.FragmentHistoryBinding;

import java.util.List;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> historyList;

    public HistoryRecyclerViewAdapter(List<PlaceholderItem> items) {
        historyList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.historyItem = historyList.get(position);
        holder.itemJidView.setText(historyList.get(position).jidId);
        holder.itemLocView.setText(historyList.get(position).locTime);
        holder.itemUtcView.setText(historyList.get(position).utcTime);
        holder.itemAltView.setText(historyList.get(position).alt);
        holder.itemLonView.setText(historyList.get(position).lon);
        holder.itemLatView.setText(historyList.get(position).lat);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView itemJidView, itemUtcView, itemLocView, itemLatView, itemLonView, itemAltView;
        public PlaceholderItem historyItem;

        public ViewHolder(FragmentHistoryBinding binding) {
            super(binding.getRoot());
            itemJidView = binding.itemJidId;
            itemLocView = binding.itemLoc;
            itemUtcView = binding.itemUtc;
            itemLatView = binding.itemLat;
            itemLonView = binding.itemLon;
            itemAltView = binding.itemAlt;
        }
    }

}