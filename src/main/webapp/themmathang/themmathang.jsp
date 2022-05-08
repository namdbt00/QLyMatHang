<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 4/15/2022
  Time: 10:46 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm mặt hàng</title>
    <!-- bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <!-- fontawesome  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">

    <!-- style -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">

    <!--  -->
    <link class="jsbin" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
    <script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.0/jquery-ui.min.js"></script>
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
            <div  class="nav-link d-flex">
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

        </menu>
        <% if ((application.getAttribute("check") == "1") && (application.getAttribute("messages") != null)) {%>
        <div id="popupNotify">
            <div id="popupNotifyBody">
                <%= application.getAttribute("messages")%>
                <% application.setAttribute("check", "0"); %>
            </div>
        </div>
        <% }%>

        <!--  phần nhét code -->
        <form id="formThem" action="<%= request.getContextPath() %>/them-mat-hang" class="form__add-product form-submit" method="POST" enctype="multipart/form-data" autocomplete="off">
            <div class="app__add-product">
                <div class="content__header">
                    <div class="content__name defaultCursor">
                        <a href="/MatHang/trangchu">
                            <i class="fa-solid fa-arrow-left-long me-2 fa-lg" style="cursor: pointer"></i>
                        </a>
                        Thêm mặt hàng
                    </div>
                    <!-- <div class="btn-accept"> -->
                    <!-- <a href="" class="btn-accept-link">Xác nhận</a> -->
                    <div id="successNotify"></div>
                    <button type="submit" class="btn-accept btn-accept-button btn-save">Xác nhận</button>
                    <!-- </div> -->
                </div>
                <div class="content__attributes">
                    <div class="content__attributes-items">
                        <div class="item-name defaultCursor">Thông tin chung</div>
                        <div class="item-content container">
                            <div class="item-content__main-row row">
                                <div class="col-9 general-info">
                                    <div class="container">
                                        <div class="row general-info__basic">
                                            <div class="row">
                                                <div class="atb col-4">
                                                    <span class="atb-name defaultCursor">Mã mặt hàng<span style="color: red"> *</span></span>
                                                    <input type="text" id="productid" name="productid" class="atb-input input-group form-control shadow-none form-control shadow-none" placeholder="Nhập Mã mặt hàng"  autofocus >
                                                    <span id="warningProductID" class="warningText validateWarnning" hidden="true"></span>
                                                </div>
                                                <div class="atb col-4">
                                                    <span class="atb-name defaultCursor">Tên mặt hàng<span style="color: red"> *</span></span>
                                                    <input type="text" id="productname" name="productname" class="atb-input input-group form-control shadow-none" placeholder="Nhập Tên mặt hàng" >
                                                    <span id="warningProductname" class="warningText validateWarnning" hidden="true"></span>
                                                </div>
                                                <div class="atb col-4">
                                                    <span class="atb-name defaultCursor">Giá bán lẻ (vnđ)<span style="color: red"> *</span></span>
                                                    <input type="text" id="productretailprice" name="productretailprice" class="atb-input input-group form-control shadow-none" placeholder="Nhập Giá bán lẻ" >
                                                    <span id="warningProductRetailprice" class="warningText validateWarnning" hidden="true"></span>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="atb col-4">
                                                    <span class="atb-name defaultCursor">Giá bán sỉ (vnđ)<span style="color: red"> *</span></span>
                                                    <input pattern="^\d+(?:\.\d{1,2})?$" type="text" id="productwholesaleprice" name="productwholesaleprice" class="atb-input input-group form-control shadow-none" placeholder="Nhập Giá bán sỉ" >
                                                    <span id="warningProductwholesaleprice" class="warningText validateWarnning" hidden="true"></span>
                                                </div>
                                                <div class="atb col-4">
                                                    <span class="atb-name defaultCursor">Đơn vị tính<span style="color: red"> *</span></span>
                                                    <input type="text" id="productunit" name="productunit" class="atb-input input-group form-control shadow-none" placeholder="Nhập Đơn vị tính" >
                                                    <span id="warningProductunit" class="warningText validateWarnning" hidden="true"></span>
                                                </div>
