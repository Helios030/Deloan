package com.neutron.deloan.main

import android.content.Intent
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.leaf.library.StatusBarUtil
import com.neutron.deloan.R
import com.neutron.deloan.base.BaseActivity
import com.neutron.deloan.bean.BaseResponse
import com.neutron.deloan.bean.LoanStatusResult
import com.neutron.deloan.bean.RepaymentBeanResult
import com.neutron.deloan.bean.UserConfigResult
import com.neutron.deloan.fragment.ApprovalRejectedFragment
import com.neutron.deloan.fragment.OverdueFragment
import com.neutron.deloan.fragment.PendingRepaymentFragment
import com.neutron.deloan.fragment.ReviewFragment
import com.neutron.deloan.product.ProductFragment
import com.neutron.deloan.user.UserFragment
import com.neutron.deloan.utils.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {

    override fun setPresenter(): MainContract.Presenter {
        return MainPresenter()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        showLoading()
        mPresenter?.getRepayment()
        mPresenter?.getRequestState()

    }

    fun getRefresh(): SwipeRefreshLayout? {
        return sfl_main
    }

    val mProductFragment = ProductFragment()
    val mUserFragment = UserFragment()
    val mReviewFragment = ReviewFragment()
    val mApprovalRejectedFragment = ApprovalRejectedFragment()
    val mPendingRepaymentFragment = PendingRepaymentFragment()
    val mOverdueFragment = OverdueFragment()
    var fragmentList = mutableListOf(
        mProductFragment,
        mUserFragment,
        mReviewFragment,
        mApprovalRejectedFragment,
        mPendingRepaymentFragment,
        mOverdueFragment

    )

    override fun onResume() {
        super.onResume()
        if(repaymentResult==null){
            mPresenter?.getRepayment()
        }
        if(loanStatusResult==null){
            mPresenter?.getRequestState()

        }

        StatusBarUtil.setTransparentForWindow(this)
    }


    override fun initView() {
        nsv_main.adapter = ViewPagerAdapter(supportFragmentManager)
        nsv_main.offscreenPageLimit = fragmentList.size
        tv_tab_main.setOnClickListener {
            showTabIsMain(true)
            if (sfl_main.isRefreshing) {
                sfl_main.isRefreshing = false
            }
            showStateView(currLoanStatus)
            nsv_main.setCurrentItem(indexFragmentByName(currFragment.javaClass.simpleName), false)
            currFragment.onResume()
        }

        tv_tab_user.setOnClickListener {
            showTabIsMain(false)
            if (sfl_main.isRefreshing) {
                sfl_main.isRefreshing = false
            }
            nsv_main.setCurrentItem(
                indexFragmentByName(mUserFragment.javaClass.simpleName),
                false
            )
            mUserFragment.onResume()
        }
        sfl_main.setOnRefreshListener {
            initData()
        }

    }


    private fun showTabIsMain(b: Boolean) {
        val textColor = getColor(R.color.text_color)
        val textGrayColor = getColor(R.color.color_gray_e0)
        if (b) {
            tv_tab_main.setDrawableTop(R.mipmap.icon_main_y)
            tv_tab_main.setTextColor(textColor)
            tv_tab_user.setTextColor(textGrayColor)
            tv_tab_user.setDrawableTop(R.mipmap.icon_user_n)
        } else {
            tv_tab_main.setTextColor(textGrayColor)
            tv_tab_user.setTextColor(textColor)
            tv_tab_main.setDrawableTop(R.mipmap.icon_main_n)
            tv_tab_user.setDrawableTop(R.mipmap.icon_user_y)
        }
    }

    private fun indexFragmentByName(name: String): Int {
        var index = 0
        fragmentList.forEach {
            if (it.javaClass.simpleName == name) {
                index = fragmentList.indexOf(it)
            }
        }
        return index
    }

    var loanStatusResult: LoanStatusResult? = null
    fun getloanStatusResult(): LoanStatusResult? {
        return loanStatusResult
    }

    var currLoanStatus = 1
    override fun returnRequestState(loanStatus: BaseResponse<LoanStatusResult>) {
        Slog.d("returnRequestState $loanStatus ")
        loanStatus?.let {
            if (it.code == "200") {
                loanStatusResult = it.result
                currLoanStatus = it.result.loan_status.toInt()
                showStateView(currLoanStatus)
                nsv_main.setCurrentItem(
                    indexFragmentByName(currFragment.javaClass.simpleName),
                    false
                )
                currFragment.onResume()
                showTabIsMain(true)
//                bnv_main.selectedItemId = bnv_main.menu.getItem(0).itemId
            } else {
                toast(it.message)
            }
        }
        hideLoading()
        if (sfl_main.isRefreshing) {
            sfl_main.isRefreshing = false
        }
    }

    override fun returnUserConfig(userConfig: BaseResponse<UserConfigResult>) {
        if (userConfig.code == "200") {
            PreferencesHelper.setAboutUs(userConfig.result.about_us)
            PreferencesHelper.setHotTel(userConfig.result.hot_tel)
            PreferencesHelper.setPPrivate(userConfig.result.k_private)
//            PreferencesHelper.setLine(userConfig.result.line.toString())
        } else {
            toast(userConfig.message)
        }
    }


    var repaymentResult: BaseResponse<List<RepaymentBeanResult>>? = null

    fun getrepaymentResult(): BaseResponse<List<RepaymentBeanResult>>? {
        return repaymentResult
    }

    override fun returnRepayment(repayment: BaseResponse<List<RepaymentBeanResult>>) {
        Slog.d("returnRepayment $repayment ")
        if (repayment.code == "200") {
            this.repaymentResult = repayment

            currFragment.onResume()

        } else {
            toast(repayment.message)
        }


    }


    var currFragment: Fragment = mProductFragment
    fun showStateView(LoanStatus: Int) {
        when (LoanStatus) {
            MoneyState.STATE_LOANING, MoneyState.STATE_APPLYING -> {
                currFragment = mReviewFragment
                PreferencesHelper.setShowFeiled(true)
            }
            MoneyState.STATE_APPROVAL_REJECTED -> {
                currFragment = mApprovalRejectedFragment

                if (PreferencesHelper.isShowFeiled()) {
                    PreferencesHelper.setShowFeiled(false)
                } else {
                    currFragment = mProductFragment
                }

            }
            MoneyState.STATE_PENDING_REPAYMENT -> {
                currFragment = mPendingRepaymentFragment
            }
            MoneyState.STATE_OVERDUE -> {
                currFragment = mOverdueFragment
            }
            MoneyState.STATE_BORROWABLE -> {

                currFragment = mProductFragment
            }


//            MoneyState.STATE_PAY_REVIEW -> {
//                currFragment = mProductFragment
//            }
        }

//        if (currFragment.javaClass.simpleName == mProductFragment.javaClass.simpleName) {
//            Slog.d("移除刷新监听")
//            mPendingRepaymentFragment.removeListener()
//        }

    }

    inner class ViewPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager) {

        var fm: FragmentManager? = null

        init {
            this.fm = fragmentManager
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getItemPosition(`object`: Any): Int {
            return POSITION_NONE
        }

        override fun getItemId(position: Int): Long {
            return fragmentList[position].hashCode().toLong()
        }
    }

    // 点击两次退出应用程序的第一个时间
    private var firstTime: Long = 0


    /**
     * 返回键按下两次退出应用程序
     */
    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                val secondTime = System.currentTimeMillis()
                return if (secondTime - firstTime > 2000) {
                    toast(R.string.quit_app)
                    firstTime = secondTime
                    true
                } else {
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.addCategory(Intent.CATEGORY_HOME)
                    startActivity(intent)
                    true
                }
            }
        }
        return super.onKeyUp(keyCode, event)
    }

}