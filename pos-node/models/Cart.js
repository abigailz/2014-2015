/**
 * Created by black on 14-12-17.
 */
var _ = require('underscore');

function Cart(){

}

Cart.free_goods = function (goods) {
    var cart_goods = _(goods).filter(function (item) { return item.is_promotion ==1});
    return _(cart_goods).map(function (item) {
        return {
            name:item.name,
            type:item.type,
            number:parseInt(item.number/3)
        }
    })
};

Cart.append_price = function (barcode,goods) {
    _(goods).each(function (item) {
        item.is_promotion = (barcode.indexOf(item.barcode) < 0)? 0 : 1;
        var promotion_price = item.is_promotion?item.price*parseInt(item.number/3):0;
        if(item.is_promotion == 1 && item.number>2){
            item.summary_price = (item.number*item.price)-promotion_price;
            item.summary_display = item.summary_price.toFixed(2)+'元（原价：'+(item.number*item.price).toFixed(2)+'元)';
        }else{
            item.summary_price = item.number*item.price;
            item.summary_display = item.summary_price.toFixed(2)+'元';
        }
    });
    return goods;
};

Cart.summary_price = function (goods) {
    var all_summary_price = 0;
    _(goods).each(function (item) {
        all_summary_price += item.summary_price;
    });
    return all_summary_price.toFixed(2);
};

Cart.promotion_price = function (goods) {
    var promotion_after_price = Cart.summary_price(goods);
    var total_price = 0;
    _(goods).each(function (item) {
        total_price += item.number*item.price;
    });
    return (total_price-promotion_after_price).toFixed(2);
};

module.exports = Cart;