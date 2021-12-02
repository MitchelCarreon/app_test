package com.ualr.app_test.Utils;

import android.context.Context;

import com.ualr.app_test.R;
import com.ualr.app_test.Creature.Creature;

import java.util.ArrayList;
import java.util.Random;

public class DataGenerator{

    private static Random random = new Random();

    public static int randInt(int max)
    {
        int min = 0;
        return r.nextInt((max-min) + 1) + min;
    }

    public static Creature getCreatureData(Context ctx){
        String name_arr[] = ctx.getResources().getStringArray(R.array.creature_name);
        String lvl_arr[] = ctx.getResources().getStringArray(R.array.creature_lvl);
        String hp_arr[] = ctx.getResources().getStringArray(R.array.creature_hp);
        String atk_arr[] = ctx.getResources().getStringArray(R.array.creature_atk);
        String def_arr[] = ctx.getResources().getStringArray(R.array.creature_def);

        int randCreature = randInt(name_arr.length - 1);
        Creature ctr = new Creature();
        ctr.setName(name_arr[randCreature]);
        ctr.setLvl(lvl_arr[randCreature]);
        ctr.setHp(hp_arr[randCreature]);
        ctr.setAtk(atk_arr[randCreature]);
        ctr.setDef(def_arr[randCreature]);
        return ctr;
    }
}