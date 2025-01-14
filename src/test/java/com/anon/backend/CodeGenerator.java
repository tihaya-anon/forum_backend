package com.anon.backend;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.nio.file.Paths;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CodeGenerator {
  @Value("${db.url}")
  private String url;

  @Value("${meta.user}")
  private String author;

  @Value("${db.username}")
  private String username;

  @Value("${db.password}")
  private String password;

  @Value("${meta.package-name}")
  private String packageName;

  @Test
  public void generate() {
    String projectPath = System.getProperty("user.dir");
    FastAutoGenerator.create(url, username, password)
        .globalConfig(
            builder ->
                builder
                    .author(author)
                    .disableOpenDir()
                    .enableSpringdoc()
                    .outputDir(Paths.get(projectPath) + "/src/main/java")
                    .commentDate("yyyy-MM-dd"))
        .packageConfig(
            builder ->
                builder
                    .parent(packageName)
                    .entity("entity")
                    //                    .controller("controller")
                    //                    .mapper("mapper")
                    //                    .service("service")
                    //                    .serviceImpl("service.impl")
                    //                    .xml("mapper")
                    .pathInfo(
                        Collections.singletonMap(
                            OutputFile.xml,
                            Paths.get(projectPath) + "/src/main/resources/mappers")))
        .strategyConfig(
            builder ->
                builder
                    .addExclude("^util_.*")
                    .entityBuilder()
                    .javaTemplate("/templates/entity.java")
                    .logicDeleteColumnName("is_delete")
                    .enableLombok()
                    .enableFileOverride()
            //                    .controllerBuilder()
            //                    .enableRestStyle()
            //                    .template("/templates/controller.java")
            )
        .templateEngine(new FreemarkerTemplateEngine())
        .execute();
  }
}
