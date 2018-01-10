var mongodb = require('../models/db'),
    ObjectID = require('mongodb').ObjectID;

function Promotion(type, barcodes) {
    this.type = type;
    this.barcodes = barcodes || [];
}
module.exports = Promotion;

Promotion.get_all_promotion_goods_info = function (callback) {
    mongodb.open(function (err,db) {
        if(err){
            return callback(err);
        }
        db.collection('promotions', function (err,collection) {
            if(err){
                mongodb.close();
                return callback(err);
            }
            collection.find().toArray(function (err,promotions) {
                mongodb.close();
                if(err){
                    return callback(err);
                }
                callback(null,promotions);
            });
        });
    });
};
Promotion.get_promotions_barcode_by_type_id = function (_id,callback) {
    mongodb.open(function (err,db) {
        if(err){
            return callback(err);
        }
        db.collection('promotions', function (err,collection) {
            if(err){
                mongodb.close();
                return callback(err);
            }
            collection.findOne({"_id":new ObjectID(_id)}, function (err,item) {
                mongodb.close();
                if(err){
                    return callback(err);
                }
                callback(null,item);
            });
        });
    });
};