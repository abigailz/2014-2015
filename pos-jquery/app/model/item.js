function Item(barcode, name, unit, price,type,number,is_promotion,promotion_price,total_price) {
    this.barcode = barcode;
    this.name = name;
    this.unit = unit;
    this.price = price || 0.00;
    this.type = type;
    this.number = number || 0;
    this.is_promotion = is_promotion || 0;
    this.promotion_price = promotion_price || 0;
    this.total_price = total_price || 0;
}