package org.pwr.metric;

import org.pwr.model.ClassEntity;
import org.pwr.report.ReportRow;

public interface IMetricCalculator {

    //TODO Composite pattern;
    //Kalkulatory odpalane są w wątkach
    void calculateMetrics(ClassEntity classEntity);
}
