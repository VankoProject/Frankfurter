package com.kliachenko.presentation.load

import com.kliachenko.presentation.core.views.ChangeVisibility
import com.kliachenko.presentation.core.views.ErrorText
import com.kliachenko.presentation.loading.LoadUiState
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LoadUiStateTest {

    private lateinit var retryButton: FakeCustomView
    private lateinit var progressBar: FakeCustomView
    private lateinit var textView: FakeCustomView

    @Before
    fun setup() {
        retryButton = FakeCustomView()
        progressBar = FakeCustomView()
        textView = FakeCustomView()
    }

    @Test
    fun testError() {
        val errorState = LoadUiState.Error("Error")
        errorState.update(
            retryButton, progressBar, textView
        )
        retryButton.checkShowCalled(1)
        textView.checkShowCalled(1)
        textView.checkTextError("Error")
        progressBar.checkHideCalled(1)
    }

    @Test
    fun testProgress() {
        val progressState = LoadUiState.Progress
        progressState.update(retryButton, progressBar, textView)
        retryButton.checkHideCalled(1)
        textView.checkHideCalled(1)
        progressBar.checkShowCalled(1)
    }

    @Test
    fun testEmpty() {
        val emptyState = LoadUiState.Empty
        emptyState.update(retryButton, progressBar, textView)
        retryButton.checkShowCalled(0)
        retryButton.checkHideCalled(0)
        textView.checkShowCalled(0)
        textView.checkHideCalled(0)
        progressBar.checkShowCalled(0)
        progressBar.checkHideCalled(0)
    }

}

private class FakeCustomView : ChangeVisibility, ErrorText {

    private var showCalledCount = 0
    private var hideCalledCount = 0
    private var changeTextList = mutableListOf<String>()

    override fun show() {
        showCalledCount++
    }

    override fun hide() {
        hideCalledCount++
    }

    override fun change(text: String) {
        changeTextList.add(text)
    }

    fun checkShowCalled(showCalledCount: Int = 0) {
        Assert.assertEquals(showCalledCount, this.showCalledCount)
    }

    fun checkHideCalled(hideCalledCount: Int = 0) {
        Assert.assertEquals(hideCalledCount, this.hideCalledCount)
    }

    fun checkTextError(message: String) {
        Assert.assertEquals(changeTextList, listOf(message))
    }

}
