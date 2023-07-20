package net.magic_and_taboo.util;

import net.minecraft.world.World;

public class MATWorldTime {
    private static long PREVIOUS;
    public static int TIME;
    public static int HOURS;
    public static int DAYS;
    public static int YEARS;
    public static int LUNAR_PHASE;
    public static int DATE;
    public static int MONTH;
    public static int MINUTES;

    public static void update(World world, boolean forced) {
        long time = world.getTimeOfDay();
        if (!forced && time == PREVIOUS) return;
        PREVIOUS = time;
        TIME = (int) (time % 24000);
        HOURS = TIME / 1000;
        MINUTES = (TIME - HOURS * 1000) * 3 / 50;
        DAYS = (int) (time / 24000);
        YEARS = DAYS / 144 + 1;
        LUNAR_PHASE = DAYS % 12;
        int totalMonths = DAYS / 24;
        DATE = DAYS + 1 - totalMonths * 24;
        MONTH = totalMonths + 1 - YEARS * 6;
    }
}
