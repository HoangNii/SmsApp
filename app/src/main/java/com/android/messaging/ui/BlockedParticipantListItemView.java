/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.messaging.ui;

import android.content.Context;
import androidx.core.text.BidiFormatter;
import androidx.core.text.TextDirectionHeuristicsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.messaging.R;
import com.android.messaging.datamodel.data.ParticipantListItemData;
import com.bumptech.glide.Glide;
import com.colorsms.style.helper.Style;
import com.colorsms.style.models.StyleModel;
import com.colorsms.style.utils.Utils;

/**
 * View for individual participant in blocked participants list.
 *
 * Unblocks participant when clicked.
 */
public class BlockedParticipantListItemView extends LinearLayout {
    private TextView mNameTextView;
    private ContactIconView mContactIconView;
    private ImageView mFrameIcon;
    private TextView mUnblockText;
    private ParticipantListItemData mData;
    private StyleModel model = Style.ColorStyle.getStyleModels().get(Style.ColorStyle.getStyleId());

    public BlockedParticipantListItemView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        mNameTextView = findViewById(R.id.name);
        mContactIconView = findViewById(R.id.contact_icon);
        mFrameIcon = findViewById(R.id.frame_contact_icon);
        mUnblockText = findViewById(R.id.tap_to_unblock);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                mData.unblock(getContext());
            }
        });
    }

    public void bind(final ParticipantListItemData data) {
        mData = data;
        final BidiFormatter bidiFormatter = BidiFormatter.getInstance();
        mNameTextView.setText(bidiFormatter.unicodeWrap(
                data.getDisplayName(), TextDirectionHeuristicsCompat.LTR));
        mContactIconView.setImageResourceUri(data.getAvatarUri(), data.getContactId(),
                    data.getLookupKey(), data.getNormalizedDestination());
        mNameTextView.setText(data.getDisplayName());
        if(model.getId()==0){
            mFrameIcon.setImageResource(0);
        }else {
            Glide.with(mFrameIcon).load(model.getAvatarFrameResource()).into(mFrameIcon);
        }
        int left = Utils.dpToPixel(model.getAvatarHomeContentPadding()[0],getContext());
        int top = Utils.dpToPixel(model.getAvatarHomeContentPadding()[1],getContext());
        int right = Utils.dpToPixel(model.getAvatarHomeContentPadding()[2],getContext());
        int bottom = Utils.dpToPixel(model.getAvatarHomeContentPadding()[3],getContext());
        mContactIconView.setPadding(left,top,right,bottom);
        mUnblockText.setTextColor(Style.Home.getStyleColor());
    }
}
