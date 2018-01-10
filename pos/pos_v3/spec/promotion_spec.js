describe('test the full less promotion class the caculate method', function () {
    var full_less_promotion,discount_promotion,goods;
    beforeEach(function () {
        full_less_promotion = new Full_Less_Promotion('single','云山荔枝',100,3);
        discount_promotion = new Discount_Promotion('single','云山荔枝',0.9);
        goods = {
            brand_discount: "云山",
            count: 12,
            name: "云山荔枝",
            price: 15,
            summary: 180,
            unit: "斤"
        }
    });

    it('full less when the goods money over 100 less 3 return right summary', function () {
        expect(full_less_promotion.calculatePromoteMoney(goods.summary)).toEqual(3);
    });

    it('discount  the goods money discount  return right summary', function () {
        expect(discount_promotion.calculatePromoteMoney(goods.summary)).toEqual(18);
    })
});