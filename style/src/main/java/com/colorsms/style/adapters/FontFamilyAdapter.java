package com.colorsms.style.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.colorsms.style.R;
import com.colorsms.style.helper.Style;

public abstract class FontFamilyAdapter extends RecyclerView.Adapter<FontFamilyAdapter.ViewHolder> {

    private Context context;

    public FontFamilyAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_font_family,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.tvFontFamily.setText(Style.Font.fontDefaultListName[position]);
            holder.tvFontFamily.setTypeface(Style.Font.getFontTypeFace(position));
    }

    @Override
    public int getItemCount() {
        return Style.Font.fontDefaultList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvFontFamily;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFontFamily = itemView.findViewById(R.id.tv_font_family);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnItemFontFamilyClick(getAdapterPosition());
                }
            });
        }
    }


    public abstract void OnItemFontFamilyClick(int position);
}
