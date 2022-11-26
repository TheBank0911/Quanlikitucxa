/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author LE BICH VY
 */
public class NhanVien {


    private String MaNV;
    private String HoTen;
    private double Luong;
    private double Thuong;
    private double TongTien;

    public NhanVien(String MaNV, String HoTen, double Luong, double Thuong, double TongTien) {
        this.MaNV = MaNV;
        this.HoTen = HoTen;
        this.Luong = Luong;
        this.Thuong = Thuong;
        this.TongTien = TongTien;
    }

    public NhanVien() {
    }


    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public double getLuong() {
        return Luong;
    }

    public void setLuong(double Luong) {
        this.Luong = Luong;
    }

    public double getThuong() {
        return Thuong;
    }

    public void setThuong(double Thuong) {
        this.Thuong = Thuong;
    }

    public double getTongTien() {
        return TongTien;
    }

    public void setTongTien(double TongTien) {
        this.TongTien =TongTien;
    }

}
