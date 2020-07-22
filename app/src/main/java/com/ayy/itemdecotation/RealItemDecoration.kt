package com.ayy.itemdecotation

class RealItemDecoration(mData: List<Star>) : MyItemDecoration<Star>(mData) {
    override fun isHeader(position: Int): Boolean {
        if (data.isEmpty()) {
            return false
        }
        if (position == 0) {
            return true
        }
        val groupName = data.get(position).groupName
        val preGroupName = data.get(position - 1).groupName
        return groupName != preGroupName
    }

    override fun getText(position: Int): String {
        if (data.isEmpty()) {
            return ""
        }
        return data[position].groupName
    }
}