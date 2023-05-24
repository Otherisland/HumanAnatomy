package com.bn.hk.humananatomy.object;


import java.io.Serializable;


public class UserAuths implements Serializable,Cloneable{

    /** 用户全局序号 */
    private String userGlobalid ;
    /** 用户账号 */
    private String userId ;
    /** 登录类型 */
    private String userIdentityType ;
    /** 手机号 */
    private String userPhone ;
    /** 邮箱 */
    private String userEmail ;
    /** 登陆凭证 */
    private String userCredential ;

    /** 用户全局序号 */
    public String getUserGlobalid(){
        return this.userGlobalid;
    }
    /** 用户全局序号 */
    public void setUserGlobalid(String userGlobalid){
        this.userGlobalid = userGlobalid;
    }
    /** 用户账号 */
    public String getUserId(){
        return this.userId;
    }
    /** 用户账号 */
    public void setUserId(String userId){
        this.userId = userId;
    }
    /** 登录类型 */
    public String getUserIdentityType(){
        return this.userIdentityType;
    }
    /** 登录类型 */
    public void setUserIdentityType(String userIdentityType){
        this.userIdentityType = userIdentityType;
    }
    /** 手机号 */
    public String getUserPhone(){
        return this.userPhone;
    }
    /** 手机号 */
    public void setUserPhone(String userPhone){
        this.userPhone = userPhone;
    }
    /** 邮箱 */
    public String getUserEmail(){
        return this.userEmail;
    }
    /** 邮箱 */
    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }
    /** 登陆凭证 */
    public String getUserCredential(){
        return this.userCredential;
    }
    /** 登陆凭证 */
    public void setUserCredential(String userCredential){
        this.userCredential = userCredential;
    }

}
