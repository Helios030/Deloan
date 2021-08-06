package com.neutron.deloan

import androidx.appcompat.app.AppCompatActivity
import com.neutron.deloan.base.BaseActivity
import com.neutron.deloan.base.IBaseActivity
import com.neutron.deloan.login.LoginActivity
import com.neutron.deloan.main.MainActivity
import com.neutron.deloan.utils.*
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_permison.*
import kotlinx.android.synthetic.main.layout_confirm_pp.*

class PermisonActivity : IBaseActivity() {


    override fun getLayoutId(): Int {
        return R.layout.activity_permison
    }




    var isSelected = false


    override fun initView() {

        if (checkPermissionAllGranted(Constants.PERMISSIONS_LIST)) {
            if (PreferencesHelper.getUserID().isNullOrEmpty()) {
                startTo(LoginActivity::class.java,true)
            } else {
                startTo(MainActivity::class.java,true)
            }

        }

        iv_select.setOnClickListener {
            isSelected = !isSelected

            if (isSelected) {
                iv_select.setImageResource(R.mipmap.icon_pp_select)
            } else {
                iv_select.setImageResource(R.mipmap.icon_pp_not_select)

            }

        }

        btn_request.setOnClickListener {

            if(isSelected){

            val ok=    getString(R.string.dialog_ok)
            val cancel=    getString(R.string.dialog_cancel)

                PermissionX.init(this)
                    .permissions(Constants.PERMISSIONS_LIST.toList())
                    .onExplainRequestReason { scope, deniedList ->
                        scope.showRequestReasonDialog(deniedList, getString(R.string.not_pp), ok, cancel)
                    }
                    .onForwardToSettings { scope, deniedList ->
                        scope.showForwardToSettingsDialog(deniedList, getString(R.string.not_pp), ok, cancel)
                    }
                    .request { allGranted, _, _ ->
                        if (allGranted) {
                            if (PreferencesHelper.getUserID().isNullOrEmpty()) {
                                startTo(LoginActivity::class.java,true)
                            } else {
                                startTo(MainActivity::class.java,true)
                            }
                        } else {
                           toast(R.string.not_pp)

                        }
                    }

            }else{
                toast(R.string.pp_not_check)

            }


        }


    }
}