package com.example.wineapp;

import java.util.Comparator;

class NameComparatorDown implements Comparator<MyWineListItem>
{
    @Override
    public int compare(MyWineListItem lhs, MyWineListItem rhs) {
        if(lhs.getFurigana().compareTo(rhs.getFurigana()) < 0)
            return 1;
        else if(lhs.getFurigana().compareTo(rhs.getFurigana()) > 0)
            return -1;
        else
            return 0;
    }
}