package it.unibo.intro.basic.state

import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * My data model
 */
data class Counter(val value: Int) {
    fun increment() = Counter(value + 1)
    fun decrement() = Counter(value - 1)
}

@Composable
fun CounterElement(counter: Counter) {
    var state = counter
    Surface() {
        Row(verticalAlignment = Alignment.CenterVertically ) {
            Text(text = state.value.toString())
            Spacer(Modifier.padding(2.dp))
            Button(onClick = { state = state.increment() }) {
                Text("Click")
            }
        }
    }
}

@Preview
@Composable
private fun CounterElementExample() {
    CounterElement(Counter(0))
}

@Composable
fun CounterElementRightState(counter: Counter) {
    var state by remember { mutableStateOf(counter) }
    Surface() {
        Row(verticalAlignment = Alignment.CenterVertically ) {
            Text(text = state.value.toString())
            Spacer(Modifier.padding(2.dp))
            Button(onClick = { state = state.increment() }) {
                Text("Click")
            }
        }
    }
}

@Preview
@Composable
private fun CounterElementRightStateExample() {
    CounterElementRightState(Counter(0))
}

@Composable
fun CounterElementRightStateWithReset(counter: Counter) {
    var state by remember { mutableStateOf(counter) }
    Surface() {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = state.value.toString())
            Spacer(Modifier.padding(2.dp))
            Button(onClick = { state = state.increment() }) {
                Text("Increment")
            }
            Spacer(Modifier.padding(2.dp))
            Button(onClick = { state = state.decrement() }) {
                Text("Decrement")
            }
            Spacer(Modifier.padding(2.dp))
            Button(onClick = { state = Counter(0) }) {
                Text("Reset")
            }
        }
    }
}

@Preview
@Composable
private fun CounterElementRightStateWithResetExample() {
    CounterElementRightStateWithReset(Counter(0))
}

/**
 * Example of state hoisting
 *
 * State hoisting is a pattern in Jetpack Compose where you move the state up to a common ancestor
 * so that it can be shared and controlled by multiple composables. This helps in managing state
 * in a more predictable and testable way.
 *
 * In this example, we will create a counter component that hoists its state to its parent.
 */

// Stateless counter component that takes state and event handlers as parameters
@Composable
fun StatelessCounter(
    counter: Counter,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onReset: () -> Unit
) {
    Surface {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = counter.value.toString())
            Spacer(Modifier.padding(2.dp))
            Button(onClick = onIncrement) {
                Text("Increment")
            }
            Spacer(Modifier.padding(2.dp))
            Button(onClick = onDecrement) {
                Text("Decrement")
            }
            Spacer(Modifier.padding(2.dp))
            Button(onClick = onReset) {
                Text("Reset")
            }
        }
    }
}

// Stateful counter component that hoists the state to its parent
@Composable
fun HoistedCounter() {
    var counter by remember { mutableStateOf(Counter(0)) }

    StatelessCounter(
        counter = counter,
        onIncrement = { counter = counter.increment() },
        onDecrement = { counter = counter.decrement() },
        onReset = { counter = Counter(0) }
    )
}

@Preview
@Composable
private fun HoistedCounterPreview() {
    HoistedCounter()
}