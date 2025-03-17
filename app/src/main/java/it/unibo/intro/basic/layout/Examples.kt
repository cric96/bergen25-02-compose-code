package it.unibo.intro.basic.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Pokemon(val name: String, val type: String, val description: String)

// Row is a composable that arranges its children in a horizontal sequence
@Composable fun RenderPokemonRow(pokemon: Pokemon) {
    Row  {
        Text(text = pokemon.name)
        Text(text = pokemon.type)
        Text(text = pokemon.description)
    }
}

// Column is a composable that arranges its children in a vertical sequence
@Composable fun RenderPokemonColumn(pokemon: Pokemon) {
     Column {
         Spacer(Modifier.padding(10.dp))
         Text(text = pokemon.name)
         Text(text = pokemon.type)
         Text(text = pokemon.description)
     }
}

//Box is a composable that allows you to position its children relative to its edges or center
@Composable fun RenderPokemonBox(pokemon: Pokemon) {
    Box {
        Text(text = pokemon.name)
        Text(text = pokemon.type)
        Text(text = pokemon.description)
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun RenderPokemonPreview() {
    val pokemonToRender = Pokemon("Pikachu", "Electric", "A cute electric mouse")
    val anotherPokemonToRender = Pokemon("Charmander", "Fire", "A cute fire lizard")
    RenderPokemonRow(pokemonToRender)
    RenderPokemonColumn(anotherPokemonToRender)
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun RenderPokemonBoxPreview() {
    val pokemonToRender = Pokemon("Pikachu", "Electric", "A cute electric mouse")
    RenderPokemonBox(pokemonToRender)
}

@Composable
fun Pokedex(vararg pokemons: Pokemon) {
    Scaffold(
        topBar = {
            Text(
                text = "Pokedex",
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)) {
                pokemons.forEach { RenderPokemonRow(it) }
            }
        },
        bottomBar = {
            Text(
                text = "End of Pokedex"
            )
        }
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)

@Composable
private fun PokedexPreview() {
    Pokedex(
        Pokemon("Pikachu", "Electric", "A cute electric mouse"),
        Pokemon("Charmander", "Fire", "A cute fire lizard")
    )
}