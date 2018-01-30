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
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pickth.imageslider.R
import kotlinx.android.synthetic.main.slide.view.*

/**
 * Created by yonghoon on 2018-01-29
 * Blog   : http://blog.pickth.com
 */

class ImageAdapter(context: Context, val backColor: Int): PagerAdapter() {
    private var inflater = LayoutInflater.from(context)
    private var mIntItems = ArrayList<Int>()

    fun addItems(items: ArrayList<Int>) {
        mIntItems.clear()
        mIntItems.addAll(items)
    }

    fun addItem(item: Int) {
        mIntItems.add(item)
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container?.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = inflater.inflate(R.layout.slide,
                container,
                false).apply {

            slide_image.setBackgroundColor(backColor)
            Glide.with(context)
                    .load(mIntItems[position])
                    .apply(RequestOptions().fitCenter())
                    .into(slide_image)
        }

        container.addView(imageView)
        return imageView
    }

    override fun isViewFromObject(view: View, `object`: Any?): Boolean = view == `object`

    override fun getCount(): Int = mIntItems.size
}