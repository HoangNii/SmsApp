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

package com.android.messaging.ui.conversationlist;

import android.content.Context;
import android.database.Cursor;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.messaging.R;
import com.android.messaging.ui.CursorRecyclerAdapter;
import com.android.messaging.ui.conversationlist.ConversationListItemView.HostInterface;
import com.colorsms.style.ads.MyAdmobController;
import com.colorsms.style.helper.Style;

/**
 * Provides an interface to expose Conversation List Cursor data to a UI widget like a ListView.
 */
public class ConversationListAdapter
        extends CursorRecyclerAdapter<ConversationListAdapter.ConversationListViewHolder> {

    private final ConversationListItemView.HostInterface mClivHostInterface;
    private boolean mArchiveMode;
    private View view;

    public ConversationListAdapter(final Context context, final Cursor cursor,
            final ConversationListItemView.HostInterface clivHostInterface,boolean archiveMode) {
        super(context, cursor, 0);
        mClivHostInterface = clivHostInterface;
        setHasStableIds(true);
        mArchiveMode = archiveMode;
        view = LayoutInflater.from(context).inflate(R.layout.native_ads_list,null);
        MyAdmobController.initNativeView(view);
    }


    /**
     * @see com.android.messaging.ui.CursorRecyclerAdapter#bindViewHolder(
     * androidx.recyclerview.widget.RecyclerView.ViewHolder, android.content.Context,
     * android.database.Cursor)
     */
    @Override
    public void bindViewHolder(final ConversationListViewHolder holder, final Context context,
            final Cursor cursor) {
        final ConversationListItemView conversationListItemView = holder.mView;
        conversationListItemView.bind(cursor, mClivHostInterface);
        if(cursor.getPosition()==0){
            FrameLayout ad = conversationListItemView.findViewById(R.id.native_ads);
            try {
                ad.removeAllViews();
                ad.addView(view);
                ((TextView)view.findViewById(R.id.ad_headline)).setTextColor(Style.Background.getHomeTextColor());
                ((TextView)view.findViewById(R.id.ad_body)).setTextColor(Style.Background.getHomeTextColor());
            }catch (Exception e){}
        }else {
            FrameLayout ad = conversationListItemView.findViewById(R.id.native_ads);
            try {
                ad.removeAllViews();
            }catch (Exception e){}
        }
    }

    @Override
    public ConversationListViewHolder createViewHolder(final Context context,
            final ViewGroup parent, final int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        final ConversationListItemView itemView =
                (ConversationListItemView) layoutInflater.inflate(
                        R.layout.conversation_list_item_view, null);
        itemView.setArchiveMode(mArchiveMode);
        return new ConversationListViewHolder(itemView);
    }

    /**
     * ViewHolder that holds a ConversationListItemView.
     */
    public static class ConversationListViewHolder extends RecyclerView.ViewHolder {
        final ConversationListItemView mView;

        public ConversationListViewHolder(final ConversationListItemView itemView) {
            super(itemView);
            mView = itemView;
        }
    }
}
