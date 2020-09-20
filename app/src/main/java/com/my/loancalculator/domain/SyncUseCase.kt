package com.my.loancalculator.domain

import com.my.loancalculator.model.ProfileResponse
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SyncUseCase {
    private val subscribeScheduler: Scheduler = Schedulers.io()
    private val observeScheduler: Scheduler = AndroidSchedulers.mainThread()

    operator fun invoke(): Single<ArrayList<ProfileResponse>> {
        //it is a demo variant, because in real life application we has to download profile from back-end Api endpoint
        //so we use this stub

        val profiles: ArrayList<ProfileResponse> = ArrayList()
        profiles.add(ProfileResponse( 1, "49002010965", "Olmer", true, 0))
        profiles.add(ProfileResponse( 2, "49002010976", "Olmer2", false, 100))
        profiles.add(ProfileResponse( 3, "49002010987", "Olmer3", false, 300))
        profiles.add(ProfileResponse( 4, "49002010998", "Olmer4", false, 1000))

        return Single.just(profiles)
            .subscribeOn(subscribeScheduler)
            .observeOn(observeScheduler)
    }

    companion object {
        private const val TAG = "SyncUseCase"
    }

}