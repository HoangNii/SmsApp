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

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.messaging.R;
import com.android.messaging.ui.UIIntents;
import com.android.messaging.util.DebugUtils;
import com.android.messaging.util.Trace;
import com.colorsms.style.fragments.BackgroundFragment;
import com.colorsms.style.fragments.BubbleThemeFragment;
import com.colorsms.style.fragments.ColorThemeFragment;
import com.colorsms.style.fragments.FontFragment;
import com.colorsms.style.fragments.ThemeStyleFragment;
import com.colorsms.style.helper.Style;
import com.colorsms.style.helper.StyleHelper;
import com.colorsms.style.utils.StoreUtils;
import com.colorsms.style.views.DrawerLayout;

public class ConversationListActivity extends AbstractConversationListActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Trace.beginSection("ConversationListActivity.onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_list_activity);
        Trace.endSection();
        invalidateActionBar();

        mDrawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        updateActionBar(getSupportActionBar());

        setStyleBackground();

        setStyleNavigation();

    }

    private void setStyleNavigation() {
        ViewGroup viewGroup = findViewById(R.id.nav_view);
        StyleHelper.Loader.loadStyleNavigationView(viewGroup);
    }

    @Override
    protected void updateActionBar(final ActionBar actionBar) {
        if(toolbar!=null){
            actionBar.setTitle(getString(R.string.app_name));
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            actionBar.show();

            setStyleToolbar(actionBar);

        }
    }

    private void setStyleBackground() {
        final View root = findViewById(R.id.root);
        View main = findViewById(R.id.main);
        StyleHelper.Loader.loadBackgroundHome(root,main);

    }
    private void setStyleToolbar(ActionBar actionBar) {
        //set style
        int styleColor = Style.Home.getStyleColor();
        int themePosition = Style.ColorStyle.getStyleId();
        int homeTittleColor = Style.Home.getHomeTitleColor();

        actionBar.setBackgroundDrawable(new ColorDrawable(themePosition==0?styleColor: Color.TRANSPARENT));
        toolbar.setTitleTextColor(homeTittleColor);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_menu);
        upArrow.setColorFilter(homeTittleColor, PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(upArrow);

        setOverflowButtonColor(toolbar,homeTittleColor);
    }


    private void setOverflowButtonColor(final Toolbar toolbar, final int color) {
        Drawable drawable = toolbar.getOverflowIcon();
        if(drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable.mutate(), color);
            toolbar.setOverflowIcon(drawable);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Invalidate the menu as items that are based on settings may have changed
        // while not in the app (e.g. Talkback enabled/disable affects new conversation
        // button)
        supportInvalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        int index = getSupportFragmentManager().getBackStackEntryCount();
        if(index>0){
            getSupportFragmentManager().popBackStack();
        }else if(!mDrawer.closeDrawer()){
            if (isInConversationListSelectMode()) {
                exitMultiSelectState();
            } else {
                super.onBackPressed();
            }
        }
    }



    public void OnItemNavigationDrawerClick(View view){
        mDrawer.closeDrawer();
        switch (view.getId()){
            case R.id.nav_home:
                break;
            case R.id.nav_themes:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ThemeStyleFragment.startAddToBackStack(ConversationListActivity.this,ThemeStyleFragment.newInstance());
                    }
                },300);
                break;
            case R.id.nav_theme_color:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onShowColorSelect();
                    }
                },300);
                break;
            case R.id.nav_bubble:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BubbleThemeFragment.startAddToBackStack(ConversationListActivity.this,BubbleThemeFragment.newInstance());
                    }
                },300);
                break;
            case R.id.nav_background:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BackgroundFragment.startAddToBackStack(ConversationListActivity.this,BackgroundFragment.newInstance());
                    }
                },300);
                break;
            case R.id.nav_font:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FontFragment.startAddToBackStack(ConversationListActivity.this,FontFragment.newInstance());
                    }
                },300);
                break;
            case R.id.nav_rate_us:
            case R.id.nav_feedback:
                StoreUtils.goToStore(this,getPackageName());
                break;
            case R.id.nav_share:
                StoreUtils.ShareApp(this,getPackageName());
                break;
        }
    }

    private void onShowColorSelect() {
        ColorThemeFragment fragment = ColorThemeFragment.newInstance();
        fragment.setColorUpdateListener(new ColorThemeFragment.ColorUpdateListener() {
            @Override
            public void onColorUpdate(boolean isDestroy) {
                setStyleBackground();
                setStyleNavigation();
                updateAddButtonColor();
                if(Style.ColorStyle.getStyleId()==0&&isDestroy){
                    final ProgressDialog dialog = new ProgressDialog(ConversationListActivity.this);
                    dialog.setMessage("Update UI...");
                    dialog.setCancelable(false);
                    dialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateListColor();
                            if (!isInConversationListSelectMode()) {
                                updateActionBar(getSupportActionBar());
                            }
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                }
                            },500);
                        }
                    },200);
                }

            }
        });
        ColorThemeFragment.startAddToBackStack(this,fragment);
    }

    @Override
    public void onHomeButtonClick() {
        super.onHomeButtonClick();
        mDrawer.openDrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        if (super.onCreateOptionsMenu(menu)) {
            return true;
        }
        getMenuInflater().inflate(R.menu.conversation_list_fragment_menu, menu);
        final MenuItem item = menu.findItem(R.id.action_debug_options);
        if (item != null) {
            final boolean enableDebugItems = DebugUtils.isDebugEnabled();
            item.setVisible(enableDebugItems).setEnabled(enableDebugItems);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.action_start_new_conversation:
                onActionBarStartNewConversation();
                return true;
            case R.id.action_settings:
                onActionBarSettings();
                return true;
            case R.id.action_debug_options:
                onActionBarDebug();
                return true;
            case R.id.action_show_archived:
                onActionBarArchived();
                return true;
            case R.id.action_show_blocked_contacts:
                onActionBarBlockedParticipants();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    public void onActionBarHome() {
        exitMultiSelectState();
    }

    public void onActionBarStartNewConversation() {
        UIIntents.get().launchCreateNewConversationActivity(this, null);
    }

    public void onActionBarSettings() {
        UIIntents.get().launchSettingsActivity(this);
    }

    public void onActionBarBlockedParticipants() {
        UIIntents.get().launchBlockedParticipantsActivity(this);
    }

    public void onActionBarArchived() {
        UIIntents.get().launchArchivedConversationsActivity(this);
    }

    @Override
    public boolean isSwipeAnimatable() {
        return !isInConversationListSelectMode();
    }

    @Override
    public void onWindowFocusChanged(final boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        final ConversationListFragment conversationListFragment =
                (ConversationListFragment) getFragmentManager().findFragmentById(
                        R.id.conversation_list_fragment);
        // When the screen is turned on, the last used activity gets resumed, but it gets
        // window focus only after the lock screen is unlocked.
        if (hasFocus && conversationListFragment != null) {
            conversationListFragment.setScrolledToNewestConversationIfNeeded();
        }
    }

    private void updateAddButtonColor(){
        final ConversationListFragment conversationListFragment =
                (ConversationListFragment) getFragmentManager().findFragmentById(
                        R.id.conversation_list_fragment);
        if(conversationListFragment!=null){
            conversationListFragment.updateAddButtonColor();
        }
    }

    private void updateListColor(){
        final ConversationListFragment conversationListFragment =
                (ConversationListFragment) getFragmentManager().findFragmentById(
                        R.id.conversation_list_fragment);
        if(conversationListFragment!=null){
            conversationListFragment.updateListColor();
        }
    }
}
