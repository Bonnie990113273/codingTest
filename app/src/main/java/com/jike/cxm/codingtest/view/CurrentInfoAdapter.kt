package com.jike.cxm.codingtest.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jike.cxm.codingtest.R
import com.jike.cxm.codingtest.databinding.LayoutCurrencyItemBinding
import com.jike.cxm.codingtest.model.local.entity.CurrencyEntity
import org.jetbrains.anko.toast

class CurrentInfoAdapter(private val currencyInfoList: List<CurrencyEntity>) :
    RecyclerView.Adapter<CurrentInfoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutCurrencyItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_currency_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: CurrencyEntity = currencyInfoList[position]
        holder.currencyItemBinding.currency = data
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return currencyInfoList.size;
    }

    class ViewHolder(binding: LayoutCurrencyItemBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        var currencyItemBinding: LayoutCurrencyItemBinding = binding

        init {
            currencyItemBinding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v != null) {
                v.context.toast("clicked")
            }
        }
    }
}