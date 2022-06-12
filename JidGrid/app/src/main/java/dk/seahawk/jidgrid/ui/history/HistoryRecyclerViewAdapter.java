package dk.seahawk.jidgrid.ui.history;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import dk.seahawk.jidgrid.util.LocationHistory.PlaceholderItem;
import dk.seahawk.jidgrid.databinding.FragmentHistoryBinding;

import java.util.List;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {

    // List of stored data
    private final List<PlaceholderItem> historyList;

    public HistoryRecyclerViewAdapter(List<PlaceholderItem> items) {
        historyList = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creates new Card
        return new ViewHolder(FragmentHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    // Populates view-fields in Card
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

    // Contain the Views that will be send to the RecyclerView
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