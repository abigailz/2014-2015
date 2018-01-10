var Discount_Promotion = function (type,name,discount) {
    Promotion.call(this,type,name);
    this.discount = discount;
};

Discount_Promotion.prototype.calculatePromoteMoney = function (money) {
    return money - Math.floor(money * this.discount);
};