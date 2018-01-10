function Strategy_Two(rule) {
    this.rule = rule;
}

Strategy_Two.prototype.filterRules = function () {
    return this.rule;
};

Strategy_Two.prototype.calculate = function (goods) {
    var rule =this.filterRules();
    var calculator = new Calculator();
    var cart = calculator.combineThePromotion(rule,goods);
    var cart_goods = this.resolveConflict(cart);
    return calculator.calculatePromotionInfo(cart_goods);
};

Strategy_Two.prototype.resolveConflict = function (goods) {
    var cart = goods;
    _(cart).map(function (element) {
        if(element.promote.length > 0){
            _(element.promote).map(function (item) {
                _filterBrandDiscount(item, element);
            });
        }
    });
    return cart;
};

function _filterBrandDiscount(item, element) {
    if (item.type == '单品打折') {
        var filter = _(element.promote).find(function (one) {
            return one.type == '品牌打折';
        });
        if (element.promote.indexOf(filter) > -1) {
            element.promote.splice(element.promote.indexOf(filter), 1)
        }
    }
}
