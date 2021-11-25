package ru.ar2code.mvilite_core

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test

class MviLiteViewModelTest {

    class TestInitialStateFactory : InitialStateFactory<String> {
        override fun getState(): String {
            return "init"
        }
    }

    class TestMviLiteViewModel : MviLiteViewModel<String, String>(TestInitialStateFactory()) {

        suspend fun updateStringState(p: Int) {

            println("current state = ${uiState.value}")

            //Например, я отправил состояние загрузки, поставил флаги, UI отобразил
            updateState { "start $p" }

            //Выполняю некоторый юзкейс
            //delay(100)

            //Снова обновляю стейт. Здесь возможно, стейт уже поменялся другими потоками
            //По сути мы снова вызываем редуктор, с помощью которого мы определяем новое состояние.
            //Редуктор будет выполняться до тех пор, пока не сможет атомарно обновить стейт.
            //Т.е. возможно, что новый стейт уже будет неактуален и мы можем просто вернуть null.

            updateState {
                "end $p"
            }
        }

    }


    @Test
    fun testViewModel() = runBlocking {
        val vm = TestMviLiteViewModel()

        launch(Dispatchers.Default) {
            vm.updateStringState(0)
        }
        launch(Dispatchers.IO) {
            vm.updateStringState(1)
        }

        Unit
    }

}