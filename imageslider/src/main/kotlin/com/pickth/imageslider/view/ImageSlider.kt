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
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.pickth.imageslider.R
import com.pickth.imageslider.adapter.ImageAdapter
import com.pickth.imageslider.listener.OnImageTouchListener
import java.io.File
import java.net.URI

/**
 * Created by yonghoon on 2018-01-29
 * Blog   : http://blog.pickth.com
 */

class ImageSlider : FrameLayout {
  val HORIZONTAL = LinearLayout.HORIZONTAL
  val VERTICAL = LinearLayout.VERTICAL

  private lateinit var mAdapter: ImageAdapter
  private lateinit var mPager: ViewPager
  private lateinit var mIndicator: CircleIndicator
  private var mOrientation = HORIZONTAL
  private var indicatorColor = 0
  private var indicatorSelectedColor = 0

  constructor(context: Context) : this(context, null, 0)
  constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    getAttrs(attrs, defStyleAttr)
  }

  private fun initializeView(orientation: Int, backColor: Int) {
    if(orientation == HORIZONTAL) {
      mPager = HorizontalViewPager(context).apply {
        overScrollMode = View.OVER_SCROLL_NEVER
      }
    } else {
      mPager = VerticalViewPager(context).apply {
        overScrollMode = View.OVER_SCROLL_NEVER
      }
    }

    addView(mPager)

    mAdapter = ImageAdapter(context, backColor)
    mPager.adapter = mAdapter
  }

  fun setOnImageTouchListener(onImageTouchListener: OnImageTouchListener) {
    mAdapter.setOnImageTouchListener(onImageTouchListener)
    mAdapter.notifyDataSetChanged()
  }

  fun setIndicator(indicatorColor: Int, indicatorSelectedColor: Int) {
    mIndicator = CircleIndicator(context, getItemCount(), mOrientation, indicatorColor, indicatorSelectedColor).apply {
      // FrameLayout 에 맞추기 위해 LinearLayout.LayoutParams 를 안 씀
      val param = FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
        if(orientation == HORIZONTAL) {
          gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
          bottomMargin = 30
        } else {
          gravity = Gravity.START or Gravity.TOP
          topMargin = 30
          leftMargin = 30
        }

      }
      layoutParams = param
    }
    addView(mIndicator)

    mPager.addOnPageChangeListener(mIndicator)
  }

  fun getItemCount() = mAdapter.count

  private fun getAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageSlider, defStyleAttr, 0)
    setTypeArray(typedArray)
  }

  private fun setTypeArray(typedArray: TypedArray) {
    val imageOrientation = typedArray.getInt(R.styleable.ImageSlider_orientation, HORIZONTAL)
    val backColor = typedArray.getColor(R.styleable.ImageSlider_background_color, ContextCompat.getColor(context, android.R.color.white))
    indicatorColor = typedArray.getColor(R.styleable.ImageSlider_indicator_color, ContextCompat.getColor(context, R.color.colorIndicator))
    indicatorSelectedColor = typedArray.getColor(R.styleable.ImageSlider_indicator_selected_color, ContextCompat.getColor(context, R.color.colorIndicatorSelected))
    mOrientation = imageOrientation
    initializeView(mOrientation, backColor)

    // give it back to cache
    typedArray.recycle()
  }

//  fun addItem(resourceId: Int) {
//    mAdapter.addItem(resourceId)
//  }
//
//  fun addItem(bitmap: Bitmap) {
//    mAdapter.addItem(bitmap)
//  }
//
//  fun addItem(uri: URI) {
//    mAdapter.addItem(uri)
//  }
//
//  fun addItem(drawable: Drawable) {
//    mAdapter.addItem(drawable)
//  }
//
//  fun addItem(string: String) {
//    mAdapter.addItem(string)
//  }
//
//  fun addItem(file: File) {
//    mAdapter.addItem(file)
//  }
//
//  fun addItem(model: Any) {
//    mAdapter.addItem(model)
//  }

  fun addItems(items: Collection<Any>) {
    mAdapter.addItems(items)
    mAdapter.notifyDataSetChanged()

    setIndicator(indicatorColor, indicatorSelectedColor)
  }
}