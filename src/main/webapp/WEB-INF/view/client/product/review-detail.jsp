<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
        $(document).ready(function(){
        // Bắt sự kiện nhấn vào ảnh
        $('.image-thumbnail').click(function(){
          const imageUrl = $(this).attr('src');
          $('#modalImage').attr('src', imageUrl);

          // Hiển thị modal tại vị trí hiện tại của ảnh
          $('#imageModal').modal('show');
        });
      });
    </script>
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
                            <li class="breadcrumb-item" aria-current="page">Thông tin đánh giá sản phẩm
                            </li>
                            <li class="breadcrumb-item active" aria-current="page">${product.id}
                            </li>
                        </ol>
                    </nav>
                </div>
                <div class="col-md-6 mx-auto col-12">
                    <div class="row g-4">
                        <div class="d-flex justify-content-center mb-3">
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
                        <div class="row justify-content-start">
                            <div class="mx-auto">
                                <h3 class="text-center mb-3">Thông Tin Đánh Giá</h3>
                                <div class="row">
                                    <div class="col-12 form-group mb-3">
                                        <div class="d-flex mb-4 justify-content-center">
                                            <c:forEach begin="1" end="${review.rating}">
                                                <i class="fa fs-3 fa-star text-secondary"></i>
                                            </c:forEach>
                                            <c:if test="${review.rating <= 4}">
                                                <c:forEach begin="${review.rating + 1}" end="5">
                                                    <i class="fa fs-3 fa-star"></i>
                                                </c:forEach>
                                            </c:if>
                                        </div>
                                    </div>
                                    <div class="col-12 form-group mb-3">
                                        <label class="form-label">Nội dung đánh giá</label>
                                        <c:if test="${empty review.content}">
                                            <h4 class="text-center">Không có nội dung</h4>
                                        </c:if>
                                        <c:if test="${not empty review.content}">
                                            <h4>${review.content}</h4>
                                        </c:if>
                                    </div>
                                    <div class="col-12 form-group mb-3">
                                        <label class="form-label">Ngày đánh giá</label>
                                        <h4>
                                            <fmt:formatDate value="${review.createdAt}" pattern="dd/MM/yyyy HH:mm:ss" />
                                        </h4>
                                    </div>
                                    <c:if test="${review.images.size() > 0}">
                                        <div class="col-12 d-flex mb-3 justify-content-center gap-1">
                                            <c:if test="${review.images.size() <= 3}">
                                                <c:forEach var="image" items="${review.images}">
                                                    <img src="/images/review/${image}" alt="${image}" class="image-thumbnail"
                                                        style="max-width: 200px; max-height: 160px; object-fit: cover; cursor: pointer;">
                                                </c:forEach>
                                            </c:if>
                                            <c:if test="${review.images.size() > 3}">
                                                <c:forEach var="index" begin="0" end="2">
                                                    <img src="/images/review/${review.images[index]}" alt="review image ${index}" class="image-thumbnail"
                                                        style="max-width: 200px; max-height: 160px; object-fit: cover; cursor: pointer;">
                                                </c:forEach>
                                                <a href="#" data-bs-toggle="modal" data-bs-target="#imageModalCarousel">
                                                    <div class="position-relative">
                                                        <img src="/images/review/${review.images[3]}" alt="Image 5"
                                                            style="max-width: 200px; max-height: 160px; object-fit: cover; cursor: pointer; opacity: 0.6;">
                                                        <span class="position-absolute top-50 start-50 translate-middle text-white bg-dark rounded px-2 fs-5">+${review.images.size() - 3}</span>
                                                    </div>
                                                </a>
                                            </c:if>
                                        </div>
                                    </c:if>
                                    <div class="mt-2 d-flex justify-content-between align-items-center">
                                        <div>
                                            <i class="fas fa-arrow-left"></i>
                                            <a href="/order-history">Quay lại</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Single Product End -->

    <!-- Modal Review Image -->
    <div class="modal fade" id="imageModal" data-bs-backdrop="static" tabindex="-1" aria-labelledby="imageModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body text-center">
                    <img src="" id="modalImage" class="img-fluid" alt="Selected Image" />
                </div>
            </div>
        </div>
    </div>

    <!-- Modal with Carousel -->
    <div class="modal fade" id="imageModalCarousel" data-bs-backdrop="static" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
                        <div class="carousel-indicators">
                            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                            <c:forEach var="index" begin="1" end="${review.images.size() - 1}">
                                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="${index}" aria-label="Slide ${index + 1}"></button>
                            </c:forEach>
                        </div>
                        <div class="carousel-inner">
                            <div class="carousel-item active">
                                <img src="/images/review/${review.images[0]}" class="d-block w-100" alt="Image 1">
                            </div>
                            <c:forEach var="index" begin="1" end="${review.images.size() - 1}">
                                <div class="carousel-item">
                                    <img src="/images/review/${review.images[index]}" class="d-block w-100" alt="Review Image">
                                </div>
                            </c:forEach>
                        </div>
                        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Previous</span>
                        </button>
                        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Next</span>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>

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