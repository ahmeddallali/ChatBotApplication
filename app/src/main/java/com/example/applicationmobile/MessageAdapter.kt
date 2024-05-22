package com.example.applicationmobile

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView



import com.example.applicationmobile.data.Message

import com.example.applicationmobile.databinding.MessageItemBinding
import com.example.applicationmobile.utils.Constants.RECEIVE_ID
import com.example.applicationmobile.utils.Constants.SEND_ID


class MessageAdapter : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    var messagesList = mutableListOf<Message>()

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                // Remove message on item click
                messagesList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messagesList[position]
        val binding = MessageItemBinding.bind(holder.itemView)

        when (currentMessage.id) {
            SEND_ID -> {
                binding.tvMessage.text = currentMessage.message
                binding.tvMessage.visibility = View.VISIBLE
                binding.tvBotMessage.visibility = View.GONE
            }
            RECEIVE_ID -> {
                binding.tvBotMessage.text = currentMessage.message
                binding.tvBotMessage.visibility = View.VISIBLE
                binding.tvMessage.visibility = View.GONE
            }
        }
    }

    fun insertMessage(message: Message) {
        messagesList.add(message)
        notifyItemInserted(messagesList.size)
    }
}
