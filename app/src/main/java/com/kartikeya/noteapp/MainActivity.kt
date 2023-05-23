package com.kartikeya.noteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kartikeya.noteapp.data.NoteDataSource
import com.kartikeya.noteapp.model.Note
import com.kartikeya.noteapp.screens.NoteScreen
import com.kartikeya.noteapp.screens.NoteViewModel
import com.kartikeya.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                   // val noteViewModel= viewModel<NoteViewModel>()//this also works
                   val  noteViewModel:NoteViewModel by viewModels()
                    NotesApp(noteViewModel)

                }
            }
        }
    }
}

@Composable
fun NotesApp(noteViewModel: NoteViewModel ){
    val notesList=noteViewModel.noteList.collectAsState().value
    NoteScreen(
        notes = notesList,
        onRemoveNote = { noteViewModel.removeNote(it) },
        onAddNote = { noteViewModel.addNote(it) })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NoteAppTheme {

    }
}
