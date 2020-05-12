package com.example.test.util;



import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王晓磊
 * @Title: TreeUtil
 * @ProjectName rxkk-police
 * @Description: 通用Util，根据父节点关系将列表转树形结构
 * @date 2018-12-10 17:54
 */
public class TreeUtil {

    /**
     * @Description: 首字母转大写
     * @param
     * @return
     * @throws
     * @author 王晓磊
     * @date 2018-12-10 20:44
     */
    public static String firstCharToUpperCase(String str){
        if(str == null){ return null; }
        if("".equals(str)){ return ""; }
        char[] cs = str.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }

    
    /** 调用示例：示例： List<TreeNode> head = toTree(TreeNode.class,nodeList,"id","parentId","children",0L);
     * @Description: List转Tree
     * @param  clazz  类.class
     * @param dataList 数据列表
     * @param idField  ID字段名称
     * @param parentIdField  父节点ID字段名称
     * @param childrenField  孩子字段名称
     * @param rootFlag  如果某个节点的ParentId等于rootFlag，即改节点为根节点
     * @return
     * @throws
     * @author 王晓磊
     * @date 2018-12-10 20:44
     */
    public static <T> List<T> toTree(Class<T> clazz,List<T> dataList,String idField,String parentIdField,String childrenField,Object rootFlag) throws Exception {
        List<T> treeList = new ArrayList<>();
        Map<Object,T> map = new HashMap<>();
        Map<Object,Boolean> flag = new HashMap<>();
        String getIdStr = "get"+firstCharToUpperCase(idField);
        String getParentIdStr = "get"+firstCharToUpperCase(parentIdField);
        String getChildrenStr = "get"+firstCharToUpperCase(childrenField);
        String setChildrenStr = "set"+firstCharToUpperCase(childrenField);
        Method getIdMethod = null;
        Method getParentIdMethod=null;
        Method getChildrenMethod=null;
        Method setChildrenMethod=null;
        try{
            getIdMethod = clazz.getDeclaredMethod(getIdStr);
            getParentIdMethod = clazz.getDeclaredMethod(getParentIdStr);
            getChildrenMethod = clazz.getDeclaredMethod(getChildrenStr);
            setChildrenMethod = clazz.getDeclaredMethod(setChildrenStr,List.class);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("转树形结构-通过反射获取方法错误！",e);
        }
        if(dataList == null){
            return treeList;
        }

        for(T node:dataList){
            map.put(getIdMethod.invoke(node),node);
            Object parentIdTmp = getParentIdMethod.invoke(node);
            if(rootFlag==null || "".equals(rootFlag)){
                if(parentIdTmp==null || "".equals(parentIdTmp)){
                    treeList.add(node);
                }
            }else{
                if(rootFlag.equals(parentIdTmp)){
                    treeList.add(node);
                }
            }
        }
        for(T node:dataList){
            T parentNode = map.get(getParentIdMethod.invoke(node));
            if(parentNode!=null){
                List<T> invoke = (List<T>)getChildrenMethod.invoke(parentNode);
                if(invoke==null){
                    invoke=new ArrayList<>();
                    setChildrenMethod.invoke(parentNode,invoke);
                }
                invoke.add(node);

            }
        }
        return treeList;
    }


  /*  public static void main(String[] args) throws Exception {
        List<TreeNode> sourceList = new ArrayList<>();
        TreeNode n1 = new TreeNode(); n1.setId(1);n1.setParentId(null); sourceList.add(n1);
        TreeNode n2 = new TreeNode(); n2.setId(2);n2.setParentId(1); sourceList.add(n2);
        TreeNode n3 = new TreeNode(); n3.setId(3);n3.setParentId(2); sourceList.add(n3);
        List<TreeNode> treeNodes = toTree(TreeNode.class, sourceList, "id", "parentId", "children", null);
        treeNodes.forEach(n-> System.out.println(n));
    }*/
}
