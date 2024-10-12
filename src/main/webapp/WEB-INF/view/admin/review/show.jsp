<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <meta name="description" content="Hoang Khang - Dự án laptopshop" />
    <meta name="author" content="Hoang Khang" />
    <title>Manager Orders - Hoang Khang</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script
      src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
      crossorigin="anonymous"
    ></script>
  </head>

  <body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp" />
    <div id="layoutSidenav">
      <jsp:include page="../layout/sidebar.jsp" />
      <div id="layoutSidenav_content">
        <main>
          <div class="container-fluid px-4">
            <h1 class="mt-4">Quản lý thông tin đánh giá sản phẩm</h1>
            <ol class="breadcrumb mb-4">
              <li class="breadcrumb-item"><a href="/admin">Bảng điều khiển</a></li>
              <li class="breadcrumb-item active">Đánh giá</li>
            </ol>
            <div class="mt-3">
              <div class="row">
                <div class="col-12 mx-auto">
                  <form action="/admin/review" method="get">
                      <input type="hidden" name="page" value="${currentPage}" />
                      <div class="row mb-3">
                          <div class="col-8">
                              <label for="pro" class="form-label">Sản phẩm</label>
                              <select class="form-select" id="pro" name="productId">
                                    <option selected value="">Vui lòng chọn sản phẩm</option>
                                  <c:forEach var="product" items="${productList}">
                                      <option value="${product.id}">${product.name}</option>
                                  </c:forEach>
                              </select>
                          </div>
                          <div class="col-4">
                              <label for="rating" class="form-label">Đánh giá</label>
                              <select class="form-select" id="rating" name="rating">
                                  <option selected value="">Vui lòng chọn đánh giá</option>
                                  <option value="1">1 sao</option>
                                  <option value="2">2 sao</option>
                                  <option value="3">3 sao</option>
                                  <option value="4">4 sao</option>
                                  <option value="5">5 sao</option>
                              </select>
                          </div>
                      </div>
                      <div class="row mb-3">
                          <div class="col-4">
                              <label for="fromDate" class="form-label">Từ ngày</label>
                              <input class="form-control" type="date" name="fromDate" id="fromDate"/>
                          </div>
                          <div class="col-4">
                              <label for="toDate" class="form-label">Đến ngày</label>
                              <input class="form-control" type="date" name="toDate" id="toDate"/>
                          </div>
                          <div class="col-4">
                              <label for="sort" class="form-label">Sắp xếp</label>
                              <select class="form-select" id="sort" name="sort">
                                  <option selected value="">Vui lòng chọn tiêu chí sắp xếp</option>
                                  <option value="rating-tang-dan">Số sao tăng dần</option>
                                  <option value="rating-giam-dan">Số sao giảm dần</option>
                                  <option value="createdAt-tang-dan">Ngày tạo đánh giá tăng dần</option>
                                  <option value="createdAt-giam-dan">Ngày tạo đánh giá giảm dần</option>
                              </select>
                          </div>
                      </div>
                      <input type="submit" value="Lọc kết quả" class="btn btn-primary" />
                  </form>
                  <div class="d-flex mt-3">
                      <h3>Danh sách đánh giá theo sản phẩm</h3>
                  </div>
                  <c:if test="${not empty product}">
                      <div class="d-flex">
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
                  </c:if>
                  <hr />
                  <c:if test="${empty product}">
                      <h3 class="text-info text-center">Vui lòng chọn sản phẩm để xem thông tin đánh giá</h3>
                  </c:if>
                  <c:if test="${reviews.size() == 0}">
                      <h3 class="text-info text-center">Không tìm thấy thông tin đánh giá thỏa mãn cho sản phẩm này</h3>
                  </c:if>
                  <c:if test="${not empty product && reviews.size() > 0}">
                  <table class="table table-bordered table-hover">
                    <thead>
                      <tr>
                        <th>ID</th>
                        <th>Người đánh giá</th>
                        <th>Số sao</th>
                        <th>Thời gian đánh giá</th>
                        <th>Hành động</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach var="review" items="${reviews}">
                        <tr>
                          <td>${review.id}</td>
                          <td>
                            ${review.user.fullName}
                          </td>
                          <td>
                            <c:forEach begin="1" end="${review.rating}">
                                <i class="fa fa-star text-warning"></i>
                            </c:forEach>
                            <c:if test="${review.rating <= 4}">
                                <c:forEach begin="${review.rating + 1}" end="5">
                                    <i class="fa fa-star text-secondary"></i>
                                </c:forEach>
                            </c:if>
                          </td>
                          <td>
                            <fmt:formatDate value="${review.createdAt}" pattern="dd/MM/yyyy HH:mm:ss" />
                          </td>
                          <td>
                            <a
                              href="/admin/review/${review.id}?page=${currentPage}&productId=${product.id}&rating=${rating}&fromDate=${fromDate}&toDate=${toDate}&sort=${sort}"
                              class="btn btn-success"
                              >Xem chi tiết</a
                            >
                          </td>
                        </tr>
                      </c:forEach>
                    </tbody>
                  </table>
                  <nav aria-label="Page navigation example">
                    <ul class="pagination justify-content-center">
                      <li class="page-item ${1 eq currentPage ? 'disabled' : ''}">
                        <a
                          class="page-link"
                          href="/admin/review?page=${currentPage - 1}${queryString}"
                          aria-label="Previous"
                        >
                          <span aria-hidden="true">&laquo;</span>
                        </a>
                      </li>
                      <c:forEach var="index" begin="0" end="${totalPages - 1}">
                        <li class="page-item">
                          <a
                            class="${(index + 1) eq currentPage ? 'active page-link' : 'page-link'}"
                            href="/admin/review?page=${index + 1}${queryString}"
                          >
                            ${index + 1}
                          </a>
                        </li>
                      </c:forEach>
                      <li class="page-item ${totalPages eq currentPage ? 'disabled' : ''}">
                        <a
                          class="page-link"
                          href="/admin/review?page=${currentPage + 1}${queryString}"
                          aria-label="Previous"
                        >
                          <span aria-hidden="true">&raquo;</span>
                        </a>
                      </li>
                    </ul>
                  </nav>
                  </c:if>
                </div>
              </div>
            </div>
          </div>
        </main>
        <jsp:include page="../layout/footer.jsp" />
      </div>
    </div>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
      crossorigin="anonymous"
    ></script>
    <script src="/js/scripts.js"></script>

    <script>
      window.onload = function() {
          if (performance.navigation.type === performance.navigation.TYPE_RELOAD) {
              if (window.location.search.length > 0) {
                  const url = window.location.href.split('?')[0];
                  window.history.replaceState({}, document.title, url);

                  window.location.reload();
              }
          } else {
              sessionStorage.setItem('firstLoad', 'true');
          }
      };
    </script>
  </body>
</html>
