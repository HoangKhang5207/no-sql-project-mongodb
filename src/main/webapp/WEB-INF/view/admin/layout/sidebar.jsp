<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="layoutSidenav_nav">
    <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
        <div class="sb-sidenav-menu">
            <div class="nav">
                <div class="sb-sidenav-menu-heading">Chức năng</div>
                <a class="nav-link" href="/admin">
                    <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                    Bảng điều khiển
                </a>

                <a class="nav-link" href="/admin/user">
                    <div class="sb-nav-link-icon"><i class="fas fa-users"></i></div>
                    Quản lý người dùng
                </a>

                <a class="nav-link" href="/admin/product">
                    <div class="sb-nav-link-icon"><i class="fa-solid fa-laptop"></i></div>
                    Quản lý sản phẩm
                </a>

                <a class="nav-link" href="/admin/order">
                    <div class="sb-nav-link-icon"><i class="fa-solid fa-cart-shopping"></i></div>
                    Quản lý đơn hàng
                </a>

                <a class="nav-link" href="/admin/review">
                    <div class="sb-nav-link-icon"><i class="fa-solid fa-comments"></i></div>
                    Quản lý đánh giá sản phẩm
                </a>
            </div>
        </div>
        <div class="sb-sidenav-footer">
            <div class="small">Đã đăng nhập như là:</div>
            <c:out value="${pageContext.request.userPrincipal.name}" />
        </div>
    </nav>
</div>