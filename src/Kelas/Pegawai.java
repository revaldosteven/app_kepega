package Kelas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.Date;

public class Pegawai {

    String dosen_id, dosen_nama, dosen_nidn, dosen_email, dosen_telepon, dosen_alamat, status;
    Date tanggal_masuk;

    private Connection konek;
    private PreparedStatement ps;
    private Statement st;
    private ResultSet rs;
    private String query;

    public Pegawai() throws SQLException {
        Koneksi koneksi = new Koneksi();
        konek = koneksi.konekDB();
    }

    public String getDosen_id() {
        return dosen_id;
    }

    public void setDosen_id(String dosen_id) {
        this.dosen_id = dosen_id;
    }

    public String getDosen_nama() {
        return dosen_nama;
    }

    public void setDosen_nama(String dosen_nama) {
        this.dosen_nama = dosen_nama;
    }

    public String getDosen_nidn() {
        return dosen_nidn;
    }

    public void setDosen_nidn(String dosen_nidn) {
        this.dosen_nidn = dosen_nidn;
    }

    public String getDosen_email() {
        return dosen_email;
    }

    public void setDosen_email(String dosen_email) {
        this.dosen_email = dosen_email;
    }

    public String getDosen_telepon() {
        return dosen_telepon;
    }

    public void setDosen_telepon(String dosen_telepon) {
        this.dosen_telepon = dosen_telepon;
    }

    public String getDosen_alamat() {
        return dosen_alamat;
    }

    public void setDosen_alamat(String dosen_alamat) {
        this.dosen_alamat = dosen_alamat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTanggal_masuk() {
        return tanggal_masuk;
    }

    public void setTanggal_masuk(Date tanggal_masuk) {
        this.tanggal_masuk = tanggal_masuk;
    }

    

    public void tambahPegawai() {
        query = "INSERT INTO dosen VALUES(?,?,?,?,?,?,?,?)";
        try {
            ps = konek.prepareStatement(query);
            ps.setString(1, dosen_id);
            ps.setString(2, dosen_nama);
            ps.setString(3, dosen_nidn);
            ps.setString(4, dosen_email);
            ps.setString(5, dosen_telepon);
            ps.setString(6, dosen_alamat);
            ps.setDate(7, tanggal_masuk);
            ps.setString(8, status);

            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil Ditambahkan");
        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, "Data Gagal  Ditambahkan");
        }
    }

    public ResultSet tampilPegawai() {
        query = "SELECT * FROM dosen";
        try {
            st = konek.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, "Pegawai Gagal Ditampilkan");
        }

        return rs;
    }

    public void hapusPegawai() {
        query = "DELETE FROM dosen WHERE dosen_id = ?";
        try {

            ps = konek.prepareStatement(query);

            ps.setString(1, dosen_id);

            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "Pegawai Berhasil Di Hapus");

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, "Pegawai Gagal Di Hapus");
        }
    }

    public void ubahPegawai() {

        query = "UPDATE dosen "
                + "SET dosen_nama = ?, "
                + "dosen_nidn = ?, "
                + "dosen_email = ?, "
                + "dosen_telepon = ?, "
                + "dosen_alamat = ?, "
                + "masuk_tanggal = ?, "
                + "status = ? "
                + "WHERE dosen_id = ?";

        try {
            ps = konek.prepareStatement(query);
            ps.setString(1, dosen_nama);
            ps.setString(2, dosen_nidn);
            ps.setString(3, dosen_email);
            ps.setString(4, dosen_telepon);
            ps.setString(5, dosen_alamat);
            ps.setDate(6, tanggal_masuk);
            ps.setString(7, status);
            ps.setString(8, dosen_id);

            ps.executeUpdate();
            ps.close();
            JOptionPane.showMessageDialog(null, "Data berhasil Diubah");

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, "Data Gagal Diubah");
        }

    }

    public String autoID() {
        String newID = "DSNITM00001"; // Default jika tidak ada data
        query = "SELECT dosen_id AS ID FROM dosen ORDER BY dosen_id DESC LIMIT 1";
        try {
            st = konek.createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                String lastID = rs.getString("ID");
                if (lastID != null && lastID.startsWith("DSNITM")) {
                    int num = Integer.parseInt(lastID.substring(6)); // Ambil angka setelah "DSNITM"
                    num++; // Increment angka
                    newID = String.format("DSNITM%05d", num); // Format ke DSNITM00001, DSNITM00002, dst.
                }
            }
        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, "Data Gagal Tampil: " + sQLException.getMessage());
        }
        return newID;
    }
    
    public ResultSet cariPegawai(String keyword) {
    query = "SELECT * FROM dosen WHERE "
            + "dosen_id LIKE ? OR "
            + "dosen_nama LIKE ? OR "
            + "dosen_nidn LIKE ? OR "
            + "dosen_email LIKE ? OR "
            + "dosen_telepon LIKE ? OR "
            + "dosen_alamat LIKE ? OR "
            + "masuk_tanggal LIKE ? OR "
            + "status LIKE ?";
    try {
        ps = konek.prepareStatement(query);
        for (int i = 1; i <= 8; i++) { // Ada 8 kolom yang dicari
            ps.setString(i, "%" + keyword + "%"); // Wildcard pencarian untuk semua kolom
        }
        rs = ps.executeQuery();
    } catch (SQLException sQLException) {
        JOptionPane.showMessageDialog(null, "Data Gagal Dicari: " + sQLException.getMessage());
    }
    return rs;
}


}
