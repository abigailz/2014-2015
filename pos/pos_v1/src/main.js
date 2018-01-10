//TODO: Please write code in this file.

var countXueBi = 0;   //记录雪碧的甁数
var countLiZhi = 0;   //记录荔枝的斤数
var countFangBM = 0;  //记录方便面的包数
var allItem = loadAllItems();
var promote = loadPromotions();
var out_put = '';
var input = [];
var sumY = 0;
var sum = 0;

function printInventory(inputs){

    //计算每种商品的数量
    aCCount(inputs);
    //去掉重复的数据
    input = Combine(inputs);
    //打印
    Print(input,allItem);
}

//打印数据
function Print(input, allItem){

    out_put += '***<没钱赚商店>购物清单***';
    for(var g=0; g<input.length;g++){
        for(var j=0; j<allItem.length; j++){
            if(allItem[j].barcode == input[g].slice(0,10)){
                out_put +="\n" + "名称："+allItem[j].name +
                    "，数量："+ ReturnCount(input[g]) + allItem[j].unit+
                    "，单价："+ allItem[j].price.toFixed(2)+ "(元)" +
                    "，小计：" + Price(input[g],allItem[j].price).toFixed(2) + "(元)";
                sum += Price(input[g],allItem[j].price);
                sumY += ReturnCount(input[g])*allItem[j].price;
            }
        }
    }
    out_put += "\n----------------------\n" + "挥泪赠送商品：\n";
    PrintY(input,allItem);
    console.log(out_put +
        "----------------------" + "\n" +
        "总计：" + sum.toFixed(2) + "(元)\n" +
        "节省：" +     (sumY-sum).toFixed(2)   + "(元)\n" +
        "**********************");

}

//去掉数组中重复数据合并数组
function Combine(arr){
    var res = [];
    var Reapte;
    for(var i = 0; i<arr.length; i++){
        Reapte = false;
        for(var j = 0; j<res.length; j++){
            if(res[j].substring(0,10) === arr[i].substring(0,10)){
                Reapte = true;
                break;
            }
        }
        if(!Reapte){
            res.push(arr[i]);
        }
    }
    return res;
}

//计算每种商品的数量
function aCCount(inputs) {
    for (var i = 0; i < inputs.length; i++) {
        if (inputs[i].slice(0, 10) == "ITEM000001") {
            if (inputs[i].length > 10) {
                countXueBi += (Number)(inputs[i].slice(11));
            } else {
                countXueBi++;
            }
        } else if (inputs[i].slice(0, 10) == "ITEM000003") {

            if (inputs[i].length > 10) {
                countLiZhi += (Number)(inputs[i].slice(11));
            } else {
                countLiZhi++;
            }
        } else if (inputs[i].slice(0, 10) == "ITEM000005") {

            if (inputs[i].length > 10) {
                countFangBM += (Number)(inputs[i].slice(11));
            } else {
                countFangBM++;
            }
        }
    }
}

//返回商品个数
function ReturnCount(str){
    if(str.slice(0,10) == "ITEM000001"){
        return countXueBi;
    }else if(str.slice(0,10) == "ITEM000003"){
        return countLiZhi;
    }else if(str.slice(0,10) == "ITEM000005"){
        return countFangBM;
    }
}

//打印部分优惠商品信息
function PrintY(input,allItem){
    var i = 0;
    while(i<input.length){
        if(ReturnCount(input[i]) > 2){
            for(var j = 0; j<allItem.length; j++){
                if(allItem[j].barcode == input[i].slice(0,10)){
                    out_put += "名称：" + allItem[j].name +"，数量：1" + allItem[j].unit + '\n';
                }
            }
        }
        ++i;
    }
}

//优惠后的各商品的总价格
function Price(str,price){
    var flag = false;
    for(var i=0; i<promote[0].barcodes.length; i++){
        if( promote[0].barcodes[i] == str.slice(0,10)){
            flag = true;
            break;
        }
    }
    if(flag){
        return (ReturnCount(str)-1)*price;
    }else{
        return ReturnCount(str)*price;
    }
}