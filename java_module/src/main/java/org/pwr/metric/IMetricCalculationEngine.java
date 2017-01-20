package org.pwr.metric;

import org.pwr.model.ClassEntity;

import java.util.LinkedHashMap;
import java.util.List;


//TODO: Zrobić z tego beana
public interface IMetricCalculationEngine {

    void calculateMetricsForAllClassEntities(LinkedHashMap<String, List<ClassEntity>> classEntities);


}
