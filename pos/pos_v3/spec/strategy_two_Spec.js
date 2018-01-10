describe('whole test the strategy two', function () {
    var allItems, inputs, dateDigitToString;

    beforeEach(function () {
        allItems = loadAllItems();
        inputs = [
            { 'ITEM000000' : 20 },
            { 'ITEM000010' : 20 },
            { 'ITEM000005' : 30 },
            { 'ITEM000008' : 25 },
            { 'ITEM000003' : 8  },
            { 'ITEM000002' : 14 }
        ];
        dateDigitToString = function (num) {
            return num < 10 ? '0' + num : num;
        };
    });

    it('Strategy two should print_item_information correct text', function () {

        spyOn(console, 'log');

        printInventoryStrategyTwo(inputs);

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
            '名称：康师傅冰红茶，数量：25瓶，单价：3.00(元)，小计：75.00(元)\n' +
            '名称：云山荔枝，数量：8斤，单价：15.00(元)，小计：120.00(元)\n' +
            '名称：云山苹果，数量：14斤，单价：5.50(元)，小计：77.00(元)\n\n' +
            '----------------------\n' +
            '优惠信息：\n' +
            '名称：可口可乐350ml单品打折，金额：3.00元\n' +
            '名称：可口可乐品牌打折，金额：8.00元\n' +
            '名称：康师傅品牌满100减2，金额：4.00元\n' +
            '名称：云山荔枝单品满100减5，金额：5.00元\n\n' +
            '----------------------\n' +
            '总计：527.00(元)\n' +
            '节省：20.00(元)\n' +
            '**********************';

        expect(console.log).toHaveBeenCalledWith(expectText);
    });
});
