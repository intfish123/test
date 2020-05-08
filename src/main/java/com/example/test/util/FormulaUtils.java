package com.example.test.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FormulaUtils {
    /**
     * 转逆波兰表达式  "a+ b *c+(d*e+f)*g" => ["a","b","c","*","+","d","e","*","f","+","g","*","+"]
     * @param exp "a+ b *c+(d*e+f)*g"
     * @author wangxiaolei
     * @date 2020/4/17 21:22
     * @return java.util.List<java.lang.String>
     */
    public static List<String> toReversePolishNotaion(String exp){
        if(!StringUtils.isEmpty(exp)){
            try{
                List<String> reversePolishNotation = new ArrayList<>();
                List<String> expUnit = splitExp(exp);
                Stack<String> stack = new Stack<>();  //操作符辅助栈
                for (String op : expUnit) {
                    switch (op){
                        case "(":   //遇到 "(" 直接入栈
                        case "（":
                            stack.push(op);
                            break;
                        case ")":  // 遇到")"则把栈内元素全部出栈直至遇到"("。注意："("不输出
                        case "）":
                            while(!stack.isEmpty()){
                                String pop = stack.pop();
                                if("(".equals(pop) || "（".equals(pop)){
                                    break;
                                }
                                reversePolishNotation.add(pop);
                            }
                            break;
                        case "+":  //遇到四则运算则 将栈内元素 >= 自己优先级的全部出栈并输出，再将自己入栈
                        case "-":
                        case "*":
                        case "/":
                            while(!stack.isEmpty() && getPriority(stack.peek()) >= getPriority(op)) {  //当栈顶元素优先级>=当前操作符优先级
                                reversePolishNotation.add(stack.pop());
                            }
                            stack.push(op);
                            break;
                        default:  //操作数 直接入栈
                            reversePolishNotation.add(op);
                            break;
                    }
                }
                while(!stack.isEmpty()){ //剩余操作符出栈
                    reversePolishNotation.add(stack.pop());
                }
                return reversePolishNotation;
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("解析表达式错误,exp："+exp, e);
            }
        }
        return new ArrayList<>();
    }

    /**
     * 操作符优先级
     * @param op
     * @author wangxiaolei
     * @date 2020/4/17 21:23
     * @return int
     */
    public static int getPriority(String op){
        switch (op){
            case "(":
            case "（":
                return 0;
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;

        }
        return -1;
    }
    /**
     * 根据操作符拆分公式
     * @param exp a+ b /3 +(2 + cccc)+(123) => ["a","+","b","/","3","+","(","2","+","cccc",")","+","(","123",")"]
     * @author wangxiaolei
     * @date 2020/4/17 18:40
     * @return java.util.List<java.lang.String>
     */
    public static List<String> splitExp(String exp){
        //拆分公式
        List<String> expUnit = new ArrayList<>();
        String tmp = "";
        for(int i=0; i<exp.length();i++){
            char ch = exp.charAt(i);
            switch (ch){
                case ' ':
                    continue;
                case '(':
                case '（':
                case ')':
                case '）':
                case '+':
                case '-':
                case '*':
                case '/':
                    if(!StringUtils.isEmpty(tmp)){
                        expUnit.add(tmp);
                    }
                    expUnit.add(String.valueOf(ch));
                    tmp = "";
                    break;
                default:
                    tmp += ch;
                    break;
            }
        }
        if(!StringUtils.isEmpty(tmp)){
            expUnit.add(tmp);
        }
        return expUnit;
    }



    /**
     * 计算逆波兰表达式
     * @param reversePolishNotation 逆波兰表达式 ["a","+","b","/","3","+","(","2","+","cccc",")","+","(","123",")"]
     * @author wangxiaolei
     * @date 2020/4/17 21:31
     * @return java.lang.String
     */
    public static String calculateReversePolishNotation(List<String> reversePolishNotation){
        Stack<String> stack = new Stack<>();
        for (String s : reversePolishNotation) {
            if(isOperator(s)){
                String b = stack.pop();
                String a = stack.pop();
                stack.push("("+a+s+b+")");
            }else{
                stack.push(s);
            }
        }
        if(!stack.isEmpty()){
            return stack.pop();
        }
        return "";
    }
    public static boolean isOperator(String str){
        return str.matches("[\\+\\-\\*\\/]");
    }

    public static void main(String[] args) throws Exception {
//        String a = "A+B*(C-D)-E*F";
//        String a = "a+ b /3 +(2 + cccc)+(123)";
        String a = "a+ b *c+(d*e+f)*g";
        List<String> strings = toReversePolishNotaion(a);
        System.out.println(JacksonUtils.obj2json(strings));
        System.out.println(calculateReversePolishNotation(strings));
        List<Object> list = new ArrayList<>();
        list.add(1.0);
        list.add("+");
        for (Object o : list) {
            System.out.println(isOperator(o.toString()));
        }
    }
}
