package com.neutron.deloan

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.just.agentweb.AgentWeb
import com.neutron.deloan.base.BaseActivity
import com.neutron.deloan.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    var binding: ActivityMainBinding? = null
    override fun getLayout(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding!!.root
    }


    override fun initData() {

    }

    override fun initView() {
        binding?.let {
            mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(
                    it.rlMain,
                    LinearLayout.LayoutParams(-1, -1)
                )
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go("https://www.baidu.com/")

        }


    }
}