package id.antasari.p4appnavigation_230104040214.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.antasari.p4appnavigation_230104040214.R
import id.antasari.p4appnavigation_230104040214.nav.Route

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Header
        Text(
            text = stringResource(R.string.home_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.home_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Menu Cards
        MenuCard(
            icon = Icons.Default.OpenInNew,
            title = stringResource(R.string.menu_start_activity),
            description = stringResource(R.string.menu_start_activity_desc),
            onClick = { navController.navigate(Route.ActivityA.path) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        MenuCard(
            icon = Icons.Default.Send,
            title = stringResource(R.string.menu_send_data),
            description = stringResource(R.string.menu_send_data_desc),
            onClick = { navController.navigate(Route.ActivityC.path) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        MenuCard(
            icon = Icons.Default.Layers,
            title = stringResource(R.string.menu_back_stack),
            description = stringResource(R.string.menu_back_stack_desc),
            onClick = { navController.navigate(Route.Step1.path) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        MenuCard(
            icon = Icons.Default.Dashboard,
            title = stringResource(R.string.menu_hub),
            description = stringResource(R.string.menu_hub_desc),
            onClick = { navController.navigate(Route.Hub.path) }
        )
    }
}

@Composable
fun MenuCard(
    icon: ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = onClick,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.try_demo),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}