(function ($) {

    let addSubmitListener = function(){
        $("#submit").click(function(event){
            event.preventDefault();
        
            let email = $("#email").val();
            if(!validateEmail(email)){
                alert("Email is invalid");
                return;
            }
            let phone = $("#phone").val();
            if(phone==""){
                alert("Phone is empty");
                return;
            }
            submitRequest(email,phone);
        });
    }

    let submitRequest = function(email, phone){
        const url = "/api/common/order/history?email="+email+"&phone="+phone;
        $.ajax({
            url: url,
            type: "GET",
            dataType: "json",
            cache: false,
            success: function (jsonData) {
                showHistory(jsonData);
            },
            error: function (request, status, error) {
                console.log(request.responseText);
                alert(request.responseText);
            }
        });
    }

    let showHistory = function(historyList){
        let htmlHistory="";
        $.each(historyList,function(index,history){
            htmlHistory+=('<div class="history">'
            +'<div class="date-history history-item">'
            +    'Date: <span id="date-value">'+history.date+'</span>'
            +'</div>'
            +'<div class="price-history history-item">'
            +    'Total price: <span id="price-value">'+history.totalPrice+'</span>'
            +'</div>'
            +'<div class="count-history history-item">'
            +    'Total items: <span id="count-value">'+history.count+'</span>'
            +'</div>'
            +'<div class="address-history history-item">'
            +    'Address: <span id="address-value">'+history.address+'</span>'
            +'</div>'
            +'<div class="status-history history-item">'
            +    'Status: <span id="status-value">'+history.status+'</span>'
            +'</div>'
            +'</div>');
        });
        $("#history-list").html(htmlHistory);
    }
    

    function validateEmail($email) {
        var pattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
        return pattern.test( $email );
    }


    $(document).ready(function() {
        addSubmitListener();
    });
})(jQuery);