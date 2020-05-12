package com.example.test.util;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.test.board.entity.Board;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;

public class WrapperUtil {

    private static boolean isSimpleType(Class<?> clazz){
        if(clazz == null){
            return false;
        }
        if(String.class.getTypeName().equals(clazz.getTypeName())){
            return true;
        }else if(Date.class.getTypeName().equals(clazz.getTypeName())){
            return true;
        }else if(LocalDateTime.class.getTypeName().equals(clazz.getTypeName())){
            return true;
        }else if(LocalDate.class.getTypeName().equals(clazz.getTypeName())){
            return true;
        }else{
            return ClassUtil.isBasicType(clazz);
        }
    }

    public static <T> QueryWrapper<T> wrapper(T obj){
        QueryWrapper<T> qw = new QueryWrapper<>();
        if(obj == null || "".equals(obj)){
            return qw;
        }
        Class<?> clazz = obj.getClass();
        Field[] fields = ReflectUtil.getFields(clazz);
        for (Field field : fields) {
            if(Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())){ //如果是static
                continue;
            }
            if(!isSimpleType(field.getType())){
                continue;
            }
            String fieldName = field.getName();
            String getMethodName = "get"+StrUtil.upperFirst(fieldName);
            String columnName = StrUtil.toUnderlineCase(fieldName);
            TableField tableField = field.getAnnotation(TableField.class);
            if(tableField !=null){
                if(!tableField.exist()){
                    continue;
                }
                if(StrUtil.isNotEmpty(tableField.value())){
                    columnName = tableField.value();
                }
            }
            Object ret = ReflectUtil.invoke(obj, getMethodName);
            qw.eq(!StringUtils.isEmpty(ret), columnName, ret);
        }
        return qw;
    }

    public static void main(String[] args) throws Exception {
        Board board = new Board();
        board.setId("1");
        board.setCreateTime(LocalDateTime.now());
        board.setList(Collections.singletonList("fff"));
        wrapper(board);

    }

}
