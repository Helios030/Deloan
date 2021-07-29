package  com.neutron.deloan.user

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import  com.neutron.deloan.NApplication
import  com.neutron.deloan.R
import  com.neutron.deloan.WelcomeActivity
import  com.neutron.deloan.base.BaseFragment
import  com.neutron.deloan.bean.ProductsResult
import  com.neutron.deloan.bean.UserStatusResult
import  com.neutron.deloan.main.MainActivity
import  com.neutron.deloan.net.BaseResponse
import com.neutron.deloan.utils.Constants
import com.neutron.deloan.utils.UIUtils
import com.neutron.deloan.view.ThemeTextView
import com.neutron.deloan.web.WebViewActivity
import kotlinx.android.synthetic.main.fragment_user.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UserFragment : BaseFragment<UserContract.View, UserContract.Presenter>(),
    UserContract.View {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onResume() {
        super.onResume()

//        val mainActivity = (activity as MainActivity)
//        mainActivity.setStatusBarColor(mainActivity, R.color.golden_light)


//        tv_phone.text = PreferencesHelper.getPhone()
//        tv_name.text = PreferencesHelper.getRealname()

//        mainActivity.getRefresh()?.isEnabled = false

    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun createPresenter(): UserContract.Presenter {
        return UserPresenter()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_user
    }

    override fun lazyLoad() {
        tv_pay.setOnClickListener { openUri(Constants.RECORD, tv_pay.text.toString()) }
        tv_info.setOnClickListener { openUri(Constants.MYPROFILE, tv_info.text.toString()) }
    }


    private fun openUri(uri: String, title: String) {

        startActivity(Intent(activity, WebViewActivity::class.java).apply {
            putExtra(Constants.Intent_URI, "${Constants.H5BaseUri}${uri}")
//            putExtra(Constants.ACTIVITY_ACTION_TITLE, title)

        })


    }

    override fun returnRequestState(products: BaseResponse<List<ProductsResult>>) {

    }

    override fun returnUserStatus(click: Boolean, userStatus: BaseResponse<UserStatusResult>) {

    }
}