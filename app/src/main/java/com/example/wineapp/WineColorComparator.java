package com.example.wineapp;

import java.util.Comparator;

class WineColorComparator implements Comparator<MyWineListItem>
{
    @Override
    public int compare(MyWineListItem lhs, MyWineListItem rhs) {
        int leftColor;
        if(lhs.getWineColor().equals("赤ワイン"))
            leftColor = 5;
        else if(lhs.getWineColor().equals("ロゼワイン"))
            leftColor = 3;
        else if(lhs.getWineColor().equals("オレンジワイン"))
            leftColor = 2;
        else if(lhs.getWineColor().equals("白ワイン"))
            leftColor = 1;
        else
            leftColor = 6;

        int rightColor;
        if(rhs.getWineColor().equals("赤ワイン"))
            rightColor = 5;
        else if(rhs.getWineColor().equals("ロゼワイン"))
            rightColor = 3;
        else if(rhs.getWineColor().equals("オレンジワイン"))
            rightColor = 2;
        else if(rhs.getWineColor().equals("白ワイン"))
            rightColor = 1;
        else
            rightColor = 6;



        if (leftColor - rightColor < 0)
            return -1;
        else if(leftColor - rightColor > 0)
            return 1;
        else
            return 0;
    }
}