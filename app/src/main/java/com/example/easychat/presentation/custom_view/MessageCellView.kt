package com.example.easychat.presentation.custom_view

import android.content.Context
import android.text.format.DateUtils
import android.text.format.DateUtils.FORMAT_SHOW_TIME
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.easychat.GlideApp
import com.example.easychat.R
import com.example.easychat.domain.model.Message
import kotlinx.android.synthetic.main.item_message_others.view.*

class MessageCellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    fun build(message: Message) {
        userName.text = message.name
        othersMessage.text = message.message
        if (message.photoURL.isNotBlank()) {
            GlideApp.with(this)
                .load(message.photoURL)
                .placeholder(R.drawable.profile_placeholder)
                .error(R.drawable.profile_placeholder)
                .into(iconImage)
        }else{
            iconImage.setImageResource(R.drawable.profile_placeholder)
        }
        timeTextView.text =
            DateUtils.formatDateTime(context, message.timestamp.time, FORMAT_SHOW_TIME)
    }
}