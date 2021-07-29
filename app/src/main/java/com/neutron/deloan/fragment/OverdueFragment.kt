package com.neutron.deloan.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import  com.neutron.deloan.R
import com.neutron.deloan.main.MainActivity

import  com.neutron.deloan.utils.Constants
import  com.neutron.deloan.utils.Slog
import kotlinx.android.synthetic.main.fragment_overdue.*
import kotlinx.android.synthetic.main.fragment_overdue.btn_pay

import kotlinx.android.synthetic.main.fragment_overdue.tv_money


class OverdueFragment : Fragment() {

    override fun onResume() {
        super.onResume()
        Slog.d("OverdueFragment onResume")

        val mainActivity = (activity as MainActivity)
//        mainActivity.setStatusBarColor(mainActivity, R.color.red_ef)
        val loanStatusResult = mainActivity.getloanStatusResult()
        loanStatusResult?.let { result ->
//            tv_money.text = result.principal
            tv_money.text = result.remainAmount
//            tv_over_money.text = result.principal
//            tv_over_money.text = result.amount2Account
//            tv_date.text = "${result.duration} "
//            tv_over_due_date.text = result.app_time
//
//            tv_over_interest.text = result.interest
//            tv_over_risk.text = result.risk
//            tv_over_service.text = result.service
//            tv_over_pay.text = result.pay
//            tv_over_penalty.text = result.penalty
//            tv_over_fale_fee.text = result.fale_fee
//            tv_order_count.text = result.application_id


            applicationId = result.application_id
            amount = result.remainAmount

        }
        isNeed=true
//        scrollView_o?.viewTreeObserver?.addOnScrollChangedListener(lister)


    }


    var isNeed=true

    fun removeListener(){
        isNeed=false
    }


    val lister = ViewTreeObserver.OnScrollChangedListener {

//      if(isNeed){
//          (activity as MainActivity?)?.getRefresh()?.isEnabled = scrollView_o.scrollY === 0
//
//      }


    }



    var isPayAll: Boolean? = null
    var applicationId: String? = null
    var amount: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_pay.setOnClickListener {
            isPayAll = true
            startToPay(isPayAll!!)
        }


//        btn_sub_pay.setOnClickListener {
//            isPayAll = false
//            startToPay(isPayAll!!)
//        }


    }

    private fun startToPay(payAll: Boolean) {
//        val intent = Intent(activity, RepaymentActivity::class.java)
//        val bundle = Bundle()
//        bundle.putBoolean("isPayAll", payAll)
//        bundle.putString("applicationId", applicationId)
//        bundle.putString("amount", amount)
//        Slog.d("startToPay  $payAll   applicationId $applicationId")
//        intent.putExtra("bundle", bundle)
//        startActivity(intent)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overdue, container, false)
    }


}