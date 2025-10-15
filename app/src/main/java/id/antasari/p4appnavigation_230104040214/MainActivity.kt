package id.antasari.p4appnavigation_230104040214

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import id.antasari.p4appnavigation_230104040214.nav.NavGraph
import id.antasari.p4appnavigation_230104040214.ui.theme.P4appnavigation_230104040214Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            P4appnavigation_230104040214Theme {
                NavGraph()
            }
        }
    }
}