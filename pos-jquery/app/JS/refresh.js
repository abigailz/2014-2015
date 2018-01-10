/**
 * Created by black on 14-11-10.
 */
$(document).ready(function(){
    refresh();
});
function refresh(){
    var all_barcode = localStorage.getItem('all_barcode');
    all_barcode=all_barcode? all_barcode.split(","):[];
    $("#item_num").text(_.size(all_barcode));
}