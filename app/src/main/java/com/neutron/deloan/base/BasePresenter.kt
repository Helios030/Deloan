package com.neutron.deloan.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.neutron.deloan.utils.Slog
import kotlinx.coroutines.Job

abstract class BasePresenter<V : IView>: IPresenter<V>, LifecycleObserver {


//    protected var mModel: M? = null
    protected var mView: V? = null
    protected var job: Job? = null

    private val isViewAttached: Boolean
        get() = mView != null

    /**
     * 创建 Model
     */
//    open fun createModel(): M? = null
    /**
     * 加载View
     */
    override fun attachView(mView: V) {
        this.mView = mView
//        mModel = createModel()
        if (mView is LifecycleOwner) {
            (mView as LifecycleOwner).lifecycle.addObserver(this)
//            if (mModel != null && mModel is LifecycleObserver) {
//                (mView as LifecycleOwner).lifecycle.addObserver(mModel as LifecycleObserver)
//            }
        }

    }

    override fun detachView() {
//        mModel?.onDetach()
//        this.mModel = null
        this.mView = null
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
         detachView()
        job?.cancel()
        job=null


        Slog.d("BasePresenter 销毁")
        owner.lifecycle.removeObserver(this)
    }



    open fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    private class MvpViewNotAttachedException internal constructor() :
        RuntimeException("Please call IPresenter.attachView(IBaseView) before  requesting data to the IPresenter")





}

