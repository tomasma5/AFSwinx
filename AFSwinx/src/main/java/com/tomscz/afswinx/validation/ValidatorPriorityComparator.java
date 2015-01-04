package com.tomscz.afswinx.validation;

import java.util.Comparator;

/**
 * This comparator compare validators agains it's priority.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class ValidatorPriorityComparator implements Comparator<AFValidations> {

    @Override
    public int compare(AFValidations o1, AFValidations o2) {
        if (o1.getPriority() > o2.getPriority()) {
            return -1;
        } else if (o1.getPriority() < o2.getPriority()) {
            return 1;
        }
        return 0;
    }

}
