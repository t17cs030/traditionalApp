package com.example.wineapp;

import java.util.Comparator;

class WineryComparator implements Comparator<MyWineListItem>
{
    @Override
    public int compare(MyWineListItem lhs, MyWineListItem rhs) {
        return lhs.getWinery().compareTo(rhs.getWinery());
    }
}