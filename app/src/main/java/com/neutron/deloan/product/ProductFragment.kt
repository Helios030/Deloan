package  com.neutron.deloan.product

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.neutron.deloan.NApplication
import com.neutron.deloan.R
import com.neutron.deloan.base.BaseFragment
import com.neutron.deloan.bean.ProductsResult
import com.neutron.deloan.bean.UserStatusResult
import com.neutron.deloan.facedetection.FaceDetectionActivity
import com.neutron.deloan.main.MainActivity
import com.neutron.deloan.net.BaseResponse
import com.neutron.deloan.utils.Constants
import com.neutron.deloan.utils.PreferencesHelper
import com.neutron.deloan.utils.Slog
import com.yalantis.ucrop.util.ScreenUtils
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
            override fun onClick(data: ProductsResult) {
                Slog.d("setOnItemChildClickListener $data ")
                PreferencesHelper.setProductId(data.productId.toString())
                mPresenter?.getUserStatus(true)
            }
        })

        val scrollY = 0

//        处理滑动冲突
        rv_product.addOnScrollListener(object : RecyclerView.OnScrollListener() {


//            var isHandScoll = false

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                when (newState) {
                    /*正在拖拽*/
                    RecyclerView.SCROLL_STATE_DRAGGING -> {
//                        if (!isHandScoll) {
//                            isHandScoll = true
//                        }
                        (activity as MainActivity).getRefresh()?.let {
                            it.isEnabled = false
                        }
                    }
                    /*滑动停止*/
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
        productsResults.clear()
        productsResults.addAll(products.result)
        Slog.d("产品列表 $productsResults")
        adapter?.notifyDataSetChanged()
//        rv_product.scrollToPosition(Int.MAX_VALUE/2)
        (rv_product.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
            Int.MAX_VALUE / 2,
            ScreenUtils.dip2px(activity, 29F)
        )


    }

    override fun returnUserStatus(isClick: Boolean, result: BaseResponse<UserStatusResult>) {

        if (result.result.person_status == "0" || result.result.comp_status == "0" || result.result.card_status == "0" || result.result.contact_status == "0") {
            if (isClick) {
                //跳转认证
                openUri(Constants.APPROVE, true)

            }

        } else {
            if (isClick) {
                startTo(FaceDetectionActivity::class.java)
            }
        }


    }
}

