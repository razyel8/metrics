package org.pwr.model;


import lombok.Getter;
import lombok.Setter;
import org.pwr.metric.QualityOfCommiterMetric;
import org.pwr.repository.ClassEntityRepository;

import java.util.*;
import java.util.stream.Collectors;

import static org.pwr.repository.ClassEntityRepository.allClasses;

@Getter@Setter
public class ClassEntity {
    public String path;
    public String commitHash;
    public String commitAuthor;
    public Date commitDate;
    public String commitMessage;
    //
    public int LOC,SLOC_P,SLOC_L,MVG,BLOC,CandSLOC,CLOC,CWORD,HCLOC,HCWORD;
    public double QofC;

    public ArrayList<MethodEntity> methodEntities;
    public String actionType; //MODIFIED, ADDED, DELETED
    public final Commit commit;

    public ClassEntity(){
        this.actionType = null;
        this.commit = null;
    }

    public ClassEntity(Commit commit, String actionType) {
        this.actionType = actionType;
        this.commit = commit;
    }

    public int getNDC(){
        HashSet<String> authors = new HashSet<>();
        ClassEntityRepository.allClasses.stream()
                .filter(ce -> ce.getCommitDate().before(this.getCommitDate())
                && ce.getPath().equals(this.getPath())).forEach(ce -> authors.add(ce.getCommitAuthor()));
        return authors.size();
    }

    public boolean isBuxFixing(){
        return this.getCommitMessage() != null ?  (this.getCommitMessage().contains("Fix") ||
                this.getCommitMessage().contains("fix") ||
                this.getCommitMessage().contains("FIX")) : false;
    }

    public boolean isBuxProne(){
        if(ClassEntityRepository.bugFixingClasses.size() == 0) throw new RuntimeException("Bug Fixing Classes list is Empty!");
        for(ClassEntity ce: ClassEntityRepository.bugFixingClasses){
            if(ce.getPrevoiusTouch() != null){
                return true;
            }
        }
        return false;

//        return ClassEntityRepository.getBugFixingClasses().stream()
//                .filter(ce -> ce.getPrevoiusTouch() != null)
//                .collect(Collectors.toList()).size() > 0;
    }

    public ClassEntity getPrevoiusTouch(){
        List<ClassEntity> allPrevious = new LinkedList<>();
        for(ClassEntity ce: ClassEntityRepository.allClasses){
            if(ce.getCommitDate().before(this.getCommitDate()) && ce.getPath().equals(this.getPath())){
                allPrevious.add(ce);
            }
        }


//        List<ClassEntity> allPrevious = ClassEntityRepository.allClasses.stream()
//                                .filter(ce -> ce.getCommitDate().before(this.getCommitDate()) && ce.getPath().equals(this.getPath())).collect(Collectors.toList());

        //System.out.println(allPrevious.size());
        return allPrevious.size()>0 ? Collections.max(allPrevious, Comparator.comparing(ClassEntity::getCommitDate)) : null;
    }


    @Override
    public String toString(){
        //return path + ": " + "LOC: " + this.getLOC() + " SLOC-L: " + this.getSLOC_L() + " SLOC-P: " + this.getSLOC_P();
        return path + ": " + toCSVString();
    }

    public String toCSVString() {
        String separator = ", ";
        return
                this.getLOC() + separator +
                        this.getSLOC_L() + separator +
                        this.getSLOC_P() + separator +
                        this.getMVG() + separator +
                        this.getBLOC() + separator +
                        this.getCandSLOC() + separator +
                        this.getCLOC() + separator +
                        this.getCWORD() + separator +
                        this.getHCLOC() + separator +
                        this.getHCWORD() + separator +
                        this.isBuxFixing() + ";";
    }
}
