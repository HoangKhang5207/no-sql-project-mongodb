<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
    <meta name="author" content="Hỏi Dân IT" />
    <title>Dashboard - Create User</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
      $(document).ready(() => {
        const avatarFile = $("#avatarFile");
        avatarFile.change(function (e) {
          const imgURL = URL.createObjectURL(e.target.files[0]);
          $("#avatarPreview").attr("src", imgURL);
          $("#avatarPreview").css({ "display": "block" });
        });
      });
    </script>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>

<body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp" />
    <div id="layoutSidenav">
        <jsp:include page="../layout/sidebar.jsp" />
        <div id="layoutSidenav_content">
            <main>
                <div class="container-fluid px-4">
                    <h1 class="mt-4">Manage Users</h1>
                    <ol class="breadcrumb mb-4">
                        <li class="breadcrumb-item">
                            <a href="/admin">Dashboard</a>
                        </li>
                        <li class="breadcrumb-item active">
                            Users
                        </li>
                    </ol>
                    <div class="mt-5 row">
                      <div class="col-md-6 col-12 mx-auto">
                        <h3>Create a User</h3>
                        <hr>
                        <form:form action="/admin/user/create" 
                              method="post" 
                              modelAttribute="newUser"
                              class="row g-3"
                              enctype="multipart/form-data">
                            <div class="col-md-6">
                              <c:set var="errorEmail">
                                <form:errors path="email" cssClass="invalid-feedback" />
                              </c:set>
                              <label for="email" class="form-label">Email:</label>
                              <form:input path="email" type="email" class="form-control ${not empty errorEmail ? 'is-invalid' : ''}"/>
                              ${errorEmail}
                            </div>
                            <div class="col-md-6">
                              <c:set var="errorPassword">
                                <form:errors path="password" cssClass="invalid-feedback" />
                              </c:set>
                              <label for="password" class="form-label">Password:</label>
                              <form:input path="password" type="password" class="form-control ${not empty errorPassword ? 'is-invalid' : ''}"/>
                              ${errorPassword}
                            </div>
                            <div class="col-md-6">
                              <label for="phone" class="form-label">Phone number:</label>
                              <form:input path="phone" type="text" class="form-control"/>
                            </div>
                            <div class="col-md-6">
                              <c:set var="errorFullName">
                                <form:errors path="fullName" cssClass="invalid-feedback" />
                              </c:set>
                              <label for="fullName" class="form-label">Full Name:</label>
                              <form:input path="fullName" type="text" class="form-control ${not empty errorFullName ? 'is-invalid' : ''}"/>
                              ${errorFullName}
                            </div>
                            <div class="col-12">
                              <label for="address" class="form-label">Address:</label>
                              <form:input path="address" type="text" class="form-control"/>
                            </div>
                            <div class="col-md-6">
                              <label for="role" class="form-label">Role:</label>
                              <form:select id="role" class="form-select" path="role.name">
                                <form:option value="ADMIN">ADMIN</form:option>
                                <form:option value="USER">USER</form:option>
                              </form:select>
                            </div>
                            <div class="col-md-6">
                              <label for="avatarFile" class="form-label">Avatar:</label>
                              <form:input path="" type="file" class="form-control" id="avatarFile"
                                          accept=".png, .jpg, .jpeg" name="khangFile"/>
                            </div>
                            <div class="col-12">
                              <img alt="Avatar Preview" id="avatarPreview" 
                                  style="max-height: 250px; display: none;" class="mx-auto"/>
                            </div>
                            <div class="col-md-3">
                              <button type="submit" class="btn btn-primary">Create</button>
                              <a href="/admin/user" class="btn btn-secondary">Back</a>
                            </div>
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