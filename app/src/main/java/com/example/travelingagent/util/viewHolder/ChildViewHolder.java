package com.example.travelingagent.util.viewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.travelingagent.R;
import com.example.travelingagent.util.model.DataBean;


/**
 * Created by hbh on 2017/4/20.
 * 子布局ViewHolder
 */

public class ChildViewHolder extends BaseViewHolder {

    private Context mContext;
    private View view;
    private TextView childLeftText;
    private ImageView childRightImage;

    private LinearLayout childContainerLayout;

    public ChildViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.view = itemView;
    }

    public void bindView(final DataBean dataBean, final int pos){

        childLeftText = (TextView) view.findViewById(R.id.child_left_text);
        childRightImage = (ImageView) view.findViewById(R.id.icon);
        childLeftText.setText(dataBean.getChildLeftTxt());
        childRightImage.setImageResource(dataBean.getChildRightImage());

        childContainerLayout = (LinearLayout) view.findViewById(R.id.childContainer);
        //后端整合2  ****点击子类框， 则跳转到这个路线的具体结果（可在地图中显示）****
        childContainerLayout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View View){
//                    Toast.makeText("现在后端要跳转到结果页面", Toast.LENGTH_SHORT).show();

//            Intent mIntent = new Intent();
//
//             startActivity(mIntent);
        } }

        );

    }





}


