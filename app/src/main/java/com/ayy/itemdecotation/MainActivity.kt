package com.ayy.itemdecotation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val groupNames = arrayOf("快乐家族", "天天兄弟", "浪姐", "婆婆妈妈")
    private val memberNum = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val data = mutableListOf<Star>()
        groupNames.forEach { groupName ->
            for (i in 1..memberNum) {
                val star = Star("$groupName $i", groupName)
                data.add(star)
            }
        }
        rv.addItemDecoration(RealItemDecoration(data))
//        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
//        rv.addItemDecoration(TestItemDecoration())
        rv.adapter = StarAdapter(data)
    }
}