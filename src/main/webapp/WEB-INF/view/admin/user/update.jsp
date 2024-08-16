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
    <title>Dashboard - Update User</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
      $(document).ready(() => {
        const avatarFile = $("#avatar");
        avatarFile.change(function (e) {
          const imgURL = URL.createObjectURL(e.target.files[0]);
          console.log(imgURL.toString());
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
                    <div class="row mt-5">
                        <div class="col-md-6 col-12 mx-auto">
                            <h3>Update User with Id = ${updateUser.id}</h3>
                            <hr>
                            <form:form action="/admin/user/update" method="post" 
                                    modelAttribute="updateUser"
                                    enctype="multipart/form-data">
                                <div class="mb-3">
                                    <form:input path="id" type="hidden" class="form-control" id="userId"/>
                                </div>
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email:</label>
                                    <form:input path="email" type="email" disabled="true" class="form-control"/>
                                </div>
                                <div class="mb-3">
                                    <label for="phone" class="form-label">Phone number:</label>
                                    <form:input path="phone" type="text" class="form-control"/>
                                </div>
                                <div class="mb-3">
                                    <label for="fullName" class="form-label">Full Name:</label>
                                    <form:input path="fullName" type="text" class="form-control"/>
                                </div>
                                <div class="mb-3">
                                    <label for="address" class="form-label">Address:</label>
                                    <form:input path="address" type="text" class="form-control"/>
                                </div>
                                <div class="mb-3">
                                    <label for="avatar" class="form-label">Avatar:</label>
                                    <form:input id="avatar" name="khangFile" path="" 
                                                accept=".png, .jpg, .jpeg" type="file" class="form-control"/>
                                </div>
                                <div class="mb-3">
                                    <img alt="Avatar Preview" src="/images/avatar/${updateUser.avatar}" 
                                        id="avatarPreview" 
                                        style="max-height: 250px; display: block;" 
                                        class="mx-auto"/> 
                                </div>
                                <button type="submit" class="btn btn-warning">Update</button>
                                <a href="/admin/user" class="btn btn-secondary">Back</a>
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