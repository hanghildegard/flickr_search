package com.tori.flickrsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.tori.flickrsearch.presentation.photolist.PhotoListFragment

class MainActivity : AppCompatActivity() {

    private var fragment: PhotoListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragment = if (savedInstanceState == null) {
            PhotoListFragment().also {
                supportFragmentManager.beginTransaction()
                    .add(R.id.navigation_content, it, it.javaClass.name)
                    .commit()
            }
        } else {
            supportFragmentManager.findFragmentByTag(PhotoListFragment::class.java.name) as PhotoListFragment
        }
    }
}