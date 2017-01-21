package org.pwr.metric;

import org.pwr.model.ClassEntity;
import org.pwr.repository.IClassEntityRepository;

import java.util.List;

public abstract class ClassMetricBase {

    protected IClassEntityRepository classEntityRepository;
    protected List<ClassEntity> entitiesList;

    public ClassMetricBase(IClassEntityRepository classEntityRepository, List<ClassEntity> entitiesList){
        this.classEntityRepository = classEntityRepository;
        this.entitiesList = entitiesList;
    }

    public void calculate(){
        this.initializeCommonFields();
        entitiesList.parallelStream().forEach(entity->calculate(entity));
    }

    abstract protected void initializeCommonFields();
    abstract protected void calculate(ClassEntity classEntity);

}
