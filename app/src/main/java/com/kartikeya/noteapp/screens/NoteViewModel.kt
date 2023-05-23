package com.kartikeya.noteapp.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kartikeya.noteapp.data.NoteDataSource
import com.kartikeya.noteapp.model.Note
import com.kartikeya.noteapp.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {
    //    private var noteList = mutableListOf<Note>()
    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList = _noteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllNotes().distinctUntilChanged()
                .collect{ listOfNotes ->
                    if (listOfNotes.isNullOrEmpty()){
                        Log.d("Empty", "Empty List: ")
                        _noteList.value= emptyList()
                    }else{
                        _noteList.value=listOfNotes
                    }
                }
        }
//        noteList.addAll(NoteDataSource().loadNotes())
    }

    fun addNote(note: Note) = viewModelScope.launch { repository.addNote(note) }
    fun updateNote(note: Note) = viewModelScope.launch { repository.updateNote(note) }
    fun removeNote(note: Note) = viewModelScope.launch { repository.deleteNote(note) }

}