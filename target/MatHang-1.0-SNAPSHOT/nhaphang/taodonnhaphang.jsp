<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="model.DonNhap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.text.NumberFormat" %>

<%
    DonNhap don = (DonNhap) request.getAttribute("bill");
    String newCode = (String) request.getAttribute("code");
    String date, code;
    Boolean isCreate = (Boolean) request.getAttribute("isCreate");
    Locale localeVN = new Locale("vi", "VN");
    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
    SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:hh:ss");
    if (isCreate) {
        date = dateFormat1.format(new Date());
        code = newCode;
    } else {
        date = dateFormat2.format(don.getCreatedTime());
        code = don.getCode();
    }
%>

<%@ page contentType="text/html;charset=UTF-8" %>
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

    <!-- css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/nhaphang.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" type="text/css">
    <style>
        #dropdown-product, #dropdown-provider {
            position: absolute;
            will-change: transform;
            display: none;
            max-height: 300px;
            overflow: auto;
            left: 0;
            top: 0;
            transform: translate3d(0px, 38px, 0px);
        }

        .dropdown-item:active {
            background-color: rgba(233, 236, 239, 255);
        }
    </style>
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
                    Quản lý mặt hàng
                </div>
            </div>
        </li>
        <li class="sidebar-item nav-item active" value="1">
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
            <div id="logout-btn" class="d-flex align-items-center">
                <i class="fa-solid fa-arrow-right-from-bracket fa-lg"></i>
                Đăng xuất
            </div>
        </menu>


        <div class="body">

            <div class="top container-fluid pl-3 pr-3 pt-2 pb-2" style="width: 99%">
                <div class="d-flex justify-content-between">
                    <div class="d-flex">
                        <a class="pt-1" href="quan-ly-nhap-hang"
                           style="margin-right:10px;"><i
                                class="fa-solid fa-arrow-left"></i></a>
                        <h4>Đơn nhập hàng</h4>
                    </div>

                    <%if (isCreate) {%>
                    <button <%if(don != null && don.getHasConfirmed()) {%>hidden<%}%>
                            class="button btn-create" id="create-bill" style="width: 80px">
                        Tạo
                    </button>
                    <%} else if (!don.getHasConfirmed()) {%>
                    <button <%if(don != null && don.getHasConfirmed()) {%>hidden<%}%>
                            class="button btn-create" id="confirm-bill" style="width: 80px">
                        Duyệt
                    </button>
                    <%}%>
                </div>
                <div class="d-flex">
                    <h3>
                        <%=code%>
                        <input hidden value="<%=code%>" id="code">
                        <%if (don != null) {%>
                        <input hidden value="<%=don.getId()%>" id="bill-id">
                        <%}%>
                    </h3>
                    <p class="pt-2" style="margin-left: 5px;">
                        <%=date%>
                    </p>
                </div>
            </div>

            <div class="ncc pt-2 pb-2 container-fluid mb-3">
                <div class="d-flex justify-content-between pb-2 pt-2">
                    <h4>Thông tin nhà cung cấp</h4>
                    <div>
                        <button <%if(!isCreate) {%>hidden<%}%> type="button" title="Thêm nhà cung cấp"
                                class="btn btn-primary btn-save btn-ccc"
                                data-bs-toggle="modal" data-bs-target="#staticBackdrop"><i
                                class="fa-solid fa-plus"></i></button>
                    </div>
                </div>

                <form method="post">
                    <div class="input dropdown">
                        <input id="key-provider" class="form-control mb-3 " type="text"
                               placeholder="Tìm kiếm nhà cung cấp..." required
                               <%if(!isCreate) {%>readonly value="<%=don.getProvider().get()%>" <%}%>

                        >
                        <div class="rbt-menu dropdown-menu w-100" id="dropdown-provider"
                             aria-labelledby="actionToggle"></div>
                    </div>


                </form>

            </div>

            <div class="ncc pt-2 pb-2 container-fluid">
                <h4>Thông tin sản phẩm</h4>
                <form method="post">
                    <div <%if(!isCreate) {%>hidden<%}%> class="input dropdown">
                        <input id="key-product" class="form-control mb-3 " type="text"
                               placeholder="Tìm kiếm sản phẩm...">
                        <div class="rbt-menu dropdown-menu w-100" id="dropdown-product"></div>
                    </div>
                </form>


                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <thead class="table-primary">
                        <tr style='text-align: center;'>
                            <th style="width: auto">Mã SKU</th>
                            <th style="width: 50%">Tên sản phẩm</th>
                            <th>Đơn giá</th>
                            <th>Số lượng</th>
                            <th style="width: 10%">Thành tiền</th>
                        </tr>
                        </thead>
                        <tbody class="table-body">
                        <%
                            if (don != null) {
                                Iterator it = don.getProducts().iterator();
                                while (it.hasNext()) {
                                    DonNhap.Product pro = (DonNhap.Product) it.next();
                        %>
                        <tr>
                            <td><%=pro.getCode()%>
                            </td>
                            <td><%=pro.getName()%>
                            </td>
                            <td><%=pro.getPrice()%>
                            </td>
                            <td><%=pro.getQuantity()%>
                            </td>
                            <td><%=currencyVN.format(pro.getQuantity() * pro.getPrice())%>
                            </td>
                        </tr>
                        <%
                                }
                            }
                        %>
                        </tbody>
                    </table>
                </div>
                <%--                <div>--%>
                <%--                    <label for="input-discount">Chiết khấu</label>--%>
                <%--                    <input id="input-discount" class="form-control mt-1" type="number" min="0"--%>
                <%--                           onkeydown="return keydown(event)"--%>
                <%--                           placeholder="Nhập số tiền chiết khấu" onchange="onDiscount(this.value)">--%>
                <%--                </div>--%>

                <hr>

                <div class="d-flex justify-content-end" style="margin-right: 50px;">
                    <p></p>
                    <div class="bill">
                        <div class="amount d-flex mb-2">
                            <span>Số lượng:</span>
                            <span style="margin-left: 100px;"
                                  id="total-product"><%if (isCreate) {%>0<%} else {%><%=don.getTotal()%><%}%></span>
                        </div>
                        <div class="amount d-flex mb-2">
                            <span>Tổng tiền:</span>
                            <span style="margin-left: 95px;"
                                  id="total-price"><%if (isCreate) {%>0 đ<%} else {%><%=currencyVN.format(don.getTotalPrice())%><%}%></span>
                        </div>
                        <%--                        <div class="amount d-flex mb-2">--%>
                        <%--                            <span>Chiết khấu:</span>--%>
                        <%--                            <span id="discount" style="margin-left: 90px;">0 ₫</span>--%>
                        <%--                        </div>--%>
                        <%--                        <div class="amount d-flex mb-2">--%>
                        <%--                            <span>Chi phí:</span>--%>
                        <%--                            <span style="margin-left: 112px;">0 ₫</span>--%>
                        <%--                        </div>--%>
                        <%--                        <div class="amount d-flex mb-2">--%>
                        <%--                            <span>tiền cần trả:</span>--%>
                        <%--                            <span id="balance" style="margin-left: 85px;">0 ₫</span>--%>
                        <%--                        </div>--%>
                    </div>
                </div>
            </div>
            <%if (!isCreate && don.getHasConfirmed()) {%>
            <div class="ncc pt-2 pb-2 mt-3 container-fluid mb-3">
                <h4>Thanh toán</h4>
                <div class="d-flex justify-content-between">
                    <%if (don.getHasPaid()) {%>Đã thanh toán vào: <%=dateFormat2.format(don.getPaymentTime())%><%}%>
                    <%--                    <p id="paid" style="display:block">Đã thanh toán: 0đ</p>--%>
                    <%--                    <p id="rest" style="display:block">Còn phải trả: 52.000.000đ</p>--%>
                    <p></p>
                    <button <%if(don.getHasPaid()){%>hidden<%}%> class="button btn-create p-1 mb-2"
                            id="confirm-payment">
                        Xác nhận thanh toán
                    </button>
                </div>
            </div>

            <div class="ncc pt-2 pb-2 container-fluid ">
                <h4>Nhập kho</h4>
                <div class="d-flex justify-content-between">
                    <%if (don.getHasReceived()) {%>Đã nhập kho vào: <%=dateFormat2.format(don.getImportTime())%><%}%>
                    <%--                    <p id="code" style="display:block">Mã nhập kho: PN000001</p>--%>
                    <%--                    <p id="date" style="display:block">Ngày nhập kho: 05/04/2022</p>--%>
                    <%--                    <p id="product" style="display:block">Sản phẩm: Abc</p>--%>
                    <%--                    <p id="total" style="display:block">Tổng tiền: 52.000.000</p>--%>
                    <p></p>

                    <button <%if(don.getHasReceived()){%>hidden<%}%> class="button btn-create p-1 mb-2"
                            id="confirm-import">
                        Xác nhận nhập kho
                    </button>
                </div>
            </div>

            <%--            <div class="ncc pt-2 pb-2 mt-3 container-fluid mb-3" style="display:block" id="rollBack">--%>
            <%--                <h4>Hoàn trả</h4>--%>
            <%--                <div class="d-flex justify-content-between">--%>
            <%--                    <p></p>--%>

            <%--                    <button class="button btn-create p-2" id="btn-confirm-payment" style="display:block">--%>
            <%--                        Xác nhận thanh toán--%>
            <%--                    </button>--%>
            <%--                </div>--%>
            <%--            </div>--%>
            <%}%>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
     aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="staticBackdropLabel">Thêm nhà cung cấp</h5>
                <button style="margin-right: 12px;" type="button" class="btn-close" data-bs-dismiss="modal"
                        aria-label="Close"></button>
            </div>
            <form method="post" action="them-nha-cung-cap" id="formThemNcc">
            <div class="modal-body">
                <div class="row align-items-start">
                    <div class="col">
                        <div class="item" style="position: relative">
                            <label class="form-label">Mã nhà cung cấp<span style="color: red"> *</span></label>
                            <input type="text" class="form-control" placeholder="" name="codeNCC" id="codeNCC">
                            <span id="warningCodeNcc" class="warningText" hidden="false" ></span>
                        </div>
                        <div class="item" style="position: relative">
                            <label class="form-label">Email<span style="color: red"> *</span></label>
                            <input type="text" class="form-control" placeholder="" name="emailNCC" id="emailNCC">
                            <span id="warningMailNcc" class="warningText" hidden="false"></span>
                        </div>
                        <div class="item" style="position: relative">
                            <label class="form-label">Địa chỉ<span style="color: red"> *</span></label>
                            <input type="text" class="form-control" placeholder="" name="addressNCC"id="addressNCC">
                            <span id="warningAddressNcc" class="warningText" hidden="false"></span>
                        </div>
                    </div>
                    <div class="col">
                        <div class="item" style="position: relative">
                            <label class="form-label">Tên nhà cung cấp<span style="color: red"> *</span></label>
                            <input type="text" class="form-control" placeholder="" name="nameNCC"id="nameNCC">
                            <span id="warningNameNcc" class="warningText" hidden="false"></span>
                        </div>
                        <div class="item" style="position: relative">
                            <label class="form-label">Số điện thoại<span style="color: red"> *</span></label>
                            <input type="text" class="form-control" placeholder="" name="phoneNCC" id="phoneNCC">
                            <span id="warningPhoneNcc" class="warningText" hidden="false"></span>
                        </div>
