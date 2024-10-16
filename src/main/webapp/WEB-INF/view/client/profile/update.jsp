<%@page contentType="text/html" pageEncoding="UTF-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <title>Laptopshop</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <meta content="" name="keywords" />
    <meta content="" name="description" />

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
      rel="stylesheet"
    />

    <!-- Icon Font Stylesheet -->
    <link
      rel="stylesheet"
      href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
    />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
      rel="stylesheet"
    />

    <!-- Libraries Stylesheet -->
    <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet" />
    <link
      href="/client/lib/owlcarousel/assets/owl.carousel.min.css"
      rel="stylesheet"
    />

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
      $(document).ready(() => {
        const avatarImage = $("#avatarImage");
        const orgImage = "${updateUser.avatar}";
        if (orgImage) {
            const urlImage = "/images/avatar/" + orgImage;
            $('#avatarPreview').attr("src", urlImage);
            $("#avatarPreview").css({ "display": "block" });
        }

        avatarImage.change(function (e) {
          const imgURL = URL.createObjectURL(e.target.files[0]);
          $("#avatarPreview").attr("src", imgURL);
          $("#avatarPreview").css({ "display": "block" });
        });
      });
    </script>

    <!-- Customized Bootstrap Stylesheet -->
    <link href="/client/css/bootstrap.min.css" rel="stylesheet" />
    <!-- Template Stylesheet -->
    <link href="/client/css/style.css" rel="stylesheet" />
  </head>

  <body>
    <!-- Spinner Start -->
    <div
      id="spinner"
      class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50 d-flex align-items-center justify-content-center"
    >
      <div class="spinner-grow text-primary" role="status"></div>
    </div>
    <!-- Spinner End -->

    <jsp:include page="../layout/header.jsp" />

    <!-- Cart Page Start -->
    <div class="container-fluid py-5">
      <div class="container py-5">
        <div class="mb-3">
          <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
              <li class="breadcrumb-item"><a href="/">Home</a></li>
              <li class="breadcrumb-item active" aria-current="page">
                Cập nhật thông tin
              </li>
            </ol>
          </nav>
        </div>
        <div class="col-6 mx-auto">
            <h3 class="text-center">Cập nhật thông tin</h3>
            <hr />
            <form:form action="/profile/update"
                    method="post"
                    modelAttribute="updateUser"
                    class="row g-3"
                    enctype="multipart/form-data"
                    >
                <form:input path="id" type="hidden" class="form-control" />
                <div class="col-md-12">
                    <c:set var="errorFullName">
                        <form:errors path="fullName" cssClass="invalid-feedback"/>
                    </c:set>
                    <label for="fullName" class="form-label">Họ và tên:</label>
                    <form:input path="fullName" type="text" class="form-control ${not empty errorFullName ? 'is-invalid' : ''}"/>
                    ${errorFullName}
                </div>
                <div class="col-md-12">
                    <label for="address" class="form-label">Địa chỉ:</label>
                    <form:input path="address" type="text" class="form-control" />
                </div>
                <div class="col-md-12">
                    <c:set var="errorPhone">
                        <form:errors path="phone" cssClass="invalid-feedback"/>
                    </c:set>
                    <label for="phone" class="form-label">Số điện thoại:</label>
                    <form:input path="phone" type="text" class="form-control ${not empty errorPhone ? 'is-invalid' : ''}" />
                    ${errorPhone}
                </div>
                <div class="col-12">
                    <label for="avatarImage" class="form-label">Ảnh đại diện:</label>
                    <form:input path="" type="file" class="form-control" id="avatarImage"
                                accept=".png, .jpg, .jpeg" name="avatarFile"/>
                    <form:input path="avatar" type="hidden" />
                </div>
                <div class="col-12">
                    <img alt="Avatar Preview" id="avatarPreview"
                        style="max-height: 250px; display: none;" class="mx-auto rounded-circle"/>
                </div>
                <div class="col-md-4">
                    <input type="submit" class="btn btn-primary" value="Cập nhật" />
                    <a href="/user-profile" class="btn btn-secondary">Quay về</a>
                </div>
            </form:form>
        </div>
      </div>
    </div>
    <!-- Cart Page End -->

    <jsp:include page="../layout/footer.jsp" />

    <!-- Back to Top -->
    <a
      href="#"
      class="btn btn-primary border-3 border-primary rounded-circle back-to-top"
      ><i class="fa fa-arrow-up"></i
    ></a>

    <!-- JavaScript Libraries -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="/client/lib/easing/easing.min.js"></script>
    <script src="/client/lib/waypoints/waypoints.min.js"></script>
    <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
    <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>

    <!-- Template Javascript -->
    <script src="/client/js/main.js"></script>
  </body>
</html>
