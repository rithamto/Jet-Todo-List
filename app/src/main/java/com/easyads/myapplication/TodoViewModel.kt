package com.easyads.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {

    private var _todoList = MutableLiveData<List<Todo>>()
    val todoList : LiveData<List<Todo>> = _todoList

    init {
        getAllTodo()
    }

    fun getAllTodo(){
        _todoList.value = TodoManager.getAllTodo().reversed()
    }

    fun addTodo(title : String){
        if(title != "")
            TodoManager.addTodo(title)
        getAllTodo()
    }

    fun deleteTodo(id : Int){
        TodoManager.deleteTodo(id)
        getAllTodo()
    }


}