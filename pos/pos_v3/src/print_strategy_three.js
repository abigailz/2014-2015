var printInventoryStrategyThree = function (inputs) {
    var cart_goods = new Cart().create_cart_goods(inputs);
    var loadPromotion = loadPromotionThree();

    var rule = new Rules(loadPromotion.brand_discount,loadPromotion.single_discount,loadPromotion.brand_full_less,[],loadPromotion.no_promotion,loadPromotion.other_full_less,[]);
    var strategy_three = new Strategy_Three(rule);
    var promotionInformation = strategy_three.calculate(cart_goods);
    //printGoodsInfo(cart_goods,promotionInformation);
};