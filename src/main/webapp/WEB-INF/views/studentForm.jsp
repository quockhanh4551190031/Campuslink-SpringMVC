<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
    <title>Quản lý sinh viên</title>
    <meta charset="UTF-8">
    <style>
        .error { color: red; }
        .message { color: green; }
        label { display: inline-block; width: 150px; margin: 5px 0; }
        input, select { margin: 5px 0; }
    </style>
</head>
<body>
    <h2>${student.soCMND != null && student.soCMND > 0 ? 'Chỉnh sửa sinh viên' : 'Thêm sinh viên mới'}</h2>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty message}">
        <div class="message">${message}</div>
    </c:if>

    <form:form action="${pageContext.request.contextPath}/student/save" method="post" modelAttribute="student">
        
        <label for="soCMND">Số CMND:</label>
        <form:input path="soCMND" type="number" min="1" /><br>

        <label for="hoTen">Họ Tên:</label>
        <form:input path="hoTen"/><br>

        <label for="email">Email:</label>
        <form:input path="email"/><br>

        <label for="soDT">Số ĐT:</label>
        <form:input path="soDT" type="number" min="1" /><br>

        <label for="diaChi">Địa Chỉ:</label>
        <form:input path="diaChi"/><br>

        <form:hidden path="graduation.soCMND"/>

        <label for="maTruong">Trường:</label>
        <form:select path="graduation.maTruong">
        	<form:option value="0" label="-- Chọn trường --"/>
            <c:forEach var="school" items="${schools}">
                <form:option value="${school.maTruong}">${school.tenTruong}</form:option>
            </c:forEach>     	
        </form:select><br>

        <label for="maNganh">Ngành:</label>
        <form:select path="graduation.maNganh">
            <form:option value="0" label="-- Chọn ngành --"/>
            <c:forEach var="major" items="${majors}">
                <form:option value="${major.maNganh}">${major.tenNganh}</form:option>
            </c:forEach>
        </form:select><br>

        <label for="heTN">Hệ TN:</label>
        <form:input path="graduation.heTN"/><br>

        <label for="ngayTN">Ngày TN:</label>
        <form:input path="graduation.ngayTN" type="date"/><br>

        <label for="loaiTN">Loại TN:</label>
        <form:input path="graduation.loaiTN"/><br>

        <input type="submit" value="${student.soCMND > 0 ? 'Cập nhật' : 'Thêm sinh viên'}"/>
    </form:form>

    <br>
    <a href="${pageContext.request.contextPath}/student/list">Quay lại danh sách sinh viên</a>
</body>
</html>