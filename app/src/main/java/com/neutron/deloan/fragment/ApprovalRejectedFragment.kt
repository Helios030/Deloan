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

class ApprovalRejectedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_approval_rejected, container, false)
    }

    override fun onResume() {
        super.onResume()
        val mainActivity = (activity as MainActivity)


    }
}