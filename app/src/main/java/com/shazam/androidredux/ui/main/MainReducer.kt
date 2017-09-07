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


import com.shazam.androidredux.redux.Reducer

object MainReducer : Reducer<MainState, ChartAction> {

    override fun reduce(oldState: MainState, action: ChartAction) = when (action) {
        is ChartAction.ChartProgress -> emptyMainState.copy(isLoading = true)
        is ChartAction.ChartResultError -> emptyMainState.copy(error = action.error)
        is ChartAction.ChartResultSuccess -> emptyMainState.copy(chart = action.chart)
    }
}