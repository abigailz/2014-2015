function Calculator() {

}

Calculator.prototype.matchName = function (array) {
    var name = [];
    _(array).each(function (element) {
        name.push(element.name)
    });
    return name;
};

Calculator.prototype.combineThePromotion = function (rule,goods) {
    var cart = goods;
    var filter_rule = _.flatten(_.values(rule));
    return this.bindingPromotionInfo(cart,filter_rule);
};

Calculator.prototype.bindingPromotionInfo = function (goods,rule) {
    var cart = goods;
    _(rule).map(function (element) {
        _(cart).map(function (item) {
            if ((item.name).indexOf(element.name) > -1) {
                item.promote.push(element);
            }
            if(element.name == 'all'){
                item.promote.push(element);
            }
        })
    });
    return cart;
};

Calculator.prototype.filterGoods = function(goods,str) {
    var result = [];
    _(goods).map(function (element) {
        _(element.promote).map(function (item) {
            if (item.type == str) {
                result.push(element);
            }
        });
    });
    return result;
};

Calculator.prototype.calculatePromotionInfo = function (goods) {
    return {
        single_discount:this.calculateGoodsInfo(goods,'单品打折'),
        brand_discount:this.calculateGoodsInfo(goods,'品牌打折'),
        single_full:this.calculateGoodsInfo(goods,'单品满'),
        brand_full:this.calculateGoodsInfo(goods,'品牌满'),
        other_discount:this.calculateGoodsInfo(goods,'打'),
        other_full:this.calculateGoodsInfo(goods,'满')
    }
};

Calculator.prototype.calculateMoney = function(goods) {
    var self = this;
    _(goods).map(function(item){
        if(item.last == 0){
            item.promote_summary = item.promote[0].calculatePromoteMoney(self.subtotal(goods));
        }else if(item.last == 1){
            var promote = item.promote_summary;
            item.promote_summary = item.promote[0].calculatePromoteMoney(promote);
        }
    });
};

Calculator.prototype.calculateGoodsInfo = function (goods,str) {
    var self = this;
    var cart = this.filterGoods(goods,str);
    var group = _(cart).groupBy('brand');
    _(group).map(function(element){
        self.calculateMoney(element);
    });
    var result = [];
    _(this.returnPromotionInfo(group)).map(function (element){
        result.push(element)
    });
    return result;
};

Calculator.prototype.returnPromotionInfo = function(group){
    var result = [];
    if(!_.isEmpty(group)){
        var array = _.values(group);
        _(array).map(function(element){
            if(element[0].promote[0].type.indexOf('满') > -1){
                result.push({
                    name : element[0].promote[0].name=='all'?(element[0].promote[0].type +element[0].promote[0].full+'减'+ element[0].promote[0].less):(element[0].promote[0].name + element[0].promote[0].type +element[0].promote[0].full +'减'+ element[0].promote[0].less),
                    total: element[0].promote_summary
                });
            }else{
                result.push({
                    name : element[0].promote[0].name + element[0].promote[0].type,
                    total: element[0].promote_summary
                });
            }
        })
    }
    return result;
};

Calculator.prototype.subtotal = function (goods) {
    return _(goods).reduce(function(memo,item){return memo+item.total},0);
};
