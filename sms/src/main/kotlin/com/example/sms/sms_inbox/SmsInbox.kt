import android.content.Context
import androidx.core.net.toUri
import com.example.sms.parser.BankAddressFilter
import com.example.sms.parser.DebitFilter
import com.example.sms.sms_inbox.SmsMessage
import com.example.sms.sms_inbox.getStartOfCurrentMonthMillis

class SmsInbox(private val context: Context) {

    fun getCurrentMonthBankDebitSms(): List<SmsMessage> {

        val smsList = mutableListOf<SmsMessage>()

        val uri = "content://sms".toUri()

        val projection = arrayOf(
            "_id",
            "address",
            "body",
            "date",
            "type"
        )

        val selection = "date >= ? AND ${BankAddressFilter.buildSelection()}"

        val selectionArgs = arrayOf(
            getStartOfCurrentMonthMillis().toString(),
            *BankAddressFilter.buildArgs()
        )

        context.contentResolver.query(
            uri,
            projection,
            selection,
            selectionArgs,
            "date DESC"
        )?.use { c ->

            val idIndex = c.getColumnIndex("_id")
            val addressIndex = c.getColumnIndex("address")
            val bodyIndex = c.getColumnIndex("body")
            val dateIndex = c.getColumnIndex("date")
            val typeIndex = c.getColumnIndex("type")

            while (c.moveToNext()) {

                val body = c.getString(bodyIndex)

                if (!DebitFilter.isDebit(body)) continue

                smsList.add(
                    SmsMessage(
                        id = c.getLong(idIndex),
                        address = c.getString(addressIndex),
                        body = body,
                        timestamp = c.getLong(dateIndex),
                        type = c.getInt(typeIndex)
                    )
                )
            }
        }

        return smsList
    }
}
