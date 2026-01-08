import androidx.navigation3.runtime.NavKey
import com.example.presentation.ui_model.UserUIModel
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object Splash : Route

    @Serializable
    data object Login : Route

    @Serializable
    data class Home(val user: UserUIModel) : Route
}
