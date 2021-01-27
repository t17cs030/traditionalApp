package com.example.wineapp;

import java.util.Comparator;

class DistanceComparator implements Comparator<Double>
{
    @Override
    public int compare(Double lhs, Double rhs) {
        if (lhs - rhs < 0)
            return -1;
        else if(lhs - rhs > 0)
            return 1;
        else
            return 0;
    }
}