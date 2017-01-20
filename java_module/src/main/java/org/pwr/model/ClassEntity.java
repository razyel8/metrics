package org.pwr.model;


import lombok.Getter;
import lombok.Setter;
import org.pwr.enums.BugFix;
import org.pwr.enums.BugProne;

@Getter@Setter
public class ClassEntity{
    public String path;
    public String commitHash;
    public String commitAuthor;
    public long commitDate;
    public String commitMessage;
    BugFix isFix;
    BugProne isBug;

    //public ArrayList<MethodEntity> methodEntities;
    public String actionType; //MODIFIED, ADDED, DELETED

    public ClassEntity(){
        this.actionType = null;
    }

    public BugFix isBuxFixing(){
        return isFix;
    }

    public BugProne isBuxProne(){
        return isBug;
    }
}
