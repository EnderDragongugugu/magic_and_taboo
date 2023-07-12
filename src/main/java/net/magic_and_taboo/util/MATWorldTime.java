package net.magic_and_taboo.util;

import net.minecraft.world.World;

public class MATWorldTime {;

    public final int time;
    public final int hours;
    public final int days;
    public final int year;
    public final int lunarPhase;
    public final int date;
    public final int month;
    public final int mins;
    public MATWorldTime(World world){
        this.time = (int) (world.getTimeOfDay() % 24000L);
        this.hours = (int)Math.floor(time / 1000);
        this.mins = (int) Math.floor((time - hours * 1000) * 3 / 50);
        this.days = (int)Math.floor (world.getTimeOfDay() / 24000);
        this.year = (int) Math.floor(days / 144) + 1;
        this.lunarPhase = days % 12;
        int totalMonths = (int) Math.floor(days / 24);
        this.date = days + 1 - totalMonths * 24;
        this.month = totalMonths + 1 - year * 6;
    }
}
