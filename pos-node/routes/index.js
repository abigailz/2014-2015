var mongodb = require('../models/db'),
    Goods = require('../models/goods.js'),
    Cart = require('../models/Cart.js'),
    Promotions = require('../models/promotions.js'),
    _ = require('underscore');
module.exports = function (app) {
  app.get('/', function (req,res) {
    if(!req.session.goods_total){
      req.session.goods_total = 0;
    }
    res.render('user/index',{title:'主页',goods_total:req.session.goods_total});
  });
  app.get('/goods_list', function (req,res) {
    if(!req.session.goods){
      req.session.goods = [];
    }
    Goods.get_all_goods(function (err,goods) {
      res.render('user/goods_list',{
        title:'主页面',goods:goods,goods_total:req.session.goods_total})
    });
  });
  app.post('/add_goods_cart', function (req,res) {
    var goods = req.session.goods;
    Goods.get_one_goods(req.body._id, function (err,item) {
      if(_(goods).where({"_id":req.body._id}).length != 0){
        _.find(goods, function (item) {return item._id == req.body._id;}).number++;
      }else {
        item.number = 1;
        goods.push(item);
      }
      req.session.goods = goods;
      req.session.goods_total++;
      res.json(req.session.goods_total);
    });
  });
  function commit_goods_data(req,res){
    Promotions.get_all_promotion_goods_info(function (err,promotions) {
      var barcode = promotions[0].barcodes;
      var goods = req.session.goods;
      req.session.goods = Cart.append_price(barcode,goods);
      req.session.summary_price = Cart.summary_price(req.session.goods);
      res.json({total:req.session.goods_total,
        item:_.find(req.session.goods, function (item) {return item._id == req.body._id;}),
        summary_price:req.session.summary_price
      })
    });
  }
  app.post('/add_goods', function (req,res) {
    _.find(req.session.goods, function (item) {return item._id == req.body._id;}).number++;
    req.session.goods_total++;
    commit_goods_data(req,res);
  });
  app.post('/delete_one_goods', function (req,res) {
    _.find(req.session.goods, function (item) {return item._id == req.body._id;}).number--;
    req.session.goods_total--;
    commit_goods_data(req,res);
  });
  app.get('/goods_cart', function (req,res) {
    var goods = req.session.goods;
    Promotions.get_all_promotion_goods_info(function (err,promotions) {
      var barcode = promotions[0].barcodes;
      req.session.goods = Cart.append_price(barcode,goods);
      req.session.summary_price = Cart.summary_price(req.session.goods);
      res.render('user/goods_cart',{
        title:'购物车页面',
        goods_total:req.session.goods_total,
        goods:_(req.session.goods).filter(function(item){return item.number != 0}),
        summary_price:req.session.summary_price
      })
    });
  });
  app.get('/goods_pay', function (req,res) {
    var goods = _(req.session.goods).filter(function(item){return item.number != 0});
    res.render('user/goods_pay',{
      title:'付款页面',
      goods_total:req.session.goods_total,
      goods:goods,
      summary_price:req.session.summary_price,
      promotion_price:Cart.promotion_price(goods),
      free_goods:_(Cart.free_goods(goods)).filter(function(item){return item.number != 0})
    });
  });
  app.get('/comfirm', function (req,res) {
    req.session.goods_total = 0;
    req.session.goods = [];
    req.session.summary_price = 0;
    res.render('user/index',{title:"主页",goods_total:req.session.goods_total})
  });

  app.get('/admin', function (req,res) {
    res.render('admin/index',{title:'管理员'});
  });
  app.get('/admin_add_goods', function (req,res) {
    res.render('admin/add_goods',{title:"添加商品页面"});
  });
  app.get('/admin_add_attribute', function (req,res) {
    res.render('admin/add_attribute',{title:"添加属性页面"});
  });
  app.get('/admin_del_attribute', function (req,res) {
    res.render('admin/del_attribute',{title:"删除属性页面"});
  });
  app.get('/admin_goods_detail', function (req,res) {
    res.render('admin/goods_detail',{title:"商品详情页面"});
  });
  app.get('/admin_promotion_rules', function (req,res) {
    res.render('admin/promotion_rules',{title:"折扣活动页面"});
  });
  app.get('/admin_add_promotion_rules', function (req,res) {
    res.render('admin/add_promotion_rules',{title:"折扣活动页面"});
  });
};