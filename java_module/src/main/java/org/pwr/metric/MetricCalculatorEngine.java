package org.pwr.metric;

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
public class MetricCalculatorEngine extends Thread implements IMetricCalculationEngine{

    protected IMetricCalculator metricCalculator;
    protected IMetricScoreUpdater scoreUpdater;
    protected LinkedHashMap<String, List<ClassEntity>> allClassEntitiesForCalculation;


    public MetricCalculatorEngine(LinkedHashMap<String, List<ClassEntity>> allClassEntitiesForCalculation, IMetricCalculator metricCalculator, IMetricScoreUpdater scoreUpdater){
        this.allClassEntitiesForCalculation = allClassEntitiesForCalculation;
        this.metricCalculator = metricCalculator;
        this.scoreUpdater = scoreUpdater;
    }

    @Override
    public void run() {
        this.calculateMetricsForAllClassEntities(this.allClassEntitiesForCalculation);
        this.scoreUpdater.batchUpdateMetrics(this.allClassEntitiesForCalculation.values().stream().flatMap(Collection::stream).collect(Collectors.toList()));
    }

    @Override
    public void calculateMetricsForAllClassEntities(LinkedHashMap<String, List<ClassEntity>> classEntities) {
        for(String hash : allClassEntitiesForCalculation.keySet()){
            metricCalculator.calculateMetricsForRevision(hash, classEntities.get(hash));
        }
    }
}
