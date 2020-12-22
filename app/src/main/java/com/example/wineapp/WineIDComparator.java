package com.example.wineapp;

import java.util.Comparator;

class WineIDComparator implements Comparator<MyWineListItem>
{
    @Override
    public int compare(MyWineListItem lhs, MyWineListItem rhs) {
        if (lhs.getWineID() - rhs.getWineID() < 0)
            return -1;
        else if(lhs.getWineID() - rhs.getWineID() > 0)
            return 1;
        else
            return 0;
    }
}