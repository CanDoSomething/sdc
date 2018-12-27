package com.zgczx.service;

import com.zgczx.dataobject.StuBase;
import com.zgczx.dataobject.TeaBase;

/**
 * @author 张杰
 */
public interface UserService {
     /**
      * 授权阶段，保存学生的账号信息
      *
      * @Date 14:38 2018/12/27
      * @param openid   唯一微信凭证
      * @param nickname     用户昵称
      * @param headImgUrl   头像地址
      * @Return StuBase     保存学生信息
      **/
     StuBase saveStuBase(String openid, String nickname, String headImgUrl);

    /**
     * 授权阶段，保存学生的账号信息
     *
     * @param openid    唯一微信凭证
     * @param nickname  用户昵称
     * @param headImgUrl    头像地址
     * @return TeaBase 保存的教师信息
     */
     TeaBase saveTeaBase(String openid, String nickname, String headImgUrl);

}
