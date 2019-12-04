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
package com.android.messaging.ui.contact;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.messaging.R;
import com.android.messaging.ui.ContactIconView;
import com.android.messaging.ui.UIIntents;
import com.android.messaging.util.AccessibilityUtil;
import com.bumptech.glide.Glide;
import com.colorsms.style.App;
import com.colorsms.style.helper.Style;
import com.colorsms.style.models.StyleModel;
import com.colorsms.style.utils.Utils;

public class AddContactsConfirmationDialog implements DialogInterface.OnClickListener {
    private final Context mContext;
    private final Uri mAvatarUri;
    private final String mNormalizedDestination;

    public AddContactsConfirmationDialog(final Context context, final Uri avatarUri,
            final String normalizedDestination) {
        mContext = context;
        mAvatarUri = avatarUri;
        mNormalizedDestination = normalizedDestination;
    }

    public void show() {
        final int confirmAddContactStringId = R.string.add_contact_confirmation;
        final int cancelStringId = android.R.string.cancel;
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext)
        .setTitle(R.string.add_contact_confirmation_dialog_title)
        .setView(createBodyView())
        .setPositiveButton(confirmAddContactStringId, this)
        .setNegativeButton(cancelStringId, null)
        .create();
        alertDialog.show();
        final Button cancelButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (cancelButton != null) {
            cancelButton.setTextColor(Style.Home.getStyleColor());
        }
        final Button addButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if (addButton != null) {
            addButton.setTextColor(Style.Home.getStyleColor());
        }
    }

    @Override
    public void onClick(final DialogInterface dialog, final int which) {
        UIIntents.get().launchAddContactActivity(mContext, mNormalizedDestination);
    }

    private View createBodyView() {
        final View view = LayoutInflater.from(mContext).inflate(
                R.layout.add_contacts_confirmation_dialog_body, null);
        final ContactIconView iconView = view.findViewById(R.id.contact_icon);
        iconView.setImageResourceUri(mAvatarUri);
        final TextView textView = view.findViewById(R.id.participant_name);
        textView.setText(mNormalizedDestination);
        final ImageView iconFrame = view.findViewById(R.id.frame_contact_icon);
        StyleModel model = Style.ColorStyle.getStyleModels().get(Style.ColorStyle.getStyleId());
        if(model.getId()==0){
            iconFrame.setImageResource(0);
        }else {
            Glide.with(iconFrame).load(model.getAvatarFrameResource()).into(iconFrame);
        }

        int left = Utils.dpToPixel(model.getAvatarHomeContentPadding()[0], App.get());
        int top = Utils.dpToPixel(model.getAvatarHomeContentPadding()[1],App.get());
        int right = Utils.dpToPixel(model.getAvatarHomeContentPadding()[2],App.get());
        int bottom = Utils.dpToPixel(model.getAvatarHomeContentPadding()[3],App.get());
        iconView.setPadding(left,top,right,bottom);
        // Accessibility reason : in case phone numbers are mixed in the display name,
        // we need to vocalize it for talkback.
        final String vocalizedDisplayName = AccessibilityUtil.getVocalizedPhoneNumber(
                mContext.getResources(), mNormalizedDestination);
        textView.setContentDescription(vocalizedDisplayName);
        return view;
    }
}
