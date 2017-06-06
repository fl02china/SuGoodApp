package com.sugood.app.entity;

import java.util.List;

/**
 * Package :com.sugood.app.entity
 * Description :
 * Author :Rc3
 * Created at :2017/04/18 13:53.
 */

public class ShopComment {


    /**
     * face : /Speedpic/img/1491877095855.jpg
     * evaluate : 0
     * createTime : 0
     * speed : 0
     * nickname : 18376543595
     * contents : 测试cxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxccccccccczkhdskhfksfhsdjfshjkfdkkshdjsjdjjjie结束
     * userId : 0
     * dianpingId : 3
     * score : 4
     * reply : shangjiahuifu
     * pic : ["/attachs/2017/03/02/thumb_58b7c4c1a83e2.jpg"]
     */

    private String face;
    private int evaluate;
    private int createTime;
    private int speed;
    private String nickname;
    private String contents;
    private int userId;
    private int dianpingId;
    private String score;
    private String reply;
    private List<String> pic;

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public int getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(int evaluate) {
        this.evaluate = evaluate;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDianpingId() {
        return dianpingId;
    }

    public void setDianpingId(int dianpingId) {
        this.dianpingId = dianpingId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public List<String> getPic() {
        return pic;
    }

    public void setPic(List<String> pic) {
        this.pic = pic;
    }
}
