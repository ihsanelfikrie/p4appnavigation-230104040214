package id.antasari.p4appnavigation_230104040214.nav

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.antasari.p4appnavigation_230104040214.screens.HomeScreen
import id.antasari.p4appnavigation_230104040214.screens.ActivityAScreen
import id.antasari.p4appnavigation_230104040214.screens.ActivityBScreen
import id.antasari.p4appnavigation_230104040214.screens.ActivityCScreen
import id.antasari.p4appnavigation_230104040214.screens.ActivityDScreen
import id.antasari.p4appnavigation_230104040214.screens.StepScreen
import id.antasari.p4appnavigation_230104040214.screens.HubScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Tentukan judul berdasarkan route
    val title = when (currentRoute) {
        Route.Home.path -> "Navio"
        Route.ActivityA.path -> "Activity A"
        Route.ActivityB.path -> "Activity B"
        Route.ActivityC.path -> "Activity C"
        Route.Step1.path -> "Back Stack - Step 1"
        Route.Step2.path -> "Back Stack - Step 2"
        Route.Step3.path -> "Back Stack - Step 3"
        Route.Hub.path, Route.HubDashboard.path,
        Route.HubMessages.path, Route.HubProfile.path -> "Hub"
        Route.HubMessageDetail.path -> "Message Detail"
        else -> {
            when {
                currentRoute?.startsWith("activity_d/") == true -> "Activity D"
                else -> "Navio"
            }
        }
    }

    // Tampilkan back button kecuali di Home
    val showBackButton = currentRoute != Route.Home.path

    Scaffold(
        topBar = {
            TopBar(
                title = title,
                showBackButton = showBackButton,
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.Home.path,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Home
            composable(Route.Home.path) {
                HomeScreen(navController = navController)
            }

            // Activity A/B (Explicit Intent Demo)
            composable(Route.ActivityA.path) {
                ActivityAScreen(navController = navController)
            }
            composable(Route.ActivityB.path) {
                ActivityBScreen(navController = navController)
            }

            // Activity C/D (Send Data Demo)
            composable(Route.ActivityC.path) {
                ActivityCScreen(navController = navController)
            }
            composable(
                route = Route.ActivityD.path,
                arguments = listOf(
                    navArgument("name") { type = NavType.StringType },
                    navArgument("nim") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val name = backStackEntry.arguments?.getString("name")
                val nim = backStackEntry.arguments?.getString("nim")
                ActivityDScreen(
                    navController = navController,
                    name = name,
                    nim = nim
                )
            }

            // Back Stack Demo
            composable(Route.Step1.path) {
                StepScreen(navController = navController, currentStep = 1)
            }
            composable(Route.Step2.path) {
                StepScreen(navController = navController, currentStep = 2)
            }
            composable(Route.Step3.path) {
                StepScreen(navController = navController, currentStep = 3)
            }

            // Hub (Activity + Fragment)
            composable(Route.Hub.path) {
                HubScreen(mainNavController = navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    showBackButton: Boolean,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    painter = painterResource(id = id.antasari.p4appnavigation_230104040214.R.drawable.ic_navio),
                    contentDescription = "Navio Logo",
                    modifier = Modifier.size(28.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(title)
            }
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Composable
fun Placeholder(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}