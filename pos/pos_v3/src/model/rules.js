var Rules = function (brand,single,brand_full,single_full,no,other_full,other) {
    this.brand_discount = brand || [];
    this.single_discount = single || [];
    this.brand_full_less = brand_full || [];
    this.single_full_less = single_full || [];
    this.no = no || [];
    this.other_full_less = other_full || [];
    this.other_discount = other || [];
};