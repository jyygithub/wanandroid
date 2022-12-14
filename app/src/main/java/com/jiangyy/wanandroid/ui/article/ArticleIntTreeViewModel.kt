package com.jiangyy.wanandroid.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jiangyy.wanandroid.entity.Article
import com.jiangyy.wanandroid.logic.API_SERVICE
import com.jiangyy.wanandroid.logic.PageData
import com.jiangyy.wanandroid.logic.netRequest

class ArticleIntTreeViewModel : ViewModel() {

    private val refreshLiveData = MutableLiveData<PageData<Article>>()
    private val loadMoreLiveData = MutableLiveData<PageData<Article>>()
    private val errorLiveData = MutableLiveData<Pair<Throwable, Boolean>>()

    var mPage = 0
    var mCid = ""

    fun firstData(): LiveData<PageData<Article>> {
        return refreshLiveData
    }

    fun loadMoreData(): LiveData<PageData<Article>> {
        return loadMoreLiveData
    }

    fun dataError(): LiveData<Pair<Throwable, Boolean>> {
        return errorLiveData
    }

    fun firstLoad() {
        mPage = 0
        netRequest {
            request { API_SERVICE.pageArticleInTree(mPage, mCid) }
            success { refreshLiveData.value = it }
            error { errorLiveData.value = it to false }
        }
    }

    fun loadMore() {
        netRequest {
            request { API_SERVICE.pageArticleInTree(mPage, mCid) }
            success { loadMoreLiveData.value = it }
            error { errorLiveData.value = it to true }
        }
    }

}