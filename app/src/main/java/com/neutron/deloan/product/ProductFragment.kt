package  com.neutron.deloan.product

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.neutron.deloan.NApplication
import com.neutron.deloan.R
import com.neutron.deloan.base.BaseFragment
import com.neutron.deloan.bean.BaseResponse
import com.neutron.deloan.bean.ProductsResult
import com.neutron.deloan.bean.UserStatusResult
import com.neutron.deloan.confirm.ConfirmActivity
import com.neutron.deloan.facedetection.FaceDetectionActivity
import com.neutron.deloan.main.MainActivity
import com.neutron.deloan.utils.Constants
import com.neutron.deloan.utils.PreferencesHelper

import com.neutron.deloan.utils.Slog
import kotlinx.android.synthetic.main.fragment_product.*


class ProductFragment : BaseFragment<ProductContract.View, ProductContract.Presenter>(),
    ProductContract.View {


    override fun createPresenter(): ProductContract.Presenter {
        return ProductPresenter()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_product
    }

    override fun lazyLoad() {
        initToolbar(getString(R.string.app_name))

        initRecycleView()

        btn_now.setOnClickListener {
            mPresenter?.getUserStatus(true)
        }

    }

    override fun onResume() {
        super.onResume()
        mPresenter?.getUserStatus(false)
        mPresenter?.getProducts()
    }


    var productsResults = mutableListOf<ProductsResult>()
    var adapter: ProductCommAdapter? = null
    private fun initRecycleView() {
        val lineLayout = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        adapter = ProductCommAdapter(NApplication.sContext, productsResults)
        rv_product.layoutManager = lineLayout
        rv_product.adapter = adapter
//        PagerSnapHelper().attachToRecyclerView(rv_product)
        LinearSnapHelper().attachToRecyclerView(rv_product)
        adapter?.setBtnClickListener(object : ProductCommAdapter.onBtnClickListener {
            override fun onClick(data: ProductsResult, view: View, index: Int, realIndex: Int) {
                (rv_product.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                    realIndex - 1,
                    0
                )
                tv_money_date.text = data.duration.toString()
                PreferencesHelper.setProductId(data.productId.toString())


            }

        })


        val scrollY = 0

//        ??????????????????
        rv_product.addOnScrollListener(object : RecyclerView.OnScrollListener() {


//            var isHandScoll = false

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                when (newState) {
                    /*????????????*/
                    RecyclerView.SCROLL_STATE_DRAGGING -> {
//                        if (!isHandScoll) {
//                            isHandScoll = true
//                        }
                        (activity as MainActivity).getRefresh()?.let {
                            it.isEnabled = false
                        }
                    }
                    /*????????????*/
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        (activity as MainActivity).getRefresh()?.let {
                            it.isEnabled = true
                        }
//                  lineLayout.findFirstCompletelyVisibleItemPosition()

//                        Slog.d("SCROLL_STATE_IDLE  $isHandScoll  $visIndex")

//                        if (isHandScoll) {
//                            (rv_product.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
//                                visIndex,
//                                ScreenUtils.dip2px(activity, 29F)
//                            )
//                            isHandScoll = false
//                        }


                    }
                }
            }
        })


    }

    override fun returnRequestState(products: BaseResponse<List<ProductsResult>>) {
        if (products.result.isNotEmpty()) {
            if (PreferencesHelper.getProductId().isEmpty()) {
                PreferencesHelper.setProductId(products.result.first().productId.toString())
            }
        }
        productsResults.clear()
        productsResults.addAll(products.result)

        tv_money_date.text = products.result.first().duration.toString()

        Slog.d("???????????? $productsResults")
        adapter?.notifyDataSetChanged()
//        rv_product.scrollToPosition(Int.MAX_VALUE/2)
        val centerIndex = (Int.MAX_VALUE / 2) - 1
        (rv_product.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(centerIndex, 0)
        try {
            adapter?.setSelectedPos(productsResults.indexOf(productsResults.find { it.enable == "1" }))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun returnUserStatus(isClick: Boolean, result: BaseResponse<UserStatusResult>) {
        if (isClick) {
            if (result.result.file_status1 == "0") {
                openUri(Constants.APPROVE, true)
            } else if (result.result.person_status == "0") {
                openUri(Constants.BASEINFO, true)
            } else if (result.result.comp_status == "0") {
                openUri(Constants.WORKINFO, true)
            } else if (result.result.contact_status == "0") {
                openUri(Constants.CONNECTINFO, true)
            } else if (result.result.card_status == "0") {
                openUri(Constants.BANKCARDINFO, true)
            } else {
                val result = (activity as MainActivity).getloanStatusResult()
                if(result?.has_loan_app=="true"){
                    PreferencesHelper.setReRequest(true)
                    startTo(ConfirmActivity::class.java)
                }else{
                    PreferencesHelper.setReRequest(false)
                    startTo(FaceDetectionActivity::class.java)
                }
            }
        } else {
            Slog.e("isClick  == false")
        }


    }
}


