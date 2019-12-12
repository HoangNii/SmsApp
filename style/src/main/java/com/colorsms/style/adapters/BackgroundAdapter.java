package com.colorsms.style.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.colorsms.style.R;
import com.colorsms.style.helper.Style;
import com.colorsms.style.models.StyleModel;

public abstract class BackgroundAdapter extends RecyclerView.Adapter<BackgroundAdapter.ViewHolder> {

    private Context context;
    private StyleModel model = Style.ColorStyle.getStyleModels().get(Style.ColorStyle.getStyleId());

    public BackgroundAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_background,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if(position==0){
                holder.viewAdd.setVisibility(View.VISIBLE);
            }else if(position==1) {
                if(model.getId()==0){
                    holder.imgBackground.setBackgroundColor(Color.parseColor("#ECECEC"));
                }else {
                    Glide.with(context)
                            .load(model.getBackground())
                            .into(holder.imgBackground);
                }
                holder.viewAdd.setVisibility(View.GONE);
            }else {
                Glide.with(context)
                        .load(Style.Background.backgroundList[position])
                        .into(holder.imgBackground);
                holder.viewAdd.setVisibility(View.GONE);
            }
    }

    @Override
    public int getItemCount() {
        return Style.Background.backgroundList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgBackground;
        private View viewAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBackground = itemView.findViewById(R.id.img_background);
            viewAdd = itemView.findViewById(R.id.view_add_photo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnItemBackgroundClick(getAdapterPosition());
                }
            });
            itemView.getLayoutParams().height = (int) (context.getResources().getDisplayMetrics().heightPixels/2.3);
            itemView.getLayoutParams().width = (int) (context.getResources().getDisplayMetrics().widthPixels/2.3);
        }
    }


    public abstract void OnItemBackgroundClick(int position);
}
