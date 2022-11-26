/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package QuanLiNhanSu;

import Model.NhanVien;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LE BICH VY
 */
public class FormLuongNhanVien extends javax.swing.JFrame {

    List<NhanVien> ds = new ArrayList<>();
    DefaultTableModel model;
    private int index = -1,n = 0;
    public static final String manv = "[NV]+[0-9]+[0-9]";
    String role = null;

    /**
     * Creates new form FormNhanVien
     */
    public FormLuongNhanVien(String s) {
        initComponents();
        Cot();
        load();
        // Dulieu();
        txtTongTien.setEditable(false);
        txtThuong.setEnabled(false);
        ktTaiKhoan(s);

    }

    public void load() {
        try {
            String user = "sa";
            String pass = "090103";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QLNS";
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement st = con.createStatement();
            String sql = "select NhanVien.MaNV,NhanVien.Hoten,Luong,Thuong,TongTien from LuongNV inner join NhanVien on LuongNV.MaNV = NhanVien.MaNV ";
            //NhanVien nv = new NhanVien();
            // tblTT.setRowCount(0);
            ds.clear();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String manv = rs.getString("MaNV");
                String hoten = rs.getString("Hoten");
                double luong = Double.parseDouble(rs.getString("Luong"));
                double thuong = Double.parseDouble(rs.getString("Thuong"));
                double tien = Double.parseDouble(rs.getString("TongTien"));
                NhanVien a = new NhanVien(manv, hoten, luong, thuong, tien);
                ds.add(a);
            }
            Hienthi();
            con.close();;
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private FormLuongNhanVien() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void Hienthi() {
        model.setRowCount(0);
        for (NhanVien nv : ds) {
            Object[] row = new Object[]{nv.getMaNV(), nv.getHoTen(), nv.getLuong(), nv.getThuong(), nv.getTongTien()};
            model.addRow(row);

        }
    }
//
//    public void Dulieu() {
//        ds.add(new NhanVien("NV001", "Lê Bích Vi", 6000000, 0.1, 6600000));
//        ds.add(new NhanVien("NV002", "Nguyễn Khánh Đan", 5000000, 0.4, 7000000));
//        ds.add(new NhanVien("NV003", "Trần Hữu Đang", 1000000, 0.1, 1100000));
//        ds.add(new NhanVien("NV004", "Phùng Quốc Vinh", 7000000, 0.2, 8400000));
//        ds.add(new NhanVien("NV005", "Đoàn Hiệp Sỹ", 4000000, 0.1, 4400000));
//        Hienthi();
//    }

    public void Cot() {
        model = (DefaultTableModel) tblLuong.getModel();
        String[] cols = new String[]{"MÃ NHÂN VIÊN", "HỌ VÀ TÊN", "LƯƠNG", "THƯỞNG", "TỔNG TIỀN"};
        model.setColumnIdentifiers(cols);
    }

    public void Lammoi() {
        txtMNVTK.setText("");
        txtMaNV.setText("");
        txtHoTen.setText("");
        txtLuong.setText("");
        txtThuong.setText("");
        txtTongTien.setText("");
        txtMaNV.requestFocus();
    }

    public void Tim() {
        int t = 0;
        for (NhanVien nv : ds) {
            if (nv.getMaNV().equals(txtMNVTK.getText())) {
                txtMaNV.setText(nv.getMaNV());
                txtHoTen.setText(nv.getHoTen());
                txtLuong.setText(String.valueOf(nv.getLuong()));
                txtThuong.setText(String.valueOf(nv.getThuong()));
                txtTongTien.setText(String.valueOf(nv.getTongTien()));
                JOptionPane.showMessageDialog(this, "Đã tìm thấy nhân viên");
                tblLuong.setRowSelectionInterval(t, t);
                return;
            }

            t++;
        }
        JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên:");
    }

    public void Xoa() {
        for (NhanVien nv : ds) {
            if (nv.getMaNV().equalsIgnoreCase(txtMNVTK.getText()) || nv.getMaNV().equalsIgnoreCase(txtMaNV.getText())) {
                int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa nhân viên?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    try {
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        String user = "sa";
                        String pass = "090103";
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        String url = "jdbc:sqlserver://localhost:1433;databaseName=QLNS";
                        Connection con = DriverManager.getConnection(url, user, pass);
                        String sql = "delete from LuongNV where MaNV = ?";
                        PreparedStatement st = con.prepareStatement(sql);
                        st.setString(1, txtMaNV.getText());
                        st.setString(1, txtMNVTK.getText());
                        st.execute();
                        JOptionPane.showMessageDialog(this, "Xóa thành công!");
                        load();
                        con.close();
                        Lammoi();
                    } catch (Exception e) {
                        System.out.println(e);
                        JOptionPane.showMessageDialog(this, "Lỗi");
                    }
                    return;
                }

            }

        }
        JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
        txtMNVTK.requestFocus();
    }

