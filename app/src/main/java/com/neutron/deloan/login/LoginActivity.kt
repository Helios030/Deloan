package com.neutron.deloan.login


import android.os.CountDownTimer
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.neutron.deloan.main.MainActivity
import com.neutron.deloan.R
import com.neutron.deloan.base.BaseActivity
import com.neutron.deloan.base.IBaseActivity
import com.neutron.deloan.bean.LoginResuleResult
import com.neutron.deloan.bean.SmsLoginResult
import com.neutron.deloan.bean.UserInfo
import com.neutron.deloan.net.BaseResponse
import com.neutron.deloan.net.RetrofitUtil
import com.neutron.deloan.utils.*
import com.ronal.crazy.util.AfPointUtils
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_confirm_pp.*
import kotlinx.android.synthetic.main.layout_keybord.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.StringBuilder


class LoginActivity : BaseActivity<LoginContract.View, LoginContract.Presenter>(),
    LoginContract.View {

    override fun setPresenter(): LoginContract.Presenter {
        return LoginPresenter()
    }



    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }




    override fun initData() {
        DeviceFactory.getInstance().getIMEI()
    }

    var isSelected = false
    var phoneNumber = ""
    override fun initView() {
        iv_select.setOnClickListener {
            isSelected = !isSelected
            if (isSelected) {
                iv_select.setImageResource(R.mipmap.icon_pp_select)
            } else {
                iv_select.setImageResource(R.mipmap.icon_pp_not_select)
            }
        }

        btn_login.setOnClickListener {
            if (isSelected) {
                phoneNumber = et_phone.text.toString()
                if (phoneNumber.isNullOrEmpty()) {
                    toast(R.string.phone_not_null)
                } else {
                    Utils.hideSoftInputWindow(this)
                    btn_login.isEnabled = false
                    timer.cancel()
                    timer.start()
                    Slog.d("btn_login  $phoneNumber  ${mPresenter==null}")
                    mPresenter?.getVerificationCode(phoneNumber)

                }
            } else {
                toast(R.string.pp_not_check)
            }
        }
    }


    var codeStr = StringBuilder()


    fun onClick(view: View) {

        when (view.id) {
            R.id.btn_0 -> {
                addCode("0")
            }
            R.id.btn_1 -> {
                addCode("1")
            }
            R.id.btn_2 -> {
                addCode("2")
            }
            R.id.btn_3 -> {
                addCode("3")
            }
            R.id.btn_4 -> {
                addCode("4")
            }
            R.id.btn_5 -> {
                addCode("5")
            }
            R.id.btn_6 -> {
                addCode("6")
            }
            R.id.btn_7 -> {
                addCode("7")
            }
            R.id.btn_8 -> {
                addCode("8")
            }
            R.id.btn_9 -> {
                addCode("9")
            }
            R.id.btn_dian -> {

            }
            R.id.btn_del -> {

                currIndex--
                var lastIndex = codeStr.length - 1
                if (lastIndex >= 0) {
                    codeStr.deleteAt(lastIndex)


                }
                showCode()

            }
            R.id.btn_input_ok -> {


                mPresenter?.testCode(phoneNumber, codeStr.toString())

            }

        }

    }


    var currIndex = 0

    private fun addCode(code: String) {
        if (codeStr.length < 6) {
            codeStr.append(code)
            showCode()
            currIndex++
        }
    }

    private fun showCode() {
        tv1?.text = ""
        tv2?.text = ""
        tv3?.text = ""
        tv4?.text = ""
        tv5?.text = ""
        tv6?.text = ""
        for (index in codeStr.indices) {
            Slog.d("showCode index $index   codeStr  ${codeStr.toString()}")
            when (index) {
                0 -> {
                    tv1?.text = codeStr[0].toString()
                }
                1 -> {
                    tv2?.text = codeStr[1].toString()
                }
                2 -> {
                    tv3?.text = codeStr[2].toString()
                }
                3 -> {
                    tv4?.text = codeStr[3].toString()
                }
                4 -> {
                    tv5?.text = codeStr[4].toString()
                }
                5 -> {
                    tv6?.text = codeStr[5].toString()
                }

            }
        }
    }

    private var popupWindow = PopupWindow(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    )

    var tv1: TextView? = null
    var tv2: TextView? = null
    var tv3: TextView? = null
    var tv4: TextView? = null
    var tv5: TextView? = null
    var tv6: TextView? = null

    private fun showPopupWindow() {
        val popupView = LayoutInflater.from(this).inflate(R.layout.layout_keybord, null)

        tv1 = popupView.findViewById<TextView>(R.id.tv_1)
        tv2 = popupView.findViewById<TextView>(R.id.tv_2)
        tv3 = popupView.findViewById<TextView>(R.id.tv_3)
        tv4 = popupView.findViewById<TextView>(R.id.tv_4)
        tv5 = popupView.findViewById<TextView>(R.id.tv_5)
        tv6 = popupView.findViewById<TextView>(R.id.tv_6)

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val navHeight = Utils.getNavigationBarHeight(this)
        layoutParams.setMargins(0, 10, 0, navHeight + 10);
        // 平移动画相对于手机屏幕的底部开始，X轴不变，Y轴从1变0
        val animation = TranslateAnimation(
            Animation.RELATIVE_TO_PARENT, 0F, Animation.RELATIVE_TO_PARENT, 0F,
            Animation.RELATIVE_TO_PARENT, 1F, Animation.RELATIVE_TO_PARENT, 0F
        )
        animation.interpolator = AccelerateInterpolator()
        animation.duration = 200
        popupWindow.contentView = popupView
        popupWindow.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        popupWindow.width = WindowManager.LayoutParams.MATCH_PARENT
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.isOutsideTouchable = false
        popupWindow.isFocusable = false
        popupWindow.isClippingEnabled = false
        if (!popupWindow.isShowing) {
            popupWindow.showAtLocation(
                this.findViewById(R.id.ll_main),
                Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL,
                0,
                0
            )
            popupView.startAnimation(animation)
            changeBackground(0.5F)
        }
        popupWindow.setOnDismissListener {
            changeBackground(1F)
        }
    }

    private fun changeBackground(alpha: Float) {
        val lp: WindowManager.LayoutParams = window.attributes
        lp.alpha = alpha
        window.attributes = lp
    }


    val timer = object : CountDownTimer(60000L, 1000L) {
        override fun onTick(millisUntilFinished: Long) {
            btn_login.text = "${millisUntilFinished / 1000 % 60}S"
        }

        override fun onFinish() {
            btn_login.isEnabled = true
            btn_login.text = getString(R.string.login_get_code)
        }
    }



    override fun getCodeState(loginResult: BaseResponse<LoginResuleResult>?) {
           Slog.d("getCodeState  $loginResult ")
        loginResult?.let {
            if (it.code == "200") {
                toast(R.string.get_code_success)
                Utils.hideSoftInputWindow(this)
                showPopupWindow()

            } else {
                toast(it.message)
            }
        }

    }

    override fun loginState(smsLoginResults: BaseResponse<SmsLoginResult>) {

        smsLoginResults?.let {
            PreferencesHelper.setUserID(it.result.user_id)
            PreferencesHelper.setPhone(it.result.phone)
            PreferencesHelper.setPhoneEPRE(it.result.phonepre)


            val vCode=if(it.result.vcode==null){""}else{it.result.vcode.toString()}
            val register=if(it.result.register==null){""}else{it.result.register.toString()}

            PreferencesHelper.setUserInfo(
                UserInfo(
                    it.result.user_id,
                    it.result.userName,
                    it.result.signKeyToken,
                    vCode,
                    it.result.phone,
                    it.result.phonepre,
                    register
                )
            )
            if (!it.result.register) {
                AfPointUtils.userAppsFlyerReturnDataEvent(
                    Constants.AF_APP_REGISTER,
                    Constants.EVENT_NEW_REGISTER,
                    it.result.phone,
                    this@LoginActivity
                )
            }
            AfPointUtils.userAppsFlyerReturnDataEvent(
                Constants.AF_APP_LOGIN,
                Constants.EVENT_CODE_LOGIN,
                it.result.phone,
                this@LoginActivity
            )
            toast(R.string.login_success)
            startTo(MainActivity::class.java, true)
        }

    }

}