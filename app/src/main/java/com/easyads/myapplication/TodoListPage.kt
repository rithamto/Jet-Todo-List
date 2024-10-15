package com.easyads.myapplication

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getContextForLanguage
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun TodoListPage(viewModel: TodoViewModel, navController: NavHostController) {

    val todoList by viewModel.todoList.observeAsState()
    var inputText by remember {
        mutableStateOf("")
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),

            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(value = inputText,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.75f),
                shape = RoundedCornerShape(8.dp),
                onValueChange = {
                    inputText = it
                })
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {

                viewModel.addTodo(inputText)
                inputText = ""
            }, shape = RoundedCornerShape(8.dp)
                ) {
                Text(text = "Add")
            }
        }
        todoList?.let {
            LazyColumn(
                content = {
                    itemsIndexed(it) { index: Int, item: Todo ->
                        TodoItem(item = item, onDelete = {
                            viewModel.deleteTodo(item.id)
                        })

                    }
                },
            )
        } ?: Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "No items yet",
            fontSize = 16.sp
        )
    }
}

@Composable
fun TodoItem(item: Todo, onDelete: () -> Unit) {
    var ctx = LocalContext.current
    Row(
        modifier = Modifier
            .clickable {
                val intent = Intent(ctx , DialogActivity::class.java)

                ctx.startActivity(intent)
            }
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = SimpleDateFormat(
                    "HH:mm:aa, dd/mm", Locale.ENGLISH
                ).format(item.createdAt), fontSize = 12.sp, color = Color.LightGray
            )
            Text(
                text = item.title, fontSize = 20.sp, color = Color.White
            )
        }
        IconButton(onClick = onDelete) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_delete_24),
                contentDescription = "Delete",
                tint = Color.White
            )
            Surface {

            }
        }
    }
}