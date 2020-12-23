package com.example.wineapp;

import java.util.Comparator;

class WineColorComparatorDown implements Comparator<MyWineListItem>
{
    @Override
    public int compare(MyWineListItem lhs, MyWineListItem rhs) {
        if (lhs.getWineColor() - rhs.getWineColor() < 0)
            return 1;
        else if(lhs.getWineColor() - rhs.getWineColor() > 0)
            return -1;
        else
            return 0;
    }
}