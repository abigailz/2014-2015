var printInventoryStrategyTwo = function (inputs) {
    var cart_goods = new Cart().create_cart_goods(inputs);
    var loadPromotion = loadPromotionTwo();

    var rule = new Rules(loadPromotion.brand_discount,loadPromotion.single_discount,loadPromotion.brand_full_less,loadPromotion.single_full_less,[],[],[]);
    var strategy_two = new Strategy_Two(rule);
    var promotionInformation = strategy_two.calculate(cart_goods);
    printGoodsInfo(cart_goods,promotionInformation);
};