-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 12 Agu 2019 pada 18.17
-- Versi server: 10.1.37-MariaDB
-- Versi PHP: 7.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sevie`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `alamat`
--

CREATE TABLE `alamat` (
  `id_alamat` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `label_alamat` varchar(30) NOT NULL,
  `alamat` text NOT NULL,
  `provinsi` varchar(20) NOT NULL,
  `kota` varchar(20) NOT NULL,
  `kecamatan` varchar(20) NOT NULL,
  `desa` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `alamat`
--

INSERT INTO `alamat` (`id_alamat`, `id_user`, `label_alamat`, `alamat`, `provinsi`, `kota`, `kecamatan`, `desa`) VALUES
(1, 2, 'Alamat Asal ', 'jl.candi jago no.11', '35', '3573', '3573040', '3573040007'),
(2, 2, 'Alamat Kos', 'jl.kesumbah 30f ', '35', '3573', '3573050', '3573050002'),
(3, 1, 'Alamat Asal', 'jl.candi jago', '11', '1101', '1101010', '1101010001'),
(4, 3, 'Alamat Rumah', 'Jl Jakarta No.13', '35', '3573', '3573030', '3573030011'),
(5, 2, 'alamat kantor', 'jl suhat', '35', '3573', '3573040', '3573040009');

-- --------------------------------------------------------

--
-- Struktur dari tabel `denda`
--

CREATE TABLE `denda` (
  `id_denda` int(11) NOT NULL,
  `id_sewa` int(11) NOT NULL,
  `denda` int(11) NOT NULL,
  `keterangan` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `denda`
--

INSERT INTO `denda` (`id_denda`, `id_sewa`, `denda`, `keterangan`) VALUES
(1, 4, 5000, 'telat 1 hari'),
(2, 5, 20000, 'telat 4 hari'),
(3, 16, 140000, 'terlambat'),
(4, 15, 60000, 'terlambat 12 hari'),
(5, 7, 150000, 'terlambat 16 hari'),
(6, 18, 80000, 'rusak'),
(7, 28, 5000, 'terlambat');

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail`
--

