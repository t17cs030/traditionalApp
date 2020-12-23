package com.example.wineapp;

import java.util.Comparator;

class WineryComparatorDown implements Comparator<MyWineListItem>
{
    @Override
    public int compare(MyWineListItem lhs, MyWineListItem rhs) {
        if(lhs.getWinery().compareTo(rhs.getWinery()) < 0)
            return 1;
        else if(lhs.getWinery().compareTo(rhs.getWinery()) > 0)
            return -1;
        else
            return 0;
    }
}