(function ($) {

    let listOrders = [];

    let readFromLocal = function(){
        return JSON.parse(localStorage.getItem("listOrders"));
    }

    let getListOfArticles = function(){
        listOrders = readFromLocal();
        if(listOrders!=null){ 
            const url = "/api/common/article/list?ids="+listOrders.toString();
            $.ajax({
                url: url,
                type: "GET",
                dataType: "json",
                cache: false,
                success: function (jsonData) {
                    showItemList(jsonData);
                },
                error: function (request, status, error) {
                    console.log(request.responseText);
                }
            });
        }
    }

    let showItemList = function (itemList){
        
        if(itemList.length>0){
            let htmlItems="";
            $.each(itemList,function(index,article){
                htmlItems+=('<div class="item">'
                +'<div class="row">'
                +    '<div class="col-md-6">'
                +        '<div class="image-item">'
                +            '<img src="'+article.imageLink+'"/>'  
                +        '</div>'
                +    '</div>'
                +    '<div class="col-md-6">'
                +        '<div class="panel-item">'
                +            '<div class="name-item">'
                +                article.name
                +            '</div>'
                +            '<div class="price">'
                +                'Price: <span id="span-price-'+article.id+'">'+ article.price+'</span>'
                +            '</div>'
                +            '<div class="count">'
                +                '<input id="count-'+article.id+'" class="count-item" type="number" min="0" value="1">'
                +            '</div>'
                +            '<div class="remove-button">'
                +                '<input id="remove-'+article.id+'" class="remove-item" type="submit" value="Delete">'
                +            '</div>'
                +        '</div>'
                +    '</div>'
                +'</div>'
                +'</div>');
            });
            $("#item-list").html(htmlItems);
            addDeleteItemListener();
            addChangeCountListener();
            addSubmitListener();
        }     
        calculateTotalPrice();
    }

    let addDeleteItemListener = function(){
        $(".remove-item").click(function(event){
            event.preventDefault();
            let id = $(this).attr('id');
            articleId = Number(id.split('-')[1]);
            $(this).closest('.item').remove();
            removeOrder(articleId);
        });
    }

    let removeOrder = function(articleId){
        const index = listOrders.indexOf(articleId);
        if (index > -1) {
            listOrders.splice(index, 1);
            saveToLocal();
            calculateTotalPrice();
        }else{
            console.log("Article " + articleId + " is not in the cart");
        }
    }

    let saveToLocal = function(){
        localStorage.setItem("listOrders", JSON.stringify(listOrders));
        if(listOrders.length==0){
            localStorage.setItem("shopWithOrders",0);
        }
    }

    let calculateTotalPrice = function(){
        let totalPrice=0;
        $.each(listOrders,function(index,id){
            price = Number($("#span-price-"+id).html());
            count = Number($("#count-"+id).val());
            totalPrice+=price*count;        
        });
        $("#total-value").html(totalPrice.toFixed(2));
    }

    let addChangeCountListener = function(){
        $(".count-item").on("input", function() {
            calculateTotalPrice(); 
        });
    }

    let addSubmitListener = function(){
        $("#submit").click(function(event){
            event.preventDefault();
            let name = $("#name").val();
            if(name==""){
                alert("Name is empty");
                return;
            }
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
            let address = $("#address").val();
            if(address==""){
                alert("Address is empty");
                return;
            }
            let orderRequestDto = {};
            orderRequestDto.name = name;
            orderRequestDto.email = email;
            orderRequestDto.phone = phone;
            orderRequestDto.address = address;
            orderRequestDto.items=[];
            $.each(listOrders,function(index,id){
                count = Number($("#count-"+id).val());
                let item={};
                item.articleId=id;
                item.count=count;
                orderRequestDto.items.push(item);        
            });
            submitRequest(orderRequestDto);
        });
    }

    let submitRequest = function(orderRequestDto){
        const url = "/api/common/order";
        $.ajax({
            url: url,
            type: "POST",
            dataType: "json",
            data:JSON.stringify(orderRequestDto),
            contentType: "application/json; charset=utf-8",
            cache: false,
            success: function (jsonData) {
                alert("Your order number:" + jsonData);
            },
            error: function (request, status, error) {
                console.log(request.responseText);
            }
        });
    }
    

    function validateEmail($email) {
        var pattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
        return pattern.test( $email );
    }

    $(document).ready(function() {
        
        getListOfArticles();

    });
})(jQuery);
