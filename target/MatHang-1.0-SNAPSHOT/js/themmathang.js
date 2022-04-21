import Common from "./common.js";
import Resource from "./resource.js";

initEvent();

function initEvent() {
    validateInput();

    addUnit();
    addAtb();
    focusDes();
    readURL();
    deleteAtb();
    deleteUnit();

    validateWhenSubmit();
    validateWhenSubmitUpdate();
    showNotify();
    Common.sidebarFunction();
}
function showNotify(){
    setTimeout(function(){
       $("#popupNotify").hide();
    },3000);
}

function validateInput() {
    $("#productid").on('keyup focusout', function (e) {
        validateProductID($(this));
    });
    $("#productname").on('keyup focusout', function (e) {
        validateProductName($(this));
    });
    $("#productretailprice").on('keyup focusout', function (e) {
        validateProductRetailPrice($(this));
    });
    $("#productwholesaleprice").on('keyup focusout', function (e) {
        validateProductWholesaleprice($(this));
    });
    $("#productunit").on('keyup focusout', function (e) {
        validateProductUnit($(this));
    });
    // $("#stock").on('keyup focusout', function (e) {
    //     validateStock($(this));
    // });
    $("#productweight").on('keyup focusout', function (e) {
        validateProductweight($(this));
    });
    //$("#img").on('change', function (e) {
        validateImageFile();
    //});
    $("#ifodesscription").on('keyup focusout', function (e) {
        validateDescription($(this));
    });
    validateTableAtb();
    validateTableUnit();
}

function validateWhenSubmit(){
    $("#formThem").submit(function (e){
        var result = "";
        result = validateProductID($("#productid"));
        if(result == ""){
            result = validateProductName($("#productname"));
        }
        if(result == ""){
            result = validateProductRetailPrice($("#productretailprice"));
        }
        if(result == ""){
            result = validateProductWholesaleprice($("#productwholesaleprice"));
        }
        if(result == ""){
            result = validateProductUnit($("#productunit"));
        }
        if(result == ""){
            result = validateProductweight($("#productweight"));
        }
        if(result == ""){
            result = validateDescription($("#ifodesscription"));
        }
        if(result == ""){
            var url = $("#img").val();
            var ext = url.substring(url.lastIndexOf('.') + 1).toLowerCase();
            if (ext.length > 0 && (ext == "jpg"))
            {
                $("#warningImg").attr("hidden", true);
            } else if(ext.length <= 0) {
                $("#warningImg").text("Không hợp lệ");
                result = "Không hợp lệ";
                $("#warningImg").attr("hidden", false);
            }
        }
        if(result == ""){
            result = validateTableAtbWhenSubmit();
        }
        if(result == ""){
            result = validateTableUnitWhenSubmit();
        }

        if(result == ""){
            //Common.showNotify("Sửa thành công");
        }else{
            e.preventDefault();
        }
    })
}

function validateWhenSubmitUpdate(){
    $("#formSua").submit(function (e){
        var result = "";
        result = validateProductID($("#productid"));
        if(result == ""){
            result = validateProductName($("#productname"));
        }
        if(result == ""){
            result = validateProductRetailPrice($("#productretailprice"));
        }
        if(result == ""){
            result = validateProductWholesaleprice($("#productwholesaleprice"));
        }
        if(result == ""){
            result = validateProductUnit($("#productunit"));
        }
        if(result == ""){
            result = validateProductweight($("#productweight"));
        }
        if(result == ""){
            result = validateDescription($("#ifodesscription"));
        }
        if(result == ""){
            var url = $("#img").val();
            var ext = url.substring(url.lastIndexOf('.') + 1).toLowerCase();
            if (ext.length > 0 && (ext == "jpg"))
            {
                $("#warningImg").attr("hidden", true);
            } else if(ext.length > 0) {
                $("#warningImg").text("Không hợp lệ");
                result = "Không hợp lệ";
                $("#warningImg").attr("hidden", false);
            }
        }
        if(result == ""){
            result = validateTableAtbWhenSubmit();
        }
        if(result == ""){
            result = validateTableUnitWhenSubmit();
        }

        if(result == ""){
            setTimeout(function(){
                var n = (document).getElementById("successNotify")
                n.innerText = "Thêm mới thành công";
                return false;
            }, 5000);
        }else{
            e.preventDefault();
        }
    })
}

