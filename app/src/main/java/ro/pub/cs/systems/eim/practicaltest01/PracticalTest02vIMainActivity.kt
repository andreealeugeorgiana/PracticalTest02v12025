package ro.pub.cs.systems.eim.practicaltest01

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ro.pub.cs.systems.eim.practicaltest01.R

class PracticalTest02vIMainActivity : AppCompatActivity() {

    private lateinit var queryEditText: EditText
    private lateinit var resultTextView: TextView
    private lateinit var searchButton: Button
    private lateinit var navigateMapsButton: Button

    // Receiver-ul pentru a prinde rezultatul de la thread
    private val autocompleteReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == General.BROADCAST_ACTION) {
                val result = intent.getStringExtra(General.BROADCAST_EXTRA_RESULT)
                // Afișare în UI (Task 3c)
                resultTextView.text = "Sugestia 3: $result"
                Toast.makeText(context, "Primit: $result", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical_test02v1_main)

        queryEditText = findViewById(R.id.query_edit_text)
        resultTextView = findViewById(R.id.result_text_view)
        searchButton = findViewById(R.id.search_button)
        navigateMapsButton = findViewById(R.id.navigate_maps_button)

        searchButton.setOnClickListener {
            val query = queryEditText.text.toString()
            if (query.isNotEmpty()) {
                // Pornim thread-ul de procesare
                val thread = ProcessingThread(this, query)
                thread.start()
            } else {
                Toast.makeText(this, "Completează câmpul!", Toast.LENGTH_SHORT).show()
            }
        }

        // ex 4.

        navigateMapsButton.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Înregistrare Receiver
        val intentFilter = IntentFilter(General.BROADCAST_ACTION)
        // Pentru Android 13+ (Tiramisu), specificăm exportarea receiver-ului
        registerReceiver(autocompleteReceiver, intentFilter, Context.RECEIVER_EXPORTED)
    }

    override fun onPause() {
        unregisterReceiver(autocompleteReceiver)
        super.onPause()
    }
}