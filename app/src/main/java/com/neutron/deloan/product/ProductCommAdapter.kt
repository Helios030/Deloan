package  com.neutron.deloan.product

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import  com.neutron.deloan.NApplication
import  com.neutron.deloan.R

import  com.neutron.deloan.bean.ProductsResult
import  com.neutron.deloan.utils.UIUtils
import  com.neutron.deloan.view.ThemeTextView


class ProductCommAdapter(private val context: Context, private val data: List<ProductsResult>) :
    RecyclerView.Adapter<ProductCommAdapter.SimpleViewHolder>() {


//    private val mBannerAdapterHelper = BannerAdapterHelper()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {

        val itemView: View = LayoutInflater.from(context)
            .inflate(R.layout.item_products, parent, false)
//        mBannerAdapterHelper.onCreateViewHolder(parent, itemView)
        return SimpleViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
//        mBannerAdapterHelper.onBindViewHolder(holder.itemView, position, itemCount)

        if (data.isEmpty()) {
            return
        }

        var item = getItemByPosition(position)
//        holder.tv_lv.text = "LV:${data.indexOf(item) + 1}"
        holder.tv_money.text = item.principal
//        holder.tv_str1.text = UIUtils.getString(R.string.loan_term).format(item.duration.toString())

//        val tvNow = holder.tvNow
//        if (item.enable == "2") {
//            tvNow.background = (UIUtils.getDrawable(NApplication.sContext, R.drawable.shape_gray))
//            tvNow.setThemTextColor(R.color.bg_color)
//            tvNow.isEnabled = false
//        } else {
//            tvNow.background = (UIUtils.getDrawable(NApplication.sContext, R.drawable.shape_golden))
//            tvNow.setThemTextColor(R.color.golden_80)
//            tvNow.isEnabled = true
//        }

//        tvNow.setOnClickListener {
//            btnClickListener?.onClick(item)
//        }


    }

    private fun getItemByPosition(position: Int): ProductsResult {
        return data[position % data.size]
    }

    override fun getItemCount(): Int {
        return if (data.isEmpty()) {
            0
        } else {
            Integer.MAX_VALUE
        }
    }


    inner class SimpleViewHolder(view1: View) : RecyclerView.ViewHolder(view1) {

//        var tv_lv = view1.findViewById<ThemeTextView>(R.id.tv_lv)
        var tv_money = view1.findViewById<ThemeTextView>(R.id.tv_money)
//        var tv_str1 = view1.findViewById<ThemeTextView>(R.id.tv_str1)
//        var tvNow = view1.findViewById<ThemeTextView>(R.id.tv_now)


    }

    interface onBtnClickListener {
        fun onClick(data: ProductsResult)
    }

    var btnClickListener: onBtnClickListener? = null

    @JvmName("setBtnClickListener1")
    fun setBtnClickListener(listener: onBtnClickListener) {
        btnClickListener = listener
    }


}