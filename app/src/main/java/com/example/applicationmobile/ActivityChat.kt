package com.example.applicationmobile
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applicationmobile.data.Message
import com.example.applicationmobile.databinding.ActivitychatMainBinding
import com.example.applicationmobile.utils.BotResponse
import com.example.applicationmobile.utils.Constants.OPEN_GOOGLE
import com.example.applicationmobile.utils.Constants.OPEN_MAIN_ACTIVITY2
import com.example.applicationmobile.utils.Constants.OPEN_SEARCH
import com.example.applicationmobile.utils.Constants.RECEIVE_ID
import com.example.applicationmobile.utils.Constants.SEND_ID
import com.example.applicationmobile.utils.Timetimp
import kotlinx.coroutines.*

class ActivityChat: ComponentActivity() {
    private lateinit var binding: ActivitychatMainBinding
    private val TAG = "ActivityChat"

    var messagesList = mutableListOf<Message>()
    private lateinit var adapter: MessageAdapter
    private val botList = listOf("Ahmed", "Denis", "Dallali", "Achraf")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitychatMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView()

        clickEvents()

        val random = (0..3).random()
        customBotMessage("Bonjour ! Aujourd'hui, vous parlez avec ${botList[random]}. Comment puis-je vous aider ?69")
    }

    private fun clickEvents() {
        binding.btnSend.setOnClickListener {
            sendMessage()
        }

        binding.etMessage.setOnClickListener {
            GlobalScope.launch {
                delay(100)
                withContext(Dispatchers.Main) {
                    binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
                }
            }
        }
    }

    private fun recyclerView() {
        adapter = MessageAdapter()
        binding.rvMessages.adapter = adapter
        binding.rvMessages.layoutManager = LinearLayoutManager(applicationContext)
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun sendMessage() {
        val message = binding.etMessage.text.toString()
        val timeStamp = Timetimp.timeStamp()

        if (message.isNotEmpty()) {
            messagesList.add(Message(message, SEND_ID, timeStamp))
            binding.etMessage.setText("")

            adapter.insertMessage(Message(message, SEND_ID, timeStamp))
            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)
        }
    }

    private fun botResponse(message: String) {
        val timeStamp = Timetimp.timeStamp()

        GlobalScope.launch {
            delay(1000)

            withContext(Dispatchers.Main) {
                val response = BotResponse.basicResponses(message)
                messagesList.add(Message(response, RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp))
                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)

                when (response) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                    OPEN_MAIN_ACTIVITY2 -> {
                        val intent = Intent(this@ActivityChat, MainActivity2::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun customBotMessage(message: String) {
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Timetimp.timeStamp()
                messagesList.add(Message(message, RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(message, RECEIVE_ID, timeStamp))
                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
}