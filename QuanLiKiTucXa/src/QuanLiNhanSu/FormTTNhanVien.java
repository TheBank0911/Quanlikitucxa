/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package QuanLiNhanSu;

import Model.NhanVien;
import Model.ThongTinNV;
import static QuanLiNhanSu.FormLuongNhanVien.manv;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LE BICH VY
 */
public class FormTTNhanVien extends javax.swing.JFrame {
Image image ;
    List<ThongTinNV> ds = new ArrayList<>();
    DefaultTableModel model;
    String HinhAnh = null, check = null;
    private int index = -1, n = 0;
    public static final String manv = "[NV]+[0-9]+[0-9]";
    public static final String sdt = "0\\d{9,10}";
    public static final String Cccd = "0\\d{11,12}";
    int ktanh = 0;
    JFileChooser f = new JFileChooser("src\\AnhNhanVien");
    File file = f.getSelectedFile();
    String fileImg;
    long millis = System.currentTimeMillis();
    java.sql.Date day = new java.sql.Date(millis);

    public java.sql.Date day() {
        return day;
    }
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Creates new form FormTTNhanVien
     */
    public FormTTNhanVien(String s) {
        initComponents();
        Tencot();
        ktTaiKhoan(s);
        load();
    }

    public String ktTaiKhoan(String s) {
        if (s.equalsIgnoreCase("admin")) {
            check = s;
        } else {
            check = "user";
        }
        return this.check;
    }

