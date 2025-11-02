# ChatBox

Aplikasi ChatBox adalah aplikasi obrolan sederhana yang dibangun menggunakan Android dan Firebase.

## Teknologi yang Digunakan

*   **Kotlin**: Bahasa pemrograman utama untuk pengembangan Android.
*   **Jetpack Compose**: Toolkit UI modern untuk membangun antarmuka pengguna Android secara deklaratif.
*   **Firebase Authentication**: Untuk manajemen pengguna (daftar, masuk, dll.).
*   **Firebase Realtime Database**: Untuk menyimpan dan menyinkronkan data obrolan secara real-time.
*   **Gradle Kotlin DSL**: Untuk konfigurasi build yang lebih aman dan ekspresif.
*   **Firebase Bill of Materials (BoM)**: Untuk mengelola versi dependensi Firebase secara konsisten.
*   **Firebase SDKs (Non-KTX)**: Menggunakan SDK Firebase standar tanpa ekstensi KTX, karena fungsionalitas Kotlin telah diintegrasikan langsung ke dalam SDK utama.

## Fitur Aplikasi

*   **Autentikasi Pengguna**: Pengguna dapat mendaftar dan masuk menggunakan email dan kata sandi.
*   **Obrolan Real-time**: Mengirim dan menerima pesan secara instan.
*   **Daftar Pengguna**: Melihat daftar pengguna yang terdaftar.

## Struktur Proyek

Proyek ini terdiri dari tiga file aktivitas utama:

*   **`AuthActivity.kt`**: Menangani proses autentikasi pengguna (login dan registrasi). File ini juga mendeklarasikan `data class User` yang digunakan di seluruh aplikasi untuk merepresentasikan data pengguna.
*   **`MainActivity.kt`**: Menampilkan daftar pengguna yang terdaftar dan memungkinkan pengguna untuk memulai obrolan dengan mereka.
*   **`ChatActivity.kt`**: Menyediakan antarmuka obrolan real-time antara dua pengguna.

## Persyaratan

*   Android Studio
*   Firebase Project
*   JDK (Java Development Kit)

## Langkah-langkah Setup Proyek

Ikuti langkah-langkah di bawah ini untuk menyiapkan dan menjalankan proyek ChatBox di lingkungan pengembangan lokal Anda.

### 1. Clone Repositori

Pertama, clone repositori ini ke mesin lokal Anda menggunakan Git:

```bash
git clone git@github.com:syahrulhuda/ChatBox.git
cd ChatBox
```

### 2. Dapatkan `google-services.json`

Aplikasi ini menggunakan Firebase untuk otentikasi dan database. Anda perlu mengunduh file konfigurasi `google-services.json` dari proyek Firebase Anda dan menempatkannya di lokasi yang benar.

**Instruksi untuk mendapatkan `google-services.json`:**

1.  **Minta file dari grup WA OTHING PROJECTS.** File ini berisi kredensial yang diperlukan aplikasi untuk berkomunikasi dengan Firebase.
2.  Setelah Anda mendapatkan file `google-services.json`, **salin file tersebut ke direktori `app/`** di dalam proyek Anda.

    Contoh: `D:\AndroidStudioProjects\ChatBox\app\google-services.json`

    Pastikan nama file adalah `google-services.json` (huruf kecil semua).

### 3. Buka Proyek di Android Studio

1.  Buka Android Studio.
2.  Pilih `File > Open` dan navigasikan ke direktori `ChatBox` yang baru saja Anda clone.
3.  Pilih file `build.gradle.kts` di root proyek dan klik `Open`.

Android Studio akan mengimpor proyek dan menyinkronkan dependensi Gradle. Ini mungkin memerlukan waktu beberapa menit.

### 4. Sinkronkan Proyek dengan File Gradle

Setelah proyek terbuka, pastikan untuk menyinkronkan proyek dengan file Gradle. Anda mungkin melihat pesan "Sync Project with Gradle Files" di bilah notifikasi Android Studio, atau Anda bisa melakukannya secara manual dengan mengklik ikon gajah di toolbar.

