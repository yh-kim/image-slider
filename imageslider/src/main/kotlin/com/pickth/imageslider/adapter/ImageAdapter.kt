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

package com.pickth.imageslider.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pickth.imageslider.R
import com.pickth.imageslider.listener.OnImageTouchListener
import kotlinx.android.synthetic.main.slide.view.*
import java.io.File
import java.net.URI

/**
 * Created by yonghoon on 2018-01-29
 * Blog   : http://blog.pickth.com
 */

class ImageAdapter(context: Context, val backColor: Int) : PagerAdapter() {
  private var inflater = LayoutInflater.from(context)
  private var mImageItems = ArrayList<Any>()
  private var mTouchListener: OnImageTouchListener? = null

  fun setOnImageTouchListener(onImageTouchListener: OnImageTouchListener) {
    mTouchListener = onImageTouchListener
  }

  override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
    container?.removeView(`object` as View)
  }

  override fun instantiateItem(container: ViewGroup, position: Int): Any {
    val imageView = inflater.inflate(R.layout.slide, container, false).apply {

      slide_image.setBackgroundColor(backColor)


      Glide.with(context).load(mImageItems[position]).apply(RequestOptions().fitCenter()).into(
          slide_image
      )
      setOnClickListener { mTouchListener?.onClickListener(position) }
      setOnLongClickListener {
        mTouchListener?.onLongClickListener(position)
        true
      }
    }

    container.addView(imageView)
    return imageView
  }

  override fun isViewFromObject(view: View, `object`: Any?): Boolean = view == `object`

  override fun getCount(): Int = mImageItems.size

  fun addItem(item: Any) {
    mImageItems.add(item)
    notifyDataSetChanged()
  }

  fun addItems(items: Collection<Any>) {
    mImageItems.clear()
    mImageItems.addAll(items)
  }
}