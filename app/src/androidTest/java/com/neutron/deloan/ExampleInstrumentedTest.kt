package com.neutron.deloan

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {

        var str ="你好"
        print(str)

        GlobalScope.launch(Dispatchers.Main) {

            str=  returnThis()
            print(str)
        }

    }

    suspend fun returnThis():String{
        Thread.sleep(2000)
        return "延迟返回"

    }
    
}