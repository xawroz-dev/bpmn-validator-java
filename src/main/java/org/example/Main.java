package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Load configuration
            Config config = new Config("config.yaml");

            // Parse BPMN files
            BPMNParser parser = new BPMNParser();
            List<ServiceTasksCustom> serviceTasksCustoms = parser.parseDirectory("bpmn_files");

            if (serviceTasksCustoms.isEmpty()) {
                System.out.println("No BPMN files to process.");
                return;
            }

            // Validate service tasks
            Validator validator = new Validator(config);
            ValidationResult results = validator.validate(serviceTasksCustoms);

            // Generate report
            Reporter reporter = new Reporter(config);
            reporter.generateReport(results);

            System.out.println("Report generated at " + config.getReportOutputFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
