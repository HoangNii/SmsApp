package com.colorsms.style.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.colorsms.style.R;
import com.colorsms.style.models.StyleModel;
import com.colorsms.style.utils.Utils;

public class ThemeStyleChatPreviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_INBOX = 0;
    private static final int ITEM_SEND = 1;
    private Context context;
    private StyleModel styleModel;
    private String[] texts = new String[]{
            "Hey! Have you watched the lastest Arsenal game?",
            "Unfortunately, no. A friend of mine was throwing birthday party that night",
            "Oh, me neither",
            "We'll catch up next time",
            "Hope so! =))"
    };

    public ThemeStyleChatPreviewAdapter(Context context, StyleModel styleModel) {
        this.context = context;
        this.styleModel = styleModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==ITEM_INBOX){
            return new InboxHolder(LayoutInflater.from(context).inflate(R.layout.item_chat_preview_inbox,parent,false));
        }else {
            return new SendHolder(LayoutInflater.from(context).inflate(R.layout.item_chat_preview_send,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof InboxHolder){
            InboxHolder inboxHolder = (InboxHolder) holder;
            ((LinearLayout.LayoutParams)inboxHolder.viewAvatar.getLayoutParams()).gravity = styleModel.getAvatarGravity();
            inboxHolder.imgFrameAvatar.setImageResource(styleModel.getAvatarFrameResource());
            inboxHolder.imgContentAvatar.setColorFilter(styleModel.getAvatarContentColor(), PorterDuff.Mode.SRC_IN);
            inboxHolder.viewBb.setBackgroundResource(styleModel.getBbChatInboxResource());
            inboxHolder.tvBb.setText(texts[position]);
            inboxHolder.tvBb.setTextColor(styleModel.getBbChatInboxTextColor());
            if(styleModel.getId()==0){
                inboxHolder.imgFrameAvatar.setColorFilter(styleModel.getStyleColor(), PorterDuff.Mode.SRC_IN);
                inboxHolder.viewBb.getBackground().setColorFilter(styleModel.getBbChatInboxColor(), PorterDuff.Mode.SRC_IN);
            }
            inboxHolder.imgContentAvatar.setPadding(
                    Utils.dpToPixel(styleModel.getAvatarHomeContentPadding()[0]*0.75f,context),
                    Utils.dpToPixel(styleModel.getAvatarHomeContentPadding()[1]*0.75f,context),
                    Utils.dpToPixel(styleModel.getAvatarHomeContentPadding()[2]*0.75f,context),
                    Utils.dpToPixel(styleModel.getAvatarHomeContentPadding()[3]*0.75f,context)
            );

        }else {
            SendHolder sendHolder = (SendHolder) holder;
            sendHolder.viewBb.setBackgroundResource(styleModel.getBbChatSendResource());
            sendHolder.tvBb.setText(texts[position]);
            sendHolder.tvBb.setTextColor(styleModel.getBbChatSendTextColor());
            if(styleModel.getId()==0){
                sendHolder.viewBb.getBackground().setColorFilter(styleModel.getBbChatSendColor(), PorterDuff.Mode.SRC_IN);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position%2==0){
            return ITEM_INBOX;
        }else return ITEM_SEND;
    }

    @Override
    public int getItemCount() {
        if(styleModel.getId()==0||styleModel.getId()==1){
            return texts.length;
        }else return texts.length-2;
    }

    public class InboxHolder extends RecyclerView.ViewHolder {

        private FrameLayout viewAvatar,viewBb;
        private ImageView imgFrameAvatar,imgContentAvatar;
        private TextView tvBb;

        public InboxHolder(@NonNull View itemView) {
            super(itemView);
            viewAvatar = itemView.findViewById(R.id.view_avatar);
            viewBb = itemView.findViewById(R.id.view_bb);
            imgFrameAvatar = itemView.findViewById(R.id.img_avatar_frame);
            imgContentAvatar = itemView.findViewById(R.id.img_avatar_content);
            tvBb = itemView.findViewById(R.id.tv_bb);
        }
    }

    public class SendHolder extends RecyclerView.ViewHolder {
        private FrameLayout viewBb;
        private TextView tvBb;
        public SendHolder(@NonNull View itemView) {
            super(itemView);
            viewBb = itemView.findViewById(R.id.view_bb);
            tvBb = itemView.findViewById(R.id.tv_bb);
        }
    }
}
