import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KRSApp {

    // ======== Class MataKuliah ========
    static class MataKuliah {
        private String kode;
        private String nama;
        private int sks;

        public MataKuliah(String kode, String nama, int sks) {
            this.kode = kode;
            this.nama = nama;
            this.sks = sks;
        }

        public String getKode() { return kode; }
        public String getNama() { return nama; }
        public int getSks() { return sks; }

        @Override
        public String toString() {
            return String.format("%-8s | %-35s | %2d SKS", kode, nama, sks);
        }
    }

    // ======== Class Mahasiswa ========
    static class Mahasiswa {
        private String nim;
        private String nama;
        private List<MataKuliah> daftarMK;

        public Mahasiswa(String nim, String nama) {
            this.nim = nim;
            this.nama = nama;
            this.daftarMK = new ArrayList<>();
        }

        public String getNim() { return nim; }
        public String getNama() { return nama; }

        public List<MataKuliah> getDaftarMK() { return daftarMK; }

        public int totalSks() {
            return daftarMK.stream().mapToInt(MataKuliah::getSks).sum();
        }

        public boolean tambahMK(MataKuliah mk) {
            int sisa = 24 - totalSks();
            if (mk.getSks() <= sisa) {
                daftarMK.add(mk);
                return true;
            } else {
                return false;
            }
        }
    }

    // ======== Class KRS ========
    static class KRS {
        public static void printKRS(Mahasiswa mhs) {
            System.out.println("===============================================");
            System.out.println("                FORMULIR KRS                   ");
            System.out.println("===============================================");
            System.out.println("NIM  : " + mhs.getNim());
            System.out.println("Nama : " + mhs.getNama());
            System.out.println("-----------------------------------------------");
            System.out.printf("%-8s | %-35s | %s%n", "Kode", "Nama Mata Kuliah", "SKS");
            System.out.println("-----------------------------------------------");
            mhs.getDaftarMK().forEach(mk -> System.out.println(mk.toString()));
            System.out.println("-----------------------------------------------");
            System.out.println("Total SKS: " + mhs.totalSks());
            System.out.println("\n\nMengetahui,\nKPS\n");
            System.out.println("_______________________________");
            System.out.println(mhs.getNim() + "  " + mhs.getNama());
            System.out.println("===============================================\n");
        }
    }

    // ======== Main Program ========
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Aplikasi Pengisian KRS (OOP - Satu File)");
        System.out.print("Masukkan NIM: ");
        String nim = sc.nextLine().trim();
        System.out.print("Masukkan Nama: ");
        String nama = sc.nextLine().trim();

        Mahasiswa mhs = new Mahasiswa(nim, nama);

        System.out.println("\nMasukkan mata kuliah yang ingin diambil (maksimal total 24 SKS).");

        while (true) {
            System.out.println("\nTotal SKS saat ini: " + mhs.totalSks() + " / 24");
            System.out.print("Tambah MK? (y/n): ");
            String yn = sc.nextLine().trim().toLowerCase();
            if (!yn.equals("y")) break;

            System.out.print("Kode MK: ");
            String kode = sc.nextLine().trim();
            System.out.print("Nama MK: ");
            String namaMk = sc.nextLine().trim();

            int sks = 0;
            while (true) {
                System.out.print("SKS (angka): ");
                try {
                    sks = Integer.parseInt(sc.nextLine().trim());
                    if (sks <= 0) {
                        System.out.println("SKS harus lebih dari 0.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Masukkan angka SKS yang valid.");
                }
            }

            MataKuliah mk = new MataKuliah(kode, namaMk, sks);
            boolean berhasil = mhs.tambahMK(mk);
            if (!berhasil) {
                System.out.println("❌ Gagal menambah MK! Melebihi batas 24 SKS.");
            } else {
                System.out.println("✅ MK berhasil ditambahkan!");
            }

            if (mhs.totalSks() == 24) {
                System.out.println("Sudah mencapai batas 24 SKS.");
                break;
            }
        }

        System.out.print("\nApakah ingin mencetak KRS? (y/n): ");
        String cetak = sc.nextLine().trim().toLowerCase();
        if (cetak.equals("y")) {
            KRS.printKRS(mhs);
        } else {
            System.out.println("Selesai. KRS tidak dicetak.");
        }

        sc.close();
    }
}