CREATE TABLE `detail` (
  `id_detail` int(11) NOT NULL,
  `id_sewa` int(11) NOT NULL,
  `id_kostum` int(11) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `status_detail` enum('menunggu','valid','tidak valid','pinjam','kembali') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `detail`
--

INSERT INTO `detail` (`id_detail`, `id_sewa`, `id_kostum`, `jumlah`, `status_detail`) VALUES
(1, 1, 3, 2, 'kembali'),
(2, 1, 2, 2, 'kembali'),
(3, 2, 4, 1, 'tidak valid'),
(4, 3, 1, 3, 'kembali'),
(5, 4, 1, 2, 'kembali'),
(6, 5, 3, 2, 'kembali'),
(7, 5, 2, 1, 'kembali'),
(8, 6, 3, 1, 'pinjam'),
(9, 7, 4, 1, 'kembali'),
(10, 7, 3, 1, 'kembali'),
(11, 8, 2, 1, 'tidak valid'),
(12, 9, 3, 2, 'menunggu'),
(13, 10, 2, 1, 'tidak valid'),
(14, 10, 4, 1, 'tidak valid'),
(15, 11, 3, 2, 'pinjam'),
(16, 12, 2, 1, 'menunggu'),
(17, 13, 1, 1, 'pinjam'),
(18, 14, 3, 1, 'valid'),
(19, 15, 2, 1, 'kembali'),
(20, 16, 1, 1, 'kembali'),
(21, 17, 3, 2, 'menunggu'),
(22, 18, 3, 1, 'kembali'),
(23, 19, 3, 1, 'kembali'),
(24, 20, 3, 2, 'menunggu'),
(25, 21, 3, 1, 'menunggu'),
(26, 22, 2, 1, 'menunggu'),
(27, 23, 2, 2, 'kembali'),
(28, 24, 1, 1, 'menunggu'),
(29, 25, 1, 1, 'menunggu'),
(30, 26, 3, 1, 'valid'),
(31, 27, 2, 1, 'menunggu'),
(32, 27, 3, 1, 'menunggu'),
(33, 28, 3, 1, 'kembali'),
(34, 29, 2, 1, 'pinjam'),
(35, 30, 4, 1, 'valid'),
(36, 31, 1, 1, 'valid'),
(37, 32, 3, 1, 'valid'),
(38, 33, 2, 1, 'pinjam'),
(39, 34, 4, 1, 'pinjam'),
(40, 35, 1, 1, 'pinjam'),
(41, 36, 3, 1, 'menunggu'),
(42, 36, 2, 1, 'menunggu');

--
-- Trigger `detail`
--
DELIMITER $$
CREATE TRIGGER `stok_kurang` BEFORE INSERT ON `detail` FOR EACH ROW IF (NEW.status_detail = 'menunggu') THEN
UPDATE kostum SET jumlah_kostum=jumlah_kostum-NEW.jumlah WHERE id_kostum = NEW.id_kostum;
END IF
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `stok_tambah` BEFORE UPDATE ON `detail` FOR EACH ROW IF (NEW.status_detail = 'tidak valid' OR NEW.status_detail ='kembali') THEN
UPDATE kostum SET jumlah_kostum=jumlah_kostum+NEW.jumlah WHERE id_kostum = NEW.id_kostum;
END IF
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `kategori`
--

CREATE TABLE `kategori` (
  `id_kategori` int(11) NOT NULL,
  `kategori` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `kategori`
--

INSERT INTO `kategori` (`id_kategori`, `kategori`) VALUES
(1, 'Adat'),
(2, 'Pahlawan'),
(3, 'Profesi'),
(4, 'Internasional'),
(5, 'Karakter'),
(6, 'Tumbuhan'),
(7, 'Halloween');

-- --------------------------------------------------------

--
-- Struktur dari tabel `komentar`
--

CREATE TABLE `komentar` (
  `id_komentar` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_detail` int(11) NOT NULL,
  `komentar` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `komentar`
--

INSERT INTO `komentar` (`id_komentar`, `id_user`, `id_detail`, `komentar`) VALUES
(1, 2, 1, 'bagus'),
(2, 2, 2, 'keren'),
(3, 1, 10, 'bagus bangett'),
(4, 3, 8, 'keren kostumnya'),
(5, 2, 6, 'keren'),
(6, 2, 7, 'bagus banget'),
(7, 2, 22, 'bagus');

-- --------------------------------------------------------

--
-- Struktur dari tabel `kostum`
--

CREATE TABLE `kostum` (
  `id_kostum` int(11) NOT NULL,
  `id_kategori` int(11) NOT NULL,
  `nama_kostum` varchar(50) NOT NULL,
  `jumlah_kostum` int(11) NOT NULL,
  `harga_kostum` int(11) NOT NULL,
  `deskripsi_kostum` text NOT NULL,
  `foto_kostum` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `kostum`
--

INSERT INTO `kostum` (`id_kostum`, `id_kategori`, `nama_kostum`, `jumlah_kostum`, `harga_kostum`, `deskripsi_kostum`, `foto_kostum`) VALUES
(1, 5, 'Princess Ana', 19, 300000, 'Dewasa M', '1564693390939.jpg'),
(2, 3, 'Dokter', 16, 150000, 'Dewasa S', '1564693405033.jpg'),
(3, 5, 'Captain America', 14, 250000, 'All Size', '1564693426764.jpg'),
(4, 4, 'King Yunani ', 11, 200000, 'Size L', '1563291078428.jpg');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pemesanan`
--

CREATE TABLE `pemesanan` (
  `id_pemesanan` int(11) NOT NULL,
  `id_kostum` int(11) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `no_hp` varchar(14) NOT NULL,
  `alamat` text NOT NULL,
  `tgl_sewa` date NOT NULL,
  `tgl_kembali` date NOT NULL,
  `denda` int(11) NOT NULL,
  `keterangan` text NOT NULL,
  `status_pesan` enum('pinjam','kembali') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `pemesanan`
--

INSERT INTO `pemesanan` (`id_pemesanan`, `id_kostum`, `jumlah`, `nama`, `no_hp`, `alamat`, `tgl_sewa`, `tgl_kembali`, `denda`, `keterangan`, `status_pesan`) VALUES
(1, 1, 2, 'ajeng', '082134567456', 'jl.kesumbah 30f kota malang', '2019-07-16', '2019-07-18', 10000, 'robek', 'kembali'),
(2, 2, 1, 'Ana', '087123456788', 'Jl Semanggi Barat No.15 Kota Malang', '2019-07-14', '2019-07-16', 0, 'tidak ada', 'kembali'),
(3, 4, 1, 'Bayu', '081234564554', 'Jl. Terusan No.9 Kota Malang', '2019-07-10', '2019-07-12', 100000, 'terlambat 20 hari', 'kembali'),
(4, 1, 2, 'wahyu', '081234557543', 'malang', '2019-08-01', '2019-08-03', 0, '0', 'kembali'),
(5, 2, 2, 'juju', '081234567', 'malang', '2019-08-01', '2019-08-03', 0, '', 'pinjam'),
(6, 4, 1, 'yaya', '081234567123', 'jl.kesumbah malang', '2019-07-31', '2019-08-02', 0, '', 'pinjam'),
(7, 3, 1, 'hanif', '072345654234', 'jl. turi malang', '2019-07-29', '2019-07-31', 2000, 'terlambat', 'kembali'),
(8, 3, 1, 'saya', '081234564231', 'malang', '2019-08-02', '2019-08-04', 0, '', 'pinjam');

--
-- Trigger `pemesanan`
--
DELIMITER $$
CREATE TRIGGER `kurang` BEFORE INSERT ON `pemesanan` FOR EACH ROW IF (NEW.status_pesan = 'pinjam') THEN
UPDATE kostum SET jumlah_kostum=jumlah_kostum-NEW.jumlah WHERE id_kostum = NEW.id_kostum;
END IF
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `tambah` BEFORE UPDATE ON `pemesanan` FOR EACH ROW IF (NEW.status_pesan = 'kembali') THEN
UPDATE kostum SET jumlah_kostum=jumlah_kostum+NEW.jumlah WHERE id_kostum = NEW.id_kostum;
END IF
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `sewa`
--

CREATE TABLE `sewa` (
  `id_sewa` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_alamat` int(11) NOT NULL,
  `kode_sewa` varchar(20) NOT NULL,
  `tgl_transaksi` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `tgl_sewa` date NOT NULL,
  `tgl_kembali` date NOT NULL,
  `bukti_sewa` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `sewa`
--

INSERT INTO `sewa` (`id_sewa`, `id_user`, `id_alamat`, `kode_sewa`, `tgl_transaksi`, `tgl_sewa`, `tgl_kembali`, `bukti_sewa`) VALUES
(1, 2, 1, 'DUQI-YNEYH-PUDOH', '2019-07-16 15:45:31', '2019-07-14', '2019-07-16', '1563292027807.jpg'),
(2, 2, 1, 'VIGE-VQTE-CFYTE', '2019-07-10 15:38:54', '2019-07-11', '2019-07-13', ''),
(3, 2, 1, 'UFAMB-GKFS-ATR', '2019-07-16 15:43:42', '2019-07-16', '2019-07-19', '1563292046112.jpg'),
(4, 2, 1, 'TGGSK-SVC-SKYN', '2019-07-16 15:43:56', '2019-07-13', '2019-07-15', '1563291866525.jpg'),
(5, 2, 1, 'GCRDN-AUNXO-STU', '2019-07-10 16:11:21', '2019-07-10', '2019-07-12', '1563291866525.jpg'),
(6, 3, 4, 'BJY-CPCW-VRZXM', '2019-07-16 16:17:14', '2019-07-18', '2019-07-20', '1563292046112.jpg'),
(7, 1, 3, 'IFCJ-OSIR-SKUU', '2019-07-16 16:18:08', '2019-07-14', '2019-07-16', '1563293390126.jpg'),
(8, 1, 3, 'PQNL-LSWLM-M', '2019-07-16 16:03:57', '2019-07-16', '2019-07-18', ''),
(9, 2, 1, 'TZNS-SYYQT-MVOZQ', '2019-07-18 04:10:18', '2019-07-18', '2019-07-20', ''),
(10, 2, 1, 'RZQP-AHPKF-JFLFH', '2019-07-18 04:10:35', '2019-07-18', '2019-07-20', ''),
(11, 3, 4, 'CBHO-XBEL-DTFON', '2019-07-18 04:12:13', '2019-07-18', '2019-07-20', '1563423514682.jpg'),
(12, 3, 4, 'LPIFR-TWE-CDQ', '2019-07-17 04:12:41', '2019-07-18', '2019-07-20', ''),
(13, 3, 4, 'XSS-AJW-YSOB', '2019-08-01 11:46:44', '2019-07-18', '2019-07-20', '1564660427490.jpg'),
(14, 3, 4, 'FNJS-HXJJ-BXJPK', '2019-07-20 04:18:13', '2019-07-21', '2019-07-23', '1563423875761.jpg'),
(15, 3, 4, 'RXBV-UPF-XCSC', '2019-07-18 04:18:05', '2019-07-18', '2019-07-20', '1563423867639.jpg'),
(16, 3, 4, 'WGNF-QEQP-RNTX', '2019-07-18 04:22:07', '2019-07-16', '2019-07-18', '1563423859724.jpg'),
(17, 2, 1, 'FEPQ-VYVE-SP', '2019-08-01 20:54:06', '2019-08-01', '2019-08-03', '1564693271076.jpg'),
(18, 2, 1, 'EIG-VAM-KPYE', '2019-08-01 05:12:25', '2019-08-01', '2019-08-03', '1564636767864.jpg'),
(19, 2, 1, 'OMTPC-JQUM-SZYB', '2019-08-01 05:11:22', '2019-08-01', '2019-08-03', '1564636704039.jpg'),
(20, 2, 1, 'OLG-AWBZS-JPZT', '2019-08-01 20:54:20', '2019-08-01', '2019-08-03', '1564693285815.jpg'),
(21, 2, 1, 'TXDLT-JENPE-QZCH', '2019-08-01 06:06:37', '2019-08-06', '2019-08-08', ''),
(22, 2, 1, 'ECAH-UPBG-KHRMN', '2019-08-01 20:53:25', '2019-08-01', '2019-08-03', '1564693230041.jpg'),
(23, 3, 4, 'FFDB-OLOW-NTSW', '2019-08-01 11:46:32', '2019-08-09', '2019-08-11', '1564660414910.jpg'),
(24, 3, 4, 'FMEBF-AOIV-EWO', '2019-08-01 20:50:55', '2019-08-05', '2019-08-07', ''),
(25, 2, 1, 'KFJJL-YHE-AONM', '2019-08-01 20:52:08', '2019-08-02', '2019-08-04', ''),
(26, 2, 1, 'JGBV-MAFZ-KNZE', '2019-08-01 20:53:49', '2019-08-04', '2019-08-06', '1564693254631.jpg'),
(27, 2, 1, 'IPTW-QPUC-YJI', '2019-08-01 20:54:57', '2019-08-07', '2019-08-09', ''),
(28, 2, 1, 'QMDG-FPTGD-MGLXJ', '2019-07-26 21:01:04', '2019-06-27', '2019-07-29', '1564693689290.jpg'),
(29, 2, 1, 'ADL-ZKO-EOHPT', '2019-07-29 21:00:50', '2019-07-31', '2019-08-02', '1564693674870.jpg'),
(30, 2, 1, 'GC-VZJ-LULPB', '2019-08-01 21:09:45', '2019-08-07', '2019-08-09', '1564693664493.jpg'),
(31, 2, 1, 'GPKZ-ACGS-JIU', '2019-08-01 21:00:29', '2019-08-02', '2019-08-04', '1564693654154.jpg'),
(32, 2, 1, 'RSP-WIS-WRL', '2019-07-30 21:10:28', '2019-05-31', '2019-08-02', '1564693643372.jpg'),
(33, 2, 1, 'PBZI-NLHQP-OV', '2019-07-30 21:00:10', '2019-08-01', '2019-08-03', '1564693634339.jpg'),
(34, 2, 1, 'HEZW-SSJOE-YNN', '2019-08-01 21:00:01', '2019-08-02', '2019-08-04', '1564693626822.jpg'),
(35, 2, 1, 'ZSJJ-PH-SNIPP', '2019-08-01 20:59:54', '2019-08-02', '2019-08-04', '1564693619136.jpg'),
(36, 2, 1, 'JNCFN-AN-KFC', '2019-08-02 01:52:19', '2019-08-02', '2019-08-04', '1564711161800.jpg');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tempat`
--

CREATE TABLE `tempat` (
  `id_tempat` int(11) NOT NULL,
  `nama_tempat` varchar(30) NOT NULL,
  `no_rekening` varchar(50) NOT NULL,
  `no_hp` varchar(13) NOT NULL,
  `email` varchar(50) NOT NULL,
  `foto_tempat` text NOT NULL,
  `slogan_tempat` text NOT NULL,
  `deskripsi_tempat` text NOT NULL,
  `status_tempat` enum('Buka','Tutup') NOT NULL,
  `alamat` text NOT NULL,
  `username` varchar(15) NOT NULL,
  `password` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tempat`
--

INSERT INTO `tempat` (`id_tempat`, `nama_tempat`, `no_rekening`, `no_hp`, `email`, `foto_tempat`, `slogan_tempat`, `deskripsi_tempat`, `status_tempat`, `alamat`, `username`, `password`) VALUES
(1, 'Galeri Sevie', '144-00-1488478-5', '081805020859', 'mitasdama@gmail.com', 'galeri_sevie.jpg', 'Persewaan Kostum Terlengkap di Kota Malang', 'Buka Setiap Hari', 'Tutup', 'Jl. Terusan Sulfat No.129, Sawojajar, Kec. Kedungkandang, Kota Malang, Jawa Timur ', 'sevie', 'sevie');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `jenis_kelamin` enum('P','L') NOT NULL,
  `email` varchar(50) NOT NULL,
  `no_hp` varchar(13) NOT NULL,
  `foto_user` text NOT NULL,
  `username` varchar(15) NOT NULL,
  `password` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id_user`, `nama`, `jenis_kelamin`, `email`, `no_hp`, `foto_user`, `username`, `password`) VALUES
(1, 'Dwi', 'P', 'dwiayuningkinanti@gmail.com', '082139799778', 'dwayning.jpeg', 'dwi', 'dwi'),
(2, 'Mita ', 'P', 'mitatata9@gmail.com', '082139799779', 'Screenshot_20190701-194253_LINE3.jpg', 'dama', 'dama'),
(3, 'Dama', 'L', 'dama@gmail.com', '082134567445', 'admin.jpg', 'mitaw', 'mitaw'),
(4, 'ning', 'P', 'ning@gmail.com', '0821345698123', '', 'ning', 'ning'),
(5, 'andro', 'P', 'mitadamasepta@gmail.com', '082123456789', '', 'andro', 'andro');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `alamat`
--
ALTER TABLE `alamat`
  ADD PRIMARY KEY (`id_alamat`),
  ADD KEY `alamat_ibfk_1` (`id_user`);

--
-- Indeks untuk tabel `denda`
--
ALTER TABLE `denda`
  ADD PRIMARY KEY (`id_denda`),
  ADD KEY `denda_ibfk_1` (`id_sewa`);

--
-- Indeks untuk tabel `detail`
--
ALTER TABLE `detail`
  ADD PRIMARY KEY (`id_detail`),
  ADD KEY `detail_ibfk_1` (`id_sewa`),
  ADD KEY `detail_ibfk_2` (`id_kostum`);

--
-- Indeks untuk tabel `kategori`
--
ALTER TABLE `kategori`
  ADD PRIMARY KEY (`id_kategori`);

--
-- Indeks untuk tabel `komentar`
--
ALTER TABLE `komentar`
  ADD PRIMARY KEY (`id_komentar`),
  ADD KEY `komentar_ibfk_1` (`id_detail`),
  ADD KEY `komentar_ibfk_2` (`id_user`);

--
-- Indeks untuk tabel `kostum`
--
ALTER TABLE `kostum`
  ADD PRIMARY KEY (`id_kostum`),
  ADD KEY `kostum_ibfk_1` (`id_kategori`);

--
-- Indeks untuk tabel `pemesanan`
--
ALTER TABLE `pemesanan`
  ADD PRIMARY KEY (`id_pemesanan`),
  ADD KEY `pemesanan_ibfk_1` (`id_kostum`);

--
-- Indeks untuk tabel `sewa`
--
ALTER TABLE `sewa`
  ADD PRIMARY KEY (`id_sewa`),
  ADD UNIQUE KEY `kode_sewa` (`kode_sewa`),
  ADD KEY `sewa_ibfk_1` (`id_user`),
  ADD KEY `sewa_ibfk_2` (`id_alamat`);

--
-- Indeks untuk tabel `tempat`
--
ALTER TABLE `tempat`
  ADD PRIMARY KEY (`id_tempat`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `alamat`
--
ALTER TABLE `alamat`
  MODIFY `id_alamat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT untuk tabel `denda`
--
ALTER TABLE `denda`
  MODIFY `id_denda` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT untuk tabel `detail`
--
ALTER TABLE `detail`
  MODIFY `id_detail` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT untuk tabel `kategori`
--
ALTER TABLE `kategori`
  MODIFY `id_kategori` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT untuk tabel `komentar`
--
ALTER TABLE `komentar`
  MODIFY `id_komentar` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT untuk tabel `kostum`
--
ALTER TABLE `kostum`
  MODIFY `id_kostum` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `pemesanan`
--
ALTER TABLE `pemesanan`
  MODIFY `id_pemesanan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT untuk tabel `sewa`
--
ALTER TABLE `sewa`
  MODIFY `id_sewa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT untuk tabel `tempat`
--
ALTER TABLE `tempat`
  MODIFY `id_tempat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `alamat`
--
ALTER TABLE `alamat`
  ADD CONSTRAINT `alamat_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `denda`
--
ALTER TABLE `denda`
  ADD CONSTRAINT `denda_ibfk_1` FOREIGN KEY (`id_sewa`) REFERENCES `sewa` (`id_sewa`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `detail`
--
ALTER TABLE `detail`
  ADD CONSTRAINT `detail_ibfk_1` FOREIGN KEY (`id_sewa`) REFERENCES `sewa` (`id_sewa`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detail_ibfk_2` FOREIGN KEY (`id_kostum`) REFERENCES `kostum` (`id_kostum`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `komentar`
--
ALTER TABLE `komentar`
  ADD CONSTRAINT `komentar_ibfk_1` FOREIGN KEY (`id_detail`) REFERENCES `detail` (`id_detail`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `komentar_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `kostum`
--
ALTER TABLE `kostum`
  ADD CONSTRAINT `kostum_ibfk_1` FOREIGN KEY (`id_kategori`) REFERENCES `kategori` (`id_kategori`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `pemesanan`
--
ALTER TABLE `pemesanan`
  ADD CONSTRAINT `pemesanan_ibfk_1` FOREIGN KEY (`id_kostum`) REFERENCES `kostum` (`id_kostum`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `sewa`
--
ALTER TABLE `sewa`
  ADD CONSTRAINT `sewa_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `sewa_ibfk_2` FOREIGN KEY (`id_alamat`) REFERENCES `alamat` (`id_alamat`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
