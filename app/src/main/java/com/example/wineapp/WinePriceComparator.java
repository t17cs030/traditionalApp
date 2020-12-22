package com.example.wineapp;

import java.util.Comparator;

class WinePriceComparator implements Comparator<MyWineListItem>
{
    @Override
    public int compare(MyWineListItem lhs, MyWineListItem rhs) {
        if (lhs.getWinePrice() - rhs.getWinePrice() < 0)
            return -1;
        else if(lhs.getWinePrice() - rhs.getWinePrice() > 0)
            return 1;
        else
            return 0;
    }
}