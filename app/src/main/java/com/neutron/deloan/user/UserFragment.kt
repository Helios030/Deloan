package  com.neutron.deloan.user

import android.Manifest
import android.content.Intent
import com.neutron.deloan.R
import com.neutron.deloan.WelcomeActivity
import com.neutron.deloan.base.BaseFragment
import com.neutron.deloan.bean.BaseResponse
import com.neutron.deloan.bean.ProductsResult
import com.neutron.deloan.bean.UserStatusResult
import com.neutron.deloan.main.MainActivity
import com.neutron.deloan.utils.Constants
import com.neutron.deloan.utils.PreferencesHelper
import com.neutron.deloan.utils.Slog
import com.neutron.deloan.utils.makeCall
import com.neutron.deloan.web.WebViewActivity
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.fragment_user.*


class UserFragment : BaseFragment<UserContract.View, UserContract.Presenter>(),
    UserContract.View {

    override fun onResume() {
        super.onResume()

//        val mainActivity = (activity as MainActivity)
//        mainActivity.setStatusBarColor(mainActivity, R.color.golden_light)


        tv_phone.text = PreferencesHelper.getPhone()
//        tv_name.text = PreferencesHelper.getRealname()

//        mainActivity.getRefresh()?.isEnabled = false
        mPresenter?.getUserStatus(false)
    }


    override fun createPresenter(): UserContract.Presenter {
        return UserPresenter()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_user
    }

    override fun lazyLoad() {

        val mainActivity = (activity as MainActivity)
        val loanStatusResult = mainActivity.getloanStatusResult()
        tv_pay.setOnClickListener { openUri(Constants.RECORD, false, loanStatusResult) }
        tv_info.setOnClickListener { openUri(Constants.MYPROFILE, false, loanStatusResult) }

        tv_about.setOnClickListener {

            startActivity(Intent(activity, WebViewActivity::class.java).apply {
                putExtra(Constants.Intent_URI, PreferencesHelper.getAboutUs())
                putExtra(Constants.IS_MAIN, false)
            })

        }


        tv_exit.setOnClickListener {
            context?.let {
                showPopWindow(it,
                    getString(R.string.exit_login_tip),
                    getString(R.string.exit_login),
                    {
                        PreferencesHelper.setUserID("")
                        PreferencesHelper.setPhone("")
                        PreferencesHelper.setShowFeiled(true)
//                        todo 2021年9月2日 添加
                        PreferencesHelper.setUploadTime(0L)
                        PreferencesHelper.setCallUploadTime(0L)
                        startTo(WelcomeActivity::class.java, true)
                    },
                    rl_main)
            }
        }

        tv_call.setOnClickListener {
            context?.let {
                showPopWindow(it, getString(R.string.is_call), getString(R.string.start_call),
                    {

                        call()

                    }, rl_main)
            }
        }


    }

    private fun call() {

                    val phone = PreferencesHelper.getHotTel()
                    if (phone.isNotEmpty()) {
                        makeCall(phone)
                    }

    }


    override fun returnRequestState(products: BaseResponse<List<ProductsResult>>) {

    }

    override fun returnUserStatus(click: Boolean, result: BaseResponse<UserStatusResult>) {

        Slog.d("returnUserStatus  $result")
        if (result.result.person_status == "0" || result.result.comp_status == "0" || result.result.card_status == "0" || result.result.contact_status == "0") {

            tv_user_ok.text = getString(R.string.main_not_certified)
        } else {
            tv_user_ok.text = getString(R.string.main_certified_success)
        }


    }
}