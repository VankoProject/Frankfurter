package com.kliachenko.presentation.load

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

@Suppress("UNCHECKED_CAST")
class FakeRunAsync : RunAsync {

    private var actualCacheUiBlock: (Any) -> Unit = {}
    private var actualCacheResult: Any = ""

    override fun <T : Any> start(
        coroutineScope: CoroutineScope,
        backGround: suspend () -> T,
        uiBlock: (T) -> Unit,
    ) = runBlocking {
        val result = backGround.invoke()
        actualCacheResult = result
        actualCacheUiBlock = uiBlock as (Any) -> Unit
    }

    fun
    fun returnLoadResult() {
        actualCacheUiBlock.invoke(actualCacheResult)
    }
}