var Strategy_Three = function (rule) {
    this.rule = rule;
};

Strategy_Three.prototype.filterRules = function () {
    return this.rule;
};
Strategy_Three.prototype.calculate = function (goods) {
    var rule =this.filterRules();
    var calculator = new Calculator();
    var cart = calculator.combineThePromotion(rule,goods);
    var cart_goods = this.resolveConflict(cart);
    //return calculator.calculatePromotionInfo(cart_goods);
};

Strategy_Three.prototype.resolveConflict = function (goods) {
    var cart = goods;
    _(cart).map(function(element){
        var index = 0;
        _(element.promote).map(function (item) {
           if(item.type == 'no_promotion'){
               var other = _(element.promote).find(function (one){
                   return one.name == 'all';
               });
               index = element.promote.indexOf(other);
           }
        });
        element.promote.splice(index,1);
    });
    return cart;
};