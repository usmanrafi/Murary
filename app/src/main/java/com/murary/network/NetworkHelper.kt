package com.murary.network

import com.murary.utils.Constants
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject


interface ServiceCallback<T> {
    fun onSuccess(response: T)
    fun onFailure(error: String)
}

class NetworkHelper @Inject constructor() {

    var disposable: Disposable? = null

    fun <T> serviceCall(call: Single<Response<T>>, callback: ServiceCallback<T>) {
        call.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Response<T>> {
                override fun onSuccess(response: Response<T>) = if (response.isSuccessful) {
                    response.body()?.let {
                        callback.onSuccess(it)
                    } ?: run {
                        callback.onFailure(response.message().toString())
                    }
                } else {
                    callback.onFailure(response.message())
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onError(e: Throwable) {
                    e.message?.let {
                        callback.onFailure(it)
                    } ?: run {
                        callback.onFailure(Constants.REQUEST_FAILURE)
                    }
                }
            })
    }

    fun dispose() {
        disposable?.let {
            if (!it.isDisposed)
                it.dispose()
        }
    }
}
