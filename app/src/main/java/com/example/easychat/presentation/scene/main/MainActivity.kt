package com.example.easychat.presentation.scene.main

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easychat.R
import com.example.easychat.presentation.recycler_view.RecyclerViewAdapter
import com.example.easychat.presentation.recycler_view.listener.EndlessScrollListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityViewModel
    lateinit var inputManager: InputMethodManager
    private var isFirstPage = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        viewModel = run {
            ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        }

        val linearLayoutManager = LinearLayoutManager(this).apply {
            reverseLayout = true
            stackFromEnd = true
        }
        val scrollListener = EndlessScrollListener(linearLayoutManager) {
            viewModel.onScrollOldest()
        }
        val recyclerViewAdapter = RecyclerViewAdapter(this)
        mainRecyclerView.apply {
            layoutManager = linearLayoutManager
            addOnScrollListener(scrollListener)
            adapter = recyclerViewAdapter

        }

        sendButton.setOnClickListener {
            val message = messageEditText.text.toString()
            viewModel.sendMessage(message)
            messageEditText.text = null
            inputManager.hideSoftInputFromWindow(
                textField.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
            messageEditText.clearFocus()
            mainRecyclerView.smoothScrollToPosition(0)
        }

        mainRecyclerView.setOnTouchListener { view, motionEvent ->
            if (mainRecyclerView.hasFocus()) {
                return@setOnTouchListener false
            }
            inputManager.hideSoftInputFromWindow(
                textField.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
            mainRecyclerView.requestFocus()
            return@setOnTouchListener true
        }

        viewModel.messages.observe(this, Observer {
            recyclerViewAdapter.insertNestPage(it)
            if (isFirstPage) {
                mainRecyclerView.scrollToPosition(0)
                isFirstPage = false
            }
        })

        viewModel.newMessage.observe(this, Observer {
            recyclerViewAdapter.insertItem(it)
            mainRecyclerView.scrollToPosition(0)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.reload()
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (event?.keyCode == KeyEvent.KEYCODE_BACK) {
            if (event.action == KeyEvent.ACTION_DOWN) {
                currentFocus.clearFocus()
                return false
            } else {
                AlertDialog.Builder(this)
                    .setMessage(resources.getString(R.string.confirmCloseApp))
                    .setPositiveButton("Quit" ){ _, _ -> finish()  }
                    .setNegativeButton("Cancel"){ _, _ -> }
                    .show()
                return false
            }
        }else{
            return super.dispatchKeyEvent(event)
        }
    }

}
