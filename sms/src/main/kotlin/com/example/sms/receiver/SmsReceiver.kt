package com.example.sms.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import com.example.sms.importer.SmsExpenseImporter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.get

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) return

        //  Tell Android we will finish asynchronously
        val pendingResult = goAsync()

        //  Resolve dependency lazily (SAFE for receivers)
        val importer: SmsExpenseImporter =
            get(SmsExpenseImporter::class.java)

        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)

        //  Offload work to background (IO-bound)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                for (sms in messages) {
                    val sender = sms.displayOriginatingAddress
                    val body = sms.messageBody

                    Log.d("SmsReceiver", "SMS from $sender: $body")

                    importer.import(
                        body = body,
                        timestamp = sms.timestampMillis
                    )
                }
            } catch (e: Exception) {
                Log.e("SmsReceiver", "Error processing SMS", e)
            } finally {
                //  MUST be called or Android will ANR
                pendingResult.finish()
            }
        }
    }
}
