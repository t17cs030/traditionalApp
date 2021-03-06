package com.example.wineapp;

import java.util.Comparator;

class NameComparator implements Comparator<MyWineListItem>
{
    @Override
    public int compare(MyWineListItem lhs, MyWineListItem rhs) {
        return lhs.getFurigana().compareTo(rhs.getFurigana());
    }
}