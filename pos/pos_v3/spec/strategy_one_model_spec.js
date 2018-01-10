describe('test the strategy one', function () {
    var strategy;
    beforeEach(function () {
        var rules = {
            brand_discount:[new Discount_Promotion('brand_discount','可口可乐',0.9)],
            single_discount :[new Discount_Promotion('single', '可口可乐350ml', 0.95)],
            brand_full_less:[],
            single_full_less:[],
            no_promotion :[new Promotion('no', '康师傅方便面')],
            other_full_less :[new Full_Less_Promotion('other', 'all', 100, 3)],
            other_discount :[]
        };
        var goods = [
            {
                brand_discount: "可口可乐",
                count: 20,
                name: "可口可乐350ml",
                price: 3,
                summary: 60,
                unit: "瓶"
            },
            {
                brand_discount: "可口可乐",
                count: 20,
                name: "可口可乐550ml",
                price: 4,
                summary: 80,
                unit: "瓶"
            },
            {
                brand_discount: "康师傅",
                count: 30,
                name: "康师傅方便面",
                price: 4.5,
                summary: 135,
                unit: "袋"
            },
            {
                brand_discount: "云山",
                count: 12,
                name: "云山荔枝",
                price: 15,
                summary: 180,
                unit: "斤"
            }
        ];
        strategy = new Strategy_One(rules,goods)
    });
});