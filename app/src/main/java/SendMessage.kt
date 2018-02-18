import android.app.Activity
import android.os.Bundle

/**
 * Created by Дом on 18.02.2018.
 */
class SendMessage: Activity() {
    var inList = ArrayList<String>()
    var outList = ArrayList<String>()
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}