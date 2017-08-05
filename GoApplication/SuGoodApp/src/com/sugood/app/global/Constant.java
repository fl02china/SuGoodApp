package com.sugood.app.global;

/**
 * 常量类
 *
 * @author dec
 */
public class Constant {
    public static final String COM_URL = "www.if.com";
    //新接口base url
    public static final String NEWSUGOODBASEURL = "http://city58.cn:8080/Speed/";
    //接口base url
   // public static final String SUGOODBASEURL = "http://sgd.92city.com:8080/Speed/";
    //接口base 正式服务器
//public static final String SUGOODBASEURL = "http://www.goodsolo.com:8080/Speed/";
 //接口base 测试服务器
    public static final String SUGOODBASEURL = "http://test.goodsolo.com/Speed/";
    //图片 base Url
    public static final String PHOTOBASEURL = "http://www.goodsolo.com";
 //upload  经纬度
 public static final String CHECKUPDATE = SUGOODBASEURL + "Speed/Version";
    //upload  经纬度
    public static final String UPLOADLATLONG = SUGOODBASEURL + "Speed/lat/lngQueryPage";
    //美食页面数据
    public static final String GOODFOOD = SUGOODBASEURL + "Speed/MeiShi/baoShop/queryPage";
    //美食广告
    public static final String GOODFOODAD = SUGOODBASEURL + "Speed/baoAd/queryPage";
    //shop
    public static final String SHOP = SUGOODBASEURL + "Speed/MeiShi/baoShop/ShopQueryPage";
    //美食类型
    public static final String GOODFOODCLASSIC = SUGOODBASEURL + "Speed/MeiShi/LeiXing/queryPage";
    //
    public static final String SUGOODTUANGOU = SUGOODBASEURL + "Speed/MeiShi/baoShop/TuanGou";
    //获取短信验证码
    public static final String GETCODE = SUGOODBASEURL + "Speed/mobile";
    //登录
    public static final String SUGOOGLOGIN = SUGOODBASEURL + "Speed/My/userLoginent";
    //第三方登录
    public static final String SUGOOGTHRLOGIN = SUGOODBASEURL + "Speed/My/userWxQqLogin";
    //注册
    public static final String SUGOODREGISTER = SUGOODBASEURL + "Speed/baoUsers/add";
    // 找回密码
    public static final String SUGOODGFINDPW = SUGOODBASEURL + "Speed/baoUsers/update";
    // 美食搜索
    public static final String SUGOOODGOODFOOODSEARCH = SUGOODBASEURL + "Speed/MeiShi/baoShop/queryPage";
    /*   商城首页*/
    public static final String SHOP_MAIN_URL = SUGOODBASEURL + "Speed/ShanCheng/LeiXinqueryPage";
    /* 商城列表*/
    public static final String SHOP_MAIN_LIST_URL = SUGOODBASEURL + "Speed//ShanCheng/SanQueryPage";
    /*商城列表详细*/

//     public static final String SHOP_MAIN_LIST_DETAIL_URL=SUGOODBASEURL+"Speed/ShanCheng/XiangQinqueryPage";

    //上传头像
    public static final String UPLOADAVATAR = SUGOODBASEURL + "Speed/My/uploadPoto";
    //修改nickneme
    public static final String CHANGENICKNAME = SUGOODBASEURL + "Speed/My/userName/update";
    //查询地址
    public static final String SUGOODADDR = SUGOODBASEURL + "Speed/My/shopAddr/queryPage";
    //订单查询
    public static final String SUGOODORDER = SUGOODBASEURL + "Speed/My/order";

    //商城订单查询
    public static final String SUGOODSCORDER = SUGOODBASEURL + "Speed/My/ShopOrder";
    //团购订单查询
    public static final String SUGOODTGORDER = SUGOODBASEURL + "Speed/My/TuanOrder";
    //外卖订单查询
    public static final String SUGOODWMORDER = SUGOODBASEURL + "Speed/My/WaiOrder";
    // 删除地址
    public static final String SUGOODDELETEADDR = SUGOODBASEURL + "Speed/baoPaddress/delete";
    //添加地址
    public static final String SUGOODADDADD = SUGOODBASEURL + "Speed/baoPaddress/add";

