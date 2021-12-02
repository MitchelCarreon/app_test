package com.example.app_test.Utils;

import android.content.Context;

import com.example.app_test.Creature.Creature;
import com.example.app_test.R;

import java.util.Random;

public class CreatureGenerator {

    private static Random r = new Random();

    public static int randInt(int max)
    {
        int min = 0;
        return r.nextInt((max-min) + 1) + min;
    }

    public static Creature getCreatureData(Context ctx){
        String name_arr[] = ctx.getResources().getStringArray(R.array.creature_name);
        int lvl_arr[] = ctx.getResources().getIntArray(R.array.creature_lvl);
        int hp_arr[] = ctx.getResources().getIntArray(R.array.creature_hp);
        int atk_arr[] = ctx.getResources().getIntArray(R.array.creature_atk);
        int def_arr[] = ctx.getResources().getIntArray(R.array.creature_def);

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