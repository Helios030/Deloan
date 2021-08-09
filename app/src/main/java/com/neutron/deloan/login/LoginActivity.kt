package com.neutron.deloan.login


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.neutron.deloan.R
import com.neutron.deloan.base.BaseActivity
import com.neutron.deloan.bean.BaseResponse
import com.neutron.deloan.bean.LoginResuleResult
import com.neutron.deloan.bean.SmsLoginResult
import com.neutron.deloan.bean.UserInfo
import com.neutron.deloan.main.MainActivity
import com.neutron.deloan.utils.*
import com.neutron.deloan.web.WebViewActivity
import com.ronal.crazy.util.AfPointUtils
import com.tuo.customview.VerificationCodeView
import com.wynsbin.vciv.VerificationCodeInputView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_confirm_pp.*
import kotlinx.android.synthetic.main.layout_keybord.*


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


        tv_permison.setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java).apply {
                putExtra(Constants.Intent_URI, Constants.privacypolicy)
                putExtra(Constants.IS_MAIN, false)
            })
        }


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
                    btn_login.isEnabled = false
                    timer.cancel()
                    timer.start()
                    Slog.d("btn_login  $phoneNumber  ${mPresenter == null}")
                    mPresenter?.getVerificationCode(phoneNumber)

                }
            } else {
                toast(R.string.pp_not_check)
            }
        }





    }


    private var popupWindow = PopupWindow(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    )




    @SuppressLint("WrongConstant")
    private fun showPopupWindow() {
        et_phone.isEnabled=false

        val popupView = LayoutInflater.from(this).inflate(R.layout.layout_keybord, null)
        val vciv_code: VerificationCodeInputView = popupView.findViewById<VerificationCodeInputView>(R.id.vciv_code)
        val icv: VerificationCodeView = popupView.findViewById<VerificationCodeView>(R.id.icv)


        icv.isFocusable = true
        icv.editText.isFocusableInTouchMode=true

        val sb=StringBuffer()

        icv.setInputCompleteListener(object :VerificationCodeView.InputCompleteListener{
            override fun inputComplete() {


           val str=     icv.editText.text.toString()

                if (str.isNotBlank()) {
                    sb.append(str)
                    Slog.d("str  $str     sb ${sb.toString()}")
                    if(sb.length==6){
                        mPresenter?.testCode(phoneNumber,sb.toString() )
                    }

                }


//

            }

            override fun deleteContent() {

            }

        })



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
        popupWindow.isClippingEnabled = false
        popupWindow.isOutsideTouchable = true;
        popupWindow.softInputMode = PopupWindow.INPUT_METHOD_NEEDED;
        popupWindow.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
        popupWindow.isFocusable = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.window.setDecorFitsSystemWindows(false)

            ll_main.setOnApplyWindowInsetsListener { _, windowInsets ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val insets = windowInsets.getInsets(WindowInsets.Type.ime() or WindowInsets.Type.systemGestures())
                    insets
                }

                windowInsets
            }
        }



        if (!popupWindow.isShowing) {
            popupWindow.showAtLocation(
                this.findViewById(R.id.ll_main),
//                Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL,
                Gravity.CENTER,
                0,
                0
            )
            popupView.startAnimation(animation)
            changeBackground(0.5F)

//            showSoftInputFromWindow(this,icv.editText)
            val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(icv.editText, 0)

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
                showPopupWindow()

            } else {
                toast(it.message)
            }
        }

    }

    override fun loginState(smsLoginResults: BaseResponse<SmsLoginResult>) {

        smsLoginResults?.let {
       if(it.code=="200"){
           PreferencesHelper.setUserID(it.result.user_id)
           PreferencesHelper.setPhone(it.result.phone)
           PreferencesHelper.setPhoneEPRE(it.result.phonepre)


           val vCode = if (it.result.vcode == null) {
               ""
           } else {
               it.result.vcode.toString()
           }
           val register = if (it.result.register == null) {
               ""
           } else {
               it.result.register.toString()
           }

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
       }else{
           toast(it.message)
       }
        }

    }

}