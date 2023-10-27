package io.github.the_jasoney.trebuchet

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.GridLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.the_jasoney.trebuchet.components.AppComponent
import io.github.the_jasoney.trebuchet.ui.theme.TrebuchetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TrebuchetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App("Android")
                }
            }
        }
    }
}

@SuppressLint("QueryPermissionsNeeded")
@Composable
fun App(name: String, modifier: Modifier = Modifier) {
    val context: Context = LocalContext.current;
    val pm: PackageManager = context.packageManager;

    // Workaround to get only launch-able apps
    val main = Intent(Intent.ACTION_MAIN);
    main.addCategory(Intent.CATEGORY_LAUNCHER);

    val apps = pm.queryIntentActivities(main, 0);
    val appNames = mutableListOf<String>()
    val appIcons = mutableListOf<Drawable>()
    val appZip = mutableListOf<Pair<String, Drawable>>()
    apps.forEach {
        val info = pm.getApplicationInfo(it.activityInfo.packageName, 0)
        val label = pm.getApplicationLabel(info).toString()
        val icon = pm.getApplicationIcon(info)
        println(info)

        if (!info.packageName.equals(context.packageName)) {
            appNames.add(label)
            appIcons.add(icon)
            appZip.add(Pair(label, icon))
        }

    }

    LazyVerticalGrid(columns = GridCells.Fixed(4)) {
        items(appZip.count()) {
            AppComponent(appZip[it].first, appZip[it].second)
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppPreview() {
    TrebuchetTheme {
        App("Android")
    }
}