    public void load() {
        try {
            ds.clear();
            String user = "sa";
            String pass = "songlong";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QLNS";
            Connection con = DriverManager.getConnection(url, user, pass);
            String sql = "select * from NhanVien";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String manv = rs.getString("MaNV");
                String hoten = rs.getString("Hoten");
                String sdt = rs.getString("SDT");
                String cccd = rs.getString("CCCD");
                String ns = rs.getString("Ngaysinh");
                String email = rs.getString("Email");
                String nbd = rs.getString("Ngaybd");
                String hinh = rs.getString("Hinh");
                ThongTinNV a = new ThongTinNV(manv, hoten, sdt, cccd, ns, email, nbd, hinh);
                ds.add(a);
            }
            Hienthi();
            con.close();;
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void Tencot() {
        model = (DefaultTableModel) tblTT.getModel();
        String[] cols = new String[]{"MÃ NHÂN VIÊN", "HỌ VÀ TÊN", "SĐT", "CCCD", "NGÀY SINH", "EMAIL", "NGÀY BẮT ĐẦU", "HÌNH"};
        model.setColumnIdentifiers(cols);
    }

    public void Hienthi() {
        model.setRowCount(0);
        for (ThongTinNV nv : ds) {
            Object[] row = new Object[]{nv.getMaNV(), nv.getHoten(), nv.getSDT(), nv.getCCCD(), nv.getNgaysinh(), nv.getEmail(), nv.getNgaybd(), nv.getHinhAnh()};
            model.addRow(row);
        }
    }

    public boolean KTLoi() {
        if (txtMaNV.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên!");
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
        if (txtTen.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhân viên!");
            txtTen.requestFocus();
            return false;
        }
        if (txtSDT.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!");
            txtSDT.requestFocus();
            return false;
        } else {
            Matcher matcher = Pattern.compile(sdt).matcher(txtSDT.getText());
            if (matcher.matches() == false) {
                JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ vui lòng nhập lại!", "Error", JOptionPane.WARNING_MESSAGE);
                txtSDT.requestFocus();
                txtSDT.setBackground(Color.YELLOW);
                return false;
            } else {
                txtSDT.setBackground(Color.WHITE);
            }
        }
        if (txtCCCD.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số CCCD!");
            txtCCCD.requestFocus();
            return false;
        } else {
            Matcher matcher = Pattern.compile(Cccd).matcher(txtCCCD.getText());
            if (matcher.matches() == false) {
                JOptionPane.showMessageDialog(this, "Số CCCD không hợp lệ vui lòng nhập lại!", "Error", JOptionPane.WARNING_MESSAGE);
                txtCCCD.requestFocus();
                txtCCCD.setBackground(Color.YELLOW);
                return false;
            } else {
                txtCCCD.setBackground(Color.WHITE);
            }
        }
        if (txtNgaysinh.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày sinh!");
            txtNgaysinh.requestFocus();
            return false;
        } else {
            try {
                df.setLenient(false);
                df.parse(txtNgaysinh.getText());
                if (day().compareTo(df.parse(txtNgaysinh.getText())) <= 0) {
                    JOptionPane.showMessageDialog(this, "Ngày nhập phải trong hoặc trước ngày " + day(), "ERROR", JOptionPane.WARNING_MESSAGE);
                    txtNgaysinh.setBackground(Color.yellow);
                    txtNgaysinh.requestFocus();
                    return false;
                }

            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày đúng định dạng", "ERROR", JOptionPane.WARNING_MESSAGE);
                txtNgaysinh.setBackground(Color.yellow);
                txtNgaysinh.requestFocus();
                return false;
            }
            txtNgaysinh.setBackground(Color.WHITE);
        }
        if (txtEmail.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Email!");
            txtEmail.requestFocus();
            return false;
        } else {
            String reEmail = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
            if (txtEmail.getText().matches(reEmail)) {
                txtEmail.setBackground(Color.WHITE);
            } else {
                JOptionPane.showMessageDialog(this, "Email không hợp lệ vui lòng nhập lại!", "Error", JOptionPane.WARNING_MESSAGE);
                txtEmail.requestFocus();
                txtEmail.setBackground(Color.YELLOW);
                return false;

            }
        }
        if (txtNgaybd.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày bắt đầu làm việc!");
            txtNgaybd.requestFocus();
            return false;
        } else {
            try {
                df.setLenient(false);
                df.parse(txtNgaybd.getText());
                if (day().compareTo(df.parse(txtNgaybd.getText())) <= 0) {
                    JOptionPane.showMessageDialog(this, "Ngày nhập phải trong hoặc trước ngày " + day(), "ERROR", JOptionPane.WARNING_MESSAGE);
                    txtNgaybd.setBackground(Color.yellow);
                    txtNgaybd.requestFocus();
                    return false;
                }

            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày đúng định dạng", "ERROR", JOptionPane.WARNING_MESSAGE);
                txtNgaybd.setBackground(Color.yellow);
                txtNgaybd.requestFocus();
                return false;
            }
            txtNgaysinh.setBackground(Color.WHITE);
        }
        if (lblanhhienthi.getIcon() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm ảnh!", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public void Lammoi() {
        txtMaNV.setText("");
        txtTen.setText("");
        txtSDT.setText("");
        txtCCCD.setText("");
        txtNgaysinh.setText("");
        txtEmail.setText("");
        txtNgaybd.setText("");
        lblanhhienthi.setIcon(null);
        lblAnh.show();
    }

    public void Dualen(int index) {
        lblAnh.hide();
        ThongTinNV nv = ds.get(index);
        txtMaNV.setText(nv.getMaNV());
        txtTen.setText(nv.getHoten());
        txtSDT.setText(nv.getSDT());
        txtCCCD.setText(nv.getCCCD());
        txtNgaysinh.setText(nv.getNgaysinh());
        txtEmail.setText(nv.getEmail());
        txtNgaybd.setText(nv.getNgaybd());
        HinhAnh = nv.getHinhAnh();
        lblanhhienthi.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/AnhNhanVien/" + nv.getHinhAnh())).getImage().getScaledInstance(220, 211, Image.SCALE_SMOOTH)));
    }

    public void Luu() {
        if (KTLoi() == false) {
            return;
        } else {

            ThongTinNV nva = new ThongTinNV();
            for (ThongTinNV nv : ds) {
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
            if (ktanh > 0) {
                return;
            }
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String user = "sa";
                String pass = "090103";
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String url = "jdbc:sqlserver://localhost:1433;databaseName=QLNS";
                Connection con = DriverManager.getConnection(url, user, pass);
                String sql = "insert into NhanVien values(?,?,?,?,?,?,?,?)";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, txtMaNV.getText());
                st.setString(2, txtTen.getText());
                st.setString(3, txtSDT.getText());
                st.setString(4, txtCCCD.getText());
                st.setString(5, txtNgaysinh.getText());
                st.setString(6, txtEmail.getText());
                st.setString(7, txtNgaybd.getText());
                st.setString(8, HinhAnh);
                st.executeUpdate();
                JOptionPane.showMessageDialog(this, "Lưu thành công!");
                load();
              Lammoi();
                con.close();

            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(this, "Lỗi");
            }
            load();
            Lammoi();
            if (n > 0) {
                System.exit(0);
            }

        }
    }

    public void Capnhat() {
        if (KTLoi() == false) {
            return;
        } else {

            ThongTinNV nva = new ThongTinNV();
            for (ThongTinNV nv : ds) {
                if (nv.getMaNV().equals(txtMaNV.getText())) {
                    int choice = JOptionPane.showConfirmDialog(this, " Bạn có muốn cập nhật?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        try {
                            File deleteImg = new File("src\\AnhNhanVien\\" + nv.getHinhAnh());
                            deleteImg.delete();
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                            String user = "sa";
                            String pass = "090103";
                            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                            String url = "jdbc:sqlserver://localhost:1433;databaseName=QLNS";
                            Connection con = DriverManager.getConnection(url, user, pass);
                            String sql = "update NhanVien set Hoten=?,SDT=?,CCCD=?,Ngaysinh = ?,Email=?, " + "Ngaybd = ?,Hinh = ? where MaNV = ?";
                            PreparedStatement st = con.prepareStatement(sql);
                            st.setString(1, txtTen.getText());
                            st.setString(2, txtSDT.getText());
                            st.setString(3, txtCCCD.getText());
                            st.setString(4, txtNgaysinh.getText());
                            st.setString(5, txtEmail.getText());
                            st.setString(6, txtNgaybd.getText());
                            st.setString(7, HinhAnh);
                            st.setString(8, txtMaNV.getText());
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

    public void Tim() {
        int t = 0;
        for (ThongTinNV nv : ds) {
            if (nv.getMaNV().equalsIgnoreCase(txtMaNV.getText())) {
                txtMaNV.setText(nv.getMaNV());
                txtTen.setText(nv.getHoten());
                txtSDT.setText(nv.getSDT());
                txtCCCD.setText(nv.getCCCD());
                txtNgaysinh.setText(nv.getNgaysinh());
                txtEmail.setText(nv.getEmail());
                txtNgaybd.setText(nv.getNgaybd());
                lblanhhienthi.setText("");
                lblAnh.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/AnhNhanVien/" + nv.getHinhAnh())).getImage().getScaledInstance(220, 211, Image.SCALE_SMOOTH)));
                JOptionPane.showMessageDialog(this, "Đã tìm thấy nhân viên");
                tblTT.setRowSelectionInterval(t, t);
                return;
            }
            t++;
        }
        JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên:");
    }
 public void anh() {
     
     JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        try {
            this.HinhAnh = file.getName();
            Image img = ImageIO.read(file);
            lblAnh.setText("");
            int width = lblAnh.getWidth();
            int height = lblAnh.getHeight();
            lblanhhienthi.setIcon(new ImageIcon(img.getScaledInstance(width, height, 0)));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ôi không!\nBạn vừa hủy thao tác mất rồi!");
            return;
        }
      try {
            this.image = ImageIO.read(file);
            BufferedImage imgCopy = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = imgCopy.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            HinhAnh = txtMaNV.getText();
            try {
                ImageIO.write(imgCopy, "PNG", new File("src\\AnhNhanVien\\" + HinhAnh + ".PNG"));
                this.HinhAnh=this.HinhAnh+".PNG";
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi!");
                return;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi!");
            return;
        }
 }
        
 //}
//    public void anhmoi() {
//        try {
//            for (ThongTinNV nv : ds) {
//                if (nv.getHinhAnh().equalsIgnoreCase(HinhAnh)) {
//                    JOptionPane.showMessageDialog(this, "Ảnh nhân viên này đã tồn tại!");
//                    lblanhhienthi.setIcon(null);
//                    lblAnh.show();
//                    ktanh++;
//                    return;
//                }
//            }
//            fileImg = file.getParent();
//            File sourceFolder = new File(fileImg + "\\" + HinhAnh);
//            File targetFolder = new File("src\\AnhNhanVien\\" + HinhAnh);
//            copyFolder(sourceFolder, targetFolder);
//            return;
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
//    }
//
//    public void anh() {
//        try {
//            f.showOpenDialog(null);
//            
//            file = f.getSelectedFile();
//            Image img = ImageIO.read(file);
//            HinhAnh = file.getName();
//            int width = lblAnh.getWidth();
//            int height = lblAnh.getHeight();
//            lblanhhienthi.setIcon(new ImageIcon(img.getScaledInstance(width, height, 0)));
//        } catch (Exception ex) {
//            System.out.println("Lỗi");
//        }
//    }

    private static void copyFolder(File sourceFolder, File targetFolder)
            throws IOException {
        InputStream in = new FileInputStream(sourceFolder);
        OutputStream out = new FileOutputStream(targetFolder);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.close();
    
    
 }
        
    public void Xoa() {
        for (ThongTinNV nv : ds) {
            if (nv.getMaNV().equals(txtMaNV.getText())) {
                int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa nhân viên?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    String sql = null;
                    try {
                        PreparedStatement st = null;
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        String user = "sa";
                        String pass = "090103";
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        String url = "jdbc:sqlserver://localhost:1433;databaseName=QLNS";
                        Connection con = DriverManager.getConnection(url, user, pass);
                        sql = "delete from LuongNV where MaNV = ?";
                        st = con.prepareStatement(sql);
                        st.setString(1, txtMaNV.getText());
                        st.executeUpdate();

                        sql = "delete from NhanVien where MaNV = ?";
                        st = con.prepareStatement(sql);
                        st.setString(1, txtMaNV.getText());
                        st.executeUpdate();
                        File file = new File("src\\AnhNhanVien\\" + nv.getHinhAnh());
                        file.delete();

                        JOptionPane.showMessageDialog(this, "Xóa thành công!");
                        load();
                        con.close();
                        Lammoi();
                    } catch (Exception e) {
                        System.out.println(e);
                        JOptionPane.showMessageDialog(this, "Lỗi");
                    }
                    return;
                } else {
                    return;
                }

            }

        }
        JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
        txtMaNV.requestFocus();
    }

    public void Thoat() {
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn thoát chương trình?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        } else {
            return;
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtTen = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtCCCD = new javax.swing.JTextField();
        txtNgaysinh = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtNgaybd = new javax.swing.JTextField();
        btnLammoi = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        btnCapnhat = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTT = new javax.swing.JTable();
        btnTim = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        Home = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblAnh = new javax.swing.JLabel();
        lblanhhienthi = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Quản Lý Thông Tin Nhân Viên");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Mã nhân viên");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Họ và tên");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Số điện thoại");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Số CCCD");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Ngày sinh");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Email");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Ngày bắt đầu làm");

        btnLammoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/Add.png"))); // NOI18N
        btnLammoi.setText("Làm mới");
        btnLammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLammoiActionPerformed(evt);
            }
        });

        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/Delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/Save.png"))); // NOI18N
        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnCapnhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/Edit.png"))); // NOI18N
        btnCapnhat.setText("Cập nhật");
        btnCapnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapnhatActionPerformed(evt);
            }
        });

        tblTT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblTT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTTMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblTT);

        btnTim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/Search.png"))); // NOI18N
        btnTim.setText("Tìm");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        btnThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/Exit.png"))); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        Home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/Home.png"))); // NOI18N
        Home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HomeActionPerformed(evt);
            }
        });

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAnh.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HinhAnh/addnv.png"))); // NOI18N
        lblAnh.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.add(lblAnh, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        lblanhhienthi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblanhhienthiMouseClicked(evt);
            }
        });
        jPanel2.add(lblanhhienthi, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 210));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(266, 266, 266))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Home, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaNV)
                            .addComponent(txtTen, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(txtSDT)
                            .addComponent(txtCCCD)
                            .addComponent(txtNgaysinh)
                            .addComponent(txtEmail)
                            .addComponent(txtNgaybd))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnCapnhat)
                        .addGap(34, 34, 34)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(Home))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNgaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNgaybd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThoat)
                    .addComponent(btnTim)
                    .addComponent(btnLammoi)
                    .addComponent(btnLuu)
                    .addComponent(btnCapnhat)
                    .addComponent(btnXoa))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLammoiActionPerformed
        Lammoi();
    }//GEN-LAST:event_btnLammoiActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        Luu();
    }//GEN-LAST:event_btnLuuActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        Xoa();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnCapnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapnhatActionPerformed
        Capnhat();
    }//GEN-LAST:event_btnCapnhatActionPerformed

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        Tim();
    }//GEN-LAST:event_btnTimActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        Thoat();
    }//GEN-LAST:event_btnThoatActionPerformed

    private void tblTTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTTMouseClicked
        index = tblTT.getSelectedRow();
        Dualen(index);
    }//GEN-LAST:event_tblTTMouseClicked

    private void HomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HomeActionPerformed
        FormMain frame = new FormMain(check);
        frame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_HomeActionPerformed

    private void lblanhhienthiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblanhhienthiMouseClicked
        anh();
        lblAnh.hide();
    }//GEN-LAST:event_lblanhhienthiMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (txtMaNV.getText().equals("") && txtTen.getText().equals("") && txtSDT.getText().equals("") && txtCCCD.getText().equals("") && txtNgaysinh.getText().equals("") && txtEmail.getText().equals("") && txtNgaybd.getText().equals("") && lblanhhienthi.getIcon() == null) {
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
            java.util.logging.Logger.getLogger(FormTTNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormTTNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormTTNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormTTNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            //@Override
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Home;
    private javax.swing.JButton btnCapnhat;
    private javax.swing.JButton btnLammoi;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblanhhienthi;
    private javax.swing.JTable tblTT;
    private javax.swing.JTextField txtCCCD;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNgaybd;
    private javax.swing.JTextField txtNgaysinh;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
