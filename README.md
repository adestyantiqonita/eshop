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

---

## Reflection 2

### 1. Unit Testing & Code Coverage
* **Feelings after writing unit tests:** Menulis unit test memberikan rasa aman dan kepercayaan diri tinggi terhadap fungsionalitas kode. Dengan adanya test, kita tidak perlu melakukan pengecekan manual yang melelahkan setiap kali ada perubahan kode.
* **Number of unit tests in a class:** Jumlah unit test harus cukup untuk memverifikasi setiap jalur logika (logic path), termasuk skenario positif (data valid) dan skenario negatif (data tidak ada atau error).
* **100% Code Coverage vs Bugs:** Memiliki 100% code coverage tidak menjamin kode bebas dari bug. Coverage hanya mengukur baris mana yang dieksekusi oleh test, namun tidak menjamin kebenaran logika test dalam menangani semua kemungkinan kasus di dunia nyata.

### 2. Functional Testing & Clean Code
* **Cleanliness of new functional test suites:** Jika kita membuat suite baru dengan prosedur setup yang identik dengan sebelumnya, hal ini akan menimbulkan masalah duplikasi kode (Code Duplication).
* **Code Quality Issues:** Duplikasi melanggar prinsip DRY (Don't Repeat Yourself), yang menurunkan kualitas kode karena jika ada perubahan pada konfigurasi setup (seperti URL atau port), kita harus memperbaruinya di banyak tempat secara manual.
* **Suggested Improvements:** Solusi untuk meningkatkan kebersihan kode adalah dengan menggunakan prinsip Inheritance. Kita dapat membuat satu **Base Class** yang menangani prosedur setup umum dan variabel instans, lalu kelas tes lainnya cukup melakukan `extends` ke kelas tersebut.

---

## Reflection 3 

### 1. Perbaikan Kualitas Kode (Code Quality)
Dalam pengerjaan latihan ini, ada beberapa masalah kualitas kode yang ditemukan lewat pemindaian **PMD** dan langsung diperbaiki:
* **Unused Imports:** Menghapus bagian *import* yang tidak terpakai supaya kode tetap bersih dan proses kompilasi lebih efisien.
* **Unused Private Fields:** Menghapus variabel privat yang sudah dideklarasikan tapi tidak pernah digunakan dalam kelas, tujuannya agar kode lebih mudah dipelihara (*maintainability*).
* **Naming Conventions:** Merapikan penamaan variabel dan metode yang belum sesuai dengan standar konvensi *camelCase* Java.

**Strategi perbaikannya:** Proses ini dilakukan secara **iteratif**. Jadi, pengerjaannya dimulai dengan menjalankan perintah `./gradlew pmdMain` secara lokal di terminal IntelliJ. Dari laporan yang muncul, setiap poin dianalisis satu per satu dan langsung diperbaiki. Dengan cara ini, kualitas kode sudah dipastikan aman sebelum masuk ke tahap *push* ke GitHub, jadi nggak perlu nunggu alur kerja CI gagal dulu baru bertindak.

### 2. Evaluasi Alur Kerja CI/CD
Kalau dilihat dari alur yang sudah berjalan, implementasi saat ini bisa dibilang sudah memenuhi definisi **Continuous Integration (CI)** dan **Continuous Deployment (CD)**.
Proses CI bisa dilihat dari adanya *workflow* di GitHub Actions yang secara otomatis menjalankan *unit tests* dan analisis kode statis (PMD) setiap kali ada kode baru yang di-*push* atau lewat *pull request*. Hal ini penting untuk menjamin kalau perubahan yang baru masuk tidak merusak fungsi yang sudah ada. Untuk CD sendiri, hal ini terlihat dari integrasi langsung dengan Heroku yang otomatis memicu *deployment* baru begitu kode masuk ke *branch* `main`. Jadi, seluruh prosesnya jadi lebih otomatis, minim intervensi manual, dan versi terbaru aplikasi yang stabil bisa langsung diakses oleh pengguna.


Berikut versi yang sudah disesuaikan sintaks dan gayanya:

---

## Reflection 4

### 1. Principles Applied

Dalam pengerjaan ini, beberapa prinsip SOLID yang diterapkan adalah:

* **SRP (Single Responsibility Principle):** Memisahkan `CarController` dari `ProductController` agar masing-masing hanya memiliki satu tanggung jawab.
* **LSP (Liskov Substitution Principle):** Menghapus inheritance antara `CarController` dan `ProductController` karena Car tidak dapat menggantikan Product secara fungsional.
* **DIP (Dependency Inversion Principle):** Mengubah dependensi di `CarController` dari `CarServiceImpl` menjadi `CarService` interface agar bergantung pada abstraksi.

### 2. Advantages of Applying SOLID

Beberapa keuntungan yang didapat dari penerapan SOLID:

* **Maintainability:** Perubahan pada fitur Product tidak akan mempengaruhi Car karena sudah dipisahkan (SRP).
* **Flexibility:** Implementasi `CarService` dapat diganti tanpa perlu mengubah kode di `CarController` (DIP).

### 3. Disadvantages of Not Applying SOLID

Jika prinsip SOLID tidak diterapkan, akan muncul beberapa masalah:

* **Tight Coupling:** Tanpa DIP, perubahan pada service akan memaksa perubahan di controller.
* **Fragility:** Tanpa SRP, class yang menangani banyak tanggung jawab akan lebih rentan terhadap bug saat ada perubahan kecil.
