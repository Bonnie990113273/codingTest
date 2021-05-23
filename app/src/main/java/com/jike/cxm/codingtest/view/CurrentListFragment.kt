package com.jike.cxm.codingtest.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jike.cxm.codingtest.viewmodel.CurrentInfoViewModel
import com.jike.cxm.codingtest.R
import com.jike.cxm.codingtest.databinding.CurrentListFragmentBinding
import com.jike.cxm.codingtest.model.local.entity.CurrencyEntity
import kotlinx.android.synthetic.main.current_list_fragment.*
import org.jetbrains.anko.support.v4.alert

class CurrentListFragment : Fragment(),SwipeRefreshLayout.OnRefreshListener{
    companion object {
        fun newInstance() = CurrentListFragment()
    }

    private lateinit var viewModel: CurrentInfoViewModel
    private var currencyList: ArrayList<CurrencyEntity> = ArrayList()
    private var newCurrencyList:ArrayList<CurrencyEntity> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var currecyInfoAdapter: CurrentInfoAdapter

    //data binding
    private lateinit var binding: CurrentListFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CurrentInfoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.current_list_fragment, container, false)
        recyclerView = binding.currencyRecyclerView
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //下拉刷新颜色
        mSwipeRefresh.setColorSchemeColors(Color.rgb(47, 223, 189))
        mSwipeRefresh.setOnRefreshListener(this)

        obeserver()
    }

    private fun obeserver() {
        viewModel.isError.observe(viewLifecycleOwner, Observer {
            errorDialog(it)
        })
        viewModel.progress.observe(viewLifecycleOwner, Observer {
            progressCurrency.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.currencyListFromDb.observe(viewLifecycleOwner, Observer {
            currencyList.clear()
            newCurrencyList.clear()
            currencyList = it!!.toCollection(currencyList)
            newCurrencyList = currencyList
            for (index in 0 until newCurrencyList.size){
               var firstLetter:String = newCurrencyList[index].id.removeRange(1,newCurrencyList[index].id.length)
               newCurrencyList[index].id = firstLetter
           }
            setRecyclerView()
        })
    }

    private fun setRecyclerView() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            var lastVisibleItem: Int? = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem!! + 1 == currecyInfoAdapter?.itemCount) {
                    addData()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                //最后一个可见的ITEM
                lastVisibleItem = layoutManager.findLastVisibleItemPosition()
            }
        })

        currecyInfoAdapter = CurrentInfoAdapter(newCurrencyList)
        recyclerView.adapter = currecyInfoAdapter
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.setHasFixedSize(true)
        recyclerView.isFocusable = false
    }

    private fun addData() {
        viewModel.onLoadClick()
        recyclerView.adapter?.notifyDataSetChanged()
    }


    private fun errorDialog(errorMsg: String) {
        alert {
            title = getString(R.string.title_error_dialog)
            message = errorMsg
            isCancelable = false
            positiveButton(getString(R.string.btn_ok)) { dialog ->
                dialog.dismiss()
            }
        }.show()
    }

    override fun onRefresh() {
        mSwipeRefresh.isRefreshing = false
        addData()
    }
}
