package com.github.daniilbug.lifeisbetter.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class StatusLiveData<T>: MutableLiveData<T>() {
    private var isShown = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer {
            if (isShown.compareAndSet(false, true))
                observer.onChanged(it)
        })
    }

    override fun postValue(value: T) {
        isShown.set(false)
        super.postValue(value)
    }

    override fun setValue(value: T) {
        isShown.set(false)
        super.setValue(value)
    }
}