package cn.microboat.mybatisplusdemo.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.util.*;
import java.util.Map.Entry;

/**
 * 描述：Bean转换工具
 * @author zhouwei
 */
public class BeanUtils {
    private static HashMap<Class<?>, PropertyEditor> customEditors = new HashMap<>();

    public static void registerCustomEditor(Class<?> clazz, PropertyEditor editor) {
        customEditors.put(clazz, editor);
    }

    public static String getConvertedName(String name) {
        return name != null && name.length() != 0 ? Character.toUpperCase(name.charAt(0)) + name.substring(1) : name;
    }

    public static <T> T map2Bean(Map map, T obj) {
        BeanWrapper bw = new BeanWrapperImpl(obj);
        PropertyDescriptor[] props = bw.getPropertyDescriptors();
        PropertyDescriptor[] var4 = props;
        int var5 = props.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            PropertyDescriptor pd = var4[var6];
            String name = pd.getName();
            if (bw.isWritableProperty(name) && bw.isReadableProperty(name)) {
                Class class0 = pd.getPropertyType();
                String convertedName;
                Object value;
                if (Enum.class.isAssignableFrom(class0)) {
                    convertedName = getConvertedName(name);
                    value = map.get(convertedName);
                    if (value != null) {
                        if (value.getClass() == class0) {
                            bw.setPropertyValue(name, value);
                        } else {
                            String enumValue = String.valueOf(value);
                            if (enumValue.length() > 0) {
                                Enum v = Enum.valueOf(class0, enumValue);
                                bw.setPropertyValue(name, v);
                            }
                        }
                    }
                } else {
                    convertedName = getConvertedName(name);
                    value = map.get(convertedName);
                    if (value != null) {
                        bw.setPropertyValue(name, value);
                    }
                }
            }
        }

