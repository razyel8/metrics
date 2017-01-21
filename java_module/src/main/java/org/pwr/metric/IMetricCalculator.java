package org.pwr.metric;

import org.pwr.model.ClassEntity;
import org.pwr.report.ReportRow;

import java.util.List;

public interface IMetricCalculator {

    //Metryki powinnybyć liczone dla ClassEntities w każdej rewizji dla wszystkich classEntity z rewizji w celu optymalizacji!
    void calculateMetricsForRevision(String revision, List<ClassEntity> classEntities);
}
