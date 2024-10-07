<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title> ${product.name} - Laptopshop</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="keywords">
    <meta content="" name="description">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
        href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
        rel="stylesheet">

    <!-- Icon Font Stylesheet -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
        rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
    <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">


    <!-- Customized Bootstrap Stylesheet -->
    <link href="/client/css/bootstrap.min.css" rel="stylesheet">

    <!-- Template Stylesheet -->
    <link href="/client/css/style.css" rel="stylesheet">

    <meta name="_csrf" content="${_csrf.token}" />
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}" />


    <link href="https://cdnjs.cloudflare.com/ajax/libs/jquery-toast-plugin/1.3.2/jquery.toast.min.css"
        rel="stylesheet">
</head>

<body>

    <!-- Spinner Start -->
    <div id="spinner"
        class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
        <div class="spinner-grow text-primary" role="status"></div>
    </div>
    <!-- Spinner End -->

    <jsp:include page="../layout/header.jsp" />

    <!-- Single Product Start -->
    <div class="container-fluid py-5 mt-5">
        <div class="container py-5">
            <div class="row g-4 mb-5">
                <div>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="/">Home</a></li>
                            <li class="breadcrumb-item" aria-current="page">Đánh giá sản phẩm
                            </li>
                            <li class="breadcrumb-item active" aria-current="page">${product.id}
                            </li>
                        </ol>
                    </nav>
                </div>
                <div class="col-md-6 mx-auto col-12">
                    <div class="row g-4">
                        <div class="d-flex justify-content-center">
                            <img src="/images/product/${product.image}"
                            class="img-fluid me-5 rounded-circle"
                            style="width: 80px; height: 80px;" alt="">
                            <div>
                                <p>
                                    <a href="/product/${product.id}" target="_blank">
                                        ${product.name}
                                    </a>
                                </p>
                                <p>
                                    <fmt:formatNumber type="number" value="${product.price}" /> đ
                                </p>
                            </div>
                        </div>
                        <form:form action="/add-review-product" method="post" modelAttribute="newReview">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <input type="hidden" name="productId" value="${product.id}" />
                            <div class="row justify-content-start">
                                <div class="mx-auto">
                                    <h3 class="text-center">Thông Tin Đánh Giá</h3>
                                    <div class="row">
                                        <div class="col-12 form-group mb-3">
                                            <label class="form-label">Đánh giá</label>
                                            <div class="d-flex mb-4 justify-content-center">
                                                <i style="cursor: pointer;" class="my-star star-1 fs-3 fa fa-star" data-star="1"></i>
                                                <i style="cursor: pointer;" class="my-star star-2 fs-3 fa fa-star" data-star="2"></i>
                                                <i style="cursor: pointer;" class="my-star star-3 fs-3 fa fa-star" data-star="3"></i>
                                                <i style="cursor: pointer;" class="my-star star-4 fs-3 fa fa-star" data-star="4"></i>
                                                <i style="cursor: pointer;" class="my-star star-5 fs-3 fa fa-star" data-star="5"></i>
                                            </div>
                                            <c:set var="errorRating">
                                                <form:errors path="rating" />
                                            </c:set>
                                            <form:input type="hidden" path="rating"/>
                                        </div>
                                        <span class="text-center text-danger mb-3">${errorRating}</span>
                                        <div class="col-12 form-group mb-3">
                                            <label class="form-label">Nội dung đánh giá</label>
                                            <form:textarea class="form-control" path="content"></form:textarea>
                                        </div>
                                        <div class="mt-2 d-flex justify-content-between align-items-center">
                                            <div>
                                                <i class="fas fa-arrow-left"></i>
                                                <a href="/order-history">Quay lại</a>
                                            </div>
                                            <button
                                                type="submit"
                                                class="float-end border-secondary rounded-pill px-1 py-2 text-primary text-uppercase">
                                                Gửi bài đánh giá
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Single Product End -->

    <jsp:include page="../layout/footer.jsp" />


    <!-- Back to Top -->
    <a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i
            class="fa fa-arrow-up"></i></a>


    <!-- JavaScript Libraries -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="/client/lib/easing/easing.min.js"></script>
    <script src="/client/lib/waypoints/waypoints.min.js"></script>
    <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
    <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>

    <!-- Template Javascript -->
    <script src="/client/js/main.js"></script>
    <script
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery-toast-plugin/1.3.2/jquery.toast.min.js"></script>

</body>

</html>