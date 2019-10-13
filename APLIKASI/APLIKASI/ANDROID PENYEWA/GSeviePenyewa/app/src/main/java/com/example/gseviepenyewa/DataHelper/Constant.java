package com.example.gseviepenyewa.DataHelper;

public class Constant {
    public static final String ID_KERANJANG = "id_keranjang";
    public static final String A = "id_user";
    public static final String B = "id_kostum";
    public static final String C = "nama_kostum";
    public static final String D = "jumlah_kostum";
    public static final String E = "harga_kostum";
    public static  final String F ="jumlah_sewa";
    public static final String G ="sub_harga";
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
                    + G + " text not null );";
}