        return (T) bw.getWrappedInstance();
    }

    public static <T> T map2Bean(Map map, Class<T> clazz) {
        BeanWrapper bw = new BeanWrapperImpl(clazz);
        Iterator var3 = customEditors.entrySet().iterator();

        while (var3.hasNext()) {
            Entry<Class<?>, PropertyEditor> en = (Entry) var3.next();
            bw.registerCustomEditor((Class) en.getKey(), (PropertyEditor) en.getValue());
        }

        PropertyDescriptor[] props = bw.getPropertyDescriptors();
        PropertyDescriptor[] var15 = props;
        int var5 = props.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            PropertyDescriptor pd = var15[var6];
            String name = pd.getName();
            if (bw.isWritableProperty(name) && bw.isReadableProperty(name)) {
                Class class0 = pd.getPropertyType();
                String convertedName;
                Object value;
                if (Enum.class.isAssignableFrom(class0)) {
                    convertedName = getConvertedName(name);
                    value = map.get(convertedName);
                    if (value != null) {
                        if (value.getClass() == class0) {
                            bw.setPropertyValue(name, value);
                        } else {
                            String enumValue = String.valueOf(value);
                            if (enumValue.length() > 0) {
                                Enum v = Enum.valueOf(class0, String.valueOf(value));
                                bw.setPropertyValue(name, v);
                            }
                        }
                    }
                } else {
                    convertedName = getConvertedName(name);
                    value = map.get(convertedName);
                    if (value != null) {
                        bw.setPropertyValue(name, value);
                    }
                }
            }
        }

        return ObjectUtils.object2Bean(bw.getWrappedInstance(),clazz);
    }

    public static Map bean2Map(Object beanObject) {
        BeanWrapperImpl bean = new BeanWrapperImpl(beanObject);
        PropertyDescriptor[] desc = bean.getPropertyDescriptors();
        HashMap dataMap = new HashMap(desc.length);

        try {
            for (int i = 0; i < desc.length; ++i) {
                String name = desc[i].getName();
                if (bean.isWritableProperty(name) && bean.isReadableProperty(name)) {
                    Object object = bean.getPropertyValue(name);
                    if (object != null){
                        String convertedName = getConvertedName(name);
                        dataMap.put(convertedName, object);
                    }

                }
            }

            return dataMap;
        } catch (Exception var8) {
            throw new RuntimeException("pe.core.util.bean2map_fail", var8);
        }
    }
    public static Map bean2MapNotnull(Object beanObject) {
        BeanWrapperImpl bean = new BeanWrapperImpl(beanObject);
        PropertyDescriptor[] desc = bean.getPropertyDescriptors();
        HashMap dataMap = new HashMap(desc.length);

        try {
            for (int i = 0; i < desc.length; ++i) {
                String name = desc[i].getName();
                if (bean.isWritableProperty(name) && bean.isReadableProperty(name)) {
                    Object object = ObjectUtils.isNotEmpty(bean.getPropertyValue(name))?bean.getPropertyValue(name):"";
                    dataMap.put(name, object);

                }
            }

            return dataMap;
        } catch (Exception var8) {
            throw new RuntimeException("pe.core.util.bean2map_fail", var8);
        }
    }

    public static List<Map> listBean2ListMap(List list) {
        List<Map> result = new ArrayList();
        Iterator it = list.iterator();

        while (it.hasNext()) {
            Map tmp = bean2Map(it.next());
            result.add(tmp);
        }

        return result;
    }
    public static List<Map> listBean2ListMapAddNull(List list) {
        List<Map> result = new ArrayList();
        Iterator it = list.iterator();

        while (it.hasNext()) {
            Map tmp = bean2MapNotnull(it.next());
            result.add(tmp);
        }

        return result;
    }

    public static <T> List<T> listMap2ListBean(List list, Class<T> class0) {
        List<T> result = new ArrayList();
        Iterator it = list.iterator();

        while (it.hasNext()) {
            T t = map2Bean((Map) it.next(), class0);
            result.add(t);
        }

        return result;
    }
    public static void list2Bean(List<?> srcBeanObject, Object destBeanObject, String listPropName) {
        BeanWrapperImpl destBean = new BeanWrapperImpl(destBeanObject);
        destBean.setPropertyValue(listPropName, srcBeanObject);
    }

    public static <T> T bean2Bean(Object srcBeanObject, Class<T> class0) {
        try {
            T t = class0.newInstance();
            if (srcBeanObject instanceof List) {
                list2Bean((List) srcBeanObject, t, "list");
            } else {
                bean2Bean(srcBeanObject, t);
            }

            return t;
        } catch (Exception var3) {
            throw new RuntimeException("pe.core.util.bean2bean_fail", var3);
        }
    }

    public static void bean2Bean(Object srcBeanObject, Object destBeanObject) {
        BeanWrapperImpl srcBean = new BeanWrapperImpl(srcBeanObject);
        BeanWrapperImpl destBean = new BeanWrapperImpl(destBeanObject);
        PropertyDescriptor[] destDesc = destBean.getPropertyDescriptors();

        try {
            for (int i = 0; i < destDesc.length; ++i) {
                String name = destDesc[i].getName();
                if (destBean.isWritableProperty(name) && srcBean.isReadableProperty(name)) {
                    Object srcValue = srcBean.getPropertyValue(name);
                    if (srcValue != null) {
                        destBean.setPropertyValue(name, srcValue);
                    }
                }
            }

        } catch (Exception var8) {
            throw new RuntimeException("pe.core.util.bean2bean_fail", var8);
        }
    }

    public static <T> T map2BeanStrict(Map map, T obj) {
        BeanWrapper bw = new BeanWrapperImpl(obj);
        PropertyDescriptor[] props = bw.getPropertyDescriptors();
        PropertyDescriptor[] var4 = props;
        int var5 = props.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            PropertyDescriptor pd = var4[var6];
            String name = pd.getName();
            if (bw.isWritableProperty(name) && bw.isReadableProperty(name)) {
                Class class0 = pd.getPropertyType();
                Object value;
                if (Enum.class.isAssignableFrom(class0)) {
                    value = map.get(name);
                    if (value != null) {
                        if (value.getClass() == class0) {
                            bw.setPropertyValue(name, value);
                        } else {
                            Enum v = Enum.valueOf(class0, String.valueOf(value));
                            bw.setPropertyValue(name, v);
                        }
                    }
                } else {
                    value = map.get(name);
                    if (value != null) {
                        bw.setPropertyValue(name, value);
                    }
                }
            }
        }

        return (T) bw.getWrappedInstance();
    }

    public static <T> T map2BeanStrict(Map map, Class<T> clazz) {
        BeanWrapper bw = new BeanWrapperImpl(clazz);
        PropertyDescriptor[] props = bw.getPropertyDescriptors();
        PropertyDescriptor[] var4 = props;
        int var5 = props.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            PropertyDescriptor pd = var4[var6];
            String name = pd.getName();
            if (bw.isWritableProperty(name) && bw.isReadableProperty(name)) {
                Class class0 = pd.getPropertyType();
                Object value;
                if (Enum.class.isAssignableFrom(class0)) {
                    value = map.get(name);
                    if (value != null) {
                        if (value.getClass() == class0) {
                            bw.setPropertyValue(name, value);
                        } else {
                            Enum v = Enum.valueOf(class0, String.valueOf(value));
                            bw.setPropertyValue(name, v);
                        }
                    }
                } else {
                    value = map.get(name);
                    if (value != null) {
                        bw.setPropertyValue(name, value);
                    }
                }
            }
        }

        return ObjectUtils.object2Bean(bw.getWrappedInstance(),clazz);
    }
}
