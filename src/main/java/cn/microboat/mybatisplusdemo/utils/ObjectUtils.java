package cn.microboat.mybatisplusdemo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ObjectUtils {
    /**
     * 实体类转换成map
     *
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @author : yql-cola
     * @date : 2021/11/16 8:57
     */
    public static Map<String,Object> object2Map(Object object){

        Map<String,Object> result=new HashMap<>();
        //获得类的的属性名 数组
        Field[]fields=object.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                String name = new String(field.getName());
                result.put(name, field.get(object));

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ObjectUtils.isNotEmpty(result.get("isExport"))){
            result.remove("isExport");
        }
        if (ObjectUtils.isNotEmpty(result.get(""))){
            result.remove("isExport");
        }
        return result;
    }

    /**
     * 将map集合中的数据转化为指定对象的同名属性中（有出错情况，重新写了beanUtils转换类）
     */
    public static <T> T map2Bean(Map<String, Object> map,Class<T> clazz) throws Exception {
        T bean = clazz.newInstance();
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    /**
     *  object 转 实体类
     * @param object
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T>T object2Bean(Object object,Class<T> clazz){
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(object,clazz);
    }

    //Object转Map
    public static Map<String, Object> getObjectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        Class<?> cla = obj.getClass();
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String keyName = field.getName();
            Object value = field.get(obj);
            if (value == null)
                value = "";
            map.put(keyName, value);
        }
        return map;
    }

    public static Boolean isNotEmpty(Object object){
        return Objects.nonNull(object);
    }

    public static Boolean isEmpty(Object object){
        return Objects.isNull(object);
    }

    public static Boolean isEmptyList(List list){
        return Objects.isNull(list) || list.size() == 0;
    }

}
