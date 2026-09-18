# Basit Hesap Makinesi (Android)

Basit bir Android hesap makinesi uygulaması. GitHub Actions ile otomatik olarak APK'ya derlenir.

## Nasıl kullanılır?

1. Bu klasördeki tüm dosyaları yeni bir GitHub reposuna yükle (push et).
   - `main` veya `master` branch'ine push yapman yeterli.
2. GitHub reposunda üstteki **Actions** sekmesine git.
3. "Build APK" workflow'unun çalışmasını bekle (birkaç dakika sürer).
4. Workflow tamamlanınca, ilgili çalışmanın (run) sayfasına gir.
5. En altta **Artifacts** bölümünden `app-debug-apk` dosyasını indir.
6. İndirdiğin zip'i aç, içinden `app-debug.apk` dosyasını telefonuna aktar.
7. Telefonunda "Bilinmeyen kaynaklardan yükleme" iznini açıp APK'yı kur.

## Elle (manuel) tetikleme

Actions sekmesinden "Build APK" workflow'unu seçip **Run workflow** butonuyla da derlemeyi elle başlatabilirsin.

## Proje yapısı

- `app/src/main/java/.../MainActivity.java` — hesap makinesi mantığı
- `app/src/main/res/layout/activity_main.xml` — arayüz (butonlar, ekran)
- `.github/workflows/build.yml` — GitHub Actions derleme betiği
