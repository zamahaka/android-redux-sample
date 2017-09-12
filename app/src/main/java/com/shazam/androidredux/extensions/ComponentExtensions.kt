/*
 * Copyright 2017 Shazam Entertainment Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.shazam.androidredux.extensions

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.shazam.androidredux.redux.*
import kotlin.reflect.KClass

fun <S : State, A : Action, VS : ViewState, T : ReduxDipatcher<S, A, VS>> Fragment.getDispatcher(
        clazz: KClass<out DispatcherHolder<S, A, VS, T>>): T {
    return ViewModelProviders.of(this).get(clazz.java).dispatcher
}

fun <S : State, A : Action, VS : ViewState, T : ReduxDipatcher<S, A, VS>> FragmentActivity.getDispatcher(
        clazz: KClass<out DispatcherHolder<S, A, VS, T>>): T {
    return ViewModelProviders.of(this).get(clazz.java).dispatcher
}
