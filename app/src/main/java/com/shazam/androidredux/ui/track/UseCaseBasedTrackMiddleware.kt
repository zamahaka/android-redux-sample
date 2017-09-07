/*
 * Copyright 2017 Shazam Entertainment Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.shazam.androidredux.ui.track

import com.shazam.androidredux.io.ChartsService
import rx.Observable
import rx.schedulers.Schedulers

class UseCaseBasedTrackMiddleware(private val chartsService: ChartsService)
    : TrackMiddleware {

    override fun fetchTrack(key: String): Observable<TrackAction> =
            chartsService.getChart()
                    .map { it.fullChart.first { it.key == key } }
                    .map { TrackAction.TrackResultAction(it) as TrackAction }
                    .onErrorReturn { TrackAction.TrackErrorAction(it) }
                    .startWith(TrackAction.TrackLoadingAction(key))
                    .subscribeOn(Schedulers.io())
}
