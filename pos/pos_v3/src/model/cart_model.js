function Cart(){
}

Cart.prototype.create_cart_goods = function(inputs){
    return _(inputs).map(function (input) {
        var goods = _.find(loadAllItems(), function (item) { return item.barcode == _.keys(input)});
        return {
            name:goods.name,
            unit:goods.unit,
            price:goods.price,
            count: _.values(input)[0],
            brand:goods.brand,
            last:0,
            total:(_.values(input)[0]*goods.price),
            promote_summary:(_.values(input)[0]*goods.price),
            promote:[]
        }
    })
};