<%--                        <div>--%>
<%--                            <label style="padding-bottom: 8px;" class="form-label">Khu vực</label><br>--%>
<%--                            <div class="form-check form-check-inline">--%>
<%--                                <input class="form-check-input" type="radio" name="inlineRadioOptions"--%>
<%--                                       id="inlineRadio" value="option1" checked>--%>
<%--                                <label class="form-check-label" for="inlineRadio1">Miền Bắc</label>--%>
<%--                            </div>--%>
<%--                            <div class="form-check form-check-inline">--%>
<%--                                <input class="form-check-input" type="radio" name="inlineRadioOptions"--%>
<%--                                       id="inlineRadio1" value="option2">--%>
<%--                                <label class="form-check-label" for="inlineRadio2">Miền Trung</label>--%>
<%--                            </div>--%>
<%--                            <div class="form-check form-check-inline">--%>
<%--                                <input class="form-check-input" type="radio" name="inlineRadioOptions"--%>
<%--                                       id="inlineRadio2" value="option2">--%>
<%--                                <label class="form-check-label" for="inlineRadio2">Miền Nam</label>--%>
<%--                            </div>--%>
<%--                        </div>--%>
                    </div>
                </div>
                <aside class="col-lg-2 col-sm-4">

                </aside>
            </div>

            <div class="modal-footer">
                <button type="submit" class="btn btn-primary">Thêm</button>
            </div>
            </form>
        </div>
    </div>
