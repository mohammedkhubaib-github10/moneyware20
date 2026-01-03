import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object Splash : Route

    @Serializable
    data object Login : Route

    @Serializable
    data class Home(val user: String) : Route
}