function validateTableUnitWhenSubmit(){
    var numberRow = (document).getElementById("numunit");
    var numRowInt = parseInt(numberRow.getAttribute("value"), 10);
    var result = "";
    for(let i = 1; i <= numRowInt; i++) {
            result = Common.ValidateInputBase($("#unitName"+i), Resource.ValidateType.Required);
            if (result == "") {
                result = Common.ValidateInputBase($("#unitName"+i), Resource.ValidateType.SpecialChar)
            }
            if (result == "") {
                result = Common.ValidateLength($("#unitName"+i), 0, 40);
            }
            if (result == "") {
                $("#warningUnitName"+i).attr("hidden", true);
            } else {
                $("#warningUnitName"+i).text(result);
                $("#warningUnitName"+i).attr("hidden", false);
            }
            var result = "";
            result = Common.ValidateInputBase($("#unitValue"+i), Resource.ValidateType.Required);
            if (result == "") {
                result = Common.ValidateInputBase($("#unitValue"+i), Resource.ValidateType.FloatNumber)
            }
            if (result == "") {
                result = Common.ValidateInputBase($("#unitValue"+i), Resource.ValidateType.SpecialChar)
            }
            if (result == "") {
                result = Common.ValidateLength($("#unitValue"+i), 0, 15);
            }
            if (result == "") {
                $("#warningUnitValue"+i).attr("hidden", true);
            } else {
                $("#warningUnitValue"+i).text(result);
                $("#warningUnitValue"+i).attr("hidden", false);
            }
    }
    return result;

}

function validateTableAtbWhenSubmit(){
    var numberRow = (document).getElementById("numatb");
    var numRowInt = parseInt(numberRow.getAttribute("value"), 10);
    var result = "";
    for(let i = 1; i <= numRowInt; i++) {
            result = Common.ValidateInputBase($("#atbName"+i), Resource.ValidateType.Required);
            if (result == "") {
                result = Common.ValidateInputBase($("#atbName"+i), Resource.ValidateType.SpecialChar)
            }
            if (result == "") {
                result = Common.ValidateLength($("#atbName"+i), 0, 40);
            }
            if (result == "") {
                $("#warningAtbName"+i).attr("hidden", true);
            } else {
                $("#warningAtbName"+i).text(result);
                $("#warningAtbName"+i).attr("hidden", false);
            }
            result = Common.ValidateInputBase($("#atbValue"+i), Resource.ValidateType.Required);
            if (result == "") {
                result = Common.ValidateInputBase($("#atbValue"+i), Resource.ValidateType.SpecialChar)
            }
            if (result == "") {
                result = Common.ValidateLength($("#atbValue"+i), 0, 40);
            }
            if (result == "") {
                $("#warningAtbValue"+i).attr("hidden", true);
            } else {
                $("#warningAtbValue"+i).text(result);
                $("#warningAtbValue"+i).attr("hidden", false);
            }
    }
    return result;
}

function validateDescription(control) {
    var result = "";
    result = Common.ValidateInputBase(control, Resource.ValidateType.SpecialCharTextArea);
    if (result == "") {
        result = Common.ValidateLength(control, 0, 255);
    }
    if (result == "") {
        $("#warningInfoDes").attr("hidden", true);
    } else {
        $("#warningInfoDes").text(result);
        $("#warningInfoDes").attr("hidden", false);
    }
    return result;
}

function  validateTableAtb(){
    var numberRow = (document).getElementById("numatb");
    var numRowInt = parseInt(numberRow.getAttribute("value"), 10);
    for(let i = 1; i <= numRowInt; i++) {
        $("#atbName"+i).off('keyup focusout');
        $("#atbValue"+i).off('keyup focusout');
    }
    for(let i = 1; i <= numRowInt; i++) {
        $("#atbName"+i).on('keyup focusout', function (e) {
            var result = "";
            result = Common.ValidateInputBase($(this), Resource.ValidateType.Required);
            if (result == "") {
                result = Common.ValidateInputBase($(this), Resource.ValidateType.SpecialChar)
            }
            if (result == "") {
                result = Common.ValidateLength($(this), 0, 40);
            }
            if (result == "") {
                $("#warningAtbName"+i).attr("hidden", true);
            } else {
                $("#warningAtbName"+i).text(result);
                $("#warningAtbName"+i).attr("hidden", false);
            }
        });
        $("#atbValue"+i).on('keyup focusout', function (e) {
            var result = "";
            result = Common.ValidateInputBase($(this), Resource.ValidateType.Required);
            if (result == "") {
                result = Common.ValidateInputBase($(this), Resource.ValidateType.SpecialChar)
            }
            if (result == "") {
                result = Common.ValidateLength($(this), 0, 40);
            }
            if (result == "") {
                $("#warningAtbValue"+i).attr("hidden", true);
            } else {
                $("#warningAtbValue"+i).text(result);
                $("#warningAtbValue"+i).attr("hidden", false);
            }
        });
    }
}

