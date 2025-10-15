package id.antasari.p4appnavigation_230104040214.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import id.antasari.p4appnavigation_230104040214.R
import id.antasari.p4appnavigation_230104040214.nav.Route

// ============================================================================
// HUB MAIN SCREEN (Container dengan Bottom Navigation)
// ============================================================================

@Composable
fun HubScreen(mainNavController: NavController) {
    val hubNavController = rememberNavController()
    val currentBackStackEntry by hubNavController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Hide bottom nav on detail screen
    val showBottomBar = currentRoute?.startsWith("hub/messages/detail") != true

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == Route.HubDashboard.path,
                        onClick = {
                            hubNavController.navigate(Route.HubDashboard.path) {
                                popUpTo(Route.HubDashboard.path) { inclusive = true }
                            }
                        },
                        icon = { Icon(Icons.Default.Dashboard, contentDescription = null) },
                        label = { Text(stringResource(R.string.tab_dashboard)) }
                    )

                    NavigationBarItem(
                        selected = currentRoute == Route.HubMessages.path,
                        onClick = {
                            hubNavController.navigate(Route.HubMessages.path) {
                                popUpTo(Route.HubDashboard.path)
                            }
                        },
                        icon = { Icon(Icons.Default.Message, contentDescription = null) },
                        label = { Text(stringResource(R.string.tab_messages)) }
                    )

                    NavigationBarItem(
                        selected = currentRoute == Route.HubProfile.path,
                        onClick = {
                            hubNavController.navigate(Route.HubProfile.path) {
                                popUpTo(Route.HubDashboard.path)
                            }
                        },
                        icon = { Icon(Icons.Default.Person, contentDescription = null) },
                        label = { Text(stringResource(R.string.tab_profile)) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = hubNavController,
            startDestination = Route.HubDashboard.path,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Route.HubDashboard.path) {
                DashboardTab()
            }

            composable(Route.HubMessages.path) {
                MessagesTab(onOpenDetail = { id ->
                    hubNavController.navigate(Route.HubMessageDetail.of(id))
                })
            }

            composable(
                route = Route.HubMessageDetail.path,
                arguments = listOf(
                    androidx.navigation.navArgument("id") {
                        type = androidx.navigation.NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val messageId = backStackEntry.arguments?.getString("id") ?: "1"
                MessageDetailScreen(
                    messageId = messageId,
                    onBack = { hubNavController.navigateUp() }
                )
            }

            composable(Route.HubProfile.path) {
                ProfileTab()
            }
        }
    }
}

// ============================================================================
// DASHBOARD TAB
// ============================================================================

@Composable
fun DashboardTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Dashboard,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.dashboard_welcome),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Text(
                text = stringResource(R.string.dashboard_desc),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Stats Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Message,
                label = "Messages",
                value = "12"
            )

            StatCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Notifications,
                label = "Notifications",
                value = "5"
            )
        }
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ============================================================================
// MESSAGES TAB
// ============================================================================

@Composable
fun MessagesTab(onOpenDetail: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.messages_title),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Message List - Custom Messages
        val messages = listOf(
            "Dosen Praktikum" to "Jangan lupa submit tugas praktikum ya!",
            "Teman Sekelas" to "Mau diskusi tugas Mobile Programming?",
            "Admin Kampus" to "Pengumuman: Kuliah minggu depan online",
            "Kakak Tingkat" to "Ada yang mau ditanyakan tentang Android?",
            "Koordinator Prodi" to "Mohon konfirmasi kehadiran seminar"
        )

        messages.forEachIndexed { index, (sender, preview) ->
            MessageItem(
                sender = sender,
                preview = preview,
                onClick = { onOpenDetail((index + 1).toString()) }
            )
            if (index < messages.size - 1) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}

@Composable
fun MessageItem(
    sender: String,
    preview: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = sender,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = preview,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}

// ============================================================================
// MESSAGE DETAIL SCREEN
// ============================================================================

@Composable
fun MessageDetailScreen(
    messageId: String,
    onBack: () -> Unit
) {
    // Data pesan berdasarkan ID
    val messageData = when (messageId) {
        "1" -> Triple(
            "Dosen Praktikum",
            "Reminder Tugas Praktikum",
            "Halo mahasiswa, ini adalah reminder untuk tugas praktikum Mobile Programming minggu ini. Jangan lupa submit tugas navigasi aplikasi sebelum deadline tanggal 20. Gunakan Jetpack Compose dan pastikan semua fitur berfungsi dengan baik. Good luck!"
        )
        "2" -> Triple(
            "Teman Sekelas",
            "Diskusi Tugas MP",
            "Hei, aku udah mulai ngerjain tugas praktikum. Kamu sudah sampai mana? Mau diskusi bareng? Kayaknya bagian navigation agak tricky, terutama yang back stack. Kita bisa meeting online nanti sore kalau kamu ada waktu."
        )
        "3" -> Triple(
            "Admin Kampus",
            "Pengumuman Kuliah Online",
            "Kepada seluruh mahasiswa, diinformasikan bahwa perkuliahan minggu depan akan dilaksanakan secara online melalui platform zoom. Link meeting akan dishare melalui grup kelas masing-masing. Mohon untuk selalu cek email dan grup secara berkala. Terima kasih."
        )
        "4" -> Triple(
            "Kakak Tingkat",
            "Bantuan Android Development",
            "Halo dek, kabar baik? Kalau ada yang mau ditanyakan tentang Android development atau praktikum MP, feel free to ask ya. Aku dulu juga sempat struggle di bagian navigation dan lifecycle. Semangat belajarnya!"
        )
        "5" -> Triple(
            "Koordinator Prodi",
            "Konfirmasi Kehadiran Seminar",
            "Assalamualaikum mahasiswa, akan diadakan seminar nasional tentang Mobile Application Development minggu depan. Bagi yang berminat hadir mohon konfirmasi melalui link yang sudah dishare di grup. Seminar ini sangat bermanfaat untuk menambah wawasan kalian. Terima kasih."
        )
        else -> Triple(
            "Unknown Sender",
            "No Subject",
            "Message content not available."
        )
    }

    val (sender, subject, content) = messageData

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Message,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Message #$messageId",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = subject,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "From: $sender",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    lineHeight = MaterialTheme.typography.bodyLarge.lineHeight.times(1.3f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.back),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

// ============================================================================
// PROFILE TAB
// ============================================================================

@Composable
fun ProfileTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Ganti dengan nama Anda
        Text(
            text = "Muhammad Rizki", // GANTI DENGAN NAMA ANDA
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Ganti dengan email/NIM Anda
        Text(
            text = "230104040214@student.antasari.ac.id", // GANTI DENGAN EMAIL ANDA
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Bio",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Ganti dengan bio Anda
                Text(
                    text = "Mahasiswa Mobile Programming | Belajar Jetpack Compose | NIM: 230104040214",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Interest Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AssistChip(
                onClick = { },
                label = { Text("Compose") },
                leadingIcon = {
                    Icon(Icons.Default.Code, contentDescription = null, modifier = Modifier.size(16.dp))
                }
            )
            AssistChip(
                onClick = { },
                label = { Text("Android") },
                leadingIcon = {
                    Icon(Icons.Default.Android, contentDescription = null, modifier = Modifier.size(16.dp))
                }
            )
            AssistChip(
                onClick = { },
                label = { Text("Kotlin") },
                leadingIcon = {
                    Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(16.dp))
                }
            )
        }
    }
}