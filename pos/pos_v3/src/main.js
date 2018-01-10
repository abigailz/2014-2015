function printCartGoods(cart_goods, result) {
    _(cart_goods).each(function (element) {
        result += '名称：' + element.name + '，数量：' + element.count + element.unit + '，单价：'
        + element.price.toFixed(2) + '(元)，小计：' + element.total.toFixed(2) + '(元)\n'
    });
    return result;
}

function printPromotionInfo(promotion) {
    var result = '';
    _(promotion).each(function (item) {
        result += '名称：' + item.name + '，金额：' + item.total.toFixed(2) + '元\n';
    });
    return result;
}

function printGoodsInfo(cart_goods, promotionInfo) {
    var result =
        '***<没钱赚商店>购物清单***\n' +
        '打印时间：' + new moment().format('YYYY年MM月DD日 HH:mm:ss') + '\n\n' +
        '----------------------\n';
    result = printCartGoods(cart_goods, result);
    result +=
        '\n----------------------\n' +
        '优惠信息：\n';
    result += printPromotionInfo(promotionInfo.single_discount);
    result += printPromotionInfo(promotionInfo.brand_discount);
    result += printPromotionInfo(promotionInfo.brand_full);
    result += printPromotionInfo(promotionInfo.single_full);
    result += printPromotionInfo(promotionInfo.other_discount);
    result += printPromotionInfo(promotionInfo.other_full);
    result +=
        '\n----------------------\n';
    var calculator = new Calculator();
    result +=
        '总计：' + (calculator.subtotal(cart_goods) - calculator.subtotal(_.flatten(_.values(promotionInfo)))).toFixed(2) + '(元)\n' +
        '节省：' + calculator.subtotal(_.flatten(_.values(promotionInfo))).toFixed(2) + '(元)\n';
    result += '**********************';


    var A = {
        k1: 1, k2: 4, say: function () {
            return ('hello world')
        }
    };
    var B = clone(A);
    B.k1 = 90;
    console.info('start----')
    console.info(A, '********************************')
    console.info(B, '********************************')
    console.info(B.say(), '&&&&&&&&&&&&&&&&&&&&&&&&&&&&&7')
    console.info('end=====')


    console.log(result);
}

//其实这个一种另一种含义的浅克隆，修改新的对象不会影响到原来的数据，但是不能克隆对象的方法
function clone(obj) {
    var s = JSON.stringify(obj);
    var o = JSON.parse(s);
    return o;
}

/*function clone(obj) {
 var o = obj.constructor === Array ? [] : {};
 for (var i in obj) {
 if (obj.hasOwnProperty(i)) {
 o[i] = typeof obj[i] === "object" ? clone(obj[i]) : obj[i];
 }
 }
 return o;
 }*/

/*function clone(item) {
 if (!item) {
 return item;
 } // null, undefined values check

 var types = [Number, String, Boolean], result;

 // normalizing primitives if someone did new String('aaa'), or new Number('444');
 types.forEach(function (type) {
 if (item instanceof type) {
 result = type(item);
 }
 });

 if (typeof result == "undefined") {
 if (Object.prototype.toString.call(item) === "[object Array]") {
 result = [];
 item.forEach(function (child, index) {
 result[index] = clone(child);
 });
 } else if (typeof item == "object") {
 // testing that this is DOM
 if (item.nodeType && typeof item.cloneNode == "function") {
 result = item.cloneNode(true);
 } else if (!item.prototype) { // check that this is a literal
 if (item instanceof Date) {
 result = new Date(item);
 } else {
 //it is an object literal
 result = {};
 for (var i in item) {
 result[i] = clone(item[i]);
 }
 }
 } else {
 // depending what you would like here,
 // just keep the reference, or create new object
 if (false && item.constructor) {
 // would not advice to do that, reason? Read below
 result = new item.constructor();
 } else {
 result = item;
 }
 }
 } else {
 result = item;
 }
 }

 return result;
 }*/

/*
 function clone(obj) {
 if (obj == null || typeof(obj) != 'object')
 return obj;

 var temp = obj.constructor(); // 返回创建此对象的引用

 for (var key in obj) {
 if (obj.hasOwnProperty(key)) {
 temp[key] = clone(obj[key]);
 }
 }
 return temp;
 }*/
