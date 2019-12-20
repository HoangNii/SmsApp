package com.android.messaging.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.android.messaging.util.AvatarUriUtil;
import com.android.messaging.util.UriUtil;
import com.bumptech.glide.Glide;
import com.colorsms.style.R;
import com.colorsms.style.helper.Style;
import com.colorsms.style.models.StyleModel;
import com.colorsms.style.utils.Utils;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewAvatar2 extends FrameLayout {

    private ImageView imgFrame;
    private CircleImageView imgAvatar;
    private TextView tvText;
    private FrameLayout gravity;

    private StyleModel model = Style.ColorStyle.getStyleModels().get(Style.ColorStyle.getStyleId());

    public ViewAvatar2(@NonNull Context context) {
        super(context);
        init();
    }

    public ViewAvatar2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewAvatar2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_avatar_2,null);
        addView(view);
        imgAvatar = findViewById(R.id.avatar);
        imgFrame = findViewById(R.id.frame);
        tvText = findViewById(R.id.text);
        gravity = findViewById(R.id.gravity);
    }

    public void setImageResourceUri(final Uri uri, final long contactId,
                                    final String contactLookupKey, final String normalizedDestination) {
        if (uri == null) {
            setAvatarUser();
        } else {
            final String avatarType = AvatarUriUtil.getAvatarType(uri);
            if (AvatarUriUtil.TYPE_GROUP_URI.equals(avatarType)) {
                setAvatarUser();
            } else {
                setAvatar(uri);
            }
        }

    }

    private void setAvatar(Uri uri) {
        String avatarType = AvatarUriUtil.getAvatarType(uri);

        final Uri primaryUri = AvatarUriUtil.getPrimaryUri(uri);

        final boolean isLocalResourceUri = UriUtil.isLocalResourceUri(uri) ||
                AvatarUriUtil.TYPE_LOCAL_RESOURCE_URI.equals(avatarType);

        if(primaryUri==null){
            Uri generatedUri = uri;
            if (isLocalResourceUri) {
                // If we are here, we just failed to load the local resource. Use the fallback Uri
                // if possible.
                generatedUri = AvatarUriUtil.getFallbackUri(uri);
                if (generatedUri == null) {
                    // No fallback Uri was provided, use the default avatar.
                    generatedUri = AvatarUriUtil.DEFAULT_BACKGROUND_AVATAR;
                }
            }
            avatarType = AvatarUriUtil.getAvatarType(generatedUri);
            if (AvatarUriUtil.TYPE_LETTER_TILE_URI.equals(avatarType)) {
                final String name = AvatarUriUtil.getName(generatedUri);
                setAvatarName(name);
            } else {
                setAvatarUser();
            }
        }else {
            setAvatarImage(primaryUri);
        }
    }

    private void setAvatarImage(Uri primaryUri) {
        Glide.with(getContext())
                .load(primaryUri)
                .into(imgAvatar);
        tvText.setVisibility(GONE);
        imgAvatar.setVisibility(VISIBLE);
        int left = Utils.dpToPixel(model.getAvatarHomeContentPadding()[0],getContext());
        int top = Utils.dpToPixel(model.getAvatarHomeContentPadding()[1],getContext());
        int right = Utils.dpToPixel(model.getAvatarHomeContentPadding()[2],getContext());
        int bottom = Utils.dpToPixel(model.getAvatarHomeContentPadding()[3],getContext());
        gravity.setPadding(left,top,right,bottom);

        if(model.getId()==0){
            Drawable drawable = getContext().getResources().getDrawable(R.drawable.bg_oval);
            drawable.setColorFilter(Style.Home.getStyleColor(), PorterDuff.Mode.SRC_IN);
            imgFrame.setImageDrawable(drawable);
        }else {
            imgFrame.setImageResource(model.getAvatarFrameResource());
        }
        imgAvatar.setColorFilter(Color.TRANSPARENT);
    }

    private void setAvatarName(String name) {
        if(!TextUtils.isEmpty(name)&&name.length()>1){
            imgAvatar.setImageResource(R.drawable.ic_user);
            tvText.setVisibility(VISIBLE);
            imgAvatar.setVisibility(GONE);
            int left = Utils.dpToPixel(model.getAvatarHomeContentPadding()[0],getContext());
            int top = Utils.dpToPixel(model.getAvatarHomeContentPadding()[1],getContext());
            int right = Utils.dpToPixel(model.getAvatarHomeContentPadding()[2],getContext());
            int bottom = Utils.dpToPixel(model.getAvatarHomeContentPadding()[3],getContext());
            gravity.setPadding(left,top,right,bottom);

            String n = name.substring(0,1);
            tvText.setText(n);
            if(model.getId()==0){
                Drawable drawable = getContext().getResources().getDrawable(R.drawable.bg_oval);
                drawable.setColorFilter(Style.Home.getStyleColor(), PorterDuff.Mode.SRC_IN);
                imgFrame.setImageDrawable(drawable);
                tvText.setTextColor(Color.WHITE);
            }else {
                imgFrame.setImageResource(model.getAvatarFrameResource());
                tvText.setTextColor(model.getAvatarContentColor());
            }
        }else setAvatarUser();


    }

    private void setAvatarUser() {

        imgAvatar.setImageResource(R.drawable.ic_user);
        tvText.setVisibility(GONE);
        imgAvatar.setVisibility(VISIBLE);
        int left = Utils.dpToPixel(model.getAvatarHomeContentPadding()[0],getContext());
        int top = Utils.dpToPixel(model.getAvatarHomeContentPadding()[1],getContext());
        int right = Utils.dpToPixel(model.getAvatarHomeContentPadding()[2],getContext());
        int bottom = Utils.dpToPixel(model.getAvatarHomeContentPadding()[3],getContext());
        gravity.setPadding(left,top,right,bottom);

        if(model.getId()==0){
            Drawable drawable = getContext().getResources().getDrawable(R.drawable.bg_oval);
            drawable.setColorFilter(Style.Home.getStyleColor(), PorterDuff.Mode.SRC_IN);
            imgFrame.setImageDrawable(drawable);
            imgAvatar.setColorFilter(Color.WHITE);
        }else {
            imgFrame.setImageResource(model.getAvatarFrameResource());
            imgAvatar.setColorFilter(model.getAvatarContentColor());
        }


    }


}
