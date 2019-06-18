package com.example.travelingagent.util.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.travelingagent.R;
import com.example.travelingagent.activity.Itinerary;
import com.example.travelingagent.entity.Spot;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class FoldingCellListAdapter extends ArrayAdapter<Itinerary> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;

    public FoldingCellListAdapter(Context context, List<Itinerary> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // get item for selected view
        Itinerary itinerary = getItem(position);
        List<Spot> spotList= itinerary.getSpotList();
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
            // binding view parts to view holder
            viewHolder.price = cell.findViewById(R.id.title_price);
            viewHolder.time = cell.findViewById(R.id.title_time_label);
            viewHolder.date = cell.findViewById(R.id.title_date_label);
            viewHolder.fromAddress = cell.findViewById(R.id.title_from_address);
            viewHolder.toAddress = cell.findViewById(R.id.title_to_address);
            viewHolder.spotCount = cell.findViewById(R.id.title_spot_count);
            viewHolder.day = cell.findViewById(R.id.title_day);
            viewHolder.contentRequestBtn = cell.findViewById(R.id.content_request_btn);
            viewHolder.star = cell.findViewById(R.id.title_star);
            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        if (null == itinerary)
            return cell;

        // bind data from selected element to view through view holder
        viewHolder.price.setText(itinerary.getCityName());
        viewHolder.time.setText("");
        viewHolder.date.setText("");
        viewHolder.fromAddress.setText("上海");
        viewHolder.toAddress.setText(itinerary.getCityName());
        viewHolder.spotCount.setText(String.valueOf(itinerary.getSpotNum()));
        viewHolder.day.setText(String.valueOf(itinerary.getSpotNum() / 3 + 1));

        float star = 0;
        for (Spot spot : spotList) {
            if (spot.getType() == 0) {
                star += spot.getTotal() / 20.0;
            }

            else {
                star += spot.getTotal() / 10.0;
            }
        }

        star /= spotList.size();
        String starString = String.valueOf(star);

        viewHolder.star.setText(starString.substring(0, starString.indexOf(".") + 2));

        // set custom btn handler for list item from that item
        if (itinerary.getRequestBtnClickListener() != null) {
            viewHolder.contentRequestBtn.setOnClickListener(itinerary.getRequestBtnClickListener());
        } else {
            // (optionally) add "default" handler if no handler found in item
            viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
        }

        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView price;
        TextView contentRequestBtn;
        TextView day;
        TextView fromAddress;
        TextView toAddress;
        TextView spotCount;
        TextView date;
        TextView time;
        TextView star;
    }
}
