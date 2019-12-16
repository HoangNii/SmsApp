package com.colorsms.style.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.colorsms.style.R;

public abstract class ColorBackgroundSmsPreviewAdapter extends RecyclerView.Adapter<ColorBackgroundSmsPreviewAdapter.ViewHolder> {

    private Context context;

    private int[] colors = new int[]{
            Color.parseColor("#00A99E"),
            Color.parseColor("#3AB54A"),
            Color.parseColor("#2AABE4"),
            Color.parseColor("#0F40F5"),
            Color.parseColor("#F0596C"),
            Color.parseColor("#F8931F"),
            Color.parseColor("#FAFAFA"),
            Color.parseColor("#222222"),
    };

    public ColorBackgroundSmsPreviewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_color_preview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int color = colors[position];
        holder.imgColor.setImageResource(0);
        holder.imgColor.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);


    }

    @Override
    public int getItemCount() {
        return colors.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgColor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgColor = itemView.findViewById(R.id.img_color);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnColorItemClick(colors[getAdapterPosition()]);
                }
            });
            itemView.getLayoutParams().width = context.getResources().getDisplayMetrics().widthPixels/5;
            itemView.getLayoutParams().height = context.getResources().getDisplayMetrics().widthPixels/5;
        }
    }

    public abstract void OnColorItemClick(int color);
}
