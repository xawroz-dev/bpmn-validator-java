package org.example;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Reporter {
    private Config config;

    public Reporter(Config config) {
        this.config = config;
    }

    public void generateReport(ValidationResult results) throws Exception {
        Configuration cfg = new Configuration(new Version("2.3.30"));
        cfg.setDirectoryForTemplateLoading(new File(config.getReportTemplateFile()).getParentFile());
        cfg.setDefaultEncoding("UTF-8");

        Template template = cfg.getTemplate(new File(config.getReportTemplateFile()).getName());

        Map<String, Object> data = new HashMap<>();
        data.put("results", results);

        try (FileWriter writer = new FileWriter(config.getReportOutputFile())) {
            template.process(data, writer);
        }
        catch (IOException e) {
            System.err.println("Error writing the report: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
