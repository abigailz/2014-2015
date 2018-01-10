/**
 * Created by black on 14-11-14.
 */
function Item_bill(all_barcode){
    this.all_barcode = all_barcode || [];
}
Item_bill.prototype.buy_all_items = function(){
    var map_item = _.map(_.groupBy(this.all_barcode,function(barcode){return barcode;}), function (value,key) { return {"barcode":key.slice(0,10),"count": _.size(key)==10? _.size(value):parseInt(key.slice(11))};});
    var buy_items = [];
    var promotion_barcode = loadPromotions()[0].barcodes;
    _(map_item.length).times(function (index) {
        var items = _.find(loadAllItems(),function(item){return item.barcode == map_item[index].barcode}) || [];
        items.number = map_item[index].count;
        items.is_promotion = (promotion_barcode.indexOf(items.barcode) < 0)? 0 : 1;
        items.promotion_price = (items.is_promotion)?items.price*parseInt(items.number/3):0;
        items.total_price = (items.is_promotion)?(items.price*(items.number - parseInt(items.number/3))):(items.number*items.price);
        buy_items.push(items);
    });
    return buy_items;
};
Item_bill.prototype.free_items = function () {
    var buy_items = this.buy_all_items();
    var free_items = _.filter(buy_items,function(item){ return (item.is_promotion == 1) && item.number > 2});
    _(free_items.length).times(function (index) {
        free_items[index].number = parseInt(free_items[index].number/3);
        free_items[index].total_price = free_items[index].number * free_items[index].price;
    });
    return free_items;
};
Item_bill.prototype.buy_item_sum_price = function(items){
    var price = 0;
    _.each(items,function(item){
        price += item.total_price;
    });
    return price;
};
