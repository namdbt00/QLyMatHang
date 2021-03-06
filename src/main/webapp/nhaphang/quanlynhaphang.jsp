<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.DonNhapHang" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%!
    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    Locale localeVN = new Locale("vi", "VN");
    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);

    public String formatTime(Date date) {
        if (date == null) return "";
        else return f.format(date);
    }

    public String formatNumber(Long number) {
        return number == null ? currencyVN.format(0) : currencyVN.format(number);
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin</title>


    <!-- library -->

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <!-- css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/nhaphang.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
</head>

<body>
<div id="page" class="d-flex">
    <nav id="sidebar" class="nav flex-column">
        <li class="nav-item logo">
            <div><i class="fa-brands fa-product-hunt fa-lg"></i>${sessionScope.account.name}</div>
        </li>
        <li class="sidebar-item nav-item " value="0">
            <div class="nav-link d-flex">
                <div class="icon-item">
                    <i class="fa-solid fa-box-archive fa-lg"></i>
                </div>
                <div>
                    Qu???n l?? m???t h??ng
                </div>
            </div>
        </li>
        <li class="sidebar-item nav-item active" value="1">
            <div class="nav-link d-flex">
                <div class="icon-item">
                    <i class="fa-solid fa-cart-plus fa-lg"></i>
                </div>
                <div>
                    Nh???p h??ng
                </div>
            </div>
        </li>
    </nav>
    <div class="flex-grow-1">
        <menu class="d-flex justify-content-end">
            <div id="logout-btn" class="d-flex align-items-center">
                <i class="fa-solid fa-arrow-right-from-bracket fa-lg"></i>
                ????ng xu???t
            </div>
        </menu>
        <!--  ph???n nh??t code -->
        <div class="body">

            <div class="top container-fluid p-3 d-flex justify-content-between" style="width: 99%">
                <h4 style="font-weight: bold">????n nh???p h??ng</h4>
                <div>
                    <a href="tao-don-nhap-hang">
                        <button type="button" title="T???o ????n nh???p h??ng" class="btn btn-primary btn-save"><i
                                class="fa-solid fa-plus"></i></button>
                    </a>
                </div>
            </div>

            <div class="list-order container-fluid">
                <ul class="nav">

                    <%--Ch???nh UI tab theo ???????ng d???n--%>
                    <li class="nav-item">
                        <% Boolean fromFilterPage = (Boolean) request.getAttribute("filter");
                            if (!fromFilterPage) {%>
                        <a id="tab1" class="nav-link " aria-current="page" href="quan-ly-nhap-hang"
                           style="<%="color: #2C80FE;padding-left: 0;padding-right: 0;border-bottom: 1px solid #000;"%>">T???t
                            c??? ????n h??ng</a>
                        <%} else {%>
                        <a id="tab1" class="nav-link " aria-current="page" href="quan-ly-nhap-hang"
                           style="<%="padding-left: 0;padding-right: 0;"%>">T???t c??? ????n h??ng</a>
                        <%} %>
                    </li>
                    <li class="nav-item">
                        <% if (fromFilterPage) {%>
                        <a id="tab2" class="nav-link " aria-current="page" href="quan-ly-nhap-hang-filter"
                           style="<%="color: #2C80FE;padding-left: 0;padding-right: 0;border-bottom: 1px solid #000;"%>">
                            ??ang giao d???ch</a>
                        <%} else {%>
                        <a id="tab2" class="nav-link " aria-current="page" href="quan-ly-nhap-hang-filter"
                           style="<%="padding-left: 0;padding-right: 0;"%>">??ang giao d???ch</a>
                        <%} %>
                    </li>
                </ul>

                <hr style="margin-top: 0;">

                <div>
                    <form method="post">
                        <div class="input">
                            <input id="search" class="form-control mb-3 shadow-none" type="text" name="keyword"
                                   style="width: 100%;"
                                   placeholder="T??m ki???m theo t??n ho???c nh?? cung c???p">
                        </div>
                    </form>
                </div>

                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <thead class="table">
                        <tr style='text-align: center;'>
                            <th>M?? ????n</th>
                            <th>T??n nh?? cung c???p</th>
                            <th>Thanh to??n</th>
                            <th>Th???i gian thanh to??n</th>
                            <th>Nh???p kho</th>
                            <th>Th???i gian nh???p kho</th>
                            <th>T???ng ti???n</th>
                            <th>Th???i gian t???o ????n</th>
                            <th>Th???i gian duy???t ????n</th>
                            <th>Thao t??c</th>
                        </tr>
                        </thead>
                        <tbody>

                        <%--Check xem c?? b???n ghi ko--%>
                        <%
                            ArrayList<DonNhapHang> list = (ArrayList<DonNhapHang>) request.getAttribute("list");
                            if (list.isEmpty()) {%>
                        <h5 style="text-align: center"><%="Kh??ng c?? ????n h??ng n??o ph?? h??p"%>
                        </h5>
                        <%} %>

                        <%--Gen b???ng--%>
                        <%
                            Iterator it = list.iterator();
                            while (it.hasNext()) {
                                DonNhapHang donNhapHang = (DonNhapHang) it.next();

                        %>
                        <tr>
                            <td style="text-align: center; vertical-align: middle"><%=donNhapHang.getTenDon()%>
                            </td>
                            <td style="vertical-align: middle;"><%=donNhapHang.getNhaCungCap()%>
                            </td>
                            <td style="vertical-align: middle;"><%=donNhapHang.getIsPayment() == 0 ? "??ang giao d???ch" : "???? thanh to??n"%>
                            </td>
                            <td style="text-align: center; vertical-align: middle"><%=formatTime(donNhapHang.getPaymentTime())%>
                            </td>
                            <td style="vertical-align: middle;"><%=donNhapHang.getIsImportToWarehouse() == 0 ? "Ch??a nh???p kho" : "???? nh???p kho"%>
                            </td>
                            <td style="text-align: center; vertical-align: middle"><%=formatTime(donNhapHang.getImportTime())%>
                            </td>
                            <td style="vertical-align: middle;"><%=formatNumber(donNhapHang.getTotalPrice())%>
                            </td>
                            <td style="text-align: center; vertical-align: middle"><%=formatTime(donNhapHang.getCreateDate())%>
                            </td>
                            <td style="text-align: center; vertical-align: middle"><%=formatTime(donNhapHang.getConfirmDate())%>
                            </td>
                            <td style="text-align: center"><a
                                    href="cap-nhat-don-nhap-hang?id=<%=donNhapHang.getDonId()%>">
                                <button type="button" title="Xem ????n nh???p h??ng" class="btn btn-primary btn-save"><i
                                        class="fa fa-eye"></i></button>
                            </a>
                            </td>
                        </tr>
                        <%} %>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<%--<div id="popupNotify">--%>
<%--    <div id="popupNotifyBody">--%>
<%--    </div>--%>
<%--</div>--%>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="module">
    // Auto focus v??o input


    document.getElementById("search").focus();
    $(".sidebar-item").click(function () {
        $(".sidebar-item").removeClass("active");
        $(this).addClass("active");
        var value = $(this).attr("value");
        if (value == 0) {
            window.location.href = "/MatHang/trangchu";
        } else if (value == 1) {
            window.location.href = "/MatHang/quan-ly-nhap-hang";
        }
    });
    $("#logout-btn").click(function() {
            window.location.href = "/MatHang/";
        }
    );
</script>
</body>

</html>
