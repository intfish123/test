package com.example.test.util.codegenerator;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.example.test.util.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.FileWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


/**
 * mapper生成器
 * @author wangxiaolei
 * @date 2020/4/15 14:11
 */
// 执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) throws InterruptedException {

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("wangxiaolei");
        gc.setOpen(false);        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://172.16.17.247:3306/pandora-test?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("keLnb89!");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.example.test");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
         String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();

        //生成 实体类 字段enum
        List<String> clazzReferenceList = new ArrayList<>();
        String packageName = pc.getParent();

        String[] include = strategy.getInclude();
        for (String s : include) {
            clazzReferenceList.add(packageName + ".entity." + StrUtil.upperFirst(s));
        }
        String path = gc.getOutputDir()+"/"+packageName.replaceAll("\\.", "/")+"/entity";

        Thread.sleep(1000);
        generateColumnsEum(path, clazzReferenceList,projectPath+"/src/main/resources");
    }
    //创建 实体类 字段enum
    private static void generateColumnsEum(String path, List<String> clazzReference, String resourcePath){
        // 初始化模板引擎
        VelocityEngine velocityEngine = new VelocityEngine();
        Properties properties = new Properties();
        properties.setProperty(velocityEngine.FILE_RESOURCE_LOADER_PATH, resourcePath); //此处的fileDir可以直接用绝对路径来
        velocityEngine.init(properties);
        // 获取模板文件
        Template template = velocityEngine.getTemplate("template/EntityColumn.vm");
        //com.example.test.entity.Directory
        try {
            for (String s : clazzReference) {
                String packageName = s.substring(0, s.lastIndexOf("."));
                Class<?> aClass = Class.forName(s);
                String enumName = aClass.getSimpleName()+"Column";

                // 设置变量，velocityContext是一个类似map的结构
                VelocityContext velocityContext = new VelocityContext();
                velocityContext.put("packageName", packageName);
                velocityContext.put("clazzName", enumName);

                List<String> list = new ArrayList<>();
                Field[] fields = aClass.getDeclaredFields();
                for (Field field : fields) {
                    if(Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())){ //如果是static
                        continue;
                    }
                    if(!isSimpleType(field.getType())){
                        continue;
                    }
                    TableField tableField = field.getAnnotation(TableField.class);
                    if(tableField !=null){
                        if(!tableField.exist()){
                            continue;
                        }
                    }
                    String columnName = StrUtil.toUnderlineCase(field.getName());
                    list.add(columnName);
                }
                velocityContext.put("columnList", list);

                // 输出渲染后的结果
                StringWriter stringWriter = new StringWriter();
                template.merge(velocityContext, stringWriter);
                FileWriter fw = new FileWriter(path + "/" +enumName+".java");
                fw.write(stringWriter.toString());
                fw.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
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

}