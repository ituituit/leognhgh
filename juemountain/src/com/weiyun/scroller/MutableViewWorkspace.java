package com.weiyun.scroller;

/*
 * Copyright (C) 2008 The Android Open Source Project
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

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

/**
 * The workspace is a wide area with a wallpaper and a finite number of screens.
 * Each screen contains a number of icons, folders or widgets the user can
 * interact with. A workspace is meant to be used with a fixed width only.
 */
public class MutableViewWorkspace extends Workspace {

	public MutableViewWorkspace(Context context, LinearLayout mTitle) {
		super(context, mTitle);
	}

	@Override
	public void addView(View child) {
		if (this.indexOfChild(child) == -1) {
			super.addView(child);
		}
		int indexOfChild = this.indexOfChild(child);
		if (indexOfChild != this.getCurrentScreen()) {
			this.snapToScreen(indexOfChild);
		}
	}
}