<%--                                                <div class="atb col-4">--%>
<%--                                                    <span class="atb-name">Tồn kho ban đầu<span style="color: red"> *</span></span>--%>
<%--                                                    <input type="text" id="stock" name="stock" class="atb-input input-group form-control shadow-none" placeholder="Nhập Tồn kho ban đầu" >--%>
<%--                                                    <span id="warningStock" class="warningText" hidden="true"></span>--%>
<%--                                                </div>--%>
                                                <div class="atb col-4">
                                                    <span class="atb-name defaultCursor">Khối lượng<span style="color: red"> *</span></span>
                                                    <div class="weight">
                                                        <input type="text" id="productweight" name="productweight" class="atb-input input-group form-control shadow-none" placeholder="Nhập Khối lượng" >
                                                        <select name="weightunit" id="weightunit" class="weight-unit form-select" style="box-shadow: none;">
                                                            <option value="1" class="weight-unit-item">g</option>
                                                            <option value="2" class="weight-unit-item">kg</option>
                                                        </select>
                                                        <span id="warningProductweight" class="warningText validateWarnning" hidden="true"></span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="atb col-4">
                                                    <span class="atb-name defaultCursor">Phân loại</span>
                                                    <select name="productcategory" id="productcategory" class="atb-input input-group form-control shadow-none category-select">
                                                        <c:forEach items="${listCategory}" var="category">
                                                            <option value="${category.getCategoryId()}" class="category-item">
                                                                <c:out value="${category.getName()}" />
                                                            </option>
<%--                                                            <option value="quanao" class="category-item">Quần áo</option>--%>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="general-info__description" style="position: relative;">
                                        <span class="atb-name defaultCursor">Mô tả</span>
                                        <textarea  maxlength="255" class="info-des form-control shadow-none" id="ifodesscription" name="ifodesscription" rows="4" cols="50" placeholder="Nhập Mô tả"></textarea>
                                        <span id="warningInfoDes" class="warningTextArea validateWarnning" hidden="true"></span>
                                    </div>
                                </div>
                                <div class="col-3 product-image">
                                    <span class="defaultCursor">Ảnh (.jpg)<span style="color: red"> *</span></span>
                                    <input type="file" id="img" name="img" class="img form-control shadow-none" >
                                    <img src="https://endlessicons.com/wp-content/uploads/2012/11/image-holder-icon.png" alt="Your image" id="img-chose" class="img-chose">
                                    <span id="warningImg" class="warningImg validateWarnning" hidden="true"></span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="content__attributes-items" style="margin-left: 14px;">
                        <div class="item-name defaultCursor">Thuộc tính bổ sung</div>
                        <div class="item-content">
                            <input type="hidden" id="numatb" name="numatb" value="0">
                            <table id="table-atb">
                                <tr>
                                    <th class="defaultCursor" style="width: 306px;">Tên thuộc tính</th>
                                    <th class="defaultCursor" style="width: 305px;">Giá trị</th>
                                    <th class="defaultCursor">Hành động</th>
                                </tr>
                                <!-- <tr>
                                    <td><input type="text" placeholder="Nhập tên thuộc tính" class="attribute-name"></td>
                                    <td><input type="text" placeholder="Nhập giá trị" class="attribute-value"></td>
                                    <td><span id="delete-attribute" onclick="deleteAtb()" class="delete-row">Xóa</span></td>
                                </tr> -->
                            </table>
                            <div class="table-add-row" id="addAtb">

                                    <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="white" class="bi bi-plus" viewBox="0 0 16 16">
                                        <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
                                    </svg>

                            </div>
                        </div>
                    </div>
                    <div class="content__attributes-items" style="margin-left: 14px;">
                        <div class="item-name defaultCursor">Đơn vị quy đổi</div>
                        <div class="item-content">
                            <input type="hidden" id="numunit" name="numunit" value="0">
                            <table id="table-unit">
                                <tr>
                                    <th class="defaultCursor" style="width: 306px;">Tên đơn vị</th>
                                    <th class="defaultCursor" style="width: 305px;">Số lượng</th>
                                    <th class="defaultCursor">Hành động</th>
                                </tr>
                                <!-- <tr>
                                    <td><input type="text" placeholder="Nhập tên đơn vị" class="unit-name"></td>
                                    <td><input type="text" placeholder="Nhập giá trị" class="unit-value"></td>
                                    <td><span id="delete-unit" onclick="deleteUnit()" class="delete-row">Xóa</span></td>
                                </tr> -->
                            </table>
                            <div class="table-add-row btn-save" id="addUnit" >
                                <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="white" class="bi bi-plus" viewBox="0 0 16 16">
                                    <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
                                </svg>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" id="attributeJSON" name="attributeJSON" value="">
                </div>
            </div>
        </form>
    </div>
</div>


<!-- bootstrap -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.6.0.js"
        integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<script type="module" src="${pageContext.request.contextPath}/js/themmathang.js"></script>
</body>

</html>