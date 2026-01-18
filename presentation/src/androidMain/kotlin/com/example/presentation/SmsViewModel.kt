import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.SettingsRepository
import com.example.presentation.AuthState
import com.example.presentation.AutonomousEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SmsViewModel(
    private val autonomousEntry: AutonomousEntry,
    private val authState: AuthState,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    fun observeAndImport() {
        viewModelScope.launch {

            combine(
                authState.user,
                settingsRepository.observeSettings()
            ) { user, settings ->
                user to settings
            }.collectLatest { (user, settings) ->

                // 1️⃣ User must be logged in
                if (user == null) return@collectLatest

                // 2️⃣ Feature must be supported & enabled
                if (settings.autoEntryEnabled != true) return@collectLatest

                // 3️⃣ Budget limit available
                val budgetLimit = settings.budgetLimit

                // 4️⃣ Run import on IO thread
                launch(Dispatchers.IO) {
                    autonomousEntry.importCurrentMonthBankSms(
                        userId = user.userId,
                        budgetLimit = budgetLimit
                    )
                }
            }
        }
    }
}
