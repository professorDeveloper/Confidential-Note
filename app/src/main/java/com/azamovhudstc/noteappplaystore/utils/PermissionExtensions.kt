import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.PointF
import android.net.Uri
import android.provider.Settings
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions.check
import java.security.Permissions
import kotlin.math.sqrt

fun Activity.checkPermissions(permission: Array<String>, granted: () -> Unit) {
    com.nabinbhandari.android.permissions.Permissions.check(
        this,
        permission,
        null, null,
        permissionHandler(granted, { goToSettings() }, { goToSettings() }, { goToSettings() })
    )
}

const val REQUEST_APP_SETTINGS = 11001
fun Activity.goToSettings() {
    val intent =
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"))
    intent.addCategory(Intent.CATEGORY_DEFAULT)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivityForResult(intent, REQUEST_APP_SETTINGS)
}

private fun permissionHandler(
    granted: () -> Unit,
    denied: () -> Unit,
    justBlocked: () -> Unit,
    blocked: () -> Unit
) = object : PermissionHandler() {
    override fun onGranted() {
        granted()
    }

    override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
        super.onDenied(context, deniedPermissions)
        denied()
    }

    override fun onJustBlocked(
        context: Context?,
        justBlockedList: ArrayList<String>?,
        deniedPermissions: ArrayList<String>?
    ) {
        super.onJustBlocked(context, justBlockedList, deniedPermissions)
        justBlocked()
    }

    override fun onBlocked(context: Context?, blockedList: ArrayList<String>?): Boolean {
        blocked()
        return super.onBlocked(context, blockedList)
    }
}

private fun sqr(a: Float) = a * a

fun PointF.distance(other: PointF): Float = sqrt(sqr(x - other.x) + sqr(y - other.y))
