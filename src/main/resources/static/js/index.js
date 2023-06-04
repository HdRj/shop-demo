(function ($) {

    let activeShopId = 0;
    let shopWithOrders = 0;
    let listOrders = [];



    let getListOfShops = function () {
        const url = "/api/common/shop";
        $.ajax({
            url: url,
            type: "GET",
            dataType: "json",
            cache: false,
            success: function (jsonData) {
                showShopList(jsonData);
            },
            error: function (request, status, error) {
                console.log(request.responseText);
            }
        });
    };
    
    let showShopList = function(shopList){
        let htmlShops="";
        if(shopList!=""){
            $.each(shopList,function(index,shop){
                htmlShops+='<div class="shop"><button id="shop-'+shop.id+'" type="button" class="btn-shop btn btn-outline-secondary btn-lg">'+shop.name+'</button></div>';
            });
            activeShopId = 1;
        }
        $("#shop-list").html(htmlShops);
        if(activeShopId!=0){
            changeActiveShop(activeShopId);
            addShopClickListener();
        }
    };

    let changeActiveShop = function(activeShopId){
        if(activeShopId!=0){
            $(".btn-shop").removeClass("btn-success");
            $(".btn-shop").addClass("btn-outline-secondary");            
            $("#shop-"+activeShopId).removeClass("btn-outline-secondary");
            $("#shop-"+activeShopId).addClass("btn-success");
            getListOfArticles(activeShopId);
        }
    }

    let addShopClickListener = function(){
        $(".btn-shop").click(function(event){
            event.preventDefault();
            let id = $(this).attr('id');
            activeShopId = id.split('-')[1];
            changeActiveShop(activeShopId);
        });
    };

    let getListOfArticles = function(shopId){
        const url = "/api/common/article/"+shopId;
        $.ajax({
            url: url,
            type: "GET",
            dataType: "json",
            cache: false,
            success: function (jsonData) {
                showArticleList(jsonData);
            },
            error: function (request, status, error) {
                console.log(request.responseText);
            }
        });
    }

    let showArticleList = function (articleList){
        htmlArticles="";
        if(articleList.length>0){
            let buttonHtml="";
            $.each(articleList,function(index,article){
                if(isArticleInCart(article.id)){
                    buttonHtml = '<button id="article-'+article.id+'" class="article-btn btn btn-success">Add to cart</button>';
                }else{
                    buttonHtml = '<button id="article-'+article.id+'" class="article-btn btn btn-outline-secondary">Add to cart</button>';
                }

                htmlArticles+=('<div class="article">'
                    +'<div class="image-article">'
                    +'<img src="'+article.imageLink+'"/>'
                    +'</div>'
                    +'<div class="name-article">'
                    +article.name
                    +'</div>'
                    +'<div class="btn-article">'
                    +buttonHtml
                    +'</div>'
                    +'</div>');
            });
        }
        $("#article-list").html(htmlArticles);
        addArticleClickListener();
    }

    let addArticleClickListener = function(){
        $(".article-btn").click(function(event){
            event.preventDefault();
            let id = $(this).attr('id');
            articleId = Number(id.split('-')[1]);
            if(isArticleInCart(articleId)){
                removeOrder(articleId);
                $(this).removeClass("btn-success");
                $(this).addClass("btn-outline-secondary");
                $(this).text("Add to cart");
            }else{
                if(addOrder(articleId)){
                    $(this).removeClass("btn-outline-secondary");
                    $(this).addClass("btn-success");
                    $(this).text("Added to cart");
                }
            }
            
        });
    }


    let addOrder = function(articleId){
        if(shopWithOrders == 0 || shopWithOrders == activeShopId){
            shopWithOrders=activeShopId;
            listOrders.push(articleId);
            saveToLocal();
            return true;
        }else{
            alert("You can place an order in only one shop");
            return false;
        }
    }

    let removeOrder = function(articleId){
        const index = listOrders.indexOf(articleId);
        if (index > -1) {
            listOrders.splice(index, 1);
            if(listOrders.length == 0){
                shopWithOrders = 0;
            }
            saveToLocal();
        }else{
            console.log("Article " + articleId + " is not in the cart");
        }
    }

    let isArticleInCart = function(articleId){
        const index = listOrders.indexOf(articleId);
        return (index > -1);
    }

    let saveToLocal = function(){
        localStorage.setItem("listOrders", JSON.stringify(listOrders));
        localStorage.setItem("shopWithOrders",shopWithOrders);
    }

    let readFromLocal = function(){
        let list = JSON.parse(localStorage.getItem("listOrders"));
        if(list!=null){
            listOrders = list;
        }
        let shop = localStorage.getItem("shopWithOrders");
        if(shop!=null){
            shopWithOrders = shop;
        }
    }


    $(document).ready(function() {
        readFromLocal();
        getListOfShops();
    });
})(jQuery);