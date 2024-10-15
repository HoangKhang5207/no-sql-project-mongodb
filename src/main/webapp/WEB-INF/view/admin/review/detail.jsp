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

<body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp" />
    <div id="layoutSidenav">
        <jsp:include page="../layout/sidebar.jsp" />
        <div id="layoutSidenav_content">
            <main>
                <div class="container-fluid px-4">
                    <h1 class="mt-4">Quản lý đánh giá sản phẩm</h1>
                    <ol class="breadcrumb mb-4">
                        <li class="breadcrumb-item">
                            <a href="/admin">Bảng điều khiển</a>
                        </li>
                        <li class="breadcrumb-item active">
                            Đánh giá sản phẩm
                        </li>
                    </ol>
                    <div class="row mt-5">
                        <div class="col-12 mx-auto">
                            <div class="d-flex justify-content-between">
                                <h3>Chi tiết thông tin đánh giá với mã: ${review.id}</h3>
                            </div>
                            <hr>
                            <div class="card" style="width: 60%;">
                                <img class="card-img-top mx-auto w-50" 
                                        src="/images/product/${product.image}" alt="Card image top">
                                <div class="card-header">
                                    Thông tin đánh giá
                                </div>
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item">
                                        <c:forEach begin="1" end="${review.rating}">
                                            <i class="fa fa-star text-warning"></i>
                                        </c:forEach>
                                        <c:if test="${review.rating <= 4}">
                                            <c:forEach begin="${review.rating + 1}" end="5">
                                                <i class="fa fa-star text-secondary"></i>
                                            </c:forEach>
                                        </c:if>
                                    </li>
                                    <li class="list-group-item">
                                        Nội dung: ${review.content}
                                    </li>
                                    <li class="list-group-item">
                                        Người đánh giá: ${review.user.fullName}
                                    </li>
                                    <li class="list-group-item">
                                        Ngày đánh giá: <fmt:formatDate value="${review.createdAt}" pattern="dd/MM/yyyy HH:mm:ss" />
                                    </li>
                                </ul>
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
                            </div>
                            <a href="/admin/review?page=${currentPage}&productId=${productId}&rating=${rating}&fromDate=${fromDate}&toDate=${toDate}&sort=${sort}"
                                class="btn btn-success mt-2">Quay về</a>
                        </div>
                    </div>
                </div>
            </main>
            <jsp:include page="../layout/footer.jsp"/>

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
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
    <script src="/js/scripts.js"></script>
</body>

</html>