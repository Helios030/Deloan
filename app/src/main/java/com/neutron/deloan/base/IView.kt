package com.neutron.deloan.base


interface IView {
    /**
     * 显示加载
     */
    fun showLoading()

    /**
     * 隐藏加载
     */
    fun hideLoading()



    /**
     * 显示错误信息
     */
    fun showError(e: Exception)


}