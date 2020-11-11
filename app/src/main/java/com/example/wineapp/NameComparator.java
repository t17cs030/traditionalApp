package com.example.wineapp;

import java.util.Comparator;

//読み仮名を実装しないと名前順はできない
class NameComparator implements Comparator<MyWineListItem>
{
    @Override
    public int compare(MyWineListItem lhs, MyWineListItem rhs) {
        return lhs.getFurigana().compareTo(rhs.getFurigana());
    }
}