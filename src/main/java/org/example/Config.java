package org.example;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.YAMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;

import java.io.File;

public class Config {
    private Configuration config;
    private String approvedVariablesFile;
    private String reportOutputFile;
    private String reportTemplateFile;
    private int fuzzyMatchThreshold;
    private boolean checkDefaultServiceTaskName;
    private boolean validateVariables;

    public Config(String configFilePath) throws ConfigurationException {
        Parameters params = new Parameters();

        FileBasedConfigurationBuilder<YAMLConfiguration> builder =
                new FileBasedConfigurationBuilder<>(YAMLConfiguration.class)
                        .configure(params.fileBased()
                                .setFile(new File(configFilePath)));

        config = builder.getConfiguration();

        this.approvedVariablesFile = config.getString("approved_variables_file", "data/approved_variables.csv");
        this.reportOutputFile = config.getString("report.output_file", "reports/report.html");
        this.reportTemplateFile = config.getString("report.template_file", "templates/report_template.html");
        this.fuzzyMatchThreshold = config.getInt("fuzzy_match_threshold", 80);
        this.checkDefaultServiceTaskName = config.getBoolean("checks.default_service_task_name", true);
        this.validateVariables = config.getBoolean("checks.validate_variables", true);
    }

    // Getters for all the configuration parameters

    public String getApprovedVariablesFile() {
        return approvedVariablesFile;
    }

    public String getReportOutputFile() {
        return reportOutputFile;
    }

    public String getReportTemplateFile() {
        return reportTemplateFile;
    }

    public int getFuzzyMatchThreshold() {
        return fuzzyMatchThreshold;
    }

    public boolean isCheckDefaultServiceTaskName() {
        return checkDefaultServiceTaskName;
    }

    public boolean isValidateVariables() {
        return validateVariables;
    }
}
