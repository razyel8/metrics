package org.pwr.metric;

import org.pwr.metric.IMetricCalculator;
import org.pwr.metric.MetricCalculatorEngine;
import org.pwr.model.ClassEntity;
import org.pwr.repository.IMetricScoreUpdater;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Maciej Wolański
 * maciekwski@gmail.com
 * on 21.01.2017.
 */
public class ConcurrentMetricCalculatorEngine extends MetricCalculatorEngine {
    public ConcurrentMetricCalculatorEngine(LinkedHashMap<String, List<ClassEntity>> allClassEntitiesForCalculation, IMetricCalculator metricCalculator, IMetricScoreUpdater scoreUpdater) {
        super(allClassEntitiesForCalculation, metricCalculator, scoreUpdater);
    }

    @Override
    public void calculateMetricsForAllClassEntities(LinkedHashMap<String, List<ClassEntity>> classEntities) {
        allClassEntitiesForCalculation.entrySet().stream().collect(Collectors.toSet()).parallelStream().forEach(entry -> metricCalculator.calculateMetricsForRevision(entry.getKey(), entry.getValue()));
    }
}
