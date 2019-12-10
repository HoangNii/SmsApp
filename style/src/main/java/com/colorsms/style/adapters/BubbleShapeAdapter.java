package com.colorsms.style.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.colorsms.style.R;
import com.colorsms.style.helper.Style;

public abstract class BubbleShapeAdapter extends RecyclerView.Adapter<BubbleShapeAdapter.ViewHolder> {

    private Context context;

    private int[][] bubbleDefaultList = Style.Bubble.getBubbleDefaultList();

    public BubbleShapeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bubble_shape,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imgBubble.setBackgroundResource(bubbleDefaultList[position][0]);
        if(position==Style.Bubble.getBubbleShapeDefaultPosition()&&Style.Bubble.isUseBubbleShapeDefault()){
            holder.check.setVisibility(View.VISIBLE);
            holder.imgBubble.getBackground().setColorFilter(Style.Home.getStyleColor(), PorterDuff.Mode.SRC_IN);
        }else {
            holder.check.setVisibility(View.GONE);
            holder.imgBubble.getBackground().setColorFilter(ContextCompat.getColor(context,R.color.colorBubblePreview), PorterDuff.Mode.SRC_IN);
        }

    }

    @Override
    public int getItemCount() {
        return bubbleDefaultList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgBubble;
        private View check;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBubble = itemView.findViewById(R.id.img_bubble);
            check = itemView.findViewById(R.id.img_check);
            imgBubble.setColorFilter(Style.Home.getStyleColor());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnBubbleShapeItemClick(getAdapterPosition());
                }
            });
        }
    }

    public abstract void OnBubbleShapeItemClick(int position);
}
