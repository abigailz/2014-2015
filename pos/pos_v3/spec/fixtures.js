function loadAllItems() {
    return [
        new Item('ITEM000000', '可口可乐350ml', '瓶', 3.00, '可口可乐'),
        new Item('ITEM000010', '可口可乐550ml', '瓶', 4.00, '可口可乐'),
        new Item('ITEM000001', '雪碧', '瓶', 3.00, ''),
        new Item('ITEM000007', '果粒橙', '瓶', 3.50, ''),
        new Item('ITEM000002', '云山苹果', '斤', 5.50, '云山'),
        new Item('ITEM000003', '云山荔枝', '斤', 15.00, '云山'),
        new Item('ITEM000004', '电池', '个', 2.00, ''),
        new Item('ITEM000005', '康师傅方便面', '袋', 4.50, '康师傅'),
        new Item('ITEM000008', '康师傅冰红茶', '瓶', 3.00, '康师傅'),
        new Item('ITEM000006', '羽毛球', '个', 1.00, '')
    ];
}

function loadPromotionOne(){
    return {
        brand_discount :[new Discount_Promotion('品牌打折','可口可乐',0.9)],
        single_discount : [new Discount_Promotion('单品打折', '可口可乐350ml', 0.95)],
        no_promotion : [new Promotion('no_promotion', '康师傅方便面')],
        other_full_less : [new Full_Less_Promotion('满', 'all', 100, 3)]
    }
}

function loadPromotionTwo(){
    return {
        brand_discount:[new Discount_Promotion('品牌打折','可口可乐',0.9)],
        single_discount:[new Discount_Promotion('单品打折', '可口可乐350ml', 0.95)],
        brand_full_less:[new Full_Less_Promotion('品牌满','康师傅',100,2)],
        single_full_less:[new Full_Less_Promotion('单品满','云山荔枝',100,5)]
    }
}

function loadPromotionThree(){
    return {
        brand_discount:[new Discount_Promotion('品牌打折','可口可乐',0.9)],
        single_discount:[new Discount_Promotion('单品打折', '可口可乐350ml', 0.95)],
        brand_full_less:[new Full_Less_Promotion('品牌满','康师傅',100,2)],
        no_promotion:[new Promotion('no_promotion','云山苹果')],
        other_full_less:[new Full_Less_Promotion('满','all',100,5)]
    }
}