function printInventory(inputs) {
    var formattedDateString = build_current_datetime_string();
    var receipt_items = build_receipt_items_form_input(inputs);
    var result = build_receipt_result(formattedDateString, receipt_items);
    console.log(result);
}