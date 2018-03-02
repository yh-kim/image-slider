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
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.pickth.imageslider.R
import org.jetbrains.anko.backgroundDrawable

/**
 * Created by yonghoon on 2018-01-29
 * Blog   : http://blog.pickth.com
 */

class CircleIndicator(context: Context, count: Int = 0, circleOrientation: Int, val indicatorColor: Int, val indicatorSelectedColor: Int) :
    LinearLayout(context, null, 0),
    ViewPager.OnPageChangeListener {
  private var isFirst = true
  private val mCircleViews = ArrayList<ImageView>()
  private var size = 15
  private var itemCount = count
  private var circleView = ContextCompat.getDrawable(context, R.drawable.circle_view).apply { (this as? GradientDrawable)?.setColor(indicatorColor) }
  private var circleSelectedView = ContextCompat.getDrawable(context, R.drawable.circle_view).apply { (this as? GradientDrawable)?.setColor(indicatorSelectedColor) }

  init {
    orientation = circleOrientation
    initializeView()
  }

  private fun initializeView() {
    addItems(itemCount)
  }

  fun addItems(count: Int) {
    for(i in 0 until count) {
      addItem()
    }
  }

  fun addItem() {
    val ivCircle = ImageView(context).apply {
      backgroundDrawable = circleView
    }
    setItemViewParam(ivCircle, size)

    addView(ivCircle)
    mCircleViews.add(ivCircle)
  }

  fun removeAllItems() {
    mCircleViews.clear()
    removeAllViews()
    itemCount = 0
  }

  private fun setItemViewParam(view: View, size: Int) {
    val param = LinearLayout.LayoutParams(size, size)
//        val margin = size/10

    if(orientation == LinearLayout.VERTICAL) {
      val margin = 12
      param.setMargins(0, margin, 0, margin)
    } else {
      val margin = 15
      param.setMargins(margin, 0, margin, 0)
    }

    view.layoutParams = param
  }

  override fun onPageScrollStateChanged(state: Int) {
  }

  override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    if(isFirst && positionOffset == 0f && positionOffsetPixels == 0) {
      onPageSelected(0)
      isFirst = false
    }
  }

  override fun onPageSelected(position: Int) {
    for(item in mCircleViews) {
      item.backgroundDrawable = circleView
    }

    mCircleViews[position].backgroundDrawable = circleSelectedView
  }
}