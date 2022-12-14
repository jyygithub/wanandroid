package com.jiangyy.wanandroid.ui.todo

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.jiangyy.core.intArgument
import com.jiangyy.core.orZero
import com.jiangyy.viewbinding.MultipleStateModule
import com.jiangyy.viewbinding.adapter.FooterAdapter
import com.jiangyy.viewbinding.base.BaseLoadFragment
import com.jiangyy.wanandroid.databinding.FragmentTodosBinding
import com.jiangyy.wanandroid.ui.adapter.TodoAdapter
import kotlinx.coroutines.launch

class TodosFragment : BaseLoadFragment<FragmentTodosBinding>(), MultipleStateModule {

    private val mStatus by intArgument("status")

    private val mAdapter = TodoAdapter()

    override fun initValue() {

    }

    override fun initWidget() {
        binding.recyclerView.adapter = mAdapter.withLoadStateFooter(
            FooterAdapter { mAdapter.retry() }
        )
        mAdapter.addLoadStateListener {
            binding.refreshLayout.isRefreshing = false
            when (it.refresh) {
                is LoadState.NotLoading -> preLoadSuccess()
//                is LoadState.Loading -> preLoading()
                is LoadState.Error -> preLoadWithFailure {
                    binding.recyclerView.swapAdapter(mAdapter, true)
                    mAdapter.refresh()
                }

                else -> Unit
            }
        }

        binding.refreshLayout.setOnRefreshListener {
            binding.recyclerView.swapAdapter(mAdapter, true)
            mAdapter.refresh()
        }
    }

    override fun initObserver() {


    }

    override fun preLoad() {
        val viewModel by viewModels<TodosViewModel>()
        lifecycleScope.launch {
            viewModel.pageTodo(mStatus.orZero()).collect { pagingData ->
                mAdapter.submitData(pagingData)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(status: Int) =
            TodosFragment().apply {
                arguments = Bundle().apply {
                    putInt("status", status)
                }
            }
    }
}