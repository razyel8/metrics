package org.pwr.enums;

public enum BugFix {
    TRUE(1), FALSE(0), UNKNOWN(2);

    public int value;
    BugFix(int i) {
        this.value = i;
    }

    public static BugFix getEnum(int i){
        switch(i){
            case 0:
                return FALSE;
            case 1:
                return TRUE;
            default:
                return UNKNOWN;
        }
    }
}
