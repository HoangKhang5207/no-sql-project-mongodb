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
                            <li class="breadcrumb-item active" aria-current="page">Chi Tiết Sản Phẩm
                            </li>
                        </ol>
                    </nav>
                </div>
                <div class="col-lg-8 col-xl-9">
                    <div class="row g-4">
                        <div class="col-lg-6">
                            <div class="border rounded">
                                <a href="#">
                                    <img src="/images/product/${product.image}"
                                        class="img-fluid rounded" alt="Image">
                                </a>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <h4 class="fw-bold mb-3"> ${product.name}</h4>
                            <p class="mb-3">Hãng sản xuất: ${product.factory}</p>
                            <h5 class="fw-bold mb-3">
                                <fmt:formatNumber type="number" value="${product.price}" /> đ
                            </h5>
                            <p>Đã bán: <span class="fw-bold fs-5">${product.sold}</span></p>
                            <p class="mb-4">
                                ${product.shortDesc}
                            </p>

                            <div class="input-group quantity mb-5" style="width: 100px;">
                                <div class="input-group-btn">
                                    <button class="btn btn-sm btn-minus rounded-circle bg-light border">
                                        <i class="fa fa-minus"></i>
                                    </button>
                                </div>
                                <input type="text"
                                    class="form-control form-control-sm text-center border-0" value="1"
                                    data-cart-detail-index="0">
                                <div class="input-group-btn">
                                    <button class="btn btn-sm btn-plus rounded-circle bg-light border">
                                        <i class="fa fa-plus"></i>
                                    </button>
                                </div>
                            </div>
                            <!-- <form action="/add-product-from-view-detail" method="post"
                                modelAttribute="product"> -->
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <input class="form-control d-none" type="text" value="${product.id}"
                                name="id" />

                            <input class="form-control d-none" type="text" name="quantity"
                                id="cartDetails0.quantity" value="1" />
                            <button data-product-id="${product.id}"
                                class="btnAddToCartDetail btn border border-secondary rounded-pill px-4 py-2 mb-4 text-primary"><i
                                    class="fa fa-shopping-bag me-2 text-primary"></i>
                                Thêm vào giỏ hàng
                            </button>
                            <!-- </form> -->
                        </div>
                        <div class="col-lg-12">
                            <nav>
                                <div class="nav nav-tabs mb-3">
                                    <button class="nav-link border-white border-bottom-0"
                                        type="button" role="tab" id="nav-about-tab" data-bs-toggle="tab"
                                        data-bs-target="#nav-about" aria-controls="nav-about"
                                        aria-selected="false">Mô tả sản phẩm</button>
                                    <button class="nav-link active border-white border-bottom-0" 
                                        type="button" role="tab" id="nav-mission-tab" data-bs-toggle="tab" 
                                        data-bs-target="#nav-mission" aria-controls="nav-mission" 
                                        aria-selected="true">Đánh giá của khách hàng (${reviews.size()})</button>
                                </div>
                            </nav>
                            <div class="tab-content mb-5">
                                <div class="tab-pane" id="nav-about" role="tabpanel"
                                    aria-labelledby="nav-about-tab">
                                    <p> ${product.detailDesc} </p>
                                </div>
                                <div class="tab-pane active" id="nav-mission" role="tabpanel" aria-labelledby="nav-mission-tab">
                                    <div class="d-flex justify-content-between mb-3">
                                        <div class="d-flex align-items-center">
                                            <i class="fa fa-star text-secondary"></i>
                                            <i class="fa fa-star text-secondary"></i>
                                            <i class="fa fa-star text-secondary"></i>
                                            <i class="fa fa-star text-secondary"></i>
                                            <i class="fa fa-star text-secondary"></i>
                                            <span class="text-warning">(${cntFiveStar})</span>
                                        </div>

                                        <div class="d-flex align-items-center">
                                            <i class="fa fa-star text-secondary"></i>
                                            <i class="fa fa-star text-secondary"></i>
                                            <i class="fa fa-star text-secondary"></i>
                                            <i class="fa fa-star text-secondary"></i>
                                            <span class="text-warning">(${cntFourStar})</span>
                                        </div>

                                        <div class="d-flex align-items-center">
                                            <i class="fa fa-star text-secondary"></i>
                                            <i class="fa fa-star text-secondary"></i>
                                            <i class="fa fa-star text-secondary"></i>
                                            <span class="text-warning">(${cntThreeStar})</span>
                                        </div>

                                        <div class="d-flex align-items-center">
                                            <i class="fa fa-star text-secondary"></i>
                                            <i class="fa fa-star text-secondary"></i>
                                            <span class="text-warning">(${cntTwoStar})</span>
                                        </div>

                                        <div class="d-flex align-items-center">
                                            <i class="fa fa-star text-secondary"></i>
                                            <span class="text-warning">(${cntOneStar})</span>
                                        </div>
                                    </div>
                                    <c:if test="${reviews.size() == 0}">
                                        <h3 class="text-center">Chưa có bài đánh giá nào của khách hàng!</h3>
                                    </c:if>
                                    <c:if test="${reviews.size() > 0}">
                                        <c:forEach var="review" items="${reviews}">
                                            <div class="mb-3">
                                                <div class="d-flex align-items-center">
                                                    <img src="/images/avatar/${review.user.avatar}"
                                                        class="img-fluid rounded-circle p-2" style="width: 90px; height: 90px;"
                                                        alt="" loading="lazy">
                                                    <div class="d-flex flex-column justify-content-center">
                                                        <h6>${review.user.fullName}</h6>
                                                        <div class="d-flex mb-2">
                                                            <c:forEach begin="1" end="${review.rating}">
                                                                <i class="fa fa-star text-secondary"></i>
                                                            </c:forEach>
                                                            <c:if test="${review.rating <= 4}">
                                                                <c:forEach begin="${review.rating + 1}" end="5">
                                                                    <i class="fa fa-star"></i>
                                                                </c:forEach>
                                                            </c:if>
                                                        </div>
                                                        <p style="font-size: 14px;">
                                                            <fmt:formatDate value="${review.createdAt}" pattern="dd/MM/yyyy HH:mm:ss" />
                                                        </p>
                                                    </div>
                                                </div>
                                                <p class="mb-0">${review.content}</p>
                                                <c:if test="${review.images.size() > 0}">
                                                    <div class="d-flex gap-1">
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
                                        </c:forEach>
                                        <div class="pagination d-flex justify-content-center mt-5">
                                            <li class="page-item ${1 eq currentPage ? 'disabled' : ''}">
                                                <a class="page-link"
                                                    href="/product/${product.id}?page=${currentPage - 1}" aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                            <c:forEach begin="0" end="${totalPages - 1}" varStatus="loop">
                                                <li class="page-item">
                                                    <a class="${(loop.index + 1) eq currentPage ? 'active page-link' : 'page-link'}"
                                                        href="/product/${product.id}?page=${loop.index + 1}">
                                                        ${loop.index + 1}
                                                    </a>
                                                </li>
                                            </c:forEach>
                                            <li class="page-item ${totalPages eq currentPage ? 'disabled' : ''}">
                                                <a class="page-link"
                                                    href="/product/${product.id}?page=${currentPage + 1}" aria-label="Next">
                                                    <span aria-hidden="true">&raquo;</span>
                                                </a>
                                            </li>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="col-lg-4 col-xl-3">
                    <div class="row g-4 fruite">
                        <div class="col-lg-12">

                            <div class="mb-4">
                                <h4>Laptop theo hãng</h4>
                                <ul class="list-unstyled fruite-categorie">
                                    <li>
                                        <div class="d-flex justify-content-between fruite-name">
                                            <a href="/products?page=1&sort=gia-nothing&factory=APPLE"><i class="fas fa-apple-alt me-2"></i>Apples</a>
                                            <span>(${cntApple})</span>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="d-flex justify-content-between fruite-name">
                                            <a href="/products?page=1&sort=gia-nothing&factory=DELL"><i class="fas fa-apple-alt me-2"></i>Dell</a>
                                            <span>(${cntDell})</span>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="d-flex justify-content-between fruite-name">
                                            <a href="/products?page=1&sort=gia-nothing&factory=ASUS"><i class="fas fa-apple-alt me-2"></i>Asus</a>
                                            <span>(${cntAsus})</span>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="d-flex justify-content-between fruite-name">
                                            <a href="/products?page=1&sort=gia-nothing&factory=ACER"><i class="fas fa-apple-alt me-2"></i>Acer</a>
                                            <span>(${cntAcer})</span>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="d-flex justify-content-between fruite-name">
                                            <a href="/products?page=1&sort=gia-nothing&factory=LENOVO"><i class="fas fa-apple-alt me-2"></i>Lenovo</a>
                                            <span>(${cntLenovo})</span>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="d-flex justify-content-between fruite-name">
                                            <a href="/products?page=1&sort=gia-nothing&factory=HP"><i class="fas fa-apple-alt me-2"></i>HP</a>
                                            <span>(${cntHP})</span>
                                        </div>
                                    </li>
                                </ul>
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