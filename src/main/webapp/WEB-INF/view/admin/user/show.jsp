<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%> <%@taglib
uri="http://www.springframework.org/tags/form" prefix="form"%>

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
    <title>Dashboard - Users</title>
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
            <h1 class="mt-4">Quản lý người dùng</h1>
            <ol class="breadcrumb mb-4">
              <li class="breadcrumb-item">
                <a href="/admin">Bảng điều khiển</a>
              </li>
              <li class="breadcrumb-item active">Người dùng</li>
            </ol>
            <div class="row mt-5">
              <div class="col-12 mx-auto">
                <div class="d-flex justify-content-between">
                  <h3>Danh sách người dùng</h3>
                  <a href="/admin/user/create" class="btn btn-primary"
                    >Tạo mới một người dùng</a
                  >
                </div>
                <hr />
                <table class="table table-bordered table-hover">
                  <thead>
                    <th>ID</th>
                    <th>Email</th>
                    <th>Họ và tên</th>
                    <th>Vai trò</th>
                    <th>Hành động</th>
                  </thead>
                  <tbody>
                    <c:forEach var="user" items="${users}">
                      <tr>
                        <td>${user.id}</td>
                        <td>${user.email}</td>
                        <td>${user.fullName}</td>
                        <td>
                          <c:if test="${user.role.name.equals('ADMIN')}">
                            Quản trị viên
                          </c:if>
                          <c:if test="${user.role.name.equals('USER')}">
                            Khách hàng
                          </c:if>
                        </td>
                        <td>
                          <a
                            href="/admin/user/${user.id}"
                            class="btn btn-success"
                            >Xem chi tiết</a
                          >
                          <a
                            href="/admin/user/update/${user.id}"
                            class="btn btn-warning"
                            >Cập nhật</a
                          >
                          <a
                            href="/admin/user/delete/${user.id}"
                            class="btn btn-danger"
                            >Xóa</a
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
                        href="/admin/user?page=${currentPage - 1}"
                        aria-label="Previous"
                      >
                        <span aria-hidden="true">&laquo;</span>
                      </a>
                    </li>
                    <c:forEach var="index" begin="0" end="${totalPages - 1}">
                      <li class="page-item">
                        <a
                          class="${(index + 1) eq currentPage ? 'active page-link' : 'page-link'}"
                          href="/admin/user?page=${index + 1}"
                        >
                          ${index + 1}
                        </a>
                      </li>
                    </c:forEach>
                    <li class="page-item">
                      <a
                        class="${totalPages eq currentPage ? 'disabled page-link' : 'page-link'}"
                        href="/admin/user?page=${currentPage + 1}"
                        aria-label="Previous"
                      >
                        <span aria-hidden="true">&raquo;</span>
                      </a>
                    </li>
                  </ul>
                </nav>
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
