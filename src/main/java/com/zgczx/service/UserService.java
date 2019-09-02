package com.zgczx.service;

import com.zgczx.dataobject.StuBase;
import com.zgczx.dataobject.TeaBase;
import com.zgczx.dataobject.UserFeedBack;
import com.zgczx.form.StuInfoForm;
import com.zgczx.form.TeaInfoForm;

/**
 * 用户接口
 * @author Jason
 */
public interface UserService {

    Integer STU_ROLE = 1;

    Integer TEA_ROLE = 2;

     /**
      * 授权阶段，新建学生的账号信息
      *
      * @Date 14:38 2018/12/27
      * @param openid   唯一微信凭证
      * @param nickname     用户昵称
      * @param headImgUrl   头像地址
      * @return StuBase 保存学生信息
      **/
     StuBase createStuBase(String openid, String nickname, String headImgUrl);

    /**
     * 授权阶段，新建教师的账号信息
     *
     * @param openid    唯一微信凭证
     * @param nickname  用户昵称
     * @param headImgUrl    头像地址
     * @return TeaBase 保存的教师信息
     */
     TeaBase createTeaBase(String openid, String nickname, String headImgUrl);

      /**
       * 根据学生的openid查找学生个人信息
       *
       * @param openid 唯一微信凭证
       * @return StuBase
       **/
     StuBase findStuBaseByOpenid(String openid);

    /**
     * 根据学生的openid查找学生个人信息
     *
     * @param openid 唯一微信凭证
     * @return TeaBase
     */
    TeaBase findTeaBaseByOpenid(String openid);


    /**
     * 根据学生的openid和学生个人信息实现注册
     *
     * @param stuOpenid    唯一微信凭证
     * @param stuInfoForm   学生注册表单
     * @return 学生基本信息
     */
    StuBase registerStuBaseByOpenid(String stuOpenid, StuInfoForm stuInfoForm);


    /**
     * 根据教师的openid和教师个人信息实现注册
     *
     * @param teaOpenid 唯一微信凭证
     * @param teaInfoForm   教师注册表单
     * @return  TeaBase
     */
    TeaBase registerTeaBaseByOpenid(String teaOpenid, TeaInfoForm teaInfoForm);


    /**
     * 删除教师账户
     *
     * @param teaOpenid 教师openid
     * @return 删除结果
     */
    String deleteTeaByOpenid(String teaOpenid);


    /**
     * 删除学生账户
     *
     * @param stuOpenid 学生id
     * @return  删除结果
     */
    String deleteStuByOpenid(String stuOpenid);

    /**
     * 根据学籍号获取学生信息
     * @param stuCode 学籍号
     * @return 学生信息
     */
    StuBase findStuBaseByStuCode(String stuCode);

    /**
     * 根据教师工号获取教师信息
     * @param teaCode 教师工号
     * @return 教师信息
     */
    TeaBase findTeaBaseByTeaCode(String teaCode);

    /**
     * 用户新增系统反馈
     *
     * @param userFeedBack 反馈基本信息
     * @return 反馈基本信息
     */
    UserFeedBack addUserFeedBack(UserFeedBack userFeedBack);

}
