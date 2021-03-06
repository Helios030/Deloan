package  com.neutron.deloan.product

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.neutron.deloan.R
import com.neutron.deloan.bean.ProductsResult
import com.neutron.deloan.utils.Slog
import com.neutron.deloan.utils.UIUtils
import com.neutron.deloan.view.ThemeTextView


class ProductCommAdapter(private val context: Context, private val data: List<ProductsResult>) :
    RecyclerView.Adapter<ProductCommAdapter.SimpleViewHolder>() {

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
        holder.tv_money.text = item.principal
        var index=  position % data.size

        if (selectedPos == index) {
            holder.llmain.background=UIUtils.getDrawable(context,R.drawable.shape_btn_blue)
            holder.llmain.isEnabled = true
            //相同设置高亮
        } else {
            if (item.enable == "2") {
                holder.llmain.background=UIUtils.getDrawable(context,R.drawable.shape_btn_gray)
                holder.llmain.isEnabled = false

            } else {
                holder.llmain.background=UIUtils.getDrawable(context,R.drawable.shape_btn_blue_light)
                holder.llmain.isEnabled = true
            }



        }
        holder.llmain.setOnClickListener {
            btnClickListener?.onClick(item, holder.llmain,index,position)
            refreshItem(index)
        }


    }


    private var selectedPos = 0
    private var oldPos = -1



    fun refreshItem(position: Int) {
        if (selectedPos != -1) {
            oldPos = selectedPos
        }
        selectedPos = position
        notifyDataSetChanged()

    }


    fun setSelectedPos(post:Int){
        selectedPos=post
    }

    private fun getItemByPosition(position: Int): ProductsResult {
        return data[position % data.size]
//        return data[position]
    }

    override fun getItemCount(): Int {
        return if (data.isEmpty()) {
            0
        } else {
            Integer.MAX_VALUE
        }

//        return data.size
    }


    inner class SimpleViewHolder(view1: View) : RecyclerView.ViewHolder(view1) {

//        var tv_lv = view1.findViewById<ThemeTextView>(R.id.tv_lv)
        var tv_money = view1.findViewById<ThemeTextView>(R.id.tv_money)
//        var tv_str1 = view1.findViewById<ThemeTextView>(R.id.tv_str1)
//        var tvNow = view1.findViewById<ThemeTextView>(R.id.tv_now)
        var llmain = view1.findViewById<LinearLayout>(R.id.ll_main)


    }

    interface onBtnClickListener {
        fun onClick(data: ProductsResult,view:View,index:Int,realIndex:Int)
    }

    var btnClickListener: onBtnClickListener? = null

    @JvmName("setBtnClickListener1")
    fun setBtnClickListener(listener: onBtnClickListener) {
        btnClickListener = listener
    }


}