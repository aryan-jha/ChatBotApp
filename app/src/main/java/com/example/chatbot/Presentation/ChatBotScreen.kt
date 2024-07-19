package com.example.chatbot.Presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chatbot.Domain.ViewModel.MessageModel
import com.example.chatbot.Domain.ViewModel.chatViewModel
import com.example.chatbot.R


@Composable
fun ChatBotScreen(navController: NavHostController, viewModel: chatViewModel) {

    Column {

        topBar()

        messageItem(modifier = Modifier.weight(1f), messageLists = viewModel.messageList)

        messageInput {
            viewModel.sendMessage(it)
        }

        Spacer(modifier = Modifier.height(10.dp))

    }


}

@Composable
fun messageItem(modifier: Modifier = Modifier, messageLists : List<MessageModel>) {

    if(messageLists.isEmpty()){
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(painter = painterResource(
                id = R.drawable.outline_chat_24),
                modifier = Modifier.size(140.dp),
                tint = Color(0xBAAD73E6),
                contentDescription = null )
            Text(text = "Ask me Anything", fontSize = 21.sp, fontWeight = FontWeight.W500, color = Color(
                0xFF6C666F
            )
            )

        }
    }else{
        LazyColumn(
            modifier = modifier,
            reverseLayout = true
        ) {
            items(messageLists.reversed()){
                messageRow(messageModel = it)
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }

}

@Composable
fun topBar() {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(top = 20.dp, start = 12.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){

        Icon(
            modifier = Modifier.size(30.dp),
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "More"
        )

        Text(
            text = "Chat With GEMINI",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,)
           
        Icon(
            modifier = Modifier.size(30.dp),
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More"
        )
    }
}

@Composable
fun messageRow(messageModel: MessageModel) {

    val isModel = messageModel.role == "model"

    Row (
        verticalAlignment = Alignment.CenterVertically
    ){
        Box (
            modifier = Modifier.fillMaxWidth()

        ){
            Box(
                modifier = Modifier
                    .align(if (isModel) Alignment.BottomStart else Alignment.BottomEnd)
                    .padding(
                        start = if (isModel) 8.dp else 70.dp,
                        end = if (isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        if (isModel) Color(0xFFF8E9FD) else Color(0xFFAD73E6),
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomEnd = if (isModel) 0.dp else 16.dp,
                            bottomStart = if (isModel) 16.dp else 0.dp
                        )
                    )
                    .padding(16.dp)
            ) {
                if(isModel){

                    SelectionContainer {
                        Text(
                            text = messageModel.message,
                            fontWeight = FontWeight.W500,
                            fontSize = 18.sp
                        )
                    }
                }else{
                    Text(text = messageModel.message,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.W400,
                        color = Color.White
                        )
                }
            }
        }
    }
}


@Composable
fun messageInput(onMessageSent: (String) -> Unit) {
    var inputMsg by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.padding(bottom = 50.dp, start = 24.dp, top = 18.dp)
    ) {
        Row {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                value = inputMsg,
                shape = RoundedCornerShape(16.dp),
                placeholder = {
                    Text(text = "Enter your message")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                onValueChange = {
                    inputMsg = it
                }
            )
            IconButton(onClick = {
                if(inputMsg.isNotEmpty()){
                onMessageSent(inputMsg)
                inputMsg = ""
            }
            }) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.round_send_24),
                    tint = Color(0xFFAD73E6),
                    contentDescription = "Send"
                )
            }

        }
    }
}