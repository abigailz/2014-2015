function printInventoryStrategyOne(inputs){
    var cart_goods = new Cart().create_cart_goods(inputs);
    var loadPromotion = loadPromotionOne();

    var rule = new Rules(loadPromotion.brand_discount,loadPromotion.single_discount,[],[],loadPromotion.no_promotion,loadPromotion.other_full_less,[]);
    var strategy_one = new Strategy_One(rule);
    var promotionInformation = strategy_one.calculate(cart_goods);
    printGoodsInfo(cart_goods,promotionInformation);
}