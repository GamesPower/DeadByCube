package deadbycube.player.killer.power.evilwithin;

import deadbycube.player.killer.Killer;

public class PowerEvilWithin3 extends PowerEvilWithin {

    PowerEvilWithin3(Killer killer) {
        super(killer);
    }

    @Override
    void onStalk() {
        this.stopUpdate();
    }

}
