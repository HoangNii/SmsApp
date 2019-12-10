package com.colorsms.style.previews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.colorsms.style.R;
import com.colorsms.style.helper.Style;
import com.colorsms.style.models.StyleModel;
import com.colorsms.style.utils.Utils;

public class ChatPreview extends FrameLayout {

    private FrameLayout viewBubbleInbox,viewBubbleSend;
    private FrameLayout frameIcon;
    private ImageView frameContactIcon,contactIcon;
    private TextView tvInbox,tvSend;
    private StyleModel model = Style.ColorStyle.getStyleModels().get(Style.ColorStyle.getStyleId());

    public ChatPreview(@NonNull Context context) {
        super(context);
        init();
    }


    public ChatPreview(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChatPreview(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.chat_preview,null);
        addView(view);

        viewBubbleInbox = findViewById(R.id.view_bubble_inbox);
        viewBubbleSend = findViewById(R.id.view_bubble_send);
        frameIcon = findViewById(R.id.frame_icon);
        frameContactIcon = findViewById(R.id.frame_contact_icon);
        contactIcon = findViewById(R.id.contact_icon);
        tvInbox = findViewById(R.id.tv_inbox);
        tvSend = findViewById(R.id.tv_send);

        updateStyle();

    }

    public void reloadDefault(){
        try {
            removeAllViews();
            init();
        }catch (Exception ignored){}
    }

    public void updateStyle() {
        if(Style.Bubble.isUseBubbleShapeDefault()){
            int[] bb = Style.Bubble.getBubbleShapeDefault();
            viewBubbleInbox.setBackgroundResource(bb[0]);
            viewBubbleSend.setBackgroundResource(bb[1]);
        }else {
            viewBubbleInbox.setBackgroundResource(model.getBbChatInboxResource());
            viewBubbleSend.setBackgroundResource(model.getBbChatSendResource());

        }
        if(Style.Bubble.getBubbleShapeReceivedColor()!= Color.TRANSPARENT){
            viewBubbleInbox.getBackground().setColorFilter(Style.Bubble.getBubbleShapeReceivedColor(), PorterDuff.Mode.SRC_ATOP);
        }
        if(Style.Bubble.getBubbleShapeSentColor()!= Color.TRANSPARENT){
            viewBubbleSend.getBackground().setColorFilter(Style.Bubble.getBubbleShapeSentColor(), PorterDuff.Mode.SRC_ATOP);
        }


        tvInbox.setTextColor(Style.Bubble.getBubbleTextReceivedColor());
        tvSend.setTextColor(Style.Bubble.getBubbleTextSentColor());

        if(model.getId()==0){
            frameContactIcon.setImageResource(R.drawable.bg_oval);
            frameContactIcon.setColorFilter(Style.Home.getStyleColor());
            contactIcon.setPadding(0,0,0,0);
        }else {
            frameContactIcon.setImageResource(model.getAvatarFrameResource());
            int left = Utils.dpToPixel(model.getAvatarHomeContentPadding()[0]*0.75f,getContext());
            int top = Utils.dpToPixel(model.getAvatarHomeContentPadding()[1]*0.75f,getContext());
            int right = Utils.dpToPixel(model.getAvatarHomeContentPadding()[2]*0.75f,getContext());
            int bottom = Utils.dpToPixel(model.getAvatarHomeContentPadding()[3]*0.75f,getContext());
            contactIcon.setPadding(left,top,right,bottom);
            contactIcon.setColorFilter(model.getAvatarContentColor());
        }

        ((LinearLayout.LayoutParams)frameIcon.getLayoutParams()).gravity = Style.Avatar.getAvatarGravity();
    }


}
