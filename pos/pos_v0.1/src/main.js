//TODO: Please write code in this file.
function printInventory(inputs) {
    var out_put = '';
    var sum = 0;
    var input = Combine(inputs);
    out_put +=  '***<没钱赚商店>购物清单***';
    for(var i=0; i<input.length;i++){
        out_put +="\n" + "名称："+input[i].name +
            "，数量："+Count(input[i].barcode, inputs) + input[i].unit+
            "，单价："+ input[i].price.toFixed(2)+ "(元)" +
            "，小计：" + (Count(input[i].barcode, inputs)*input[i].price).toFixed(2)+ "(元)";
        sum += Count(input[i].barcode, inputs)*input[i].price;
        }
    print(out_put, sum);
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
        if(code == inputs[j].barcode) {
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
            if(res[j].name == arr[i].name){
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