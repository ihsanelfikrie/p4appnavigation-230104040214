# Navio - Praktikum Mobile Programming #4

**App Navigation dengan Jetpack Compose**

---

## 📱 Tentang Aplikasi

Navio adalah aplikasi Android demo untuk mempelajari konsep navigasi dalam pengembangan aplikasi mobile menggunakan Jetpack Compose. Aplikasi ini dibuat sebagai bagian dari Praktikum Mobile Programming #4.

---

## ✨ Fitur Utama

### 1. **Start Activity (Explicit Intent Demo)**
- Navigasi dari Activity A ke Activity B
- Demonstrasi konsep Intent eksplisit

### 2. **Send Data**
- Form input Nama dan NIM di Activity C
- Validasi input (field tidak boleh kosong, NIM harus 12 digit angka)
- Kirim data ke Activity D untuk ditampilkan
- **Form Persistent**: Data tidak hilang saat rotasi atau "Don't keep activities" aktif

### 3. **Back Stack Demo**
- Visualisasi cara kerja Back Stack
- 3 step navigasi dengan progress bar
- Tombol "Clear to Home" yang membersihkan seluruh stack

### 4. **Activity + Fragment Style (Hub)**
- Bottom Navigation dengan 3 tab: Dashboard, Messages, Profile
- **Dashboard**: Statistik jumlah messages dan notifications
- **Messages**: List 5 pesan dengan detail dinamis per ID
- **Profile**: Informasi user dengan bio dan skill chips
- Bottom Navigation disembunyikan saat di Message Detail

---

## 🛠️ Teknologi yang Digunakan

- **Kotlin** - Bahasa pemrograman
- **Jetpack Compose** - UI Framework modern
- **Material 3** - Design system
- **Navigation Compose** - Navigasi antar screen
- **DataStore** - Penyimpanan data persistent
- **ViewModel** - State management
- **Lifecycle** - Lifecycle-aware components

---

## 📦 Struktur Project

```
app/src/main/java/id/antasari/p4appnavigation_230104040214/
├── data/
│   └── FormPreferences.kt          # DataStore untuk form
├── nav/
│   ├── Routes.kt                   # Definisi semua route
│   └── NavGraph.kt                 # Konfigurasi navigasi
├── screens/
│   ├── HomeScreen.kt               # Home dengan 4 menu
│   ├── ActivityA_B.kt              # Activity A & B
│   ├── ActivityC_D.kt              # Activity C & D (Form + Data)
│   ├── BackStackScreens.kt         # Back Stack Step 1-3
│   └── HubScreens.kt               # Hub (Dashboard, Messages, Profile)
├── viewmodel/
│   └── FormViewModel.kt            # ViewModel untuk form
└── MainActivity.kt                 # Entry point
```

---

## 🚀 Cara Menjalankan

1. **Clone repository**:
   ```bash
   git clone https://github.com/username-anda/p4appnavigation-230104040214.git
   ```

2. **Buka project** di Android Studio Narwhal (2025.1.1) atau lebih baru

3. **Sync Gradle**:
    - Klik "Sync Now" saat muncul banner
    - Pastikan JDK 17 aktif

4. **Run aplikasi**:
    - Pilih emulator atau device fisik
    - Klik tombol Run (▶️)

---

## 📋 Requirements

- **Android Studio**: Narwhal 2025.1.1 atau lebih baru
- **JDK**: 17
- **Minimum SDK**: API 24 (Android 7.0)
- **Target SDK**: API 35
- **Compile SDK**: API 35

---

## 📸 Screenshots

*(Tambahkan screenshot aplikasi di sini)*

1. Home Screen
2. Activity A
3. Activity B
4. Activity C (Form)
5. Activity D (Data Display)
6. Back Stack Step 1-3
7. Dashboard Fragment
8. Messages Fragment
9. Message Detail
10. Profile Fragment

---

## 👨‍💻 Pengembang

**Nama**: [Nama Anda]  
**NIM**: 230104040214  
**Mata Kuliah**: Mobile Programming  
**Praktikum**: #4 - App Navigation  
**Dosen**: Muhayat, M.IT

---

## 📝 Lisensi

Project ini dibuat untuk keperluan edukasi dalam rangka praktikum Mobile Programming.

---

## 🙏 Acknowledgments

- Modul Praktikum Mobile Programming #4
- Dokumentasi resmi Android Developers
- Jetpack Compose Documentation
- Material Design 3 Guidelines

---

**⭐ Jika project ini membantu, jangan lupa kasih star!**