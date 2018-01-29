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
import android.view.MotionEvent
import android.view.View


/**
 * Created by yonghoon on 2018-01-29
 * Blog   : http://blog.pickth.com
 */

class VerticalViewPager : ViewPager {
    constructor(context: Context) : super(context) {
        initializeView()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initializeView()
    }

    private fun initializeView() {
        setPageTransformer(true, VerticalPageTransformer())
    }

    /**
     * Swaps the X and Y coordinates of your touch event.
     */
    private fun swapXY(ev: MotionEvent): MotionEvent {
        val width = width.toFloat()
        val height = height.toFloat()

        val newX = ev.y / height * width
        val newY = ev.x / width * height

        ev.setLocation(newX, newY)

        return ev
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val intercepted = super.onInterceptTouchEvent(swapXY(ev))
        swapXY(ev) // return touch coordinates to original reference frame for any child views
        return intercepted
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return super.onTouchEvent(swapXY(ev))
    }

    private inner class VerticalPageTransformer : ViewPager.PageTransformer {

        override fun transformPage(view: View, position: Float) {

            when {
                (position < -1) -> // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.alpha = 0f
                (position <= 1) -> { // [-1,1]
                    view.alpha = 1f

                    // Counteract the default slide transition
                    view.translationX = view.width * -position

                    //set Y position to swipe in from top
                    val yPosition = position * view.height
                    view.translationY = yPosition

                }
                else -> // (1,+Infinity]
                    // This page is way off-screen to the right.
                    view.alpha = 0f
            }
        }
    }
}