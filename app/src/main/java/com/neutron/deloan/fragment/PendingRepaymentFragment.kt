package  com.neutron.deloan.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import com.neutron.deloan.R
import com.neutron.deloan.bean.LoanStatusResult
import com.neutron.deloan.main.MainActivity
import com.neutron.deloan.utils.Constants
import com.neutron.deloan.web.WebViewActivity
import kotlinx.android.synthetic.main.fragment_pending_repayment.*

class PendingRepaymentFragment : Fragment() {

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
        val mainActivity = (activity as MainActivity)
        loanStatusResult = mainActivity.getloanStatusResult()
//        mainActivity.setStatusBarColor(mainActivity, R.color.meet_fc)
        loanStatusResult?.let { result ->
            tv_money.text = result.remainAmount
//            tv_money.text = result.remainAmount
            tv_due_money.text = result.principal
//            tv_over_money.text = result.amount2Account
            tv_date_p.text = "${result.duration} "
//            tv_time.text=result.app_time
            tv_due_date.text = result.deposit_time
//            tv_due_money_count.text = result.remainAmount
//            tv_pen_money.text = result.amount2Account
            tv_pen_interest.text = result.interest
            applicationId = result.application_id
            amount = result.remainAmount
        }
        isNeed = true
        scrollView?.viewTreeObserver?.addOnScrollChangedListener(lister)
    }


    var isNeed = true
    fun removeListener() {
        isNeed = false

    }


    val lister = ViewTreeObserver.OnScrollChangedListener {
        if (isNeed) {
            (activity as MainActivity?)?.getRefresh()?.isEnabled = scrollView.scrollY === 0
        }

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


        btn_sub_pay_r.setOnClickListener {
            isPayAll = false
            startToPay(isPayAll!!)
        }


    }

    private fun startToPay(payAll: Boolean) {
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
        return inflater.inflate(R.layout.fragment_pending_repayment, container, false)
    }

}