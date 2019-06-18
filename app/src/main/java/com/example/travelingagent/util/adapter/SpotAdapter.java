package com.example.travelingagent.util.adapter;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.travelingagent.myentity.Spot;
import com.telenav.expandablepager.adapter.ExpandablePagerAdapter;
import com.example.travelingagent.R;

public class SpotAdapter extends ExpandablePagerAdapter<Spot> {
    public SpotAdapter(List<Spot> items) {
        super(items);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ViewGroup rootView = (ViewGroup) LayoutInflater.from(container.getContext()).inflate(R.layout.page, container, false);
        rootView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        Spot currentSpot = items.get(position);
//        ((TextView) rootView.findViewById(R.id.text)).setText(items.get(position).getName());
        ((TextView) rootView.findViewById(R.id.header_title)).setText(currentSpot.getName());
        ((TextView) rootView.findViewById(R.id.header_subtitle)).setText("第" + String.valueOf((position) / 3 + 1) +"天");

        ImageView imageView = rootView.findViewById(R.id.word_cloud_image);

        ImageView iconView = rootView.findViewById(R.id.header_action);

        if (currentSpot.getType() == 0) {
            imageView.setImageResource(currentSpot.getWordCloudResourceID());
            iconView.setImageResource(currentSpot.getIconResourceID());
        }
        else {
            if (currentSpot.getType() == 1) {
                imageView.setImageResource(currentSpot.getWordCloudResourceID());
                iconView.setImageResource(currentSpot.getIconResourceID());
            }
        }

        TextView rating = rootView.findViewById(R.id.page_rating);
        if (currentSpot.getType() == 0) {
            String rate = String.valueOf(currentSpot.getTotal() / 20.0);
            rate = rate.substring(0, rate.indexOf(".") + 2);
            rating.setText(rate + " / 5.0\n 评分");
        }
        else {
            String rate = String.valueOf(currentSpot.getTotal() / 10.0);
            rate = rate.substring(0, rate.indexOf(".") + 2);
            rating.setText(rate + " / 5.0\n 评分");
        }
        setSpan(rating, "\\d\\.\\d / \\d\\.\\d");

        TextView popularity = rootView.findViewById(R.id.page_popularity);
        popularity.setText(String.valueOf((int) currentSpot.getPopularity()) + "\n人气指数");
        setSpan(popularity, "\\d+");

        TextView descirption = rootView.findViewById(R.id.description);
        descirption.setText(currentSpot.getDescription());

        TextView money = rootView.findViewById(R.id.page_money);

        if (currentSpot.getType() == 0) {
            money.setText(String.valueOf((int) currentSpot.getMoney())+ "\n门票");
        }
        else {
            money.setText(String.valueOf((int) currentSpot.getMoney())+ "\n均价");
        }
        setSpan(money, "\\d+");

        return attach(container, rootView, position);
    }

    private void setSpan(TextView textView, String pattern) {
        float relativeSize = 1.5f;
        Pattern pat = Pattern.compile(pattern);
        Matcher m = pat.matcher(textView.getText());
        if (m.find()) {
            SpannableString span = new SpannableString(textView.getText());
            span.setSpan(new RelativeSizeSpan(relativeSize), 0, m.group(0).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(span);
        }
    }

}