package com.example.ssm_demo.util;

import com.example.ssm_demo.entity.Org;
import com.example.ssm_demo.enums.AreaRnkEnum;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 中国统计用区划代码和城乡划分代码 爬虫工具类
 */
public class GrabUtil {

    private static final String URL="http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2020/";
    static Set<Integer> ids=new HashSet<>();
    static int sid =1;

    /**
     * 爬虫获取组织机构
     * @return 组织机构
     * @throws Exception
     */
    public static Set<Org> parseOrg() throws Exception{
        Document document= Jsoup.parse(new URL(URL+"index.html"),10000);
//        String url=URL+"index.html";
//        Document document= Jsoup.parse(new URL(url).openStream(),"GBK",url);
        Elements elements1=document.getElementsByClass("provincetable");
        Element element=elements1.get(0);
        Elements elements=element.getElementsByTag("a");
        Set<Org> list = new HashSet<>();
        int id=1;
        int rank=1;
        String childUrl="";

        try{
            //  国家
            saveOrg(new Org(id,0,"中国", AreaRnkEnum.country.getValue(),""),list);
            //  省/直辖市
            for(Element element1:elements){
                if (id < sid){
                    id=sid+1;
                }else{
                    id++;
                }
                childUrl=element1.attr("href");
                Org org = new Org(id,1,element1.text(), AreaRnkEnum.province.getValue(),"");
                saveOrg(org,list);
                while(StringUtils.hasLength(childUrl)){
                    childUrl=resetOrg(URL+childUrl,rank+1,id,id,list);
                }

            }
        }catch (Exception e){
            return list;
//            e.printStackTrace();
        }
        return list;
    }

    private static String resetOrg(String url,int rank,int id,int pid,Set<Org> list) throws Exception{
        Thread.currentThread().sleep(2000);
        String hrefUrl="";
        String areaCode="";
        String name="";
        pid=id;
//        Document document=Jsoup.parse(new URL(url).openStream(),"GBK",url);
        Document document=Jsoup.parse(new URL(url),10000);
        //  市/州 模板
        Elements elements_city=document.getElementsByClass("citytable");
        //  县/区 模板
        Elements elements_county=document.getElementsByClass("countytable");
        // 镇/乡/街道 模板
        Elements elements_town=document.getElementsByClass("towntable");
        //  村/社区 模板
        Elements elements_village= document.getElementsByClass("villagetr");

        //  市/州
        if(!elements_city.text().equals("")){
            Elements elements = elements_city.get(0).getElementsByClass("citytr");
            for (Element element:elements){
                if (id < sid){
                    id=sid++;
                }
                id++;
                //下级跳转页面
                hrefUrl=element.child(0).child(0).attr("href");
                //区域编码
                areaCode=element.child(0).text();
                //组织机构名称
                name=element.child(1).text();
                Org org = new Org(id,pid,name, String.valueOf(rank),areaCode);
                saveOrg(org,list);
                if(StringUtils.hasLength(hrefUrl)){
                    resetOrg(URL+hrefUrl,rank+1,id,pid,list);
                }
            }

        }
        // 县/区
        if(!elements_county.text().equals("")){
            Elements elements = elements_county.get(0).getElementsByClass("countytr");
            for(int i=0;i<elements.size();i++){
                if (id < sid){
                    id=sid++;
                }
                id++;
                Element element=elements.get(i);
                //下级跳转页面
                if(element.child(0).children().text().isEmpty()){
                    areaCode=element.child(0).getElementsByTag("td").text();
                    name=element.child(1).getElementsByTag("td").text();
                    Org org = new Org(id,pid,name, String.valueOf(rank),areaCode);
                    saveOrg(org,list);
                    continue;
                }
                hrefUrl=element.child(0).child(0).attr("href");
                //区域编码
                areaCode=element.child(0).text();
                //组织机构名称
                name=element.child(1).text();
                Org org = new Org(id,pid,name, String.valueOf(rank),areaCode);
                saveOrg(org,list);
                if(!hrefUrl.isEmpty()){
                    hrefUrl=URL+hrefUrl.split("\\/")[1].substring(0,2)+"/"+hrefUrl;
                    resetOrg(hrefUrl,rank+1,id,pid,list);
                }
            }

        }
        //  镇/乡/街道
        if(!elements_town.text().equals("")){
            Elements elements = elements_town.get(0).getElementsByClass("towntr");
            for (Element element:elements){
                if (id < sid){
                    id=sid++;
                }
                id++;
                //下级跳转页面
                hrefUrl=element.child(0).child(0).attr("href");
                //区域编码
                areaCode=element.child(0).text();
                //组织机构名称
                name=element.child(1).text();
                Org org = new Org(id,pid,name, String.valueOf(rank),areaCode);
                saveOrg(org,list);
                if(StringUtils.hasLength(hrefUrl)){
                    hrefUrl=URL+hrefUrl.substring(3,5)+"/"+hrefUrl.substring(5,7)+"/"+hrefUrl;
                    resetOrg(hrefUrl,rank+1,id,pid,list);
                }
            }
        }
        // 村/社区
        if(!elements_village.text().equals("")){
            for (Element element:elements_village){
                if (id < sid){
                    id=sid++;
                }
                id++;
                if(!element.child(0).text().equals("")){
                    Org org = new Org(id,pid,element.child(2).text(), String.valueOf(rank),element.child(0).text());
                    saveOrg(org,list);
                }
            }
            sid = id;
            return null;
        }
//        sid = id;
        return null;
    }

    public static void saveOrg(Org org,Set<Org> orgs){
        if(ids.equals(org.getId())){
            System.out.println("该id:"+org.getId()+"已存在  "+org);
        }else {
            ids.add(org.getId());
            orgs.add(org);
            System.out.println(org);
        }

    }

    public static void main(String[] args) throws Exception {
        GrabUtil.parseOrg();
    }
}
