package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.text.similarity.FuzzyScore;

import java.io.FileReader;
import java.io.Reader;
import java.util.*;

public class Validator {
    private Config config;
    private Set<String> approvedVariables;
    private FuzzyScore fuzzyScore;

    public Validator(Config config) throws Exception {
        this.config = config;
        this.approvedVariables = loadApprovedVariables(config.getApprovedVariablesFile());
        this.fuzzyScore = new FuzzyScore(Locale.ENGLISH);
    }

    private Set<String> loadApprovedVariables(String csvFilePath) throws Exception {
        Set<String> variables = new HashSet<>();
        Reader in = new FileReader(csvFilePath);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        for (CSVRecord record : records) {
            variables.add(record.get("variable_name"));
        }
        return variables;
    }

    public ValidationResult validate(List<ServiceTasksCustom> serviceTasksCustoms) {
        ValidationResult results = new ValidationResult();

        for (ServiceTasksCustom task : serviceTasksCustoms) {
            // Check for default service task names
            if (config.isCheckDefaultServiceTaskName() && task.getId().startsWith("Activity_")) {
                results.addDefaultNameIssue(task);
            }

            // Validate variables
            if (config.isValidateVariables()) {
                for (String variable : task.getVariables()) {
                    if (!approvedVariables.contains(variable)) {
                        String suggestion = findClosestMatch(variable);
                        results.addVariableIssue(task, variable, suggestion);
                    }
                }
            }
        }

        return results;
    }

    private String findClosestMatch(String variable) {
        int threshold = config.getFuzzyMatchThreshold();
        String closestMatch = null;
        int highestScore = 0;

        for (String approvedVar : approvedVariables) {
            int score = fuzzyScore.fuzzyScore(variable, approvedVar);
            if (score > highestScore && score >= threshold) {
                highestScore = score;
                closestMatch = approvedVar;
            }
        }

        return closestMatch != null ? closestMatch : "No close match found";
    }
}
