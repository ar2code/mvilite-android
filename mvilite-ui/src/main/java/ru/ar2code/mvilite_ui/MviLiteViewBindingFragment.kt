package ru.ar2code.mvilite_ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.ar2code.mvilite_core.MviLiteViewModel

/**
 * MviLite fragment that has single [MviLiteViewModel] for UI State and Side effects.
 * Such fragment should has only one view model and does not has shared view model.
 *
 * To render ui depends on State use [onNewUiViewState].
 *
 * To handle side effect use [onNewUiSideEffect].
 *
 */
abstract class MviLiteViewBindingFragment<VB, S> :
    ViewBindingFragment<VB>() where VB : ViewBinding {

    /**
     * Single view model that describes current UI screen.
     */
    protected abstract val viewModel: MviLiteViewModel<S>

    /**
     * Current UI state, that was rendered.
     */
    protected var currentUiViewState: S? = null
        private set

    /**
     * Render new ui state.
     *
     * You can compare new state with previous [currentUiViewState] to make decision what parts of UI should be re-rendered.
     */
    protected abstract fun onNewUiViewState(newUiViewState: S)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUiState()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        currentUiViewState = null
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    onNewUiViewState(it)
                    currentUiViewState = it
                }
            }
        }
    }

}