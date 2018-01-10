function Strategy_One(rule) {
    this.rule = rule;
}

Strategy_One.prototype.filterRules = function () {
    var rules = this.rule;
    var calculate = new Calculator();
    _(calculate.matchName(rules.single_discount)).map(function (item) {
        _(calculate.matchName(rules.brand_discount)).map(function (element) {
            if(item.indexOf(element) > -1){
                var useless = _(rules.single_discount).find(function(element){return element.name == item})
                rules.single_discount.splice(useless,1)
            }
        })
    });
    this.rule = rules;
    return this.rule;
};

Strategy_One.prototype.calculate = function (goods) {
    var rule =this.filterRules();
    var calculate = new Calculator();
    var cart = calculate.combineThePromotion(rule,goods);
    this.resolveConflict(cart);
    return calculate.calculatePromotionInfo(cart);
};

Strategy_One.prototype.resolveConflict = function (goods) {
    _(goods).map(function(element){
        if(element.promote.length > 1){
            var all = _(element.promote).find(function (item){
                return item.name == 'all';
            });
            element.promote.splice(element.promote.indexOf(all));
        }
    })

};