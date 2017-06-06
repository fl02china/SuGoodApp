package com.sugood.app.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wilk on 17/4/26 18:04
 * ganweib@gmail.com
 * describe:
 */

public class PingLun implements Serializable {

    private static final long serialVersionUID = 3859825211207420658L;
    String face;
    String evaluate;
    String createTime;
    String speed;
    String nickname;
    String contents;
    String userId;
    String dianpingId;
    String score;
    String reply;
    List<String> pic;

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDianpingId() {
        return dianpingId;
    }

    public void setDianpingId(String dianpingId) {
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


    @Override
    public String toString() {
        return "PingLun{" +
                "face='" + face + '\'' +
                ", evaluate='" + evaluate + '\'' +
                ", createTime='" + createTime + '\'' +
                ", speed='" + speed + '\'' +
                ", nickname='" + nickname + '\'' +
                ", contents='" + contents + '\'' +
                ", userId='" + userId + '\'' +
                ", dianpingId='" + dianpingId + '\'' +
                ", score='" + score + '\'' +
                ", reply='" + reply + '\'' +
                ", pic=" + pic +
                '}';
    }
}
