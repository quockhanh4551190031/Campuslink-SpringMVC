<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>Tìm kiếm thông tin tốt nghiệp và việc làm</title>
    <meta charset="UTF-8">
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid black; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .error { color: red; }
    </style>
</head>
<body>
    <h2>Tìm kiếm thông tin tốt nghiệp và việc làm</h2>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/student/searchGradJob" method="get">
        <label for="soCMND">Số CMND:</label>
        <input type="text" id="soCMND" name="soCMND"/>
        <input type="submit" value="Tìm kiếm"/>
    </form>

    <br>

    <c:if test="${not empty results}">
        <table>
            <tr>
                <th>Họ Tên</th>
                <th>Tên Trường</th>
                <th>Tên Ngành</th>
                <th>Hệ TN</th>
                <th>Ngày TN</th>
                <th>Loại TN</th>
                <th>Công việc</th>
            </tr>
            <c:forEach var="result" items="${results}">
                <tr>
                    <td>${result[0]}</td>
                    <td>${result[1]}</td>
                    <td>${result[2]}</td>
                    <td>${result[3]}</td>
                    <td>${result[4]}</td>
                    <td>${result[5]}</td>
                    <td>${result[6]}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${empty results && param.soCMND != null}">
        <p>Không tìm thấy thông tin.</p>
    </c:if>

    <br>
    <a href="${pageContext.request.contextPath}/student/list">Quay lại danh sách sinh viên</a>
</body>
</html>