package  com.neutron.deloan.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import  com.neutron.deloan.R
import com.neutron.deloan.main.MainActivity


import kotlinx.android.synthetic.main.fragment_pending_repayment.*
import kotlinx.android.synthetic.main.fragment_pending_repayment.btn_pay

class PendingRepaymentFragment : Fragment() {
    override fun onResume() {
        super.onResume()
        val mainActivity = (activity as MainActivity)
        val loanStatusResult = mainActivity.getloanStatusResult()
//        mainActivity.setStatusBarColor(mainActivity, R.color.meet_fc)
        loanStatusResult?.let { result ->
            tv_money.text = result.principal
//            tv_money.text = result.remainAmount
            tv_due_money.text = result.principal
//            tv_over_money.text = result.amount2Account
            tv_date_p.text = "${result.duration} "
//            tv_time.text=result.app_time
            tv_due_date.text = result.app_time
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


        return inflater.inflate(R.layout.fragment_pending_repayment, container, false)
    }

}