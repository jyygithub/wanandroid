package com.jiangyy.wanandroid.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    var author: String?,
    var shareUser: String?,
    var niceDate: String?,
    var desc: String?,
    var title: String?,
    var envelopePic: String?,
    var chapterName: String?,
    var superChapterName: String?,
    var link: String?,
    var id: String?,
    var originId: String?,
    var collect: Boolean?,
) : Parcelable {
    val itemType: Int get() = if (envelopePic.isNullOrEmpty()) 0 else 1
}