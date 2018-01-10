/**
 * Created by black on 14-12-17.
 */
function add_goods(item_id){
    $.post('/add_goods',{_id:item_id}, function (data) {
        $(".goods_total").text(data["total"]);
        $(".item div button[name="+data["item"]._id+"]").text(data["item"].number);
        $(".item td[name="+data["item"]._id+"]").text(data["item"].summary_display);
        $(".summary").text(data["summary_price"]);
    })
}

function delete_one_goods(item_id){
    $.post('/delete_one_goods',{_id:item_id}, function (data) {
        $(".goods_total").text(data["total"]);
        if(data["item"].number == 0){
            $(".item[name="+data["item"]._id+"]").remove();
        }else{
            $(".item div button[name="+data["item"]._id+"]").text(data["item"].number);
        }
        $(".item td[name="+data["item"]._id+"]").text(data["item"].summary_display);
        $(".summary").text(data["summary_price"]);
    })
}