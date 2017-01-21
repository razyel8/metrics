package org.pwr.repository;

import org.pwr.model.ClassEntity;

import java.util.List;
// TODO: TO BEAN
public interface IMetricScoreUpdater {


    //
    void update(ClassEntity classEntity);

    void batchUpdateMetrics(List<ClassEntity> collect);
}
