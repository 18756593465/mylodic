package com.bcx.mylodic.helper;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class StringHelper {


    /**
     * 判断对象是否为空
     * @param obj 对象
     * @return    boolean 空true  为空false
     */
    public static boolean isEmpty(Object obj){
        if (obj == null) {
            return true;
        } else if (obj instanceof Optional) {
            return !((Optional)obj).isPresent();
        } else if (obj instanceof CharSequence) {
            return ((CharSequence)obj).length() == 0;
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        } else if (obj instanceof Collection) {
            return ((Collection)obj).isEmpty();
        } else if(obj instanceof Map){
            return ((Map) obj).isEmpty();
        }else{
            return false;
        }
    }
}
