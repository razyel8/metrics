package org.pwr.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Maciej Wolański
 * maciekwski@gmail.com
 * on 18.01.2017.
 */
public interface IClassEntity {
    List<IClassEntity> getAllPreviousChanges();
    Set<String> getAllpreviousCommitters();
    String getCommitAuthor();

    Date getCommitDate();
}
