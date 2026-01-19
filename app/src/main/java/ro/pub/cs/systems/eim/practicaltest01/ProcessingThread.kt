package ro.pub.cs.systems.eim.practicaltest01

import android.content.Context
import android.content.Intent
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

class ProcessingThread(
    private val context: Context,
    private val query: String
) : Thread() {

    override fun run() {
        val client = OkHttpClient()

        // Construim URL-ul complet
        val url = "${General.GOOGLE_AUTOCOMPLETE_URL}$query"
        Log.d(General.TAG, "Requesting URL: $url")

        val request = Request.Builder()
            .url(url)
            .build()

        try {
            // 3.a) Executare Request
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()

            if (responseBody != null) {
                // Task 3a: Afișare răspuns complet în Logcat
                Log.d(General.TAG, "Full Response: $responseBody")

                // 3.b) Parsing (Task 3b)
                // Structura Google API: ["query", ["sugestie1", "sugestie2", "sugestie3", ...], ...]
                val mainArray = JSONArray(responseBody)

                // Array-ul cu sugestii este la indexul 1
                val suggestionsArray = mainArray.getJSONArray(1)

                // Cerința: "Afișare intrarea a 3-a LogCat" (adică index 2)
                if (suggestionsArray.length() > 2) {
                    val thirdSuggestion = suggestionsArray.getString(2)
                    Log.d(General.TAG, "Intrarea a 3-a: $thirdSuggestion")

                    // 3 c). Trimite Broadcast (Task 3c)
                    val intent = Intent(General.BROADCAST_ACTION)
                    intent.putExtra(General.BROADCAST_EXTRA_RESULT, thirdSuggestion)
                    context.sendBroadcast(intent)
                } else {
                    Log.d(General.TAG, "Nu exista suficiente sugestii (min 3 necesare).")
                }
            }
        } catch (e: Exception) {
            Log.e(General.TAG, "Error: ${e.message}")
            e.printStackTrace()
        }
    }
}