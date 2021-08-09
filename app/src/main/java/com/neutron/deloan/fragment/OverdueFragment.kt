package com.neutron.deloan.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.neutron.deloan.R
import com.neutron.deloan.bean.LoanStatusResult
import com.neutron.deloan.main.MainActivity
import com.neutron.deloan.utils.Constants
import com.neutron.deloan.utils.Slog
import com.neutron.deloan.web.WebViewActivity
import kotlinx.android.synthetic.main.fragment_overdue.*


class OverdueFragment : Fragment() {
    fun openUri(uri: String, isMain: Boolean, result: LoanStatusResult) {
        startActivity(Intent(activity, WebViewActivity::class.java).apply {
            putExtra(Constants.Intent_URI, "${Constants.H5BaseUri}${uri}")
            putExtra(Constants.IS_MAIN, isMain)
            putExtra(Constants.LOAN_STATUS_RESULT, result)
        })
    }

    var loanStatusResult: LoanStatusResult? = null
    override fun onResume() {
        super.onResume()
        Slog.d("OverdueFragment onResume")

        val mainActivity = (activity as MainActivity)
//        mainActivity.setStatusBarColor(mainActivity, R.color.red_ef)
        loanStatusResult = mainActivity.getloanStatusResult()
        loanStatusResult?.let { result ->
            tv_money.text = result.remainAmount
            tv_money.text = result.remainAmount
//            tv_over_money.text = result.principal
//            tv_over_money.text = result.amount2Account
//            tv_date.text = "${result.duration} "
//            tv_over_due_date.text = result.app_time
//

            tv_pen_interest.text = result.interest
//            tv_over_risk.text = result.risk
//            tv_over_service.text = result.service
//            tv_over_pay.text = result.pay
            tv_over_penalty.text = result.penalty
            tv_due_over_date.text = result.deposit_time
            tv_over_fale_fee.text = result.fale_fee
//            tv_order_count.text = result.application_id


            applicationId = result.application_id
            amount = result.remainAmount

        }

    }


    var applicationId: String? = null
    var amount: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_pay.setOnClickListener {
            startToPay(true)
        }
        btn_sub_pay.setOnClickListener {
            startToPay(false)
        }

    }

    fun startToPay(payAll: Boolean) {
        loanStatusResult?.let {
            if (payAll) {
                openUri(Constants.REPAY, true, it)
            } else {
                openUri(Constants.PERIOD, true, it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overdue, container, false)
    }


}