function  validateTableUnit(){
    var numberRow = (document).getElementById("numunit");
    var numRowInt = parseInt(numberRow.getAttribute("value"), 10);
    for(let i = 1; i <= numRowInt; i++) {
        $("#unitName"+i).off('keyup focusout');
        $("#unitValue"+i).off('keyup focusout');
    }
    for(let i = 1; i <= numRowInt; i++) {
        $("#unitName"+i).on('keyup focusout', function (e) {
            var result = "";
            result = Common.ValidateInputBase($(this), Resource.ValidateType.Required);
            if (result == "") {
                result = Common.ValidateInputBase($(this), Resource.ValidateType.SpecialChar)
            }
            if (result == "") {
                result = Common.ValidateLength($(this), 0, 40);
            }
            if (result == "") {
                $("#warningUnitName"+i).attr("hidden", true);
            } else {
                $("#warningUnitName"+i).text(result);
                $("#warningUnitName"+i).attr("hidden", false);
            }
        });
        $("#unitValue"+i).on('keyup focusout', function (e) {
            var result = "";
            result = Common.ValidateInputBase($(this), Resource.ValidateType.Required);
            if (result == "") {
                result = Common.ValidateInputBase($(this), Resource.ValidateType.FloatNumber)
            }
            if (result == "") {
                result = Common.ValidateInputBase($(this), Resource.ValidateType.SpecialChar)
            }
            if (result == "") {
                result = Common.ValidateLength($(this), 0, 15);
            }
            if (result == "") {
                $("#warningUnitValue"+i).attr("hidden", true);
            } else {
                $("#warningUnitValue"+i).text(result);
                $("#warningUnitValue"+i).attr("hidden", false);
            }
        });
    }
}

function  validateImageFile(){
    $("#img").on('change', function (e) {
        var result = "";
        var input = this;
        var url = $(this).val();
        var ext = url.substring(url.lastIndexOf('.') + 1).toLowerCase();
        if (input.files && input.files[0] && (ext == "jpg"))
        {
            $("#warningImg").attr("hidden", true);
        } else if(input.files && input.files[0]) {
            $("#warningImg").text("Không hợp lệ");
            result = "Không hợp lệ";
            $("#warningImg").attr("hidden", false);
        }
        return result;
    });
}
function  validateProductweight(control){
        var result = "";
        result = Common.ValidateInputBase(control, Resource.ValidateType.Required);
        if (result == "") {
            result = Common.ValidateInputBase(control, Resource.ValidateType.FloatNumber)
        }
        if (result == "") {
            result = Common.ValidateInputBase(control, Resource.ValidateType.SpecialChar)
        }
        if (result == "") {
            result = Common.ValidateLength(control, 0, 10);
        }
        if (result == "") {
            $("#warningProductweight").attr("hidden", true);
        } else {
            $("#warningProductweight").text(result);
            $("#warningProductweight").attr("hidden", false);
        }
        return result;
}

function  validateStock(control){
        var result = "";
        result = Common.ValidateInputBase(control, Resource.ValidateType.Required);
        if (result == "") {
            result = Common.ValidateInputBase(control, Resource.ValidateType.SpecialChar)
        }
        if (result == "") {
            $("#warningStock").attr("hidden", true);
        } else {
            $("#warningStock").text(result);
            $("#warningStock").attr("hidden", false);
        }
        return result;
}

function  validateProductUnit(control){
        var result = "";
        result = Common.ValidateInputBase(control, Resource.ValidateType.Required);
        if (result == "") {
            result = Common.ValidateInputBase(control, Resource.ValidateType.SpecialChar)
        }
        if (result == "") {
            result = Common.ValidateLength(control, 0, 40);
        }
        if (result == "") {
            $("#warningProductunit").attr("hidden", true);
        } else {
            $("#warningProductunit").text(result);
            $("#warningProductunit").attr("hidden", false);
        }
        return result;
}

