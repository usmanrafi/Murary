package com.murary.base

abstract class BasePresenter<T> where T : BaseView {

    var view: T? = null
    open fun attachView(view: T) {
        this.view = view
    }

    open fun detachView() {
        this.view = null
    }
}