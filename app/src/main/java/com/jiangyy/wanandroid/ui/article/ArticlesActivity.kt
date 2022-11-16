package com.jiangyy.wanandroid.ui.article

import android.content.Context
import android.content.Intent
import com.jiangyy.core.parcelableIntent
import com.jiangyy.core.stringIntent
import com.jiangyy.viewbinding.base.BaseActivity
import com.jiangyy.wanandroid.R
import com.jiangyy.wanandroid.databinding.ActivityArticlesBinding
import com.jiangyy.wanandroid.entity.Tree

class ArticlesActivity : BaseActivity<ActivityArticlesBinding>() {

    private val mTree by parcelableIntent<Tree>("tree")
    private val mType by stringIntent("type")

    override fun initValue() {

    }

    override fun initWidget() {
        binding.toolbar.setTitle(mTree?.name.orEmpty())
        when (mType) {
            "tree" -> supportFragmentManager.beginTransaction().add(R.id.frameLayout, ArticleInTreeFragment.newInstance()).commit()
            "wechat" -> supportFragmentManager.beginTransaction().add(R.id.frameLayout, ArticleInWechatFragment.newInstance()).commit()
        }
    }

    companion object {
        fun actionStart(context: Context, type: String) {
            Intent(context, ArticlesActivity::class.java).apply {
                this.putExtra("type", type)
                context.startActivity(this)
            }
        }

        fun actionStart(context: Context, type: String?, tree: Tree?) {
            Intent(context, ArticlesActivity::class.java).apply {
                this.putExtra("tree", tree)
                this.putExtra("type", type)
                context.startActivity(this)
            }
        }
    }

}