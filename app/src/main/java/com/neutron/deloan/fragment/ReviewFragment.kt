package  com.neutron.deloan.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import  com.neutron.deloan.R
import com.neutron.deloan.main.MainActivity
import  com.neutron.deloan.utils.Constants
import kotlinx.android.synthetic.main.fragment_approval_rejected.*
import kotlinx.android.synthetic.main.fragment_review.*

class ReviewFragment : Fragment() {
    override fun onResume() {
        super.onResume()
        val mainActivity = (activity as MainActivity)
        val loanStatusResult=  mainActivity.  getloanStatusResult()
        loanStatusResult?.let {
            tv_money.text = it.principal
            tv_due_date.text = "${it.duration} "
            tv_time.text = it.app_time
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

}