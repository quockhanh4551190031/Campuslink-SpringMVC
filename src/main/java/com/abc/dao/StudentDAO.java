package com.abc.dao;

import com.abc.models.*;
import com.abc.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class StudentDAO {
	private final DatabaseConnection dbConnection = new DatabaseConnection();
	
	public void addStudent(Student student, Graduation graduation) throws SQLException {
	    Connection conn = null;
	    try {
	        conn = dbConnection.getConnection();
	        conn.setAutoCommit(false);

	        // Thêm vào bảng SINHVIEN
	        String sqlSinhVien = "INSERT INTO SINHVIEN (SoCMND, HoTen, Email, SoDT, DiaChi) VALUES (?, ?, ?, ?, ?)";
	        try (PreparedStatement stmt = conn.prepareStatement(sqlSinhVien)) {
	            stmt.setLong(1, student.getSoCMND());
	            stmt.setString(2, student.getHoTen());
	            stmt.setString(3, student.getEmail());
	            stmt.setLong(4, student.getSoDT());
	            stmt.setString(5, student.getDiaChi());
	            stmt.executeUpdate();
	        }

	        // Chuyển đổi ngayTN từ String sang java.sql.Date
	        java.sql.Date sqlNgayTN = null;
	        if (graduation.getNgayTN() != null && !graduation.getNgayTN().trim().isEmpty()) {
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            sdf.setLenient(false);
	            Date parsedDate = sdf.parse(graduation.getNgayTN());
	            sqlNgayTN = new java.sql.Date(parsedDate.getTime());
	        }

	        // Thêm vào bảng TOT_NGHIEP
	        String sqlTotNghiep = "INSERT INTO TOT_NGHIEP (SoCMND, MaTruong, MaNganh, HeTN, NgayTN, LoaiTN) VALUES (?, ?, ?, ?, ?, ?)";
	        try (PreparedStatement stmt = conn.prepareStatement(sqlTotNghiep)) {
	            stmt.setLong(1, student.getSoCMND());
	            stmt.setInt(2, graduation.getMaTruong());
	            stmt.setInt(3, graduation.getMaNganh());
	            stmt.setString(4, graduation.getHeTN());
	            stmt.setDate(5, sqlNgayTN);
	            stmt.setString(6, graduation.getLoaiTN());
	            stmt.executeUpdate();
	        }

	        conn.commit();
	    } catch (ParseException e) {
	        throw new SQLException("Ngày tốt nghiệp không hợp lệ: " + e.getMessage(), e);
	    } catch (SQLException e) {
	        if (conn != null) {
	            try {
	                conn.rollback();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	        throw e;
	    } finally {
	        if (conn != null) {
	            try {
	                conn.setAutoCommit(true);
	                conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}
	
	public void updateStudent(Student student, Graduation graduation) throws SQLException {
	    Connection conn = null;
	    try {
	        conn = dbConnection.getConnection();
	        conn.setAutoCommit(false);

	        // Cập nhật bảng SINHVIEN
	        String sqlSinhVien = "UPDATE SINHVIEN SET HoTen = ?, Email = ?, SoDT = ?, DiaChi = ? WHERE SoCMND = ?";
	        try (PreparedStatement stmt = conn.prepareStatement(sqlSinhVien)) {
	            stmt.setString(1, student.getHoTen());
	            stmt.setString(2, student.getEmail());
	            stmt.setLong(3, student.getSoDT());
	            stmt.setString(4, student.getDiaChi());
	            stmt.setLong(5, student.getSoCMND());
	            stmt.executeUpdate();
	        }

	        // Chuyển đổi ngayTN từ String sang java.sql.Date
	        java.sql.Date sqlNgayTN = null;
	        if (graduation.getNgayTN() != null && !graduation.getNgayTN().trim().isEmpty()) {
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            sdf.setLenient(false);
	            Date parsedDate = sdf.parse(graduation.getNgayTN());
	            sqlNgayTN = new java.sql.Date(parsedDate.getTime());
	        }

	        // Cập nhật bảng TOT_NGHIEP
	        String sqlTotNghiep = "UPDATE TOT_NGHIEP SET HeTN = ?, NgayTN = ?, LoaiTN = ? WHERE SoCMND = ? AND MaTruong = ? AND MaNganh = ?";
	        try (PreparedStatement stmt = conn.prepareStatement(sqlTotNghiep)) {
	            stmt.setString(1, graduation.getHeTN());
	            stmt.setDate(2, sqlNgayTN);
	            stmt.setString(3, graduation.getLoaiTN());
	            stmt.setLong(4, student.getSoCMND());
	            stmt.setInt(5, graduation.getMaTruong());
	            stmt.setInt(6, graduation.getMaNganh());
	            stmt.executeUpdate();
	        }

	        conn.commit();
	    } catch (ParseException e) {
	        throw new SQLException("Ngày tốt nghiệp không hợp lệ: " + e.getMessage(), e);
	    } catch (SQLException e) {
	        if (conn != null) {
	            try {
	                conn.rollback();
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	        throw e;
	    } finally {
	        if (conn != null) {
	            try {
	                conn.setAutoCommit(true);
	                conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}
	
	public void deleteStudent(long soCMND) throws SQLException {
        String deleteGraduationSQL = "DELETE FROM TOT_NGHIEP WHERE SoCMND = ?";
        String deleteStudentSQL = "DELETE FROM SINHVIEN WHERE SoCMND = ?";

        try (Connection conn = dbConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                PreparedStatement gradStmt = conn.prepareStatement(deleteGraduationSQL);
                gradStmt.setLong(1, soCMND);
                gradStmt.executeUpdate();

                PreparedStatement studentStmt = conn.prepareStatement(deleteStudentSQL);
                studentStmt.setLong(1, soCMND);
                studentStmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
	
	public Student getStudentById(long soCMND) throws SQLException {
	    Student student = null;
	    String sql = "SELECT sv.soCMND, sv.hoTen, sv.email, sv.soDT, sv.diaChi, " +
	                "tn.maTruong, tn.maNganh, tn.heTN, tn.ngayTN, tn.loaiTN " +
	                "FROM SINHVIEN sv " +
	                "LEFT JOIN TOT_NGHIEP tn ON sv.soCMND = tn.soCMND " +
	                "WHERE sv.soCMND = ?";
	    try (Connection conn = dbConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setLong(1, soCMND);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                student = new Student();
	                student.setSoCMND(rs.getLong("soCMND"));
	                student.setHoTen(rs.getString("hoTen"));
	                student.setEmail(rs.getString("email"));
	                student.setSoDT(rs.getLong("soDT"));
	                student.setDiaChi(rs.getString("diaChi"));

	                Graduation graduation = new Graduation();
	                graduation.setSoCMND(rs.getLong("soCMND"));
	                graduation.setMaTruong(rs.getInt("maTruong"));
	                graduation.setMaNganh(rs.getInt("maNganh"));
	                graduation.setHeTN(rs.getString("heTN"));
	                java.sql.Date sqlDate = rs.getDate("ngayTN");
	                graduation.setNgayTN(sqlDate != null ? sqlDate.toString() : null);
	                graduation.setLoaiTN(rs.getString("loaiTN"));
	                student.setGraduation(graduation);
	            }
	        }
	    }
	    return student;
	}
	
	public List<Graduation> getGraduationsById(long soCMND) throws SQLException {
        List<Graduation> graduations = new ArrayList<>();
        String sql = "SELECT * FROM TOT_NGHIEP WHERE SoCMND = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, soCMND);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    graduations.add(new Graduation(
                            rs.getLong("SoCMND"),
                            rs.getInt("MaTruong"),
                            rs.getInt("MaNganh"),
                            rs.getString("HeTN"),
                            rs.getString("NgayTN"),
                            rs.getString("LoaiTN")
                    ));
                }
            }
        }
        return graduations;
    }
	
	public Graduation getGraduationByMaTotNghiep(int maTotNghiep) throws SQLException {
        String sql = "SELECT * FROM TOT_NGHIEP WHERE MaTotNghiep = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maTotNghiep);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Graduation(
                            rs.getLong("SoCMND"),
                            rs.getInt("MaTruong"),
                            rs.getInt("MaNganh"),
                            rs.getString("HeTN"),
                            rs.getString("NgayTN"),
                            rs.getString("LoaiTN")
                    );
                }
            }
        }
        return null;
    }
	
	public List<Object[]> getAllStudents() throws SQLException {
	    List<Object[]> students = new ArrayList<>();
	    String sql = "SELECT sv.soCMND, sv.hoTen, sv.email, sv.soDT, sv.diaChi, " +
	                "t.tenTruong, n.tenNganh, tn.heTN, tn.ngayTN, tn.loaiTN " +
	                "FROM SINHVIEN sv " +
	                "LEFT JOIN TOT_NGHIEP tn ON sv.soCMND = tn.soCMND " +
	                "LEFT JOIN TRUONG t ON tn.maTruong = t.maTruong " +
	                "LEFT JOIN NGANH n ON tn.maNganh = n.maNganh";
	    try (Connection conn = dbConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {
	        while (rs.next()) {
	            java.sql.Date sqlDate = rs.getDate("ngayTN");
	            String ngayTN = sqlDate != null ? sqlDate.toString() : null;
	            students.add(new Object[]{
	                rs.getLong("soCMND"),
	                rs.getString("hoTen"),
	                rs.getString("email"),
	                rs.getLong("soDT"),
	                rs.getString("diaChi"),
	                rs.getString("tenTruong"),
	                rs.getString("tenNganh"),
	                rs.getString("heTN"),
	                ngayTN,
	                rs.getString("loaiTN")
	            });
	        }
	    }
	    return students;
	}
	
	public List<School> getAllSchools() throws SQLException {
        List<School> schools = new ArrayList<>();
        String sql = "SELECT MaTruong, TenTruong FROM TRUONG";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                schools.add(new School(rs.getInt("MaTruong"), rs.getString("TenTruong")));
            }
        }
        return schools;
    }
	
	public List<Major> getAllMajors() throws SQLException {
	    List<Major> majors = new ArrayList<>();
	    String sql = "SELECT n.maNganh, n.tenNganh FROM NGANH n";
	    try (Connection conn = dbConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {
	        while (rs.next()) {
	            majors.add(new Major(rs.getInt("maNganh"), rs.getString("tenNganh")));
	        }
	    }
	    return majors;
	}
	public List<Object[]> searchStudentsByName(String hoTen) throws SQLException {
        List<Object[]> students = new ArrayList<>();
        String sql = "SELECT s.SoCMND, s.HoTen, s.Email, s.SoDT, s.DiaChi " +
                     "FROM SINHVIEN s " +
                     "WHERE s.HoTen LIKE ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + hoTen + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    students.add(new Object[]{
                            rs.getLong("SoCMND"),
                            rs.getString("HoTen"),
                            rs.getString("Email"),
                            rs.getLong("SoDT"),
                            rs.getString("DiaChi")
                    });
                }
            }
        }
        return students;
    }
	
	public List<Object[]> searchGraduationAndJobById(long soCMND) throws SQLException {
        List<Object[]> results = new ArrayList<>();
        String sql = "SELECT s.HoTen, t.TenTruong, n.TenNganh, tn.HeTN, tn.NgayTN, tn.LoaiTN, cv.TenCV " +
                     "FROM SINHVIEN s " +
                     "LEFT JOIN TOT_NGHIEP tn ON s.SoCMND = tn.SoCMND " +
                     "LEFT JOIN TRUONG t ON tn.MaTruong = t.MaTruong " +
                     "LEFT JOIN NGANH n ON tn.MaNganh = n.MaNganh " +
                     "LEFT JOIN CONG_VIEC cv ON tn.MaNganh = cv.MaNganh " +
                     "WHERE s.SoCMND = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, soCMND);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(new Object[]{
                            rs.getString("HoTen"),
                            rs.getString("TenTruong"),
                            rs.getString("TenNganh"),
                            rs.getString("HeTN"),
                            rs.getString("NgayTN"),
                            rs.getString("LoaiTN"),
                            rs.getString("TenCV")
                    });
                }
            }
        }
        return results;
    }
	
	public boolean graduationExists(long soCMND, int maTruong, int maNganh) throws SQLException {
	    String sql = "SELECT COUNT(*) FROM TOT_NGHIEP WHERE SoCMND = ? AND MaTruong = ? AND MaNganh = ?";
	    try (Connection conn = dbConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setLong(1, soCMND);
	        stmt.setInt(2, maTruong);
	        stmt.setInt(3, maNganh);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	        }
	    }
	    return false;
	}
}
