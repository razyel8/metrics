package org.pwr.metric;

import org.pwr.model.ClassEntity;
import org.pwr.report.ReportRow;

import java.util.LinkedList;
import java.util.List;

public class CommitterMetricCalculator implements IMetricCalculator{

    @Override
    public void calculateMetricsForRevision(String revision, List<ClassEntity> classEntities) {
        LinkedList<ClassMetricBase> metrics = new LinkedList<>();
        metrics.parallelStream().forEach(ClassMetricBase::calculate);
    }
}
