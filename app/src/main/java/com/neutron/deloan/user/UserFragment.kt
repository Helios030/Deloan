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
import com.neutron.deloan.utils.PreferencesHelper
import com.neutron.deloan.utils.UIUtils
import com.neutron.deloan.utils.makeCall
import com.neutron.deloan.view.ThemeTextView
import com.neutron.deloan.web.WebViewActivity
import kotlinx.android.synthetic.main.fragment_user.*


class UserFragment : BaseFragment<UserContract.View, UserContract.Presenter>(),
    UserContract.View {

    override fun onResume() {
        super.onResume()

//        val mainActivity = (activity as MainActivity)
//        mainActivity.setStatusBarColor(mainActivity, R.color.golden_light)


//        tv_phone.text = PreferencesHelper.getPhone()
//        tv_name.text = PreferencesHelper.getRealname()

//        mainActivity.getRefresh()?.isEnabled = false

    }



    override fun createPresenter(): UserContract.Presenter {
        return UserPresenter()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_user
    }

    override fun lazyLoad() {
        tv_pay.setOnClickListener { openUri(Constants.RECORD,false) }
        tv_info.setOnClickListener { openUri(Constants.MYPROFILE,false) }


        tv_exit.setOnClickListener {
            context?.let {
                showPopWindow(it,getString(R.string.exit_login_tip),getString(R.string.exit_login),
                    {
                        PreferencesHelper.setUserID("")
                        PreferencesHelper.setPhone("")
                        PreferencesHelper.setShowFeiled(true)
                        startTo(WelcomeActivity::class.java,true)
                    } ,rl_main)
            }
            }

        tv_call.setOnClickListener {
            context?.let {
                showPopWindow(it,getString(R.string.is_call),getString(R.string.start_call),
                    {

                      val phone=  PreferencesHelper.getPhone()
                        if (phone.isNotEmpty()) {
                            makeCall(phone)
                        }

                    } ,rl_main)
            }
        }





    }






    override fun returnRequestState(products: BaseResponse<List<ProductsResult>>) {

    }

    override fun returnUserStatus(click: Boolean, userStatus: BaseResponse<UserStatusResult>) {

    }
}