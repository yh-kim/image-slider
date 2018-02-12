package com.pickth.imageslider.example

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.pickth.imageslider.listener.OnImageTouchListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var items = ArrayList<Int>().apply {
            add(R.drawable.habit)
            add(R.drawable.movie_image)
            add(R.drawable.movie_image)
            add(R.drawable.movie_image)
        }

        is_main.setOnImageTouchListener(object: OnImageTouchListener {
            override fun onClickListener(position: Int) {
                // do something
            }

            override fun onLongClickListener(position: Int) {
                // do something
            }
        })

        is_main.addItems(items)
    }
}
