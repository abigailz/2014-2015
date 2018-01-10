describe('whole test the strategy one', function () {
    var allItems, inputs, dateDigitToString;

    beforeEach(function () {
        allItems  = loadAllItems();
        inputs = [
            { 'ITEM000000' : 20 },
            { 'ITEM000010' : 20 },
            { 'ITEM000005' : 30 },
            { 'ITEM000003' : 12 }
            ];
        dateDigitToString = function (num) {
            return num < 10 ? '0' + num : num;
        };
    });

    it('Strategy one should print_item_information correct text', function () {

        spyOn(console, 'log');

        printInventoryStrategyOne(inputs);

        var currentDate = new Date(),
            year = dateDigitToString(currentDate.getFullYear()),
            month = dateDigitToString(currentDate.getMonth() + 1),
            date = dateDigitToString(currentDate.getDate()),
            hour = dateDigitToString(currentDate.getHours()),
            minute = dateDigitToString(currentDate.getMinutes()),
            second = dateDigitToString(currentDate.getSeconds()),
            formattedDateString = year + '年' + month + '月' + date + '日 ' + hour + ':' + minute + ':' + second;

        var expectText =
            '***<没钱赚商店>购物清单***\n' +
            '打印时间：' + formattedDateString + '\n\n' +
            '----------------------\n' +
            '名称：可口可乐350ml，数量：20瓶，单价：3.00(元)，小计：60.00(元)\n' +
            '名称：可口可乐550ml，数量：20瓶，单价：4.00(元)，小计：80.00(元)\n' +
            '名称：康师傅方便面，数量：30袋，单价：4.50(元)，小计：135.00(元)\n' +
            '名称：云山荔枝，数量：12斤，单价：15.00(元)，小计：180.00(元)\n\n' +
            '----------------------\n' +
            '优惠信息：\n' +
            '名称：可口可乐品牌打折，金额：14.00元\n' +
            '名称：满100减3，金额：3.00元\n\n' +
            '----------------------\n' +
            '总计：438.00(元)\n' +
            '节省：17.00(元)\n' +
            '**********************';

        expect(console.log).toHaveBeenCalledWith(expectText);
    });
});
