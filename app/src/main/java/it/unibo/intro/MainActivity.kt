package it.unibo.intro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import it.unibo.intro.basic.theme.StylishPokedex
import it.unibo.intro.basic.theme.loadPokemonFromResources
import it.unibo.intro.ui.theme.IntroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntroTheme {
                StylishPokedex {
                    loadPokemonFromResources()
                }
            }
        }
    }
}
