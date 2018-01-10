/**
 * Created by black on 14-12-13.
 */
function add_goods_cart(item_id){
    $.post('/add_goods_cart', {"_id":item_id} ,function (data) {
        $(".goods_total").text(data);
    })
}