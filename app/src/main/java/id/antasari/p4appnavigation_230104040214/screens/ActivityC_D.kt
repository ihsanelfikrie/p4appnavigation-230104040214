package id.antasari.p4appnavigation_230104040214.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import id.antasari.p4appnavigation_230104040214.R
import id.antasari.p4appnavigation_230104040214.nav.Route
import id.antasari.p4appnavigation_230104040214.viewmodel.FormViewModel
import kotlinx.coroutines.launch

@Composable
fun ActivityCScreen(navController: NavController) {
    val context = LocalContext.current
    val formViewModel: FormViewModel = viewModel()

    // Use rememberSaveable for configuration changes (rotation)
    var name by rememberSaveable { mutableStateOf("") }
    var nim by rememberSaveable { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Load saved data from DataStore on first composition
    LaunchedEffect(Unit) {
        formViewModel.name.collect { savedName ->
            if (name.isEmpty() && savedName.isNotEmpty()) {
                name = savedName
            }
        }
    }

    LaunchedEffect(Unit) {
        formViewModel.nim.collect { savedNim ->
            if (nim.isEmpty() && savedNim.isNotEmpty()) {
                nim = savedNim
            }
        }
    }

    // Auto-save when leaving the screen
    DisposableEffect(Unit) {
        onDispose {
            if (name.isNotEmpty() || nim.isNotEmpty()) {
                scope.launch {
                    formViewModel.saveFormData(name, nim)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Title
            Text(
                text = stringResource(R.string.activity_c_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle
            Text(
                text = stringResource(R.string.activity_c_desc),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Form Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    // Name TextField
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(stringResource(R.string.label_name)) },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // NIM TextField
                    OutlinedTextField(
                        value = nim,
                        onValueChange = { nim = it },
                        label = { Text(stringResource(R.string.label_student_id)) },
                        leadingIcon = {
                            Icon(Icons.Default.School, contentDescription = null)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Send Button
            Button(
                onClick = {
                    when {
                        name.isBlank() || nim.isBlank() -> {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Please fill in all fields",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                        nim.length != 12 -> {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Student ID must be 12 digits",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                        else -> {
                            // Save to DataStore before navigating
                            scope.launch {
                                formViewModel.saveFormData(name, nim)
                                navController.navigate(Route.ActivityD.of(name, nim))
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.send_to_detail),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun ActivityDScreen(
    navController: NavController,
    name: String?,
    nim: String?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Success Icon
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Success Title
        Text(
            text = stringResource(R.string.activity_d_success),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Activity Title
        Text(
            text = stringResource(R.string.activity_d_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Data Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Received Data:",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Spacer(modifier = Modifier.height(16.dp))

                DataRow(
                    icon = Icons.Default.Person,
                    label = stringResource(R.string.label_name),
                    value = name ?: "-"
                )

                Spacer(modifier = Modifier.height(12.dp))

                DataRow(
                    icon = Icons.Default.School,
                    label = stringResource(R.string.label_student_id),
                    value = nim ?: "-"
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Back Button
        OutlinedButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = stringResource(R.string.resend_edit),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun DataRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}