package  com.neutron.deloan.product

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.neutron.deloan.R
import com.neutron.deloan.bean.ProductsResult


class ProductAdapter(layoutResId: Int, data: MutableList<ProductsResult>) :
    BaseQuickAdapter<ProductsResult, BaseViewHolder>(layoutResId, data) {


//    override fun getItemCount(): Int {
//        return Int.MAX_VALUE
//    }
//
//    override fun getItem(position: Int): ProductsResult {
//        return data[position % data.size]
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        var count = headerLayoutCount + data.size
//        if (count <= 0) {
//            count = 1
//        }
//        return super.getItemViewType(position % count)
//    }




    override fun convert(holder: BaseViewHolder, item: ProductsResult) {

        data.sortBy { it.principal.replace(",","").toInt() }
//        holder.setText(R.id.tv_lv, "LV:${data.indexOf(item) + 1}")
        holder.setText(R.id.tv_money,item.principal)
//        holder.setText(R.id.tv_str1,UIUtils.getString(R.string.loan_term).format(item.duration.toString()))
//       val tvNow= holder.getView<ThemeTextView>(R.id.tv_now)
//        if (item.enable == "2") {
//            tvNow.background=(UIUtils.getDrawable(NApplication.sContext,R.drawable.shape_gray))
//            tvNow.setThemTextColor(R.color.bg_color)
//            tvNow.isEnabled=false
//        }else{
//            tvNow.background=(UIUtils.getDrawable(NApplication.sContext,R.drawable.shape_golden))
//            tvNow.setThemTextColor(R.color.golden_80)
//            tvNow.isEnabled=true
//        }

//        tvNow.setOnClickListener {
//
//            btnClickListener?.onClick(item)
//        }

        

    }




// interface  onBtnClickListener{
//     fun onClick(data:ProductsResult)
// }
//
//    var btnClickListener: onBtnClickListener?=null
//
//    @JvmName("setBtnClickListener1")
//    fun setBtnClickListener(listener: onBtnClickListener){
//        btnClickListener=listener
//    }




}