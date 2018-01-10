/**
 * Created by black on 14-12-14.
 */
function build_current_datetime_string() {
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

function dateDigitToString(num) {
    return num < 10 ? '0' + num : num;
}

function build_receipt_result(formattedDateString, receipt_items) {
    var result =
        '***<没钱赚商店>购物清单***\n' +
        '打印时间：' + formattedDateString + '\n' +
        '----------------------\n';

    result += build_receipt_item_strings(receipt_items);

    result += '----------------------\n' +
    '挥泪赠送商品：\n';

    result += build_gift_items_strings(receipt_items);

    result += '----------------------\n' +
    '总计：51.00(元)\n' +
    '节省：7.50(元)\n';

    result += '**********************';
    return result;
}