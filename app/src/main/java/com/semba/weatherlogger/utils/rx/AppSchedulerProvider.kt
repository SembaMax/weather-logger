package com.semba.weatherlogger.utils.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by SeMbA on 2020-01-02.
 */
class AppSchedulerProvider : ISchedulerProvider{

    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun newThread(): Scheduler {
        return Schedulers.newThread()
    }

    override fun single(): Scheduler {
        return Schedulers.single()
    }

    override fun trampoline(): Scheduler {
        return Schedulers.trampoline()
    }
}