    public static final String SHOP_MAIN_LIST_DETAIL_URL = SUGOODBASEURL + "Speed/ShanCheng/XiangQinqueryPage";
    /* 商城商家信息*/
    public static final String SHOP_INFO_URL = SUGOODBASEURL + "Speed/ShanCheng/ShopInfo";
    /*商城商家商品的数据*/
    public static final String SHOP_PRODUCT_URL = SUGOODBASEURL + "Speed/ShanCheng/shopIdQueryPage";
    /*外卖二级页面下拉数据*/
    public static final String TAKEAWAY_MAIN_LIST_URL = SUGOODBASEURL + "Speed/WaiMai/baoShop/queryPage";
    /* 外卖三级页面类型查询*/
    public static final String TAKEAWAY_MENU_LIST_URL = SUGOODBASEURL + "Speed/WaiMai/baoShop/ShuJu";
    /*外卖评价查询*/
    public static final String TAKEAWAY_REMARK_LIST_URL = SUGOODBASEURL + "Speed/WaiMai/baoShop/eleDianPing";
    /*外卖详情*/
    public static final String TAKEAWAY_DESC_LIST_URL = SUGOODBASEURL + "Speed/WaiMai/baoShop/eleXiangQing";
    //查结果
    public static final String SUGOODRESULT = SUGOODBASEURL + "Speed/pay/query";
    //提交订单
    public static final String SUGOODSUBMITORDER = SUGOODBASEURL + "Speed/alipay";
    //查询是否新用户
    public static final String SUGOODISNEW = SUGOODBASEURL + "Speed/baoEleOrder/queryPage";
    //下单
    public static final String SUGOODALIPAY = SUGOODBASEURL + "Speed/alipay/placeOrder";
   public static final String SUGOODWX = SUGOODBASEURL + "Speed/pay";

    //新增地址
    public static final String ADD_ADDRESS_URL = SUGOODBASEURL + "Speed/baoPaddress/add";
    //修改地址
    public static final String UPDATE_ADDRESS_URL = SUGOODBASEURL + "Speed/baoPaddress/update";
    //删除地址
    public static final String DELETE_ADDRESS_URL = SUGOODBASEURL + "Speed/baoPaddress/delete";

    //获取商店评论
    public static final String SHOP_COMMENT_URL = SUGOODBASEURL + "Speed/MeiShi/baoShop/ShopQuanDianP";


    //获取商店团购评论
    public static final String SHOP_TUAN_COMMENT_URL = SUGOODBASEURL + "Speed/MeiShi/baoShop/ShopTuanQuanDianP";

    //商品收藏
    public static final String PRODUCT_COLLECTION_URL = SUGOODBASEURL + "Speed/My/goodsFavorites";
    //商店收藏
    public static final String SHOP_COLLECTION_URL = SUGOODBASEURL + "Speed/My/shopFavorites";
    //xiaoxi
    public static final String SUGOODMSG_URL = SUGOODBASEURL + "Speed/baoMsg/queryPage";
    //添加商铺收藏
    public static final String SHOP_COLLECtION_ADD = SUGOODBASEURL + "Speed/baoShopFavorites/add";
    //添加商品收藏
    public static final String GOODS_COLLECtION_ADD = SUGOODBASEURL + "Speed/baoGoodsFavorites/add";
    //获取外卖状态列表
    public static final String DIANDAN_LIST_URL = SUGOODBASEURL + "Speed/baoDeliveryOrder/queryPages";
    //获取订单状态
    public static final String DINGDAN_STATUS_URL = SUGOODBASEURL + "Speed/baoDeliveryOrder/queryStatus";
    //黄页
    public static final String HUANFGYE_URL = SUGOODBASEURL + "Speed/baoPoisContent/queryPage";
    //商店评论
    public static final String SHOPPINLUN_URL = SUGOODBASEURL + "Speed/MeiShi/baoShop/ShopQuanDianP";

    public static final String TUANPINGLUN_URL = SUGOODBASEURL + "Speed/MeiShi/baoShop/ShopTuanQuanDianP";

    public static final String TIXIAN_URL = SUGOODBASEURL + "Speed/My/deposit";
    // 退款
    public static final String TUIKUAN_URL = SUGOODBASEURL + "Speed/clienteleRefused";

    // 取消订单
    public static final String CANCLEORDER = SUGOODBASEURL + "Speed/alipay/cancel";
    // 删除店铺
    public static final String DELETESHOP = SUGOODBASEURL + "Speed/baoShopFavorites/delete";


}
