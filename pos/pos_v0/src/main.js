//TODO: Please write code in this file.
function printInventory(inputs) {
    var out_put = '';
    var sum = 0;
    out_put +=  '***<没钱赚商店>购物清单***';
    for(var i=0; i<inputs.length; ++i){
        out_put +="\n" + "名称："+inputs[i].name +
                        "，数量："+inputs[i].count +inputs[i].unit+
                        "，单价："+ inputs[i].price.toFixed(2)+ "(元)" +
                        "，小计：" + (inputs[i].count*inputs[i].price).toFixed(2)+ "(元)";
        sum += inputs[i].count*inputs[i].price;
    }
    print(out_put, sum);
}
//打印数据
function print(out_put,sum) {
    console.log(out_put + "\n" +
                "----------------------" + "\n" +
                "总计：" + sum.toFixed(2) + "(元)\n" + "**********************");
}