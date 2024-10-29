package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaInputOutput;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaInputParameter;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaOutputParameter;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.ServiceTask;

public class BPMNParser {

    public List<ServiceTasksCustom> parseDirectory(String directoryPath) throws Exception {

        List<ServiceTasksCustom> serviceTasksCustoms = new ArrayList<>();
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            throw new Exception("Directory not found: " + directoryPath);
        }

        File[] files = directory.listFiles((dir, name) -> name.endsWith(".bpmn"));
        if (files != null) {
            for (File file : files) {
                serviceTasksCustoms.addAll(parseFile(file));
            }
        }

        return serviceTasksCustoms;
    }

    public List<ServiceTasksCustom> parseFile(File file) throws Exception {
        List<ServiceTasksCustom> serviceTasksList = new ArrayList<>();

        org.camunda.bpm.model.bpmn.BpmnModelInstance modelInstance = Bpmn.readModelFromFile(file);

        // Get all service tasks
        Collection<ServiceTask> serviceTasksCustoms = modelInstance.getModelElementsByType(ServiceTask.class);

        for (ServiceTask task : serviceTasksCustoms) {
            String id = task.getId();
            String name = task.getName();
            List<String> variables = new ArrayList<>();

            ExtensionElements extensionElements = task.getExtensionElements();
            if (extensionElements != null) {
                Collection<CamundaInputOutput> inputOutputs = extensionElements.getElementsQuery()
                        .filterByType(CamundaInputOutput.class)
                        .list();
                for (CamundaInputOutput io : inputOutputs) {
                    // Input parameters
                    Collection<CamundaInputParameter> inputParameters = io.getCamundaInputParameters();
                    for (CamundaInputParameter param : inputParameters) {
                        variables.add(param.getCamundaName());
                    }

                    // Output parameters
                    Collection<CamundaOutputParameter> outputParameters = io.getCamundaOutputParameters();
                    for (CamundaOutputParameter param : outputParameters) {
                        variables.add(param.getCamundaName());
                    }
                }
            }


            serviceTasksList.add(new ServiceTasksCustom(file.getName(), id, name, variables));
        }

        return serviceTasksList;
    }
}
