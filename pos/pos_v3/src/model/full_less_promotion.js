var Full_Less_Promotion = function (type,name,full,less) {
    Promotion.call(this,type,name);
    this.full = full;
    this.less = less;
};

Full_Less_Promotion.prototype.calculatePromoteMoney = function (money) {
    return money>this.full ? (parseInt(money/this.full)) * this.less : 0;
};