</div>
<%--<div id="popupNotify">--%>
<%--    <div id="popupNotifyBody">--%>
<%--    </div>--%>
<%--</div>--%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
    var data = {
        providerId: -1,
        products: [],
        discount: 0
    }

    function productHtml(object) {
        return `<button type="button" id="product-\${object['id']}" role="option" class="dropdown-item item-product"
                    onclick="productItemClicked('\${object['id']}', '\${object['code']}', '\${object['name']}')">
                    <div style="color: black;padding: 4px 0">\${object['code']} - \${object['name']}</div>
                </button>
                <hr style="margin: 0 6px;">`
    }

    function providerHtml(object) {
        return `<button type="button" id="provider-\${object['id']}" role="option" class="dropdown-item item-provider"
                    onclick="providerItemClicked('\${object['id']}','\${object['code']}', '\${object['name']}')">
                    <div style="color: black;padding: 4px 0">\${object['code']} - \${object['name']}</div>
                </button>
                <hr style="margin: 0 6px;">`
    }

    function addRow(id, code, name) {
        const row = `<tr>
                        <td style="text-align: center; vertical-align: middle">\${code}</td>
                        <td style="vertical-align: middle">\${name}</td>
                        <td><input id="product-price-\${id}" class="form-control mb-0" type="number" min="0"
                               onkeydown="return keydown(event)"
                               placeholder="Nhập đơn giá" onchange="onTableChange('\${id}')"></td>
                        <td><input id="product-quantity-\${id}" class="form-control mb-0" type="number" min="0"
                               onkeydown="return keydown(event)"
                               placeholder="Nhập số lượng" onchange="onTableChange('\${id}')"></td>
                        <td style="vertical-align: middle;text-align: center" id="product-total-\${id}">
                            0 ₫
                        </td>
                    </tr>`
        $(".table-body").append(row)
    }

    function keydown(e) {
        if (!((e.keyCode > 95 && e.keyCode < 106)
            || (e.keyCode > 47 && e.keyCode < 58)
            || e.keyCode == 8)) {
            return false;
        }
    }

    function onTableChange(id) {
        const quantity = $(`#product-quantity-` + id).val()
        const price = $(`#product-price-` + id).val()
        const total = quantity * price
        const product = data['products'].find(item => item.id == id)
        product['quantity'] = Number(quantity)
        product['price'] = Number(price)
        product['total'] = Number(total)
        $('#product-total-' + id).text(format(total))
        calculateBill()
    }

    // function onDiscount(value) {
    //     data.discount = Number(value)
    //     $('#discount').text(format(data.discount))
    //     calculateBill()
    // }

    function calculateBill() {
        let totalProduct = 0
        let totalPrice = 0
        for (const x in data.products) {
            if (x < 0) break
            totalProduct += Number(data.products[x].quantity)
            totalPrice += Number(data.products[x].total)
        }
        $('#total-product').text(totalProduct)
        $('#total-price').text(format(totalPrice))

        let balance = (totalPrice - data.discount < 0) ?
            0 : totalPrice - data.discount
        $('#balance').text(format(balance))
    }

    function getBodyRequest() {
        return {
            code: $("#code").val(),
            products: data.products,
            // discount: data.discount,
            providerId: data.provider
        }
    }

    function format(number) {
        return new Intl.NumberFormat(
            'vi-VN', {
                style: 'currency',
                currency: 'VND'
            }
        ).format(number)
    }

    function providerItemClicked(id, code, name) {
        $('#key-provider').val(`\${code} - \${name}`)
        data.provider = Number(id)
    }

    function productItemClicked(id, code, name) {
        for (const x in data.products) {
            if (data.products[x].id == Number(id))
                return
        }
        data['products'].push({
            id: Number(id),
            quantity: 0,
            price: 0,
            total: 0
        })
        addRow(id, code, name)
    }

    $(document).on("click", function () {
        const elPv = document.querySelector('#key-provider');
        if (elPv !== document.activeElement) {
            $("#dropdown-provider").css("display", "none");
        }
        const elPd = document.querySelector('#key-product');
        if (elPd !== document.activeElement) {
            $("#dropdown-product").css("display", "none");
        }
    })

    $('.class-number').on('keydown', function (e) {
        if (!((e.keyCode > 95 && e.keyCode < 106)
            || (e.keyCode > 47 && e.keyCode < 58)
            || e.keyCode == 8)) {
            return false;
        }
    })

    $('#key-provider').on('focus', function (event) {
        $("#dropdown-provider").css("display", "block");
    })
    $('#key-product').on('focus', function (event) {
        $("#dropdown-product").css("display", "block");
    })

    $('#key-product').on('keyup', function () {
        searchProduct()
    });
    $('#key-provider').on('keyup', function () {
        searchProvider()
    });


    function searchProduct() {
        const value = $("#key-product").val()
        $.ajax({
            url: `/MatHang/search-product?name =\${value}`,
            type: 'GET',
            dataType: 'json',
        }).done(function (data) {
            $('#dropdown-product').empty()
            for (const x in data) {
                $('#dropdown-product').append(productHtml(data[x]))
            }
            if (data.length == 0) {
                $('#dropdown-product').append(` < span style = "padding-left: 8px; color: black" >Không tìm thấy</span>`)
            }
        });
    }

    function searchProvider() {
        var value = $("#key-provider").val()
        $.ajax({
            url: `/MatHang/search-provider?name=\${value}`,
            type: 'GET',
            dataType: 'json',
        }).done(function (data) {
            $('#dropdown-provider').empty()
            for (const x in data) {
                $('#dropdown-provider').append(providerHtml(data[x]))
            }
            if (data.length == 0) {
                $('#dropdown-provider').append(`<span style="padding-left: 8px; color: black">Không tìm thấy</span>`)
            }
        });
    }

    $("#create-bill").on('click', function () {
        debugger
        var data = getBodyRequest();
        $.ajax({
            url: `/MatHang/create-bill`,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(data),
            success: function (result) {
                if (result.isSuccessful) {
                    setShowMessageAferReload(result.message)
                    window.location.href = `/MatHang/cap-nhat-don-nhap-hang?id=\${result.id}`
                }
            }
        });
    })

    $("#confirm-bill").on('click', function () {
        var data = getBodyRequest();
        $.ajax({
            url: `/MatHang/confirm-bill`,
            type: 'POST',
            dataType: 'json',
            data: {'id': $('#bill-id').val()},
            success: function (result) {
                setShowMessageAferReload(result.message)
            }
        });
    })

    $("#confirm-import").on('click', function () {
        var data = getBodyRequest();
        $.ajax({
            url: `/MatHang/confirm-import`,
            type: 'POST',
            dataType: 'json',
            data: {'id': $('#bill-id').val()},
            success: function (result) {
                setShowMessageAferReload(result.message)
            }
        });
    })

    $("#confirm-payment").on('click', function () {
        var data = getBodyRequest();
        $.ajax({
            url: `/MatHang/confirm-payment`,
            type: 'POST',
            dataType: 'json',
            data: {'id': $('#bill-id').val()},
            success: function (result) {
                setShowMessageAferReload(result.message)
            }
        });
    })

    function setShowMessageAferReload(message) {
        sessionStorage.showMessage = 'true';
        sessionStorage.message = message;
        window.location.reload();
    }

    function init() {
        if (sessionStorage.showMessage == 'true') {
            sessionStorage.showMessage = 'false';
            alert(sessionStorage.message);
        }
        searchProvider()
        searchProduct()
    }

    init()


</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="module" src="${pageContext.request.contextPath}/js/themnhacungcap.js"></script>

</body>

</html>