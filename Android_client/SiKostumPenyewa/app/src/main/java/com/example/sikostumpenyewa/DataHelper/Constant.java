package com.example.sikostumpenyewa.DataHelper;

public class Constant {
    public static final String ID_KERANJANG = "id_keranjang";
    public static final String A = "id_user";
    public static final String B = "id_kostum";
    public static final String C = "id_tempat";
    public static final String D = "id_alamat";
    public static final String E = "nama_kostum";
    public static final String F = "nama_tempat";
    public static final String G = "alamat";
    public static final String H = "jumlah_kostum";
    public static final String I = "harga_kostum";
    public static  final String J ="jumlah_sewa";
    public static final String K ="sub_harga";
   public static final String DB_NAME="temp_keranjang";
   public static final String TB_CART="tb_keranjang";
    public static final int DB_VERSION= Integer.parseInt("11");

    public static final String CREATE_TB_CART =
            "create table tb_keranjang ("
                    + ID_KERANJANG + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
                    + A + " text not null, "
                    + B + " text not null, "
                    + C + " text not null, "
                    + D + " text not null, "
                    + E + " text not null, "
                    + F + " text not null, "
                    + G + " text not null, "
                    + H + " text not null, "
                    + I + " text not null, "
                    + J + " text not null,"
                    + K + " text not null );";
}