    public boolean Ktloi() {
        if (txtMaNV.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên");
            txtMaNV.requestFocus();
            return false;
        } else {
            Matcher matcher = Pattern.compile(manv).matcher(txtMaNV.getText());
            if (matcher.matches() == false) {
                JOptionPane.showMessageDialog(this, "Mã nhân viên(NV...) không hợp lệ vui lòng nhập lại!", "Error", JOptionPane.WARNING_MESSAGE);
                txtMaNV.requestFocus();
                txtMaNV.setBackground(Color.YELLOW);
                return false;
            } else {
                txtMaNV.setBackground(Color.WHITE);
            }
        }
        if (txtHoTen.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên nhân viên");
            txtHoTen.requestFocus();
            return false;
        }
        if (txtLuong.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lương nhân viên");
            txtLuong.requestFocus();
            return false;
        } else {
            txtThuong.setEnabled(true);
            try {
                double sp = Double.parseDouble(txtLuong.getText());

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Không nhập chữ!");
                return false;

            }
        }
        if (txtThuong.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thưởng của nhân viên");
            txtThuong.requestFocus();
            return false;
        } else {
            try {
                double sp = Double.parseDouble(txtThuong.getText());

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra!");
                return false;
            }
        }
        return true;

    }

    public void Capnhat() {
        if (Ktloi() == false) {
            return;
        } else {
            NhanVien nva = new NhanVien();
            for (NhanVien nv : ds) {
                if (nv.getMaNV().equals(txtMaNV.getText())) {
                    int choice = JOptionPane.showConfirmDialog(this, " Bạn có muốn cập nhật?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        try {
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                            String user = "sa";
                            String pass = "090103";
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                            String url = "jdbc:sqlserver://localhost:1433;databaseName=QLNS";
                            Connection con = DriverManager.getConnection(url, user, pass);
                            String sql = "update LuongNV set Luong = ?,Thuong = ?, TongTien=? where MaNV = ?";
                            PreparedStatement st = con.prepareStatement(sql);
                            st.setString(1, txtLuong.getText());
                            st.setString(2, txtThuong.getText());
                            st.setString(3, txtTongTien.getText());
                            st.setString(4, txtMaNV.getText());
                            st.executeUpdate();
                            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                            con.close();
                            load();
                            Lammoi();
                        } catch (Exception e) {
                            System.out.println(e);
                            JOptionPane.showMessageDialog(this, "Lỗi");
                        }
                        return;
                    } else {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên khác!");
                        txtMaNV.requestFocus();
                        return;

                    }
                }

            }
            JOptionPane.showMessageDialog(this, "Mã nhân viên chưa tồn tại!");
        }
    }

    public double tongtien(String luongindex, String thuongindex) {
        double luong, thuong;
        if (luongindex.equals("0")) {
            luong = 0;
        } else {
            luong = Double.valueOf(luongindex);
        }
        //
        if (thuongindex.equals("0")) {
            thuong = 0;
        } else {
            thuong = Double.valueOf(thuongindex);
        }
        double Tongtien = luong + (thuong * luong);
        txtTongTien.setText(String.valueOf(Tongtien));

        return Tongtien;

    }

    public String getLuong() {
        String luong = txtLuong.getText();
        if (luong.equals("")) {
            luong = "0";
        } else {
            luong = txtLuong.getText();

        }
        return luong;
    }

    public String getThuong() {
        String thuong = txtThuong.getText();
        if (thuong.equals("")) {
            thuong = "0";
        } else {
            thuong = txtThuong.getText();
        }
        return thuong;
    }

    public void Luu() {
        if (Ktloi() == false) {
            return;
        } else {
            NhanVien nva = new NhanVien();
            for (NhanVien nv : ds) {
                if (nv.getMaNV().equals(txtMaNV.getText())) {
                    int choice = JOptionPane.showConfirmDialog(this, "Mã nhân viên đã tồn tại bạn có muốn cập nhật?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        Capnhat();
                        return;
                    } else {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên khác!");
                        txtMaNV.requestFocus();
                        return;

                    }
                }
            }
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String user = "sa";
                String pass = "090103";
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url = "jdbc:sqlserver://localhost:1433;databaseName=QLNS";
                Connection con = DriverManager.getConnection(url, user, pass);
                String sql = "insert into LuongNV(MaNV,Luong,Thuong,TongTien) values(?,?,?,?)";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, txtMaNV.getText());
//                st.setString(2, txtHoTen.getText());
                st.setString(2, txtLuong.getText());
                st.setString(3, txtThuong.getText());
                st.setString(4, txtTongTien.getText());
                st.executeUpdate();
                JOptionPane.showMessageDialog(this, "Lưu thành công!");
                con.close();
                load();
                Lammoi();
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(this, "Lỗi");
            }
        }
    }

    public void Thoat() {
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn thoát chương trình?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        } else {
            return;
        }
    }

    public void Dualen(int index) {
        NhanVien nv = ds.get(index);
        txtMaNV.setText(nv.getMaNV());
        txtHoTen.setText(nv.getHoTen());
        txtLuong.setText(String.valueOf(nv.getLuong()));
        txtThuong.setText(String.valueOf(nv.getThuong()));
        txtTongTien.setText(String.valueOf(nv.getTongTien()));
    }

    private void Capnhatvitri() {
        tblLuong.setRowSelectionInterval(index, index);
        Dualen(index);
    }

    public void Dautien() {
        if (ds.size() != 0) {
            index = 0;
            Capnhatvitri();
        }
    }

    public void Cuoi() {
        if (ds.size() != 0) {
            index = ds.size() - 1;
            Capnhatvitri();
        }
    }

    public void Lui() {
        if (ds.size() != 0) {
            if (index == 0) {
                Cuoi();
            } else {
                index--;
            }

            Capnhatvitri();
        }
    }

    public void Tien() {
        if (ds.size() != 0) {
            if (index == ds.size() - 1) {
                Dautien();
            } else {
                index++;
            }
            Capnhatvitri();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMNVTK = new javax.swing.JTextField();
        btnTim = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        txtLuong = new javax.swing.JTextField();
        txtThuong = new javax.swing.JTextField();
        txtTongTien = new javax.swing.JTextField();
        btnnew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLuong = new javax.swing.JTable();
        btnfirst = new javax.swing.JButton();
        btnPre = new javax.swing.JButton();
        btnnext = new javax.swing.JButton();
        btnlast = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Quản Lý Lương Nhân Viên");

        jPanel2.setBackground(new java.awt.Color(0, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm Kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Mã nhân viên");

        btnTim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/Search.png"))); // NOI18N
        btnTim.setText("Tìm ");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtMNVTK, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTim)
                .addGap(56, 56, 56))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMNVTK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(btnTim))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setText("Họ và tên");

        jLabel4.setText("Lương");

        jLabel5.setText("Hệ số tiền thưởng");

        jLabel6.setText("Tổng tiền");

        jLabel7.setText("Mã nhân viên");

        txtLuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtLuongMouseClicked(evt);
            }
        });
        txtLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLuongActionPerformed(evt);
            }
        });
        txtLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLuongKeyPressed(evt);
            }
        });

        txtThuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThuongActionPerformed(evt);
            }
        });
        txtThuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtThuongKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaNV)
                    .addComponent(txtHoTen)
                    .addComponent(txtLuong)
                    .addComponent(txtThuong)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtThuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        btnnew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/Add.png"))); // NOI18N
        btnnew.setText("Làm mới");
        btnnew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnewActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/Save.png"))); // NOI18N
        btnSave.setText("Lưu");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/Delete.png"))); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/Edit.png"))); // NOI18N
        btnUpdate.setText("Cập nhật");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/Exit.png"))); // NOI18N
        btnExit.setText("Thoát");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        tblLuong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblLuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLuongMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblLuong);

        btnfirst.setText("<|");
        btnfirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfirstActionPerformed(evt);
            }
        });

        btnPre.setText("<<");
        btnPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreActionPerformed(evt);
            }
        });

        btnnext.setText(">>");
        btnnext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnextActionPerformed(evt);
            }
        });

        btnlast.setText("|>");
        btnlast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlastActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/Home.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(129, 129, 129)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnfirst, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnPre, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(44, 44, 44)
                                        .addComponent(btnnext, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(41, 41, 41)
                                        .addComponent(btnlast, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnExit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnSave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnnew, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(14, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnfirst)
                            .addComponent(btnPre)
                            .addComponent(btnnext)
                            .addComponent(btnlast))
                        .addGap(29, 29, 29))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnnew)
                        .addGap(18, 18, 18)
                        .addComponent(btnSave)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(btnExit)
                        .addGap(35, 35, 35)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnnewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewActionPerformed
        Lammoi();
    }//GEN-LAST:event_btnnewActionPerformed

    private void btnfirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfirstActionPerformed
        Dautien();
    }//GEN-LAST:event_btnfirstActionPerformed

    private void btnPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreActionPerformed
        Lui();
    }//GEN-LAST:event_btnPreActionPerformed

    private void btnnextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnextActionPerformed
        Tien();
    }//GEN-LAST:event_btnnextActionPerformed

    private void btnlastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlastActionPerformed
        Cuoi();
    }//GEN-LAST:event_btnlastActionPerformed

    private void tblLuongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLuongMouseClicked
        index = tblLuong.getSelectedRow();
        Dualen(index);
    }//GEN-LAST:event_tblLuongMouseClicked

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        Tim();
    }//GEN-LAST:event_btnTimActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        Xoa();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        Capnhat();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        Luu();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        Thoat();
    }//GEN-LAST:event_btnExitActionPerformed

    private void txtLuongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLuongMouseClicked

    }//GEN-LAST:event_txtLuongMouseClicked

    private void txtLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLuongActionPerformed
        String luong = txtLuong.getText();
        //System.out.println(luong);
        String thuong = txtThuong.getText();

        if (luong.equals("")) {
            luong = "0";

        } else {
            //luong = Double.valueOf(txtLuong.getText());
            //System.out.println(luong);
            luong = txtLuong.getText();

        }
        //
        if (thuong.equals("")) {
            thuong = "0";
        } else {
            //thuong = Double.valueOf(thuong);
            //System.out.println(thuong);
            thuong = txtThuong.getText();
        }
        tongtien(luong, thuong);
    }//GEN-LAST:event_txtLuongActionPerformed

    private void txtThuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThuongActionPerformed
        String luong = txtLuong.getText();
        //System.out.println(luong);
        String thuong = txtThuong.getText();

        if (luong.equals("")) {
            luong = "0";
        } else {
            //luong = Double.valueOf(txtLuong.getText());
            //System.out.println(luong);
            luong = txtLuong.getText();
        }
        //
        if (thuong.equals("")) {
            thuong = "0";
        } else {
            //thuong = Double.valueOf(thuong);
            //System.out.println(thuong);
            thuong = txtThuong.getText();
        }
        tongtien(luong, thuong);
    }//GEN-LAST:event_txtThuongActionPerformed

    private void txtLuongKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLuongKeyPressed
        String luong = txtLuong.getText();;
        String thuong = txtThuong.getText();

        if (luong.equals("")) {
            luong = "0";
        } else {
            luong = txtLuong.getText();
        }
        if (thuong.equals("")) {
            thuong = "0";
        } else {
            thuong = txtThuong.getText();
        }
        if (evt.getKeyCode() == KeyEvent.VK_1) {
            luong = getLuong();
            thuong = getThuong();
            luong = luong + "1";
            tongtien(luong, thuong);
        }
        if (evt.getKeyCode() == KeyEvent.VK_2) {
            luong = getLuong();
            thuong = getThuong();
            luong = luong + "2";
            tongtien(luong, thuong);
        }

        if (evt.getKeyCode() == KeyEvent.VK_3) {
            luong = getLuong();
            thuong = getThuong();
            luong = luong + "3";
            tongtien(luong, thuong);
        }

        if (evt.getKeyCode() == KeyEvent.VK_4) {
            luong = getLuong();
            thuong = getThuong();
            luong = luong + "4";
            tongtien(luong, thuong);
        }

        if (evt.getKeyCode() == KeyEvent.VK_5) {
            luong = getLuong();
            thuong = getThuong();
            luong = luong + "5";
            tongtien(luong, thuong);
        }
        if (evt.getKeyCode() == KeyEvent.VK_6) {
            luong = getLuong();
            thuong = getThuong();
            luong = luong + "6";
            tongtien(luong, thuong);
        }
        if (evt.getKeyCode() == KeyEvent.VK_7) {
            luong = getLuong();
            thuong = getThuong();
            luong = luong + "7";
            tongtien(luong, thuong);
        }
        if (evt.getKeyCode() == KeyEvent.VK_8) {
            luong = getLuong();
            thuong = getThuong();
            luong = luong + "8";
            tongtien(luong, thuong);

        }
        if (evt.getKeyCode() == KeyEvent.VK_9) {
            luong = getLuong();
            thuong = getThuong();
            luong = luong + "9";
            tongtien(luong, thuong);

        }
        if (evt.getKeyCode() == KeyEvent.VK_0) {
            luong = getLuong();
            thuong = getThuong();
            luong = luong + "0";
            tongtien(luong, thuong);

        }
        if (txtLuong.getText().equalsIgnoreCase("")) {
            txtThuong.setEnabled(true);
        }
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            luong = getLuong();
            thuong = getThuong();

            if (luong.length() > 0) {
                luong = luong.substring(0, luong.length() - 1);

                if (luong.length() == 0) {
                    luong = "0";
                    txtThuong.setEnabled(false);
                    tongtien(luong, thuong);
                } else {
                    tongtien(luong, thuong);
                }
            }

        }

    }//GEN-LAST:event_txtLuongKeyPressed

    private void txtThuongKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtThuongKeyPressed

        String luong = txtLuong.getText();
        String thuong = txtThuong.getText();

        if (evt.getKeyCode() == KeyEvent.VK_1) {
            luong = getLuong();
            thuong = getThuong();
            thuong = thuong + "1";
            tongtien(luong, thuong);
        }
        if (evt.getKeyCode() == KeyEvent.VK_2) {
            luong = getLuong();
            thuong = getThuong();
            thuong = thuong + "2";
            tongtien(luong, thuong);
        }

        if (evt.getKeyCode() == KeyEvent.VK_3) {
            luong = getLuong();
            thuong = getThuong();
            thuong = thuong + "3";
            tongtien(luong, thuong);
        }

        if (evt.getKeyCode() == KeyEvent.VK_4) {
            luong = getLuong();
            thuong = getThuong();
            thuong = thuong + "4";
            tongtien(luong, thuong);
        }

        if (evt.getKeyCode() == KeyEvent.VK_5) {
            luong = getLuong();
            thuong = getThuong();
            thuong = thuong + "5";
            tongtien(luong, thuong);
        }
        if (evt.getKeyCode() == KeyEvent.VK_6) {
            thuong = thuong + "6";
            tongtien(luong, thuong);
        }
        if (evt.getKeyCode() == KeyEvent.VK_7) {
            luong = getLuong();
            thuong = getThuong();
            thuong = thuong + "7";
            tongtien(luong, thuong);
        }
        if (evt.getKeyCode() == KeyEvent.VK_8) {
            luong = getLuong();
            thuong = getThuong();
            thuong = thuong + "8";
            tongtien(luong, thuong);

        }
        if (evt.getKeyCode() == KeyEvent.VK_9) {
            luong = getLuong();
            thuong = getThuong();
            thuong = thuong + "9";
            tongtien(luong, thuong);

        }
        if (evt.getKeyCode() == KeyEvent.VK_0) {
            luong = getLuong();
            thuong = getThuong();
            thuong = thuong + "0";
            tongtien(luong, thuong);
        }
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            if (thuong.length() > 0) {
                thuong = thuong.substring(0, thuong.length() - 1);

                if (thuong.length() == 0) {
                    thuong = "0";
                    //t.setEnabled(false);
                    tongtien(luong, thuong);
                } else {
                    tongtien(luong, thuong);
                }
            }
        }
    }//GEN-LAST:event_txtThuongKeyPressed

    public String ktTaiKhoan(String kt) {
        if (kt.equalsIgnoreCase("admin")) {
            role = kt;

        } else {
            role = "user";
        }
        return this.role;

    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        FormMain frame = new FormMain(ktTaiKhoan(role));
        frame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (txtMaNV.getText().equals("") && txtHoTen.getText().equals("") && txtLuong.getText().equals("") && txtThuong.getText().equals("") && txtTongTien.getText().equals("")) {
            System.exit(0);
        } else {
            int messageType = JOptionPane.QUESTION_MESSAGE;
            String[] option = {"Don't Save", "Save", "Cancel"};
            int code = JOptionPane.showOptionDialog(this, "Bạn có muốn lưu dữ liệu?", "", 0, messageType, null, option, "Save");
            if (code == 0) {
                System.exit(0);
            } else if (code == 1) {
                n++;
                setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
                Luu();
            } else {
                setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
            }
        }

    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormLuongNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormLuongNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormLuongNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormLuongNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnPre;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnfirst;
    private javax.swing.JButton btnlast;
    private javax.swing.JButton btnnew;
    private javax.swing.JButton btnnext;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblLuong;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtLuong;
    private javax.swing.JTextField txtMNVTK;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtThuong;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
