package it.unibo.intro.basic.theme


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.unibo.intro.R
import java.util.Locale


/** My data models */
enum class PokemonType {
    FIRE,
    WATER,
    ELECTRIC,
    GRASS,
    NORMAL;

    companion object {
        fun fromString(type: String): PokemonType {
            return try {
                valueOf(type.uppercase())
            } catch (e: IllegalArgumentException) {
                NORMAL
            }
        }
    }

    override fun toString(): String {
        return name.replaceFirstChar { if (it. isLowerCase()) it. titlecase(Locale.getDefault()) else it. toString() }
    }
}

data class Pokemon(val name: String, val type: PokemonType)

/**
 * Gets the color associated with a Pokémon type
 */
@Composable
fun getPokemonTypeColor(type: PokemonType): Color {
    return when(type) {
        PokemonType.FIRE -> Color(0xFFFFCCCB)
        PokemonType.WATER -> Color(0xFFB3E5FC)
        PokemonType.ELECTRIC -> Color(0xFFFFF9C4)
        PokemonType.GRASS -> Color(0xFFDCEDC8)
        PokemonType.NORMAL -> Color(0xFFE0E0E0)
    }
}
/**
 * Displays the first letter of the Pokémon's name in a box
 */
@Composable
fun PokemonInitial(initial: Char) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(Color.White.copy(0.5f), RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = initial.toString(), fontWeight = FontWeight.Bold)
    }
}

@Preview
@Composable
private fun PokemonInitialPreview() {
    PokemonInitial('C')
}
/**
 * Displays Pokémon details
 */
@Composable
fun PokemonDetails(name: String, type: String) {
    Column {
        Text(text = name, fontWeight = FontWeight.Bold)
        Text(text = type, style = MaterialTheme.typography.bodySmall)
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun PokemonDetailsPreview() {
    PokemonDetails("Charmander", "Fire")
}
/**
 * Card displaying a single Pokémon
 */
@Composable
fun PokemonItem(pokemon: Pokemon) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = getPokemonTypeColor(pokemon.type)
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PokemonInitial(pokemon.name.first())
            Spacer(modifier = Modifier.width(8.dp))
            PokemonDetails(pokemon.name, pokemon.type.toString())
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun PokemonItemPreview() {
    PokemonItem(Pokemon("Charmander", PokemonType.FIRE))
}
/**
 * Search bar for filtering Pokémon
 */
@Composable
fun SearchBar(searchQuery: String, onQueryChange: (String) -> Unit) {
    TextField(
        value = searchQuery,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        placeholder = { Text("Search") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        singleLine = true
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun SearchBarPreview() {
    SearchBar("", {})
}
/**
 * List of Pokémon filtered by search query
 */
@Composable
fun PokemonList(pokemonList: List<Pokemon>) {
    LazyColumn {
        items(pokemonList) { pokemon ->
            PokemonItem(pokemon)
        }
    }
}


@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun PokemonListPreview() {
    PokemonList(getPokemonSampleList())
}
/**
 * Sample list of Pokémon for demo purposes
 */
@Composable
fun getPokemonSampleList(): List<Pokemon> {
    return remember {
        listOf(
            Pokemon("Pikachu", PokemonType.ELECTRIC),
            Pokemon("Charmander", PokemonType.FIRE),
            Pokemon("Squirtle", PokemonType.WATER),
            Pokemon("Bulbasaur", PokemonType.WATER),
            Pokemon("Eevee", PokemonType.NORMAL),
        )
    }
}

@Composable fun loadPokemonFromResources(): List<Pokemon> {
       val context = LocalContext.current
       val resources = context.resources
       val pokemonNames = resources.getStringArray(R.array.pokemon_names)
       val pokemonTypes = resources.getStringArray(R.array.pokemon_types)

       return pokemonNames.zip(pokemonTypes) { name, type ->
           Pokemon(name, PokemonType.fromString(type))
       }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StylishPokedex(loader: @Composable () -> List<Pokemon>) {
    // Hoisting
    var searchQuery by remember { mutableStateOf("") }
    val pokemonList = loader()
    // Filter from searchQuery
    val filteredList = remember(searchQuery) {
        pokemonList.filter {
            it.name.contains(searchQuery, ignoreCase = true) ||
            it.type.toString().contains(searchQuery, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Pokédex") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            SearchBar(searchQuery) { searchQuery = it }
            PokemonList(filteredList)
        }
    }
}
@Preview
@Composable fun StylishPokedexPreview() {
    StylishPokedex {
        loadPokemonFromResources()
    }
}