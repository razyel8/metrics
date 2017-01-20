package org.pwr.metric.mbrc;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.NotImplementedException;
import org.pwr.enums.BugProne;

import org.pwr.metric.IClassMetric;
import org.pwr.model.ClassEntity;

import org.pwr.model.IClassEntity;
import org.pwr.repository.ClassEntityRepository;

/**
 * Represents MBRC metric implementation. Provides method to calculate value of
 * metric. Score is mean bug ratio for all intervals, which length is set in
 * {@link DAY_INTERVAL}. First {@link INTERVALS_TO_SKIPP} intervals are omitted.
 * Intervals with number of bugs smaller than
 * {@link MINIMAL_NUBMER_OF_COMMITS_PER_INTERVAL} are omitted.
 *
 * @author Grzegorz
 *
 */
public class MeanBugRatioMetric implements IClassMetric {

    /** Number of days per interval */
    public static final int DAY_INTERVAL = 300;

    /** Number of intervals */
    public static final int MINIMAL_NUMBER_OF_INTERVALS = 0;

    /** Minimal number of days per interval */
    public static final int MINIMAL_NUBMER_OF_COMMITS_PER_INTERVAL = 10;

    /** Number of intervals to skip */
    public static final int INTERVALS_TO_SKIPP = 0;

    /**
     * {@inheritDoc}}
     */

    public Double calculate(String commiter) {

        return calculateRatio(ClassEntityRepository.getAllClassEntitiesOfCommiter(commiter));
    }

    /**
     * Calculates mean bug ratio for chosen commits (from {@link ClassEntity}}.
     * Returns mean ratio value for intervals, which had number of commits
     * equals or greater than {@link #MINIMAL_NUBMER_OF_COMMITS_PER_INTERVAL}.
     * Skips first {@link #INTERVALS_TO_SKIPP} intervals.
     *
     * @param commits
     *            commits
     * @throws IllegalArgumentException
     *             when number of intervals is too small for chosen commits or
     *             argument is null
     * @return mean bug ratio
     */
    public double calculateRatio(List<ClassEntity> classEntities) {

        if (classEntities != null) {
            List<Interval> intervals = splitByInterval(classEntities);

            double sumOfRatios = 0;
            double sumOfProperIntervals = 0;
            double skippedIntervals = 0;

            for (Interval interval : intervals) {

                if (skippedIntervals < INTERVALS_TO_SKIPP) {
                    skippedIntervals++;
                } else {
                    if (interval.getCommits() >= MINIMAL_NUBMER_OF_COMMITS_PER_INTERVAL) {
                        sumOfRatios += ((double) interval.getCommitsWithBugs() / (double) interval.getCommits());
                        sumOfProperIntervals++;
                    }
                }

            }

            if (sumOfProperIntervals >= MINIMAL_NUMBER_OF_INTERVALS) {
                return sumOfRatios / sumOfProperIntervals;
            }
            throw new IllegalArgumentException(
                    "Number of intervals is to low (lower than: " + MINIMAL_NUMBER_OF_INTERVALS + ")");
        }

        throw new IllegalArgumentException("Commiter commits cannot be null");

    }

    /**
     * Creates list of intervals for selected class entities. They are splitted
     * to intervals of equals length, which is taken from {@link #DAY_INTERVAL}.
     * For each interval updates number of commits and commits with bugs
     *
     * @param commits
     *            splitted commits
     * @throws IllegalArgumentException
     *             when commits are null or empty
     * @return intervals
     */
    private List<Interval> splitByInterval(List<ClassEntity> classEntities) {
        if (classEntities != null && !classEntities.isEmpty()) {
            sort(classEntities);

            Date start = new Date(classEntities.get(0).getCommitDate());
            Date end =  new Date(classEntities.get(classEntities.size()-1).getCommitDate());

            ArrayList<Interval> intervals = prepareIntervals(start,end);
            int currentIntervalIndex = 0;




            for (ClassEntity commit : classEntities) {

                if (!isCommitInInterval(intervals.get(currentIntervalIndex),  new Date(commit.getCommitDate()))) {
                    while (!isCommitInInterval(intervals.get(currentIntervalIndex), new Date(commit.getCommitDate()))) {
                        currentIntervalIndex++;
                    }
                }

                if (commit.getIsBug() == BugProne.TRUE) {
                    intervals.get(currentIntervalIndex).incrementCommitsWithBugs();
                }
                intervals.get(currentIntervalIndex).incrementCommits();
                intervals.get(currentIntervalIndex).getClassEntities().add(commit);
            }

            return intervals;
        }
        throw new IllegalArgumentException("Commits cannot be null or empty");

    }

