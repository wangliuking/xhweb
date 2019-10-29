package xh.func.plugin;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
/**
 * 过滤掉map集合中key或value为空的值
 * @author lmb
 * @date 2017-3-14
 */
public class MapRemoveNullUtil { 
      
    /**
     * 移除map中空key或者value空值 
     * @param map
     */
    public static void removeNullEntry(Map map){ 
        removeNullKey(map); 
        removeNullValue(map); 
    } 
     
    /**
     * 移除map的空key
     * @param map
     * @return
     */ 
    public static void removeNullKey(Map map){ 
        Set set = map.keySet(); 
        for (Iterator iterator = set.iterator(); iterator.hasNext();) { 
            Object obj = (Object) iterator.next(); 
            remove(obj, iterator); 
        } 
    } 
     
    /**
     * 移除map中的value空值
     * @param map
     * @return
     */ 
    public static void removeNullValue(Map map){ 
        Set set = map.keySet(); 
        for (Iterator iterator = set.iterator(); iterator.hasNext();) { 
            Object obj = (Object) iterator.next(); 
            Object value =(Object)map.get(obj); 
            remove(value, iterator); 
        } 
    } 
     
    /**
     * 移除map中的空值
     ** @param obj
     * @param iterator
     */ 
    private static void remove(Object obj,Iterator iterator){ 
        if(obj instanceof String){ 
            String str = (String)obj;
            if(isEmpty(str)){
            	iterator.remove(); 
            } 
           
        }else if(obj instanceof Collection){ 
            Collection col = (Collection)obj; 
            if(col==null||col.isEmpty()){ 
                iterator.remove(); 
            } 
             
        }else if(obj instanceof Map){ 
            Map temp = (Map)obj; 
            if(temp==null||temp.isEmpty()){ 
                iterator.remove(); 
            } 
             
        }else if(obj instanceof Object[]){ 
            Object[] array =(Object[])obj; 
            if(array==null||array.length<=0){ 
                iterator.remove(); 
            } 
        }else{ 
            if(obj==null){ 
                iterator.remove(); 
            } 
        } 
    } 
     
    public static boolean isEmpty(Object obj){
        return obj == null || obj.toString().length() == 0;
    }
} 
