
package com.sugood.app.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

public class TakeawayShopInfo implements Serializable {


    private static final long serialVersionUID = 5421738422267540168L;
    private List<EleBean> Ele;
    private List<TypeBean> type;

    public List<EleBean> getEle() {
        return Ele;
    }

    public void setEle(List<EleBean> Ele) {
        this.Ele = Ele;
    }

    public List<TypeBean> getType() {
        return type;
    }

    public void setType(List<TypeBean> type) {
        this.type = type;
    }

    public static class EleBean implements Serializable {
        /**
         * shopName : 沉鱼落宴
         * shopId : 91
         * sinceMoney : 13800
         * photo : /attachs/2017/02/03/thumb_58944d73ac340.jpg
         * logistics : 300
         * intro : 本店营业时间为：16：30至24：30
         */

        private String shopName;
        private int shopId;
        private int sinceMoney;
        private String photo;
        private Double logistics;
        private String intro;

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

//

        public int getSinceMoney() {
            return sinceMoney / 100;
        }


//        public String getSinceMoney() {
//
//            String thePrice = String.valueOf(sinceMoney);
//
//            if (sinceMoney==0) {
//                return "起送价格 0元";
//            } else {
//                return "起送价格 "+thePrice.substring(0, thePrice.length() - 2) + "." + thePrice.substring(thePrice.length() - 2) + "元";
//
//            }
//        }

        public void setSinceMoney(int sinceMoney) {
            this.sinceMoney = sinceMoney;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public Double getLogistics() {
            return logistics / 100;
        }

        public void setLogistics(Double logistics) {
            this.logistics = logistics;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }
    }

    public static class TypeBean {

        @JSONField(name = "酸菜鱼")
        private List<PicklesFish> picklesFish;

        @JSONField(name = "烤鱼")
        private List<ToastFish> toastFish;

        @JSONField(name = "炒菜类")
        private List<Cook> cook;

        @JSONField(name = "主食")
        private List<StapleFood> stapleFood;

        @JSONField(name = "营养汤、煲类")
        private List<Soup> soup;

        @JSONField(name = "米饭")
        private List<Rice> rice;

        public List<PicklesFish> getPicklesFish() {
            return picklesFish;
        }

        public void setPicklesFish(List<PicklesFish> picklesFish) {
            this.picklesFish = picklesFish;
        }

        public List<ToastFish> getToastFish() {
            return toastFish;
        }

        public void setToastFish(List<ToastFish> toastFish) {
            this.toastFish = toastFish;
        }

        public List<Cook> getCook() {
            return cook;
        }

        public void setCook(List<Cook> cook) {
            this.cook = cook;
        }

        public List<StapleFood> getStapleFood() {
            return stapleFood;
        }

        public void setStapleFood(List<StapleFood> stapleFood) {
            this.stapleFood = stapleFood;
        }

        public List<Soup> getSoup() {
            return soup;
        }

        public void setSoup(List<Soup> soup) {
            this.soup = soup;
        }

        public List<Rice> getRice() {
            return rice;
        }

        public void setRice(List<Rice> rice) {
            this.rice = rice;
        }

        public static class PicklesFish {
            /**
             * price : 6800
             * desc : 招牌酸菜鱼
             * productName : 招牌酸菜鱼
             * photo : /attachs/2017/02/04/thumb_58954029a904d.jpg
             * soldNum : 0
             * productId : 1897
             */

            private int price;
            private String desc;
            private String productName;
            private String photo;
            private int soldNum;
            private int productId;

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public int getSoldNum() {
                return soldNum;
            }

            public void setSoldNum(int soldNum) {
                this.soldNum = soldNum;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }
        }

        public static class ToastFish {
            /**
             * price : 5800
             * desc : 秘制烤黄骨鱼
             * productName : 秘制烤黄骨鱼
             * photo : /attachs/2017/02/04/thumb_58953f971be00.jpg
             * soldNum : 0
             * productId : 1894
             */

            private int price;
            private String desc;
            private String productName;
            private String photo;
            private int soldNum;
            private int productId;

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public int getSoldNum() {
                return soldNum;
            }

            public void setSoldNum(int soldNum) {
                this.soldNum = soldNum;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }
        }

        public static class Cook {
            /**
             * price : 1500
             * desc : 葱花煎蛋|
             * productName : 葱花煎蛋|
             * photo : /attachs/2017/02/04/thumb_58953dca3e351.jpg
             * soldNum : 0
             * productId : 1878
             */

            private int price;
            private String desc;
            private String productName;
            private String photo;
            private int soldNum;
            private int productId;

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public int getSoldNum() {
                return soldNum;
            }

            public void setSoldNum(int soldNum) {
                this.soldNum = soldNum;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }
        }

        public static class StapleFood {
            /**
             * price : 3800
             * desc : 剁椒鱼头
             * productName : 剁椒鱼头
             * photo : /attachs/2017/02/04/thumb_589541c804fca.jpg
             * soldNum : 0
             * productId : 1903
             */

            private int price;
            private String desc;
            private String productName;
            private String photo;
            private int soldNum;
            private int productId;

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public int getSoldNum() {
                return soldNum;
            }

            public void setSoldNum(int soldNum) {
                this.soldNum = soldNum;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }
        }

        public static class Soup {
            /**
             * price : 6800
             * desc : 经典干鸡煲
             * productName : 经典干鸡煲
             * photo : /attachs/2017/02/04/thumb_5895404e58e90.jpg
             * soldNum : 0
             * productId : 1898
             */

            private int price;
            private String desc;
            private String productName;
            private String photo;
            private int soldNum;
            private int productId;

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public int getSoldNum() {
                return soldNum;
            }

            public void setSoldNum(int soldNum) {
                this.soldNum = soldNum;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }
        }

        public static class Rice {
            /**
             * price : 200
             * desc : 米饭
             * productName : 米饭
             * photo : /attachs/2017/02/04/thumb_58954012608a2.jpg
             * soldNum : 0
             * productId : 1896
             */

            private int price;
            private String desc;
            private String productName;
            private String photo;
            private int soldNum;
            private int productId;

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }

            public int getSoldNum() {
                return soldNum;
            }

            public void setSoldNum(int soldNum) {
                this.soldNum = soldNum;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }
        }
    }


    @Override
    public String toString() {
        return "TakeawayShopInfo{" +
                "Ele=" + Ele +
                ", type=" + type +
                '}';
    }
}