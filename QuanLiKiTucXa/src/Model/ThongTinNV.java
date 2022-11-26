/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author LE BICH VY
 */
public class ThongTinNV {
    private String MaNV;
    private String Hoten;
    private String SDT;
    private String CCCD;
    private String Ngaysinh;
    private String Email;
    private String Ngaybd;
    private String HinhAnh;

    public ThongTinNV() {
    }

    public ThongTinNV(String MaNV, String Hoten, String SDT, String CCCD, String Ngaysinh, String Email, String Ngaybd, String HinhAnh) {
        this.MaNV = MaNV;
        this.Hoten = Hoten;
        this.SDT = SDT;
        this.CCCD = CCCD;
        this.Ngaysinh = Ngaysinh;
        this.Email = Email;
        this.Ngaybd = Ngaybd;
        this.HinhAnh = HinhAnh;
    }

   

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getHoten() {
        return Hoten;
    }

    public void setHoten(String Hoten) {
        this.Hoten = Hoten;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getNgaysinh() {
        return Ngaysinh;
    }

    public void setNgaysinh(String Ngaysinh) {
        this.Ngaysinh = Ngaysinh;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getNgaybd() {
        return Ngaybd;
    }

    public void setNgaybd(String Ngaybd) {
        this.Ngaybd = Ngaybd;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String HinhAnh) {
        this.HinhAnh = HinhAnh;
    }
    
    
}
