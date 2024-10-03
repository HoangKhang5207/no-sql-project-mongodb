<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
    <meta name="author" content="Hỏi Dân IT" />
    <title>Dashboard - Product</title>
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
            <h1 class="mt-4">Manage Products</h1>
            <ol class="breadcrumb mb-4">
              <li class="breadcrumb-item">
                <a href="/admin">Dashboard</a>
              </li>
              <li class="breadcrumb-item active">Products</li>
            </ol>
            <div class="row mt-5">
              <div class="col-12 mx-auto">
                <div class="d-flex justify-content-between">
                  <h3>Table Products</h3>
                  <a href="/admin/product/create" class="btn btn-primary"
                    >Create a product</a
                  >
                </div>
                <hr />
                <c:if test="${products.size() == 0}">
                  <h2 class="alert alert-info text-center">No products found.</h2>
                </c:if>
                <c:if test="${products.size() > 0}">
                  <table class="table table-bordered table-hover">
                    <thead>
                      <th>ID</th>
                      <th>Name</th>
                      <th>Price</th>
                      <th>Factory</th>
                      <th>Action</th>
                    </thead>
                    <tbody>
                      <c:forEach var="product" items="${products}">
                        <tr>
                          <td>${product.id}</td>
                          <td>${product.name}</td>
                          <td>
                            <fmt:formatNumber
                              value="${product.price}"
                              type="number"
                            />
                            đ
                          </td>
                          <td>${product.factory}</td>
                          <td>
                            <a
                              href="/admin/product/${product.id}"
                              class="btn btn-success"
                              >View</a
                            >
                            <a
                              href="/admin/product/update/${product.id}"
                              class="btn btn-warning"
                              >Update</a
                            >
                            <a
                              href="/admin/product/delete/${product.id}"
                              class="btn btn-danger"
                              >Delete</a
                            >
                          </td>
                        </tr>
                      </c:forEach>
                    </tbody>
                  </table>
                  <nav aria-label="Page navigation example">
                    <ul class="pagination justify-content-center">
                      <li class="page-item">
                        <a
                          class="${1 eq currentPage ? 'disabled page-link' : 'page-link'}"
                          href="/admin/product?page=${currentPage - 1}"
                          aria-label="Previous"
                        >
                          <span aria-hidden="true">&laquo;</span>
                        </a>
                      </li>
                      <c:forEach var="index" begin="0" end="${totalPages - 1}">
                        <li class="page-item">
                          <a
                            class="${(index + 1) eq currentPage ? 'active page-link' : 'page-link'}"
                            href="/admin/product?page=${index + 1}"
                          >
                            ${index + 1}
                          </a>
                        </li>
                      </c:forEach>
                      <li class="page-item">
                        <a
                          class="${totalPages eq currentPage ? 'disabled page-link' : 'page-link'}"
                          href="/admin/product?page=${currentPage + 1}"
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
        </main>
        <jsp:include page="../layout/footer.jsp" />
      </div>
    </div>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
      crossorigin="anonymous"
    ></script>
    <script src="/js/scripts.js"></script>
  </body>
</html>
