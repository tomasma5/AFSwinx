package com.tomscz.afswinx.validation;

import java.util.Comparator;

public class ValidatorPriorityComparator implements Comparator<AFValidations> {

    @Override
    public int compare(AFValidations o1, AFValidations o2) {
       if(o1.getPriority() > o2.getPriority()){
           return -1;
       }
       else if(o1.getPriority() < o2.getPriority()){
           return 1;
       }
       return 0;
    }

}
