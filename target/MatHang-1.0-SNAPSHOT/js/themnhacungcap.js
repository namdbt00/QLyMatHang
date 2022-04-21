import Common from "./common.js";
import Resource from "./resource.js";

initEvent();

function initEvent() {
    validateInput();

    validateWhenSubmit();
    Common.sidebarFunction();
}

function validateInput() {
    $("#codeNCC").on('keyup focusout', function (e) {
        validateCodeNcc($(this));
    });
    $("#emailNCC").on('keyup focusout', function (e) {
        validateEmailNcc($(this));
    });
    $("#addressNCC").on('keyup focusout', function (e) {
        validateAddressNcc($(this));
    });
    $("#nameNCC").on('keyup focusout', function (e) {
        validateNameNcc($(this));
    });
    $("#phoneNCC").on('keyup focusout', function (e) {
        validatePhoneNcc($(this));
    });
}

function validateWhenSubmit(){
    $("#formThemNcc").submit(function (e){
        var result = "";
        result = validateCodeNcc($("#codeNCC"));
        if(result === ""){
            result = validateEmailNcc($("#emailNCC"));
        }
        if(result === ""){
            result = validateAddressNcc($("#addressNCC"));
        }
        if(result === ""){
            result = validateNameNcc($("#nameNCC"));
        }
        if(result === ""){
            result = validatePhoneNcc($("#phoneNCC"));
        }
        if (result !== "") {
            e.preventDefault();
        }
    })
}

function validateCodeNcc(control){
    var result = "";
    result = Common.ValidateInputBase(control, Resource.ValidateType.Required);
    if (result === "") {
        result = Common.ValidateInputBase(control, Resource.ValidateType.SpecialChar)
    }
    if (result === "") {
        result = Common.ValidateLength(control, 0, 100);
    }
    if (result === "") {
        $("#warningCodeNcc").attr("hidden", true);
    } else {
        $("#warningCodeNcc").text(result);
        $("#warningCodeNcc").attr("hidden", false);
    }
    return result;
}

function validateEmailNcc(control){
    debugger
    var result = "";
    result = Common.ValidateInputBase(control, Resource.ValidateType.Required);
    if (result === "") {
        result = Common.ValidateInputBase(control, Resource.ValidateType.Email)
    }
    if (result === "") {
        result = Common.ValidateLength(control, 0, 100);
    }
    if (result === "") {
        $("#warningMailNcc").attr("hidden", true);
    } else {
        $("#warningMailNcc").text(result);
        $("#warningMailNcc").attr("hidden", false);
    }
    return result;
}

function validateAddressNcc(control){
    var result = "";
    result = Common.ValidateInputBase(control, Resource.ValidateType.Required);
    if (result === "") {
        result = Common.ValidateLength(control, 0, 100);
    }
    if (result === "") {
        $("#warningAddressNcc").attr("hidden", true);
    } else {
        $("#warningAddressNcc").text(result);
        $("#warningAddressNcc").attr("hidden", false);
    }
    return result;
}

function validateNameNcc(control){
    var result = "";
    result = Common.ValidateInputBase(control, Resource.ValidateType.Required);
    if (result === "") {
        result = Common.ValidateLength(control, 0, 100);
    }
    if (result === "") {
        $("#warningNameNcc").attr("hidden", true);
    } else {
        $("#warningNameNcc").text(result);
        $("#warningNameNcc").attr("hidden", false);
    }
    return result;
}

function validatePhoneNcc(control){
    var result = "";
    result = Common.ValidateInputBase(control, Resource.ValidateType.Required);
    if (result === "") {
        result = Common.ValidateInputBase(control, Resource.ValidateType.NegaNumber)
    }
    if (result === "") {
        $("#warningPhoneNcc").attr("hidden", true);
    } else {
        $("#warningPhoneNcc").text(result);
        $("#warningPhoneNcc").attr("hidden", false);
    }
    return result;
}