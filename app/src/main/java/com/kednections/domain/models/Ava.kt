package com.kednections.domain.models

import com.kednections.R

enum class Ava(val imgRes: Int, val imgResSelected: Int) {
    AVA0(R.drawable.img_ava_10, R.drawable.img_ava_10_selected),
    AVA1(R.drawable.img_ava_1, R.drawable.img_ava_1_selected),
    AVA2(R.drawable.img_ava_2, R.drawable.img_ava_2_selected),
    AVA3(R.drawable.img_ava_3, R.drawable.img_ava_3_selected),
    AVA4(R.drawable.img_ava_4, R.drawable.img_ava_4_selected),
    AVA5(R.drawable.img_ava_5, R.drawable.img_ava_5_selected),
    AVA6(R.drawable.img_ava_6, R.drawable.img_ava_6_selected),
    AVA7(R.drawable.img_ava_7, R.drawable.img_ava_7_selected),
    AVA8(R.drawable.img_ava_8, R.drawable.img_ava_8_selected);

    companion object {
        fun fromName(name: String?): Ava =
            Ava.entries.find { it.name == name } ?: AVA0
    }
}