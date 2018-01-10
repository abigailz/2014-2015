/**
 * Created by black on 14-11-6.
 */
$(document).ready(function(){
    var content = table_content();
    $("thead").append(content);
    $(".add").on('click',buy_item_barcode);
});
function buy_item_barcode(){
    var items = localStorage.getItem("all_barcode" || '');
    items=items? items.split(","):[];
    items.push($(this).data('barcode'));
    localStorage.setItem("all_barcode",items);
    $("#item_num").text(_.size(items));
}
function table_content(){
    var items = loadAllItems();
    var str = '';
    $.each(items, function(index){
        str += "<tr><td>" + items[index].type+"</td>"+
               "<td>" + items[index].name +"</td>"+
               "<td>" + items[index].price +"</td>"+
               "<td>" + items[index].unit +"</td>"+
               "<td><button class='add btn btn-primary'data-barcode="+items[index].barcode + ">加入购物车</button></td></tr>";
    });
    return str;
}

