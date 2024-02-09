/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cobaan;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class Cobaan {
    public record Barang(String kode, String nama, int stok, int harga){
    }
    public record Transaksi(Barang barang, int jumlah){
    }
    public record StrukPembelanjaan(String namaPelanggan, List<Transaksi> riwayatTransaksi) {

        public void tambahTransaksi(Transaksi transaksi) {
            this.riwayatTransaksi.add(transaksi);
        }

        public void tampilkanRiwayatTransaksi() {
            for (Transaksi transaksi : riwayatTransaksi) {
                System.out.println("Barang: " + transaksi.barang.nama + ", Jumlah: " + transaksi.jumlah);
            }
        }
    }
     static void inisialisasiDataBarang(List<Barang> daftarBarang) {
        daftarBarang.add(new Barang("A1", "Pensil    ", 10, 1000));
        daftarBarang.add(new Barang("B2", "Pulpen    ", 15, 1500));
        daftarBarang.add(new Barang("C3", "Penghapus ", 20, 2000));
        daftarBarang.add(new Barang("D4", "Penggaris ", 20, 5000));
        daftarBarang.add(new Barang("E5", "Tipe-x    ", 20, 7000));
        daftarBarang.add(new Barang("F6", "Buku      ", 25, 4000));
        daftarBarang.add(new Barang("G7", "Kertas Hvs", 30, 500));
        daftarBarang.add(new Barang("H8", "Cutter    ", 20, 6000));
    }

     static void tampilkanDaftarBarang(List<Barang> daftarBarang) {
        System.out.println("Daftar barang yang dijual:");
        for (Barang barang : daftarBarang) {
            System.out.println(barang.kode + ". " + barang.nama + ", Stok: " + barang.stok + ", Harga: " + barang.harga);
        }
    }

     static Barang cariBarang(List<Barang> daftarBarang, String kode) {
        for (Barang barang : daftarBarang) {
            if (barang.kode.equals(kode)) {
                return barang;
            }
        }
        return null;    
    }
     static void updateStokBarang(List<Barang> daftarBarang, String kode, int jumlahBeli){
        for (int i=0; i < daftarBarang.size(); i++){
            Barang barang = daftarBarang.get(i);
            if (barang.kode.equals(kode)){
              daftarBarang.set(i, new Barang(kode, barang.nama, barang.stok-jumlahBeli, barang.harga));
              return;
            }
        }
     }
     static StrukPembelanjaan cariRiwayatPelanggan(List<StrukPembelanjaan> riwayatPelanggan, String namaPelanggan) {
        for (StrukPembelanjaan riwayat : riwayatPelanggan) {
            if (riwayat.namaPelanggan.equals(namaPelanggan)) {
                return riwayat;
            }
        }
        return null;
    }

     static int hitungTotalBelanja(Stack<Transaksi> keranjang) {
        int totalBelanja = 0;
        for (Transaksi transaksi : keranjang) {
            totalBelanja += transaksi.barang.harga * transaksi.jumlah;
        }
        return totalBelanja;
    }

     static void tampilkanStrukPembelanjaan(String namaPelanggan, Stack<Transaksi> keranjang,
            int totalBelanja, double diskon, double totalPembayaran) {
        System.out.println("======================= STRUK PEMBELANJAAN ====================== ");
        System.out.println("Pelanggan: " + namaPelanggan);

        for (Transaksi transaksi : keranjang) {
            System.out.println(transaksi.barang.nama + "    x " + transaksi.jumlah + " : Rp " +
                    (transaksi.barang.harga * transaksi.jumlah));
        }
        System.out.println("Total Belanja     : " + "Rp " + totalBelanja);
        System.out.println("Diskon (10%)      : " + "Rp " + (totalBelanja * diskon));
        System.out.println("Total Pembayaran  : " + "Rp " + totalPembayaran);
        System.out.println("================================================================ ");    
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Barang> daftarBarang = new ArrayList<>();
        Stack<Transaksi> keranjang = new Stack<>();
        Queue<String> antrianPelanggan = new LinkedList<>();
        List<StrukPembelanjaan> riwayatPelanggan = new LinkedList<>();
        
        System.out.println("===================================================================");
        System.out.println("                 SELAMAT DATANG DI TOKO ALAT TULIS                 ");
        System.out.println("===================================================================");
        
        System.out.println("");
        // Inisialisasi data barang
        inisialisasiDataBarang(daftarBarang);

        boolean ulangProgram = true;

        while (ulangProgram) {
            System.out.println("======================== DAFTAR BARANG ========================= ");
            tampilkanDaftarBarang(daftarBarang);
            System.out.println("================================================================ ");
            System.out.println("");
            
            System.out.println("======================= PROSES TRANSAKSI ======================= ");
            // Memasukkan nama pelanggan
            System.out.print("Masukkan nama pelanggan: ");
            String namaPelanggan = scanner.nextLine();

            // Cek apakah pelanggan memiliki riwayat transaksi
            StrukPembelanjaan strukPelanggan = cariRiwayatPelanggan(riwayatPelanggan, namaPelanggan);

            if (strukPelanggan == null) {
                // Jika pelanggan belum memiliki riwayat, buat riwayat baru
                List<Transaksi> riwayatTransaksi = new LinkedList<>();
                strukPelanggan = new StrukPembelanjaan(namaPelanggan, riwayatTransaksi  );
                riwayatPelanggan.add(strukPelanggan);
            }

            boolean ulangTransaksi = true;

            while (ulangTransaksi) {
                // Memilih barang yang akan dibeli
                System.out.println("\nPelanggan: " + namaPelanggan);
                while (true) {
                    System.out.print("Masukkan kode barang yang akan dibeli (atau 'selesai'): ");
                    String kodeBarang = scanner.nextLine();
                    if (kodeBarang.equalsIgnoreCase("selesai")) {
                        break;
                    }

                    Barang barangDipilih = cariBarang(daftarBarang, kodeBarang);

                    if (barangDipilih != null && barangDipilih.stok > 0) {
                        System.out.print("Masukkan jumlah barang yang akan dibeli: ");
                        int jumlahBeli = scanner.nextInt();
                        scanner.nextLine();

                        if (jumlahBeli > barangDipilih.stok) {
                            System.out.println("Stok tidak mencukupi!");
                        } else {
                            updateStokBarang(daftarBarang, kodeBarang, jumlahBeli);
                            keranjang.push(new Transaksi(barangDipilih, jumlahBeli));
                        }
                    } else {
                        System.out.println("Kode barang tidak valid!");
                    }
                }

                // Menghitung total belanja
                int totalBelanja = hitungTotalBelanja(keranjang);

                // Mendapatkan diskon jika total pembelanjaan mencapai 25000
                double diskon = (totalBelanja >= 25000) ? 0.1 : 0.0;
                double totalPembayaran = totalBelanja - (totalBelanja * diskon);

                System.out.println("=============================================================== ");
                System.out.println("");
                 
                // Menampilkan struk pembelanjaan
                tampilkanStrukPembelanjaan(namaPelanggan, keranjang, totalBelanja, diskon, totalPembayaran);

                // Menambahkan transaksi ke dalam riwayat pelanggan
                while (!keranjang.isEmpty()) {
                    Transaksi transaksi = keranjang.pop();
                    strukPelanggan.tambahTransaksi(transaksi);
                }                
                // Mengecek apakah ingin mengulang transaksi dengan orang yang sama
                System.out.print("\nIngin melakukan transaksi lagi untuk pelanggan " + namaPelanggan + "? (y/t): ");
                String ulangiTransaksi = scanner.nextLine();
                ulangTransaksi = ulangiTransaksi.equalsIgnoreCase("y");
            }
               // Mengecek apakah ingin mengulang program dengan orang yang berbeda
               System.out.print("\nIngin melakukan transaksi dengan pelanggan lain? (y/t): ");
               String ulangiProgram = scanner.nextLine();
               ulangProgram = ulangiProgram.equalsIgnoreCase("y");

               System.out.println("");
        }

        // Menampilkan riwayat transaksi dari semua pelanggan
        System.out.println("======================= RIWAYAT TRANSAKSI ====================== ");
        for (StrukPembelanjaan riwayat : riwayatPelanggan) {
            riwayat.tampilkanRiwayatTransaksi();
        }
        System.out.println("================================================================ ");
        System.out.println("");
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Terima kasih atas kunjungan Anda!");
    } 
}
