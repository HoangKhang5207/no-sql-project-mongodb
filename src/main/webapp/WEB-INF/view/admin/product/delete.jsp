<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="Hoang Khang - Dự án laptopshop" />
    <meta name="author" content="Hoang Khang" />
    <title>Dashboard - Delete Product</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>

<body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp" />
    <div id="layoutSidenav">
        <jsp:include page="../layout/sidebar.jsp" />
        <div id="layoutSidenav_content">
            <main>
                <div class="container-fluid px-4">
                    <h1 class="mt-4">Quản lý sản phẩm</h1>
                    <ol class="breadcrumb mb-4">
                        <li class="breadcrumb-item">
                            <a href="/admin">Bảng điều khiển</a>
                        </li>
                        <li class="breadcrumb-item active">
                            Sản phẩm
                        </li>
                    </ol>
                    <div class="row mt-5">
                        <div class="col-12 mx-auto">
                            <h3>Xóa sản phẩm với mã: ${productId}</h3>
                            <hr>
                            <div class="alert alert-danger" role="alert">
                                Bạn có chắc chắn muốn xóa sản phẩm này?
                            </div>
                            <form:form action="/admin/product/delete" method="post" modelAttribute="newProduct">
                                <form:input type="hidden" path="id" value="${productId}" />
                                <input type="submit" value="Xác nhận" class="btn btn-danger"/>
                            </form:form>
                        </div>
                    </div>
                </div>
            </main>
            <jsp:include page="../layout/footer.jsp"/>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
    <script src="/js/scripts.js"></script>
</body>

</html>