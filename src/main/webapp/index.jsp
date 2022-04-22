<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!------ Include the above in your HEAD tag ---------->

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
          integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" type="text/css">
    <title>Đăng nhập</title>
</head>
<body>
<div id="logreg-forms">

    <form class="form-signin" action="CheckLoginServlet" method="post">
        <h1 class="h3 mb-3 font-weight-normal" style="text-align: center"> Đăng nhập</h1>

        <input name="user" type="text" id="inputEmail" class="form-control" placeholder="Tên tài khoản (*)"
               required="true" autofocus="true">
        <input name="pass" type="password" id="inputPassword" class="form-control" placeholder="Mật khẩu (*)"
               required="true">
        <p style="color: red">${requestScope.error}</p>
        <button class="btn btn-success btn-block" type="submit"><i class="fas fa-sign-in-alt"></i> Đăng nhập</button>
        <a href="#" id="forgot_pswd" onclick="quenmatkhau()">Quên mật khẩu</a>
        <hr>
        <!-- <p>Don't have an account!</p>  -->

        <button class="btn btn-primary btn-block" type="button" id="btn-signup" onclick="dangky()"><i
                class="fas fa-user-plus"></i> Đăng ký tài khoản mới
        </button>
    </form>

    <br>

</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
<script src="/script.js"></script>
<script type="module" src="${pageContext.request.contextPath}/js/login.js"></script>
<script>
    function dangky() {
        if (confirm("Không có chức năng dang ky ?")) {
            window.location.href = "/MatHang/"
        } else {
            window.location.href = "/MatHang/"
        }
    }

    function quenmatkhau() {
        if (confirm("Không có chức năng quên mật khẩu ?")) {
            window.location.href = "/MatHang/"
        } else {
            window.location.href = "/MatHang/"
        }
    }
</script>
</body>
</html>