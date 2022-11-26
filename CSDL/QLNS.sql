use master
CREATE DATABASE QLNS
create TABLE NhanVien(
    MaNV NVARCHAR(10) PRIMARY KEY NOT NULL,
    Hoten NVARCHAR(50) NULL,
    SDT NVARCHAR(12) NULL,
    CCCD NVARCHAR(15) NULL,
    Ngaysinh NVARCHAR(15) NULL,
    Email NVARCHAR(50) NULL,
    Ngaybd NVARCHAR(15) NULL,
	Hinh nvarchar(50) null
)

CREATE TABLE LuongNV(
    STT INT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    Luong NVARCHAR(10) NULL,
	Thuong NVARCHAR(10),
	TongTien NVARCHAR(10),
    MaNV NVARCHAR(10) FOREIGN KEY REFERENCES NhanVien(MaNV)
)
CREATE table DangNhap(
    TenDN NVARCHAR(50) PRIMARY KEY NOT NULL,
    MK NVARCHAR(50) NULL,
	Email NVARCHAR(50) null
)

INSERT into DangNhap
VALUES('Admin','090103','Lebichvicm2020@gmail.com'),('User','1234567','Lebichvycm2019@gmail.com')

select * from NhanVien
select * from LuongNV
select * from DangNhap


