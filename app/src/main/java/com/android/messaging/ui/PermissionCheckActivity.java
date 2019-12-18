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

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.android.messaging.R;
import com.android.messaging.util.PhoneUtils;
import com.android.messaging.util.UiUtils;
import com.colorsms.style.helper.Style;

/**
 * Activity to check if the user has required permissions. If not, it will try to prompt the user
 * to grant permissions. However, the OS may not actually prompt the user if the user had
 * previously checked the "Never ask again" checkbox while denying the required permissions.
 */
public class PermissionCheckActivity extends Activity {
    private static final int REQUIRED_PERMISSIONS_REQUEST_CODE = 1;
    private TextView mNextView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (redirectIfNeeded()) {
            return;
        }

        setContentView(R.layout.permission_check_activity);
        UiUtils.setStatusBarColor(this, ContextCompat.getColor(this,R.color.permission_check_activity_background));

        mNextView = findViewById(R.id.next);
        mNextView.getBackground().setColorFilter(Style.Home.getStyleColor(), PorterDuff.Mode.SRC_IN);
        mNextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {

                tryRequestPermission();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

        if (redirectIfNeeded()) {
            return;
        }
    }

    private void tryRequestPermission() {
        startActivityForResult(UIIntentsImpl.get().getChangeDefaultSmsAppIntent(this),REQUIRED_PERMISSIONS_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUIRED_PERMISSIONS_REQUEST_CODE) {
            // We do not use grantResults as some of the granted permissions might have been
            // revoked while the permissions dialog box was being shown for the missing permissions.
            redirectIfNeeded();
        }
    }


    /** Returns true if the redirecting was performed */
    private boolean redirectIfNeeded() {
        if (!PhoneUtils.getDefault().isDefaultSmsApp()) {
            return false;
        }

        redirect();
        return true;
    }

    private void redirect() {
        UIIntents.get().launchConversationListActivity(this);
        finish();
    }
}
