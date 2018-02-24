/*
 * Copyright 2017 Yonghoon Kim
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

package com.pickth.imageslider.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View

/**
 * Created by yonghoon on 2018-01-29
 * Blog   : http://blog.pickth.com
 */

class HorizontalViewPager : ViewPager {
  constructor(context: Context) : super(context) {
    initializeView()
  }

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    initializeView()
  }

  private fun initializeView() {
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    var heightMeasureSpec = heightMeasureSpec
    val mode = View.MeasureSpec.getMode(heightMeasureSpec)

    if(mode == View.MeasureSpec.UNSPECIFIED || mode == View.MeasureSpec.AT_MOST) {
      // super has to be called in the beginning so the child views can be initialized.
      super.onMeasure(widthMeasureSpec, heightMeasureSpec)
      var height = 0
      for(i in 0 until childCount) {
        val child = getChildAt(i)
        child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        val h = child.measuredHeight
        if(h > height) height = h
      }
      heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
    }
    // super has to be called again so the new specs are treated as exact measurements
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
  }
}