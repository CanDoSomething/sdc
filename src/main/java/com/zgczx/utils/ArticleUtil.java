package com.zgczx.utils;

import com.alibaba.fastjson.JSONObject;
import com.zgczx.dataobject.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason
 * @date 2019/2/23 14:41
 */
public class ArticleUtil {

    private static String noContent = "None";

    public static List<JSONObject> getArticlePara(Article article){

        List<JSONObject> paraList = new ArrayList<>();

        if(!noContent.equals(article.getPara1())){
            paraList.add(getOneParaContent(article.getPara1()));
        }
        if(!noContent.equals(article.getPara2())){
            paraList.add(getOneParaContent(article.getPara2()));
        }
        if(!noContent.equals(article.getPara3())){
            paraList.add(getOneParaContent(article.getPara3()));
        }
        if(!noContent.equals(article.getPara4())){
            paraList.add(getOneParaContent(article.getPara4()));
        }
        if(!noContent.equals(article.getPara5())){
            paraList.add(getOneParaContent(article.getPara5()));
        }
        if(!noContent.equals(article.getPara6())){
            paraList.add(getOneParaContent(article.getPara6()));
        }
        if(!noContent.equals(article.getPara7())){
            paraList.add(getOneParaContent(article.getPara7()));
        }
        if(!noContent.equals(article.getPara8())){
            paraList.add(getOneParaContent(article.getPara8()));
        }
        if(!noContent.equals(article.getPara9())){
            paraList.add(getOneParaContent(article.getPara9()));
        }
        if(!noContent.equals(article.getPara10())){
            paraList.add(getOneParaContent(article.getPara10()));
        }
        if(!noContent.equals(article.getPara11())){
            paraList.add(getOneParaContent(article.getPara11()));
        }
        if(!noContent.equals(article.getPara12())){
            paraList.add(getOneParaContent(article.getPara12()));
        }
        if(!noContent.equals(article.getPara13())){
            paraList.add(getOneParaContent(article.getPara13()));
        }
        if(!noContent.equals(article.getPara14())){
            paraList.add(getOneParaContent(article.getPara14()));
        }
        if(!noContent.equals(article.getPara15())){
            paraList.add(getOneParaContent(article.getPara15()));
        }
        if(!noContent.equals(article.getPara16())){
            paraList.add(getOneParaContent(article.getPara16()));
        }
        if(!noContent.equals(article.getPara17())){
            paraList.add(getOneParaContent(article.getPara17()));
        }
        if(!noContent.equals(article.getPara18())){
            paraList.add(getOneParaContent(article.getPara18()));
        }
        if(!noContent.equals(article.getPara19())){
            paraList.add(getOneParaContent(article.getPara19()));
        }
        if(!noContent.equals(article.getPara20())){
            paraList.add(getOneParaContent(article.getPara20()));
        }
        return paraList;
    }

    private static JSONObject getOneParaContent(String onePara){
        String urlPrefix = "http";
        JSONObject para = new JSONObject();
        para.put("text",onePara);
        if(onePara.startsWith(urlPrefix)){
            para.put("type","img");
            return para;
        }else{
            para.put("type","text");
            return para;
        }
    }
}