    /**
     * Determines, if commit belongs to selected interval
     *
     * @param interval
     *            interval
     * @param commit
     *            commit
     * @return true when belongs to, false otherwise
     */
    private boolean isCommitInInterval(Interval interval, Date commit) {
        return commit.equals(interval.getStart())
                || (commit.before(interval.getEnd()) && commit.after(interval.getStart()));
    }

    /**
     * Prepares list of intervals for days difference between older and earlier
     * date.
     *
     * @param startDate
     *            earlier date
     * @param endDate
     *            older date
     * @throws IllegalArgumentException,
     *             when dates not in order or null
     * @return intervals
     */
    private ArrayList<Interval> prepareIntervals(Date startDate, Date endDate) {

        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("First date must be earlier than second.");
        }

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Both dates cannot be null.");
        }

        ArrayList<Interval> intervals = new ArrayList<Interval>();

        long numberOfIntervals = (getDifferenceDays(startDate, endDate) / DAY_INTERVAL) + 1;
        Date current = new Date(startDate.getTime());

        while (numberOfIntervals-- > 0) {

            Interval interval = new Interval();
            interval.setStart(current);
            interval.setEnd(calculateNextIntervalDate(current));
            intervals.add(interval);

            current = calculateNextIntervalDate(current);
        }
        return intervals;
    }

    /**
     * Determines difference in days between dates
     *
     * @param d1
     *            first date
     * @param d2
     *            second date
     * @return difference
     */
    private long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    /**
     * Calculates next interval date from date. Distance is taken from
     * {@link #DAY_INTERVAL}
     *
     * @param date
     *            date
     * @return next interval date
     */
    private Date calculateNextIntervalDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, DAY_INTERVAL);
        return c.getTime();
    }

    /**
     * Sorts commits by date
     *
     * @param commits
     *            commits
     */
    private void sort(List<ClassEntity> classEntities) {
        Collections.sort(classEntities, new Comparator<ClassEntity>() {

            @Override
            public int compare(ClassEntity c1, ClassEntity c2) {

                if (c1 == null && c2 == null) {
                    return 0;
                }
                if (c1 != null && c2 == null) {
                    return 1;
                }
                if (c1 == null && c2 != null) {
                    return -1;
                }

                return c1.getCommitDate() >= c2.getCommitDate() ? 1 : -1;
            }

        });
    }

    @Override
    public Double calculate(ClassEntity classEntity) {
        return  calculate(classEntity.getCommitAuthor());
    }

    /**
     * Represents interval used in this metric
     *
     * @author Grzegorz
     *
     */
    private class Interval {
        private List<ClassEntity> classEntities = new ArrayList<ClassEntity>();
        private int commitsWithBugs;
        private int commits;
        private Date start;
        private Date end;

        public Double calculateRatio() {
            return (double) commitsWithBugs / commits;
        }

        public void incrementCommits() {
            commits++;
        }

        public void incrementCommitsWithBugs() {
            commitsWithBugs++;
        }

        public int getCommitsWithBugs() {
            return commitsWithBugs;
        }

        public int getCommits() {
            return commits;
        }

        public Date getStart() {
            return start;
        }

        public void setStart(Date start) {
            this.start = start;
        }

        public Date getEnd() {
            return end;
        }

        public void setEnd(Date end) {
            this.end = end;
        }

        public List<ClassEntity> getClassEntities() {
            return classEntities;
        }

        public void setCommitsList(List<ClassEntity> classEntities) {
            this.classEntities = classEntities;
        }

    }

}
