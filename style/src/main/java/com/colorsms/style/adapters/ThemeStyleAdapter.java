package com.colorsms.style.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.colorsms.style.R;
import com.colorsms.style.helper.Style;
import com.colorsms.style.models.StyleModel;

import java.util.ArrayList;

public class ThemeStyleAdapter extends RecyclerView.Adapter<ThemeStyleAdapter.ViewHolder> {

    private Context context;
    private ArrayList<StyleModel> styleModels;

    public ThemeStyleAdapter(Context context, ArrayList<StyleModel> styleModels) {
        this.context = context;
        this.styleModels = styleModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_theme_style,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StyleModel model = styleModels.get(position);

        Glide.with(context)
                .load(model.getPreview())
                .into(holder.imgBackground);
        holder.tvName.setText(model.getName());

        if(position== Style.ColorStyle.getStyleId()){
            holder.imgCheck.setImageResource(R.drawable.ic_style_check);
            holder.imgCheck.setColorFilter(Style.Home.getStyleColor(), PorterDuff.Mode.SRC_IN);
        }else {
            holder.imgCheck.setImageResource(R.drawable.ic_style_uncheck);
            holder.imgCheck.setColorFilter(Color.parseColor("#7ADADADA"), PorterDuff.Mode.SRC_IN);
        }

    }

    @Override
    public int getItemCount() {
        return styleModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgBackground;
        private TextView tvName;
        private ImageView imgCheck;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBackground = itemView.findViewById(R.id.img_background);
            tvName = itemView.findViewById(R.id.tv_name);
            imgCheck = itemView.findViewById(R.id.img_check);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnItemStyleClick(getAdapterPosition());
                }
            });
            itemView.getLayoutParams().height = (int) (context.getResources().getDisplayMetrics().heightPixels/2.3);
            itemView.getLayoutParams().width = (int) (context.getResources().getDisplayMetrics().widthPixels/2.3);
        }
    }


    public void OnItemStyleClick(int position){}
}
