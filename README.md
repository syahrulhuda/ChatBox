# ChatBox

Aplikasi obrolan sederhana untuk Android, dibangun dengan Jetpack Compose dan Firebase.

## Fitur

*   Registrasi dan login pengguna dengan Firebase Authentication.
*   Obrolan satu-ke-satu dengan pengguna lain.
*   Pesan real-time dengan Firebase Realtime Database.

## Petunjuk Penyiapan

### 1. Kloning Repositori

Pertama, kloning repositori ini ke komputer lokal Anda menggunakan perintah berikut di terminal atau Git Bash:

```bash
git clone https://github.com/syahrulhuda/ChatBox.git
```

Setelah kloning selesai, masuk ke direktori proyek:

```bash
cd ChatBox
```

### 2. Penyiapan Firebase

Proyek ini menggunakan Firebase untuk layanan autentikasi dan basis data. Anda perlu menyiapkan proyek Firebase agar aplikasi dapat berjalan.

#### a. Dapatkan `google-services.json`

Berkas `google-services.json` berisi konfigurasi untuk proyek Firebase Anda. Berkas ini sangat penting untuk menghubungkan aplikasi Android Anda dengan proyek Firebase.

**Silakan unduh berkas `google-services.json` dari grup WhatsApp "OTHING PROJECTS".** Pastikan Anda mendapatkan berkas yang paling baru dan benar.

#### b. Tempatkan `google-services.json`

Setelah mengunduh berkas `google-services.json`, Anda perlu menempatkannya di direktori `app` dari proyek Anda.

*   Buka folder proyek `ChatBox` yang baru saja Anda kloning.
*   Masuk ke dalam folder `app`.
*   Salin (copy) berkas `google-services.json` yang telah Anda unduh ke dalam folder `app` ini.

Jalur lengkap dari root proyek adalah: `ChatBox/app/google-services.json`

**Penting:** Pastikan nama berkasnya tetap `google-services.json` dan tidak ada angka tambahan (misalnya `google-services(1).json`). Jika ada, ganti namanya menjadi `google-services.json`.

### 3. Bangun dan Jalankan Proyek

Setelah Anda menempatkan berkas `google-services.json` di direktori yang benar, Anda dapat membuka proyek di Android Studio dan menjalankannya di emulator atau perangkat fisik.

1.  **Buka Android Studio:** Luncurkan Android Studio.
2.  **Buka Proyek:**
    *   Jika Anda melihat layar selamat datang, pilih **"Open"** atau **"Open an existing Android Studio project"**.
    *   Navigasikan ke direktori `ChatBox` yang telah Anda kloning dan pilih folder tersebut.
3.  **Sinkronisasi Gradle:** Android Studio akan secara otomatis mulai menyinkronkan proyek dengan Gradle. Tunggu hingga proses ini selesai. Ini mungkin memakan waktu beberapa menit tergantung pada koneksi internet Anda dan spesifikasi komputer.
4.  **Jalankan Aplikasi:** Setelah sinkronisasi selesai dan tidak ada kesalahan yang terlihat, klik tombol **"Run 'app'"** (ikon panah hijau di toolbar atas) untuk membangun dan menjalankan aplikasi di emulator atau perangkat Android yang terhubung.

Jika Anda mengalami masalah selama proses penyiapan, pastikan Anda telah mengikuti semua langkah dengan cermat dan berkas `google-services.json` berada di lokasi yang benar.