### 5. Jalankan Aplikasi

Setelah sinkronisasi Gradle selesai dan tidak ada kesalahan, Anda dapat menjalankan aplikasi:

1.  Hubungkan perangkat Android fisik Anda atau mulai emulator Android.
2.  Klik tombol `Run` (ikon panah hijau) di toolbar Android Studio.
3.  Pilih perangkat atau emulator target Anda.

Aplikasi akan dibangun dan diinstal pada perangkat atau emulator yang dipilih.

---

## Pemecahan Masalah Umum

### Error: `Redeclaration: data class User`

Error ini terjadi jika `data class User` dideklarasikan lebih dari satu kali di proyek. Untuk menjaga kesederhanaan dan menghindari deklarasi ganda, `data class User` hanya dideklarasikan di `AuthActivity.kt`. Pastikan tidak ada deklarasi `User` di file lain seperti `MainActivity.kt` atau `ChatActivity.kt`, dan tidak ada file `User.kt` terpisah.

### Error: `Could not find lifecycleViewmodelCompose`

Jika Anda mengalami error seperti `Could not find lifecycleViewmodelCompose` saat membangun proyek, ini berarti versi untuk `lifecycle-viewmodel-compose` belum dideklarasikan di file `gradle/libs.versions.toml`.

**Solusi:**

Tambahkan baris berikut di bagian `[versions]` pada file `gradle/libs.versions.toml`:

```toml
lifecycleViewmodelCompose = "2.9.4"
```

Pastikan versi ini konsisten dengan `lifecycleRuntimeKtx` yang sudah ada. Setelah menambahkan baris ini, lakukan "Sync Project with Gradle Files" di Android Studio.

---

### Error: `Unresolved reference 'Firebase'` atau `firebase-auth-ktx` tidak ditemukan

Jika Anda mengalami error `Unresolved reference 'Firebase'` di kode Kotlin Anda, atau error build yang menunjukkan `firebase-auth-ktx` tidak ditemukan, ini kemungkinan karena ekstensi KTX untuk Firebase Authentication tidak lagi diperlukan dan telah dihapus. Fungsionalitas Kotlin sekarang terintegrasi langsung ke dalam SDK Firebase utama.

**Solusi:**

1.  **Perbarui `gradle/libs.versions.toml`:**
    Pastikan Anda memiliki entri `firebase-auth` di bagian `[libraries]` tanpa referensi versi, karena versi akan dikelola oleh Firebase BoM:

    ```toml
    firebase-auth = { group = "com.google.firebase", name = "firebase-auth" }
    ```

2.  **Perbarui `app/build.gradle.kts`:**
    Ganti dependensi `firebase-auth-ktx` yang lama dengan `firebase-auth` yang baru:

    ```kotlin
    // Hapus baris ini:
    // implementation("com.google.firebase:firebase-auth-ktx")
    // Ganti dengan:
    implementation(libs.firebase.auth)
    ```

3.  **Perbarui Kode Kotlin Anda (misalnya, `MainActivity.kt` atau `AuthActivity.kt`):**
    Hapus impor KTX yang tidak lagi diperlukan dan inisialisasi `FirebaseAuth` menggunakan `getInstance()`:

    ```kotlin
    // Hapus impor ini jika ada:
    // import com.google.firebase.auth.ktx.auth
    // import com.google.firebase.ktx.Firebase

    // Ganti inisialisasi jika menggunakan Firebase.auth:
    // auth = Firebase.auth
    // Dengan:
    auth = FirebaseAuth.getInstance()
    ```

Setelah melakukan perubahan ini, lakukan "Sync Project with Gradle Files" di Android Studio.

---

**Catatan Penting:**

*   Jangan pernah membagikan file `google-services.json` Anda secara publik atau mengunggahnya ke repositori Git publik. File ini berisi informasi sensitif tentang proyek Firebase Anda.
*   Jika Anda membuat proyek Firebase baru, pastikan untuk menambahkan aplikasi Android Anda ke dalamnya dan ikuti petunjuk untuk mengunduh `google-services.json` yang sesuai.