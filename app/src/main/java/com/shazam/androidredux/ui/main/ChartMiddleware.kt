/*
 * Copyright 2017 Shazam Entertainment Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.shazam.androidredux.ui.main

import com.shazam.androidredux.io.ChartsService
import com.shazam.androidredux.model.Chart
import com.shazam.androidredux.redux.Action
import rx.Observable
import rx.schedulers.Schedulers

class ChartMiddleware(private val chartsService: ChartsService) {

    fun getChart(): Observable<ChartAction> =
            chartsService.getChart()
                    .map { ChartAction.ChartResultSuccess(it) as ChartAction }
                    .subscribeOn(Schedulers.io())
                    .onErrorReturn { ChartAction.ChartResultError(it) }
                    .startWith(ChartAction.ChartProgress)
}

sealed class ChartAction : Action {
    data class ChartResultSuccess(val chart: Chart) : ChartAction()
    data class ChartResultError(val error: Throwable) : ChartAction()
    object ChartProgress : ChartAction()
}
