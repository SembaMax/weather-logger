package com.semba.weatherlogger.utils.rx

import io.reactivex.Scheduler

/**
 * Created by SeMbA on 2020-01-02.
 */
interface ISchedulerProvider {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler

    fun newThread(): Scheduler

    fun single(): Scheduler

    fun trampoline() : Scheduler
}