function  validateProductWholesaleprice(control){
        var result = "";
        result = Common.ValidateInputBase(control, Resource.ValidateType.Required);
        if (result == "") {
            result = Common.ValidateInputBase(control, Resource.ValidateType.FloatNumber)
        }
        if (result == "") {
            result = Common.ValidateInputBase(control, Resource.ValidateType.SpecialChar)
        }
        if (result == "") {
            result = Common.ValidateLength(control, 0, 15);
        }
        if (result == "") {
            $("#warningProductwholesaleprice").attr("hidden", true);
        } else {
            $("#warningProductwholesaleprice").text(result);
            $("#warningProductwholesaleprice").attr("hidden", false);
        }
        return result;
}

function  validateProductRetailPrice(control){
        var result = "";
        result = Common.ValidateInputBase(control, Resource.ValidateType.Required);
        if (result == "") {
            result = Common.ValidateInputBase(control, Resource.ValidateType.FloatNumber)
        }
        if (result == "") {
            result = Common.ValidateInputBase(control, Resource.ValidateType.SpecialChar)
        }
        if (result == "") {
            result = Common.ValidateLength(control, 0, 15);
        }
        if (result == "") {
            $("#warningProductRetailprice").attr("hidden", true);
        } else {
            $("#warningProductRetailprice").text(result);
            $("#warningProductRetailprice").attr("hidden", false);
        }
        return result;
}

function  validateProductName(control){
    var result = "";
        result = Common.ValidateInputBase(control, Resource.ValidateType.Required);
        if (result == "") {
            result = Common.ValidateInputBase(control, Resource.ValidateType.SpecialChar)
        }
        if (result == "") {
            result = Common.ValidateLength(control, 0, 40);
        }
        if (result == "") {
            $("#warningProductname").attr("hidden", true);
        } else {
            $("#warningProductname").text(result);
            $("#warningProductname").attr("hidden", false);
        }
    return result;
}

function  validateProductID(control){
        var result = "";
        result = Common.ValidateInputBase(control, Resource.ValidateType.Required);
        if (result == "") {
            result = Common.ValidateInputBase(control, Resource.ValidateType.SpecialChar)
        }
        if (result == "") {
            result = Common.ValidateLength(control, 0, 100);
        }
        if (result == "") {
            $("#warningProductID").attr("hidden", true);
        } else {
            $("#warningProductID").text(result);
            $("#warningProductID").attr("hidden", false);
        }
        return result;
}


function focusDes() {
    $("#ifodesscription").click(function () {
        const textarea = document.getElementById('ifodesscription');
        const end = textarea.value.length;
        textarea.setSelectionRange(end, end);
        textarea.focus();
    })

}


// Attribute
function addAtb() {
    $("#addAtb").click(function () {

        var table = document.getElementById("table-atb");
        var totalRowCount = table.rows.length;

        var row = table.insertRow(-1);
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);

        cell1.innerHTML = '<tr>' +
            '<td><span class="defaultCursor" style="color: red"> *</span>' +
            '<div style="position: relative;"><input type="text" placeholder="Nhập tên thuộc tính" class="attribute-name"' +
            ' name="atbName' + totalRowCount + '" id="atbName' + totalRowCount + '" >' +
            '<span id="warningAtbName'+totalRowCount+ '" class="warningTextTable defaultCursor validateWarnning" hidden="true"></span></div></td>';
        cell2.innerHTML = '<td><span class="defaultCursor" style="color: red"> *</span>' +
            '<div style="position: relative;"><input type="text" placeholder="Nhập giá trị" class="attribute-value"' +
            ' name="atbValue' + totalRowCount + '" id="atbValue' + totalRowCount + '" >' +
            '<span id="warningAtbValue'+totalRowCount+ '" class="warningTextTable defaultCursor validateWarnning" hidden="true"></span></div></td>';
        cell3.innerHTML = '<td><span class="delete-row delete-row-attb">Xóa</span></td>' +
            '</tr>';
        var numberAtb = document.getElementById("numatb");
        $("#atbName"+totalRowCount).focus();
        numberAtb.setAttribute('value', '' + totalRowCount);
        validateTableAtb();
    });
}

