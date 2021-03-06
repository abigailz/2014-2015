//TODO: Please write code in this file.
var allItem = loadAllItems();
var pro = loadPromotions();
var probarcode = pro[0].barcodes;
var out_put = '';
var input = [];
var sumY = 0;   //存放优惠后的价格
var sumZ = 0;   //存放优惠前的价格

function printInventory(inputs) {
    input = _.uniq(inputs);
    out_put += '***<没钱赚商店>购物清单***';

    out_put += "\n" + "打印时间：" + date() + "\n----------------------";
    for (var i = 0; i < _.size(input); i++) {
        for (var j = 0; j < _.size(allItem); j++) {
            if (_.contains(input[i], allItem[j].barcode)) {
                out_put += "\n" + "名称：" + allItem[j].name +
                    "，数量：" + aCCount(input[i], inputs) + allItem[j].unit +
                    "，单价：" + allItem[j].price.toFixed(2) + "(元)" +
                    "，小计：" + Price(input[i],inputs, allItem[j].price).toFixed(2) + "(元)";
                sumZ += Price(input[i],inputs, allItem[j].price);
                sumY += aCCount(input[i],inputs) * allItem[j].price;
            }
        }
    }
    out_put += "\n----------------------\n" + "挥泪赠送商品：\n";
    //追加部分商品优惠信息
    Append(input, inputs);
    //打印数据
    print(out_put);
    console.log(out_put +
        "----------------------" + "\n" +
        "总计：" + sumZ.toFixed(2) + "(元)\n" +
        "节省：" + (sumY - sumZ).toFixed(2) + "(元)\n" +
        "**********************");
}

//打印时间
function date(){
    var dateDigitToString = function (num) {
        return num < 10 ? '0' + num : num;
    };
    var currentDate = new Date(),
        year = dateDigitToString(currentDate.getFullYear()),
        month = dateDigitToString(currentDate.getMonth() + 1),
        date = dateDigitToString(currentDate.getDate()),
        hour = dateDigitToString(currentDate.getHours()),
        minute = dateDigitToString(currentDate.getMinutes()),
        second = dateDigitToString(currentDate.getSeconds()),
        formattedDateString = year + '年' + month + '月' + date + '日 ' + hour + ':' + minute + ':' + second;
    return formattedDateString;
}
//计算每种商品的数量并返回
function aCCount(code,inputs){
    var count = 0;
    for(var j = 0; j < _.size(inputs); j++){
        if (_.contains(code , inputs[j])){
            if (_.size(code) > 10) {
                count += _.parseInt(code.slice(11));
            }else{
                count++;
            }
        }
    }
    return count;
}
//部分优惠商品信息
function Append(input,inputs){
    var i = 0;
    while(i< _.size(input)){
        if(aCCount(input[i],inputs) > 2){
            for(var j = 0; j< _.size(allItem); j++){
                if(_.contains(input[i],allItem[j].barcode)){
                    out_put += "名称：" + allItem[j].name +"，数量：1" + allItem[j].unit + '\n';
                }
            }
        }
        ++i;
    }
}
//优惠后的各商品的总价格
function Price(code,inputs,price){
    var flag = false;
    for(var i=0; i< _.size(probarcode); i++){
        if( _.contains(code , probarcode[i])){
            flag = true;
            break;
        }
    }
    if(flag){
        return (aCCount(code,inputs)-1)*price;
    }else{
        return (aCCount(code,inputs)*price);
    }
}