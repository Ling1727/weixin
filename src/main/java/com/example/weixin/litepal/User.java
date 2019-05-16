package com.example.weixin.litepal;

import com.example.weixin.tool.PinyinUtils;

import org.litepal.crud.LitePalSupport;

/**
 * Created by hasee on 2019/3/10.
 */

public class User extends LitePalSupport implements Comparable{
        private String firstAlphabet; //名字的第一个字的首字母
        private String name;
        private Boolean isChat;
        private String me;
        private String touxiang;
        public User(){

        }

        public User(String name,String me,String touxiang) {
            setName(name);
            this.me=me;
            this.touxiang=touxiang;
        }

        public String getFirstAlphabet() {
            return firstAlphabet;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
            firstAlphabet = PinyinUtils.getPingYin(name);  //获取第一个字符的字母,若为中文则使用pinyin4j获取第一个字的拼音的第一个字符,若为英文字母获取第一个字母,否则返回#
        }

    public Boolean getChat() {
        return isChat;
    }

    public void setChat(Boolean chat) {
        isChat = chat;
    }

    public String getMe() {
        return me;
    }

    public void setMe(String me) {
        this.me = me;
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang;
    }

    public int compareTo(Object another) {
            User compareContact = (User) another;
            if (compareContact.getFirstAlphabet().equals("#")){
                return 1;
            }else if (getFirstAlphabet().equals("#")){
                return -1;
            }else {
                return getFirstAlphabet().compareTo(((User) another).getFirstAlphabet());
            }
        }


    @Override
    public String toString() {
        return "User{" +
                "firstAlphabet='" + firstAlphabet + '\'' +
                ", name='" + name + '\'' +
                ", isChat=" + isChat +
                ", me='" + me + '\'' +
                ", touxiang='" + touxiang + '\'' +
                '}';
    }
}
