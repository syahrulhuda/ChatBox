# ChatBox

Aplikasi ChatBox adalah aplikasi obrolan sederhana yang dibangun menggunakan Android dan Firebase.

## Persyaratan

*   Android Studio
*   Firebase Project
*   JDK (Java Development Kit)

## Langkah-langkah Setup Proyek

Ikuti langkah-langkah di bawah ini untuk menyiapkan dan menjalankan proyek ChatBox di lingkungan pengembangan lokal Anda.

### 1. Clone Repositori

Pertama, clone repositori ini ke mesin lokal Anda menggunakan Git:

```bash
git clone <URL_REPOSITORI_ANDA>
cd ChatBox
```

Ganti `<URL_REPOSITORI_ANDA>` dengan URL repositori Git yang sebenarnya.

### 2. Dapatkan `google-services.json`

Aplikasi ini menggunakan Firebase untuk otentikasi dan database. Anda perlu mengunduh file konfigurasi `google-services.json` dari proyek Firebase Anda dan menempatkannya di lokasi yang benar.

**Instruksi untuk mendapatkan `google-services.json`:**

1.  **Minta file dari grup WA OTHING PROJECTS.** File ini berisi kredensial yang diperlukan aplikasi untuk berkomunikasi dengan Firebase.
2.  Setelah Anda mendapatkan file `google-services.json`, **salin file tersebut ke direktori `app/`** di dalam proyek Anda.

    Contoh: `d:\AndroidStudioProjects\ChatBox\app\google-services.json`

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

**Catatan Penting:**

*   Jangan pernah membagikan file `google-services.json` Anda secara publik atau mengunggahnya ke repositori Git publik. File ini berisi informasi sensitif tentang proyek Firebase Anda.
*   Jika Anda membuat proyek Firebase baru, pastikan untuk menambahkan aplikasi Android Anda ke dalamnya dan ikuti petunjuk untuk mengunduh `google-services.json` yang sesuai.
