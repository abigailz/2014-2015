/**
 * Created by black on 14-11-10.
 */
$(document).ready(function(){
    var item_list = new Item_bill(localStorage.getItem("all_barcode").split(','));
    if(item_list.all_barcode != ''){
        var items = item_list.buy_all_items();
        $("h4").text(moment().format('YYYY年MM月DD日 hh:mm:ss'));
        $("#item").append(item_content(items));
        $("#promotion-item").append(promotion_item_content());
        $(".sure").on('click',clear_cart);
    }else{
        $(".cart").html("<h2>购物车为空</h2>");
    }
    $(".sum_price").text(item_list.buy_item_sum_price(items).toFixed(2));
    $(".promotion_price").text(item_list.buy_item_sum_price(item_list.free_items()).toFixed(2));
});
function clear_cart(){
    localStorage.clear();
}
function item_content(items){
    var str ='';
    $.each(items,function(index){
        str += "<tr><td class='col-md-2'>" + items[index].type+"</td><td class='col-md-2'>"+items[index].name+
               "</td><td class='col-md-2'>"+items[index].price +"</td><td class='col-md-2'>" + items[index].unit+
               "</td><td class='col-md-2'>" + items[index].number + "</td><td class='col-md-2'>" + display_item_price(items[index].barcode) + "</td></tr>";
    });
    return str;
}
function display_item_price(barcode){
    var item_list = new Item_bill(localStorage.getItem("all_barcode").split(','));
    var items = item_list.buy_all_items();
    var item = _.find(items, function(item) { return item.barcode == barcode;});
    return (item.is_promotion==1 && item.number > 2)?(item.total_price+"元(原价：" + (item.total_price+item.promotion_price) + "元)"):(item.total_price + "元");
}
function promotion_item_content(){
    var item_list = new Item_bill(localStorage.getItem("all_barcode").split(','));
    var items = item_list.free_items();
    var str = '';
    $.each(items,function(index){
        str += "<tr><td class='col-md-4'>" + items[index].type+"</td><td class='col-md-4'>" + items[index].name+"</td><td class='col-md-4'>" + items[index].number + "</td></tr>";
    });
    return str;
}