//TODO: Please write code in this file.
function printInventory(inputs){
    var input = Combine(inputs);
    var allItem = loadAllItems();
    var out_put = '';
    var sum = 0;

    out_put += '***<没钱赚商店>购物清单***';
    for(var i=0; i<input.length;i++) {
        for (var j = 0; j < allItem.length; j++) {
            if (allItem[j].barcode === input[i]) {
                out_put += "\n" + "名称：" + allItem[j].name +
                    "，数量：" + Count(input[i], inputs) + allItem[j].unit +
                    "，单价：" + allItem[j].price.toFixed(2) + "(元)" +
                    "，小计：" + (Count(input[i], inputs) * allItem[j].price).toFixed(2) + "(元)";
                sum += Count(input[i], inputs) * allItem[j].price;
            }
        }
    }
    print(out_put,sum);
}
//打印数据
function print(out_put,sum) {
    console.log(out_put + "\n" +
        "----------------------" + "\n" +
        "总计：" + sum.toFixed(2) + "(元)\n" + "**********************");
}

//计算各商品的个数
function Count(code,inputs) {
    var count = 0;
    for(var j=0; j<inputs.length; j++){
        if(code == inputs[j]) {
            count++;
        }
    }
    return count;
}

//去掉重复数据
function Combine(arr){
    var res = [], Reapte;
    for(var i = 0; i<arr.length; i++)
    {
        Reapte = false;
        for(var j = 0; j<res.length; j++)
        {
            if(res[j] == arr[i]){
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