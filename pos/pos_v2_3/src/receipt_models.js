function build_receipt_item_strings(receipt_items){
    var result = '';
    for(var i=0; i<receipt_items.paid_items.length; i++){
        var paid_item = receipt_items.paid_items[i];
        result += '名称：'+paid_item.name+
        '，数量：'+paid_item.count+paid_item.unit+
        '，单价：'+paid_item.price+'(元)，小计：'+paid_item.summary+'(元)\n';
    }
    return result;
}
function build_gift_items_strings(receipt_items){
    var result = '';
    for(var i=0; i<receipt_items.gift_items.length;i++){
        var gift_item = receipt_items.gift_items[i];
        result += '名称：'+gift_item.name+'，数量：'+gift_item.count+gift_item.unit+'\n';
    }
    return result;
}

function find_item_by_barcode(barcode){
    return _(loadAllItems()).findWhere({"barcode":barcode});
}

function build_cart_item(inputs) {
    return _.chain(inputs).groupBy(function(item){return item;
    }).map(function(value,key){
        var item_barcode = key;
        var item_count = value.length;
        if(key.indexOf("-") != -1){
            item_barcode = key.substring(0,key.indexOf("-"));
            item_count = key.substring(key.indexOf("-")+1)
        }
        return {barcode:item_barcode,count:item_count}
    }).map(function(element){
        var item = find_item_by_barcode(element.barcode);
        return {
            name:item.name,
            count:element.count,
            unit:item.unit,
            price:item.price.toFixed(2),
            price_number:item.price,
            barcode:item.barcode
        }
    }).value();
}

function isPromotion(item){
    return _(loadPromotions()[0].barcodes).any(function (barcode) {
        return barcode == item.barcode;
    })
}

function calculate_gift_count(count){
    var left = count;
    var promote_count = 0;
    while(left  > 2){
        left -= 3;
        promote_count++;
    }
    return promote_count;
}

function build_gift_items_by_cart_items(cart_item) {
    return _.chain(cart_item).filter(function (item) {
       return isPromotion(item);
    }).map(function (item) {
        return {
            name:item.name,
            unit:item.unit,
            count:calculate_gift_count(item.count),
            barcode:item.barcode
        }
    }).value();
}

function has_gift(item,gift_item) {
    return _(gift_item).any(function (gift) {
        return gift.barcode == item.barcode;
    });
}

function calculate_paid_item_summary(item,gift_items) {
    var item_gift = _(gift_items).findWhere({"barcode":item.barcode});
    return item.price_number * (item.count - item_gift.count);
}

function build_receipt_items_form_input(inputs) {
    var receipt_items = {
    };

    var cart_item = build_cart_item(inputs);

    receipt_items.paid_items = cart_item;

    receipt_items.gift_items = build_gift_items_by_cart_items(cart_item);

    _(receipt_items.paid_items).each(function (item) {
       if(has_gift(item,receipt_items.gift_items)){
           item.summary = calculate_paid_item_summary(item,receipt_items.gift_items).toFixed(2);
       }else{
           item.summary = (item.price_number * item.count).toFixed(2);
       }
    });

    return receipt_items;
}
