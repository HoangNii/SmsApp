package com.colorsms.style.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.colorsms.style.R;
import com.colorsms.style.models.StyleModel;
import com.colorsms.style.utils.Utils;

public class ThemeStyleSmsPreviewAdapter extends RecyclerView.Adapter<ThemeStyleSmsPreviewAdapter.SmsHolder> {

    private Context context;
    private StyleModel styleModel;
    private String[][] listPreviews = new String[][]{
            new String[]{"Justin","Hey! Have you watched the lastest Arsenal game?","4:10 PM"},
            new String[]{"Hana","Hey! Have you watched the lastest Arsenal game?","Friday"},
            new String[]{"1234567890","Hey! Have you watched the lastest Arsenal game?","3/4/2019"},
            new String[]{"1346802","Hey! Have you watched the lastest Arsenal game?","1/4/2019"},
            new String[]{"Jeremy","Hey! Have you watched the lastest Arsenal game?","26/3/2019"},
            new String[]{"Romeo","You: Where are you going?","22/3/2019"},
            new String[]{"Victoria","You: Where are you going?","22/3/2019"},
            new String[]{"Chris","You: Where are you going?","22/3/2019"},
            new String[]{"Lena","You: Where are you going?","22/3/2019"},
    };

    public ThemeStyleSmsPreviewAdapter(Context context, StyleModel styleModel) {
        this.context = context;
        this.styleModel = styleModel;
    }

    @NonNull
    @Override
    public ThemeStyleSmsPreviewAdapter.SmsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SmsHolder(LayoutInflater.from(context).inflate(R.layout.item_sms_preview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeStyleSmsPreviewAdapter.SmsHolder holder, int position) {


        holder.viewCount.getBackground().setColorFilter(styleModel.getUnReadColor(), PorterDuff.Mode.SRC_IN);

        holder.imgContentAvatar.setColorFilter(styleModel.getAvatarContentColor(), PorterDuff.Mode.SRC_IN);

        holder.imgContentAvatar.setPadding(
                Utils.dpToPixel(styleModel.getAvatarHomeContentPadding()[0],context),
                Utils.dpToPixel(styleModel.getAvatarHomeContentPadding()[2],context),
                Utils.dpToPixel(styleModel.getAvatarHomeContentPadding()[1],context),
                Utils.dpToPixel(styleModel.getAvatarHomeContentPadding()[3],context)
        );

        holder.imgFrameAvatar.setImageResource(styleModel.getAvatarFrameResource());

        holder.tvBody.setTextColor(styleModel.getSmsHomeColor());
        holder.tvTime.setTextColor(styleModel.getSmsHomeColor());
        holder.tvName.setTextColor(styleModel.getSmsHomeColor());
        holder.imgArrowEnd.setColorFilter(styleModel.getSmsHomeColor());

        holder.tvName.setText(listPreviews[position][0]);
        holder.tvBody.setText(listPreviews[position][1]);
        holder.tvTime.setText(listPreviews[position][2]);

        holder.viewCount.setVisibility(position>2?View.INVISIBLE:View.VISIBLE);

        if(styleModel.getId()==0){
            holder.imgFrameAvatar.setColorFilter(styleModel.getStyleColor(), PorterDuff.Mode.SRC_IN);
        }

    }

    @Override
    public int getItemCount() {
        return listPreviews.length;
    }

    public class SmsHolder extends RecyclerView.ViewHolder {

        private ImageView imgContentAvatar,imgFrameAvatar;
        private View viewCount;
        private TextView tvName,tvTime,tvBody;
        private ImageView imgArrowEnd;


        public SmsHolder(@NonNull View itemView) {
            super(itemView);
            viewCount = itemView.findViewById(R.id.view_count_sms);
            imgContentAvatar = itemView.findViewById(R.id.img_avatar_content);
            imgFrameAvatar = itemView.findViewById(R.id.img_avatar_frame);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvBody = itemView.findViewById(R.id.tv_body);
            imgArrowEnd = itemView.findViewById(R.id.img_arrow_end);
        }
    }

}
