<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
    <title>Tìm kiếm thông tin cơ bản</title>
    <meta charset="UTF-8">
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid black; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .error { color: red; }
    </style>
</head>
<body>
    <h2>Tìm kiếm thông tin cơ bản</h2>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/student/searchBasic" method="get">
        <label for="hoTen">Họ Tên:</label>
        <input type="text" id="hoTen" name="hoTen"/>
        <input type="submit" value="Tìm kiếm"/>
    </form>

    <br>

    <c:if test="${not empty students}">
        <table>
            <tr>
                <th>Số CMND</th>
                <th>Họ Tên</th>
                <th>Email</th>
                <th>Số ĐT</th>
                <th>Địa Chỉ</th>
            </tr>
            <c:forEach var="student" items="${students}">
                <tr>
                    <td>${student[0]}</td>
                    <td>${student[1]}</td>
                    <td>${student[2]}</td>
                    <td>${student[3]}</td>
                    <td>${student[4]}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${empty students && param.hoTen != null}">
        <p>Không tìm thấy sinh viên nào.</p>
    </c:if>

    <br>
    <a href="${pageContext.request.contextPath}/student/list">Quay lại danh sách sinh viên</a>
</body>
</html>