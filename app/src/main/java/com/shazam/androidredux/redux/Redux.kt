/*
 * Copyright 2017 Shazam Entertainment Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.shazam.androidredux.redux

import rx.Observable
import rx.subjects.BehaviorSubject
import rx.subjects.PublishSubject
import rx.subscriptions.CompositeSubscription

interface Action

interface State

interface Reducer<S : State, in A : Action> {
    fun reduce(oldState: S, action: A): S
}

interface Store<S : State, in A : Action> {
    fun dispatch(action: A)
    fun dispatch(actions: Observable<out A>)
    fun asObservable(): Observable<S>
    fun currentState(): S
    fun tearDown()
}

class SimpleStore<S : State, in A : Action>(private val initialValue: S,
                                            reducer: Reducer<S, A>) : Store<S, A> {

    private val actionsSubject = PublishSubject.create<A>()
    private val statesSubject = BehaviorSubject.create<S>()

    private val compositeSub = CompositeSubscription()

    init {
        compositeSub.add(actionsSubject
                .scan(initialValue, reducer::reduce)
                .distinctUntilChanged()
                .subscribe { statesSubject.onNext(it) }
        )
    }

    override fun dispatch(action: A) {
        actionsSubject.onNext(action)
    }

    override fun dispatch(actions: Observable<out A>) {
        compositeSub.add(actions.subscribe { dispatch(it) })
    }

    override fun asObservable(): Observable<S> = statesSubject

    override fun currentState(): S = if (statesSubject.hasValue()) statesSubject.value else initialValue

    override fun tearDown() {
        compositeSub.unsubscribe()
    }
}