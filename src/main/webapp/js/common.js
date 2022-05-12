import resource from "./resource.js";

var Common = Common || {};

Common.ValidateInputBase = function (inputData, type) {

    var result = "";

    // lấy giá trị input
    var inputText = "";
    inputText = $(inputData).val();

    switch (type) {
        case resource.ValidateType.Required:
            if (inputText.length == 0) {
                result = "Không được để trống";
            }
            break;
        case resource.ValidateType.Number:
            var reg = /^\d+$/;
            if (reg.test(inputText)) {
                result = "Chỉ được nhập số";
            }
            break;
        case resource.ValidateType.Email:
            var reg =
                /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
            if (!reg.test(inputText)) {
                result = "Email sai định dạng";
            }
            break;
        case resource.ValidateType.SpecialChar:
            var format = /[`!@#$%^&*()_+\-=\[\]{};':"\\|,<>\/?~]/;
            if (format.test(inputText)) {
                result = "Không để ký tự đặc biệt";
            }
            break;
        case resource.ValidateType.SpecialCharTextArea:
            var format = /[`!@#$%^&*()_+\-=\[\]{};':"\\|<>\/?~]/;
            if (format.test(inputText)) {
                result = "Không để ký tự đặc biệt";
            }
            break;
        case resource.ValidateType.FloatNumber:

            var format = /^\d+(?:\.\d{1,2})?$/;
            if (!format.test(inputText)) {
                result = "Nhập sai định dạng số: a.bc và >0";
            }
            break;
        case resource.ValidateType.NegaNumber:
            var value = parseFloat(inputText);
            if (value < 0) {
                result = "Không nhập số âm";
            }
            break;
        default:
            result = "";
            break;
    }
    return result;
}
Common.ValidateLength = function (inputData, minLength, maxLength) {
        // lấy giá trị input
        var inputText = $(inputData).val();
        if (inputText.length < minLength) {
            var result = "Tối thiểu " + minLength + " ký tự";
            return result;
        }
        // kiểm tra dộ dài chu
        if (inputText.length > maxLength) {
            var result = "Tối đa " + maxLength + " ký tự";
            return result;
        }
        return "";
}
Common.sidebarFunction= function()  {
    $(".sidebar-item").click(function () {
        debugger
        $(".sidebar-item").removeClass("active");
        $(this).addClass("active");
        var value = $(this).attr("value");
        if (value == 0) {
            window.location.href = "/MatHang/trangchu";
        } else if (value == 1) {
            window.location.href = "/MatHang/quan-ly-nhap-hang";
        }
    });
}
export default Common;