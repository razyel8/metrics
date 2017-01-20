package org.pwr.report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportRow {
    private String commitHash;
    private String path;
    private boolean isBug;
    double QoC; //Quality of
    double MBRC; //
    double SCC; //Seniority
    double CCRC; //Core Commiters

    //LOC bez pustych linijek, bez komentarzy

    //TODO: Do zbadania jakie metryki bierzemy
    //Odpada LCOM, WMC, RFC
    //Usunąć LCOM3 z sharelatexa

//TODO: String builder
    public String toCSVString() {
        String separator = ", ";
        return this.getPath() + separator+
                this.getCommitHash();
    }


}
