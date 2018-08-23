package com.czh.adapter

import czh.adapter.AnkoMultiAdapter


class Mult : AnkoMultiAdapter.MultiItem {
    override var itemType: Int = 0
    constructor(itemType: Int) {
        this.itemType = itemType
    }
}