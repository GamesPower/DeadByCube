package deadbycube.player.killer.power.cartersspark.madness;

public enum MadnessTier {

    TIER_0(400, false), TIER_1(1500, true), TIER_2(1500, true), TIER_3(0, true);

    private final double requiredMadness;
    private final boolean display;

    MadnessTier(double requiredMadness, boolean display) {
        this.requiredMadness = requiredMadness;
        this.display = display;
    }

    public double getRequiredMadness() {
        return requiredMadness;
    }

    public boolean hasDisplay() {
        return display;
    }

}
