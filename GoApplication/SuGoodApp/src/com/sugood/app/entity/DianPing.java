package com.sugood.app.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wilk on 17/3/8 15:09
 * ganweib@gmail.com
 * describe:
 */

public class DianPing implements Serializable {

    private static final long serialVersionUID = -5605285980904172282L;

    private String dianpingId;
    private String userId;
    private String evaluate;
    private String score;
    private String contents;
    private String reply;
    private List<String> pic;
    private String face;
    private String nickname;

    public String getDianpingId() {
        return dianpingId;
    }

    public void setDianpingId(String dianpingId) {
        this.dianpingId = dianpingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
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

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "DianPing{" +
                "dianpingId='" + dianpingId + '\'' +
                ", userId='" + userId + '\'' +
                ", evaluate='" + evaluate + '\'' +
                ", score='" + score + '\'' +
                ", contents='" + contents + '\'' +
                ", reply='" + reply + '\'' +
                ", pic=" + pic +
                ", face='" + face + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
