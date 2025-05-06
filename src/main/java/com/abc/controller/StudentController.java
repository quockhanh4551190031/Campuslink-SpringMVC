package com.abc.controller;

import com.abc.dao.StudentDAO;
import com.abc.models.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class StudentController {

    @Autowired
    private StudentDAO studentDAO;

    @GetMapping({"/", "/student"})
    public String showStudentForm(Model model) throws SQLException {
    	try {
            Student student = new Student();
            student.setGraduation(new Graduation()); // Đảm bảo student có graduation
            model.addAttribute("student", student);
            model.addAttribute("schools", studentDAO.getAllSchools());
            model.addAttribute("majors", studentDAO.getAllMajors());
            return "studentForm";
        } catch (SQLException e) {
            System.err.printf("Lỗi khi lấy dữ liệu trường và ngành: {}", e.getMessage(), e);
            model.addAttribute("error", "Không thể tải form do lỗi cơ sở dữ liệu: " + e.getMessage());
            return "studentForm";
        }
    }

    @GetMapping("/student/list")
    public String listStudents(Model model) throws SQLException {
        List<Object[]> students = studentDAO.getAllStudents();
        model.addAttribute("students", students);
        return "studentList";
    }

    @GetMapping("/student/edit")
    public String editStudent(@RequestParam("soCMND") long soCMND, Model model) {
        try {
            Student student = studentDAO.getStudentById(soCMND);
            model.addAttribute("student", student);
            model.addAttribute("schools", studentDAO.getAllSchools());
            model.addAttribute("majors", studentDAO.getAllMajors());
        } catch (SQLException e) {
            model.addAttribute("error", "Lỗi khi lấy thông tin sinh viên: " + e.getMessage());
        }
        return "studentForm";
    }

    @PostMapping("/student/save")
    public String saveStudent(@ModelAttribute("student") Student student, 
                              RedirectAttributes redirectAttributes, 
                              Model model) {
        StringBuilder errorMsg = new StringBuilder();
        long soCMND = student.getSoCMND();
        long soDT = student.getSoDT();
        Graduation graduation = student.getGraduation();
        if (graduation == null) {
            graduation = new Graduation();
            student.setGraduation(graduation);
        }
        int maTruong = graduation.getMaTruong();
        int maNganh = graduation.getMaNganh();
        String ngayTN = graduation.getNgayTN();

        // Validate dữ liệu
        if (soCMND <= 0) {
            errorMsg.append("Số CMND phải là số dương.<br>");
        }
        if (soDT <= 0) {
            errorMsg.append("Số điện thoại phải là số dương.<br>");
        }
        if (maTruong <= 0) {
            errorMsg.append("Mã trường là bắt buộc.<br>");
        }
        if (maNganh <= 0) {
            errorMsg.append("Mã ngành là bắt buộc.<br>");
        }
        if (ngayTN != null && !ngayTN.trim().isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                Date graduationDate = sdf.parse(ngayTN);
                Date today = new Date();
                if (graduationDate.after(today)) {
                    errorMsg.append("Ngày tốt nghiệp không được là ngày trong tương lai.<br>");
                }
            } catch (ParseException e) {
                errorMsg.append("Ngày tốt nghiệp phải có định dạng yyyy-MM-dd.<br>");
            }
        }

        // Nếu có lỗi validate, trả lại form với dữ liệu đã nhập
        if (errorMsg.length() > 0) {
            model.addAttribute("error", errorMsg.toString());
            model.addAttribute("student", student);
            try {
                model.addAttribute("schools", studentDAO.getAllSchools());
                model.addAttribute("majors", studentDAO.getAllMajors());
            } catch (SQLException e) {
                model.addAttribute("error", model.getAttribute("error") + "Lỗi khi tải danh sách trường/ngành: " + e.getMessage());
            }
            return "studentForm";
        }

        try {
            // Kiểm tra xem bản ghi tot_nghiep đã tồn tại chưa dựa trên khóa chính (soCMND, maTruong, maNganh)
            boolean graduationExists = studentDAO.graduationExists(soCMND, maTruong, maNganh);
            if (graduationExists) {
                // Cập nhật sinh viên và thông tin tốt nghiệp
                studentDAO.updateStudent(student, graduation);
                redirectAttributes.addFlashAttribute("message", "Cập nhật sinh viên thành công!");
            } else {
                // Thêm sinh viên mới
                studentDAO.addStudent(student, graduation);
                redirectAttributes.addFlashAttribute("message", "Thêm sinh viên thành công!");
            }
        } catch (SQLException e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi lưu sinh viên: " + e.getMessage());
        }
        return "redirect:list";
    }

    @GetMapping("/student/delete")
    public String deleteStudent(@RequestParam("soCMND") long soCMND, RedirectAttributes redirectAttributes) throws SQLException {
        try {
            studentDAO.deleteStudent(soCMND);
            redirectAttributes.addFlashAttribute("message", "Xóa sinh viên thành công!");
        } catch (SQLException e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa sinh viên: " + e.getMessage());
        }
        return "redirect:/student/list";
    }

    @GetMapping("/student/searchBasic")
    public String showSearchBasicForm(@RequestParam(value = "hoTen", required = false) String hoTen, Model model) throws SQLException {
        List<Object[]> students = null;
        if (hoTen != null && !hoTen.trim().isEmpty()) {
            students = studentDAO.searchStudentsByName(hoTen);
        }
        model.addAttribute("students", students);
        return "searchStudent";
    }

    @GetMapping("/student/searchGradJob")
    public String showSearchGradJobForm(@RequestParam(value = "soCMND", required = false) String soCMNDStr, Model model) throws SQLException {
        List<Object[]> results = null;
        if (soCMNDStr != null && !soCMNDStr.trim().isEmpty()) {
            try {
                long soCMND = Long.parseLong(soCMNDStr);
                results = studentDAO.searchGraduationAndJobById(soCMND);
            } catch (NumberFormatException e) {
                model.addAttribute("error", "Số CMND phải là số hợp lệ.");
            }
        }
        model.addAttribute("results", results);
        return "searchGraduationJob";
    }
}