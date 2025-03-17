package it.unibo.intro.basic.functions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 * My data model
 */
data class Message(val user: String, val data: String)

val myMessageForYou = Message("Gianluca", "Hello Bergen!!")
// Mark my function as a "composable", namely a rendering elements
// A composable function may take data as input and render them inside
@Composable fun UserElement(message: Message) {
    Text( // This is a composable element (a function) that renders the text
        text = message.user
    )
}

@Preview
@Composable
private fun UserElementPreview() {
    UserElement(Message("Gianluca", "Hello Bergen!!"))
}

// As they are function, you can compose more component inside one!
@Composable fun MessageElementWrong(message: Message) {
    Text(text = message.user)
    Text(text = message.data)
}

// Note!! You can compose the functions but without layout the elements are not well positioned!
@Preview
@Composable
private fun MessageElementWrongPreview() {
    MessageElement(myMessageForYou)
}

// You can arrange element with "Boxes"/"Slots" already preconfigured
@Composable fun MessageElement(message: Message) {
    Row {
        Text(text = message.user)
        Text(text = message.data)
    }
}

@Preview
@Composable
private fun MessageElementPreview() {
    MessageElement(myMessageForYou)
}

@Composable fun MessageElementList(vararg messages: Message) {
    Column {
        messages.forEach { MessageElement(it) }
    }
}

@Preview
@Composable
private fun MessageElementListPreview() {
    MessageElementList(
        Message("Gienna", "Hey"),
        Message("Foo", "Bau"),
    )
}