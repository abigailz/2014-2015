var mongodb = require('./db'),
    ObjectID = require('mongodb').ObjectID;
function Goods(barcode, name, unit, price,type,number) {
    this.barcode = barcode;
    this.name = name;
    this.unit = unit;
    this.price = price || 0.00;
    this.type = type;
    this.number = number || 0;
}
module.exports = Goods;

Goods.get_all_goods = function (callback) {
     mongodb.open(function (err,db) {
         if(err){
             return callback(err);
         }
         db.collection('goods', function (err,collection) {
             if(err){
                 mongodb.close();
                 return callback(err);
             }
             collection.find().toArray(function (err,item) {
                 mongodb.close();
                 if(err){
                     return callback(err);
                 }
                 callback(null,item);
             });
         });
     });
 };

Goods.get_one_goods = function (_id,callback) {
    mongodb.open(function (err,db) {
        if(err){
            return callback(err);
        }
        db.collection('goods', function (err,collection) {
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