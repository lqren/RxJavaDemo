package com.project.rxjavademo;

import java.io.Serializable;
import java.util.List;

/**
 * 包名:      com.hgg.plm.bean
 * 文件名:    CityBean
 * 创建者:    hello
 * 创建时间:  2016/12/12 13:21
 * 描述:      TODO
 */
public class CityBean {
    public int err;// 0
    public CityResult result;//	Object

    public class CityResult {
        public List<CityData> data;
    }

    public class CityData implements Serializable {
        public long   area_ag_id;//179
        public long   area_id;//3311
        public long   area_level;//2
        public String area_name;//    成都
        public long   id;//179
        public long   is_open;//1
        public String name;//    派乐盟成都
        public String area_first ;

        @Override
        public String toString() {
            return "CityData{" +
                    "area_ag_id=" + area_ag_id +
                    ", area_id=" + area_id +
                    ", area_level=" + area_level +
                    ", area_name='" + area_name + '\'' +
                    ", id=" + id +
                    ", is_open=" + is_open +
                    ", name='" + name + '\'' +
                    ", area_first='" + area_first + '\'' +
                    '}';
        }
    }
}