function deleteAtb() {
    $(document).on('click', '.delete-row-attb', function () {
        var i = $(this).parent().parent().index();
        document.getElementById("table-atb").deleteRow(i);
        var table = document.getElementById("table-atb");
        var totalRowCount = table.rows.length - 1;
        var numberAtb = document.getElementById("numatb");
        numberAtb.setAttribute('value', '' + totalRowCount);
        for(let j = i+1; j < totalRowCount+2; j++){
            let k = j -1;
            var newIndex = document.getElementById("atbName" + j);
            newIndex.setAttribute('name','atbName' + k);
            newIndex.setAttribute('id','atbName' + k);
            var newIndex1 = document.getElementById("atbValue" + j);
            newIndex1.setAttribute('name','atbValue' + k);
            newIndex1.setAttribute('id','atbValue' + k);
            var newIndex2 = document.getElementById("warningAtbName" + j);
            newIndex2.setAttribute('id','warningAtbName' + k);
            var newIndex2 = document.getElementById("warningAtbValue" + j);
            newIndex2.setAttribute('id','warningAtbValue' + k);
        }
        validateTableAtb();
    });
}

// Unit
function addUnit() {
    $("#addUnit").click(function () {
        var table = document.getElementById("table-unit");
        var totalRowCount = table.rows.length;

        var row = table.insertRow(-1);
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        cell1.innerHTML = '<tr>' +
            '<td><span class="defaultCursor" style="color: red"> *</span>' +
            '<div style="position: relative;"><input type="text" placeholder="Nhập tên đơn vị" class="unit-name"' +
            ' name="unitName' + totalRowCount + '" id="unitName' + totalRowCount + '" >' +
            '<span id="warningUnitName'+totalRowCount+ '" class="warningTextTable defaultCursor validateWarnning" hidden="true"></span></div></td>';
        cell2.innerHTML = '<td><span class="defaultCursor" style="color: red"> *</span>' +
            '<div style="position: relative;"><input type="text" placeholder="Nhập giá trị" class="unit-value"' +
            ' name="unitValue' + totalRowCount + '" id="unitValue' + totalRowCount + '" >' +
            '<span id="warningUnitValue'+totalRowCount+ '" class="warningTextTable defaultCursor validateWarnning" hidden="true"></span></div></td>';
        cell3.innerHTML = '<td><span class="delete-row delete-row-unit" >Xóa</span></td>' +
            '</tr>';
        var numberUnit = document.getElementById("numunit");
        $("#unitName"+totalRowCount).focus();
        numberUnit.setAttribute('value', '' + totalRowCount);
        validateTableUnit();
    });
}

function deleteUnit() {
    $(document).on('click', '.delete-row-unit', function () {

        var i = $(this).parent().parent().index();
        document.getElementById("table-unit").deleteRow(i);
        var table = document.getElementById("table-unit");
        var totalRowCount = table.rows.length - 1;
        var numberUnit = document.getElementById("numunit");
        numberUnit.setAttribute('value', '' + totalRowCount);
        for(let j = i+1; j < totalRowCount+2; j++){
            let k = j -1;
            var newIndex = document.getElementById("unitName" + j);
            newIndex.setAttribute('name','unitName' + k);
            newIndex.setAttribute('id','unitName' + k);
            var newIndex1 = document.getElementById("unitValue" + j);
            newIndex1.setAttribute('name','unitValue' + k);
            newIndex1.setAttribute('id','unitValue' + k);
            var newIndex2 = document.getElementById("warningUnitName" + j);
            newIndex2.setAttribute('id','warningUnitName' + k);
            var newIndex2 = document.getElementById("warningUnitValue" + j);
            newIndex2.setAttribute('id','warningUnitValue' + k);
        }
        validateTableUnit();
    });

}

// Show image
function readURL() {
    $("#img").change(function () {
        var input = this;
        var url = $(this).val();
        var ext = url.substring(url.lastIndexOf('.') + 1).toLowerCase();
        if (input.files && input.files[0]&& (ext == "jpg"))
        {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#img-chose').attr('src', e.target.result).width(240);
            };

            reader.readAsDataURL(input.files[0]);
        } else {
            $('#img-chose').attr('src', 'https://endlessicons.com/wp-content/uploads/2012/11/image-holder-icon.png').width(240);
        }
    });
}
