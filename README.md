# Tutorial 1 - Coding Standards

**Nama:** Qonita Adestyanti
**NPM:** 2106750925  
**Kelas:** Pemrograman Lanjut B

---

## Reflection 1

### Prinsip Clean Code yang Diterapkan
Dalam mengerjakan fitur List, Create, Edit, dan Delete Product, saya telah menerapkan beberapa prinsip Clean Code sesuai modul:
* **Meaningful Names**: Menggunakan nama variabel dan fungsi yang jelas seperti `productId`, `productQuantity`, dan `findAll()` sehingga maksud kodenya mudah dipahami tanpa banyak komentar.
* **Function**: Setiap fungsi dibuat fokus hanya melakukan satu hal (Do One Thing). Misalnya, fungsi `delete` di Repository hanya bertugas menghapus data.
* **Layout and Formatting**: Menjaga kerapian kode dengan baris kosong di antara fungsi agar logika antar bagian terpisah dengan jelas.

### Praktik Secure Coding yang Diterapkan
* **Penggunaan Metode POST**: Untuk pengiriman data sensitif (tambah dan edit produk), menggunakan `@PostMapping` agar data tidak muncul di URL browser, yang mengurangi risiko kebocoran data.
* **UUID untuk Identitas**: Menggunakan `java.util.UUID` untuk menghasilkan `productId` yang unik dan sulit ditebak, guna mencegah serangan yang memanipulasi urutan ID sederhana.

### Rencana Perbaikan ke Depannya
Jika ditemukan kekurangan, saya berencana untuk:
* Menambahkan validasi input yang lebih ketat agar pengguna tidak bisa memasukkan jumlah produk negatif atau nama kosong.
* Memisahkan logika bisnis yang lebih kompleks ke dalam Service Layer secara lebih mendalam untuk menjaga prinsip *Separation of Concerns*.