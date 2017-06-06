package com.sugood.app.util;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 
 * @author Administrator
 *
 */
public class JsonUtil {

	/**
	 * 从给定位置读取Json文件
	 * @param path
	 * @return String
	 */
    public static String readJsonFile(String path) {
        //从给定位置获取文件
        File file = new File(path);
        BufferedReader reader = null;
        //返回值,使用StringBuffer
        StringBuffer data = new StringBuffer();
        //
        try {
            reader = new BufferedReader(new FileReader(file));
            //每次读取文件的缓存
            String temp = null;
            while((temp = reader.readLine()) != null){
                data.append(temp);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            //关闭文件流
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data.toString();
    }

    /**
     * 给定路径与Json文件，存储到硬盘
     * @param path
     * @param json
     * @param fileName
     */
    public static void writeJsonFile(String path, Object json, String fileName) {
        BufferedWriter writer = null;
        File file = new File(path, fileName + ".json");
        //如果文件不存在，则新建一个
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        //写入
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(json.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(writer != null) {
                    writer.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
//        System.out.println("文件写入成功！");
    }
    
    /**
     * 此方法接收一个参数，参数类型为Object，即任何类型，可以是基本数据类型（即String、int、double、boolean等等），
     * 也可以是引用数据类型（对象、数组、集合等等），在方法中会对传入的参数进行判断，然后根据具体的参数类型，
     * 调用不同的转换方法，最终返回Json格式字符串。
     * @param object
     * @return String
     */
    public static String toJson(Object object) {
        String jsonString = null;
        
        jsonString = JSON.toJSONString(object, SerializerFeature.WriteDateUseDateFormat);
        
        return jsonString == null ? "" : jsonString; 
    }
    
    
    /**
     * 从一个JSON 对象字符格式中得到一个java对象，这是一个最终Bean，这个Bean可以包含另一个Bean，也可以包含容器类型的Bean，即多个Bean，形如：  
     * {"id" : idValue, "name" : nameValue, "aBean" : {"aBeanId" : aBeanIdValue, ...}}   
     * @param jsonString
     * @param clazz
     * @return
     */
    public static final <T> T toObject(String jsonString, Class<T> clazz) {

    	return (T)JSON.parseObject(jsonString, clazz);
    }
    
    
    /**
     * 从一个JSON对象字符格式中得到一个java对象数组，形如：
     * ["123", "456"]
     * @param jsonString
     * @return
     */
    public static Object[] toArray(String jsonString) {
        JSONArray jsonArray = JSONArray.parseArray(jsonString);
        
        return jsonArray.toArray();
    }
    
    /**
     * 从一个JSON对象字符格式中得到一个java对象数组，形如：
     * [{"id" : idValue, "name" : nameValue}, {"id" : idValue, "name" : nameValue}, ...] 
     * @param jsonString
     * @param clazz
     * @return
     */
    public static <T> Object[] toArray(String jsonString, Class<T> clazz) {

    	return toList(jsonString, clazz).toArray();
    }
    
    
    /**
     * 此方法将一个json格式的字符串转换为一个java List，这是一个包含一至多个最终Bean的list，形如：
     * [{"id" : idValue, "name" : nameValue}, {"id" : idValue, "name" : nameValue}, ...] 
     * @param jsonString
     * @param clazz
     * @return
     */
    public static <T> List<T> toList(String jsonString, Class<T> clazz) {

        return JSONArray.parseArray(jsonString, clazz);
    }
    
    
    /**
     * 此方法将一个json格式的字符串转换为一个java Map，形如：
     * {"id" : "johncon", "name" : "小强"}  
     * @param jsonString
     * @return
     */
    public static Map<String, Object> toMap(String jsonString) {
    	JSONObject jsonObject = JSONObject.parseObject(jsonString);
    	
    	Set<String> keySet = jsonObject.keySet();
    	Iterator<String> keyIter = keySet.iterator();
    	
    	Map<String, Object> valueMap = new HashMap<String, Object>();
    	String key;
    	Object value;
    	
    	while (keyIter.hasNext()) {
    		key = (String)keyIter.next();
    		value = jsonObject.get(key);
    		valueMap.put(key, value);
    	}
    	
    	return valueMap;
    }

    /**
     *
     * @param json
     * @param items
     * @return
     */
    public static Map<String, String> getMap(String json, String[] items) {

        Map<String, String> map = null;

        // 数据形式：{"id":1,"name":"小猪","age":22}
        String[] json_items = new String[items.length];
        JSONObject item = JSONObject.parseObject(json);// 每条记录又由几个Object对象组成

        for (int j = 0; j < items.length; j++) {
            json_items[j] = item.getString(items[j]);
        }

        map = new HashMap<String, String>();
        for (int j = 0; j < items.length; j++) {
            map.put(items[j], json_items[j]);
//            Log.i("", items[j] + " " + json_items[j]);
        }

        return map;
    }

    /**
     *
     * @param json
     * @param items
     * @return
     */
    public static List<Map<String, String>> getJSON(String json, String[] items) {

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = null;

        // 数据形式：{"id":1,"name":"小猪","age":22}
        String[] json_items = new String[items.length];
        JSONObject item = JSONObject.parseObject(json);// 每条记录又由几个Object对象组成

        for (int j = 0; j < items.length; j++) {
            json_items[j] = item.getString(items[j]);
        }

        map = new HashMap<String, String>();
        for (int j = 0; j < items.length; j++) {
            map.put(items[j], json_items[j]);
//            Log.i("", items[j] + " " + json_items[j]);
        }
        list.add(map);

        return list;
    }

    /**
     *
     * @param json
     * @param items
     * @return
     */
    public static List<Map<String, String>> getJSON2(String json, String[] items) {

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = null;

        // 数据形式：{"id":1,"name":"小猪","age":22}
        String[] json_items = new String[items.length];
        try {

            org.json.JSONObject item = new org.json.JSONObject(json); // 每条记录又由几个Object对象组成

            for (int j = 0; j < items.length; j++) {
                try {
                    json_items[j] = item.getString(items[j]);
                } catch (Exception e) {
                    e.printStackTrace();
                    json_items[j] = "";
                }
            }

            map = new HashMap<String, String>();
            for (int j = 0; j < items.length; j++) {
                map.put(items[j], json_items[j]);
                Log.i("", items[j] + " " + json_items[j]);
            }
            list.add(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     *
     * @param json
     * @param items
     * @return
     */
    public static List<Map<String, String>> getJSONArray(String json, String[] items) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = null;

        // 数据形式：[{"id":1,"name":"小猪","age":22},{"id":2,"name":"小猫","age":23}]
        JSONArray jsonArray = JSONObject.parseArray(json);//new JSONArray(json); // 数据直接为一个数组形式，所以可以直接用android提供的框架JSONArray读取JSON数据，转换成Array

        String[] json_items = new String[items.length];
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject item = jsonArray.getJSONObject(i); // 每条记录又由几个Object对象组成
            for (int j = 0; j < items.length; j++) {
                json_items[j] = item.getString(items[j]);
            }
            map = new HashMap<String, String>();
            for (int j = 0; j < items.length; j++) {
                map.put(items[j], json_items[j]);
            }
            list.add(map);
        }
        return list;
    }

    /**
     *
     * @param json
     * @param items
     * @return
     */
    public static List<Map<String, String>> getJSONArray2(String json, String[] items) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = null;

        try {
            // 数据形式：[{"id":1,"name":"小猪","age":22},{"id":2,"name":"小猫","age":23}]
            org.json.JSONArray jsonArray = new org.json.JSONArray(json); // 数据直接为一个数组形式，所以可以直接用android提供的框架JSONArray读取JSON数据，转换成Array

            String[] json_items = new String[items.length];
            for (int i = 0; i < jsonArray.length(); i++) {
                org.json.JSONObject item = jsonArray.getJSONObject(i); // 每条记录又由几个Object对象组成
                for (int j = 0; j < items.length; j++) {
                    try {
                        json_items[j] = item.getString(items[j]);
                    } catch (Exception e) {
                        e.printStackTrace();
                        json_items[j] = "";
                    }
                }
                map = new HashMap<String, String>();
                for (int j = 0; j < items.length; j++) {
                    map.put(items[j], json_items[j]);
                }
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}