/**
 * Created by black on 14-11-6.
 */
$(document).ready(function(){
    $("#content").append(append_buy_item_content());
    var all_barcode = localStorage.getItem("all_barcode").split(",");
    $(".decrease").on('click',update_cart);
    $(".increase").on('click',update_cart);
    refresh();
});
function append_buy_item_content(){
    var str ='';
    var items = new Item_bill(localStorage.getItem("all_barcode").split(',')).buy_all_items();
    if(items != ''){
        $.each(items,function(index){
            str += "<tr data-barcode=" + items[index].barcode + "><td class='col-md-2'>" + items[index].name + "</td><td class='col-md-2'>" + items[index].price + "</td><td class='col-md-2'>" + items[index].unit + "</td>" +
                "<td class='col-md-3'><div class='btn-group'><button type='button' class='decrease btn btn-default'>-</button>" +
                "<button type='button' class='btn btn-default disabled'><span>" + items[index].number + "</span></button>" +
                "<button type='button' class='increase btn btn-default'>+</button></div></td>"
                +"<td class='col-md-3'>" + table_display_price(items[index]) + "</td></tr>";
        });}
    $(".sum_price").text(display_sum_price().toFixed(2));
    return str;
}
function update_cart(){
    var now = $(this);
    var barcode = now.closest("tr").data('barcode');
    var item = _.find(new Item_bill(localStorage.getItem("all_barcode").split(',')).buy_all_items(), function(item) { return item.barcode == barcode;});
    if(now.text() == '+'){
        add_number(item,now);
    }else if(now.text() == '-'){
        reduce_number(item,now);}
    now.parent().parent().next().text(table_display_price(item));
    $(".sum_price").text(display_sum_price().toFixed(2));
    refresh();
}
function add_number(item,that){
    item.number++;
    update_barcode('add',item);
    that.prev().text(parseInt(that.prev().text()) + 1);
}
function reduce_number(item,that){
    item.number--;
    update_barcode('reduce',item);
    that.next().text(parseInt(that.next().text()) - 1);
    if(that.next().text() == 0){
        that.parent().parent().parent().remove();
    }
}
function  update_barcode(str,item) {
    var all_barcode = localStorage.getItem("all_barcode").split(',');
    if(str == 'add'){
        all_barcode.push(item.barcode);
    }else if(str == 'reduce'){
        all_barcode.splice(_.lastIndexOf(all_barcode,item.barcode),1);
    }
    localStorage.setItem("all_barcode",all_barcode);
}
function table_display_price(item){
    item.total_price = item.number * item.price;
    item.promotion_price = parseInt(item.number/3)*item.price;
    return (item.is_promotion==1 && item.number > 2)?((item.total_price-item.promotion_price)+"元(原价：" + item.total_price + "元)"):(item.total_price + "元");
}
function display_sum_price(){
    var all_barcode = localStorage.getItem("all_barcode").split(',');
    if(all_barcode != ''){
        var item_list = new Item_bill(all_barcode);
        var items = item_list.buy_all_items();
        return item_list.buy_item_sum_price(items);
    }else{
        return 0;
    }
}
