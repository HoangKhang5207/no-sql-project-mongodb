<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="HoangKhang - Dự án laptopshop" />
    <meta name="author" content="HoangKhang" />
    <title>Dashboard - Product Detail</title>
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
                            <div class="d-flex justify-content-between">
                                <h3>Chi tiết sản phẩm với mã: ${product.id}</h3>
                            </div>
                            <hr>
                            <div class="card" style="width: 60%;">
                                <img class="card-img-top mx-auto w-50" 
                                        src="/images/product/${product.image}" alt="Card image top">
                                <div class="card-header">
                                    Thông tin sản phẩm
                                </div>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item">Tên sản phẩm: ${product.name}</li>
                                    <li class="list-group-item">Giá: <fmt:formatNumber value="${product.price}" type="number" /> đ </li>
                                    <li class="list-group-item">Số lượng: ${product.quantity}</li>
                                    <li class="list-group-item">Mô tả chi tiết: ${product.detailDesc}</li>
                                    <li class="list-group-item">Mô tả ngắn: ${product.shortDesc}</li>
                                    <li class="list-group-item">Hãng sản xuất: ${product.factory}</li>
                                    <li class="list-group-item">Mục đích sử dụng: ${product.target}</li>
                                </ul>
                            </div>
                            <a href="/admin/product" class="btn btn-success mt-2">Quay về</a>
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