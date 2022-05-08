<%@ page import="java.util.Locale" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý mặt hàng</title>
    <!-- bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <!-- fontawesome  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">

    <!-- style -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">

</head>

<body>

<div id="page" class="d-flex">
    <nav id="sidebar" class="nav flex-column">
        <li class="nav-item logo">
            <div><i class="fa-brands fa-product-hunt fa-lg"></i>${sessionScope.account.name}</div>
        </li>
        <li class="sidebar-item nav-item active" value="0">
            <div class="nav-link d-flex">
                <div class="icon-item">
                    <i class="fa-solid fa-box-archive fa-lg"></i>
                </div>
                <div>
                    Quản lý mặt hàng
                </div>
            </div>
        </li>
        <li class="sidebar-item nav-item" value="1">
            <div class="nav-link d-flex">
                <div class="icon-item">
                    <i class="fa-solid fa-cart-plus fa-lg"></i>
                </div>
                <div>
                    Nhập hàng
                </div>
            </div>
        </li>
    </nav>
    <div class="flex-grow-1">
        <menu class="d-flex justify-content-end">
            <div id="logout-btn"  class="d-flex align-items-center">
                <i class="fa-solid fa-arrow-right-from-bracket fa-lg"></i>
                Đăng xuất
            </div>
        </menu>
        <!--  phần nhét code -->
        <main>
            <div class="header-main">
                <h4 style="font-weight: bold">Danh sách mặt hàng</h4>
            </div>
            <div class="background-main">
                <div class="toolbar d-flex justify-content-between">
                    <form action="search" method="post">
                        <div id="search" class="input-group mb-3">
                            <input name="txt" type="text" class="form-control shadow-none" placeholder="Tìm theo tên mặt hàng">
                            <button type="submit" class="input-group-text" title="Tìm kiếm"><i
                                    class="fa-solid fa-magnifying-glass"></i></button>
                        </div>
                    </form>
                    <div>
                        <button type="button" title="Thêm" id="btnAdd" class="btn btn-primary btn-save"><i
                                class="fa-solid fa-plus"></i></button>
                    </div>
                </div>
                <div id="product-data">
                    <table class="table table-borderless">
                        <thead class="sticky-top">
                        <tr>
                            <th scope="col">Mã mặt hàng</th>
                            <th scope="col">Tên mặt hàng</th>
                            <th scope="col">Gía bán lẻ</th>
                            <th scope="col">Gía bán sỉ</th>
                            <th scope="col">Số lượng</th>
                            <th class="table-function"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${data}" var="m">
                            <tr>
                                <td style="vertical-align: middle;" value="${m.getId()}"
                                    nameMatHang="${m.getName()}">${m.getCode()}</td>
                                <td style="vertical-align: middle;">${m.getName()}</td>
                                <td style="vertical-align: middle;">${m.getRetailPriceF()}</td>
                                <td style="vertical-align: middle;">${m.getWholesalePriceF()}</td>
                                <td style="vertical-align: middle;">${m.getQuantity()}</td>
                                <td class="table-function text-center">
                                    <button type="button" title="Sửa" class="btn btn-save btn-save-table">
                                        <i class="fa-solid fa-pencil"></i></button>
                                    <button type="button" title="Xóa" class="btn btn-delete btn-delete-table">
                                        <i class="fa-solid fa-trash"></i></button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </main>
    </div>
</div>


<!-- bootstrap -->
<script src="https://code.jquery.com/jquery-3.6.0.js"
        integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<script type="module">
    import Common from "./js/common.js";

    Common.sidebarFunction();
    $("#btnAdd").click(function () {
        debugger
        window.location.href = "them-mat-hang";
    });
    $(document).on('click', '.btn-save-table', function () {
        var value = $(this).parent().parent().children().first().attr('value');
        window.location.href = "sua-mat-hang?idEdit=" + value;
    });

    $(document).on('click', '.btn-delete-table', function () {
        var value = $(this).parent().parent().children().first().attr('value');
        var name = $(this).parent().parent().children().first().attr('nameMatHang');
        doDelete(value, name);
    });

    function doDelete(id, name) {
        if (confirm("Bạn có muốn xóa mặt hàng <" + name + ">")) {
            window.location = "delete?id=" + id;
        }
    }
    $("#logout-btn").click(function() {
        window.location.href = "/MatHang/";
        }
    );

</script>
</body>

</html>
