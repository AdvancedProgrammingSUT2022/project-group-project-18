package client.controller.unitactoins;

import model.BaseCivilization;
import model.unit.Melee;
import model.unit.Ranged;
import model.unit.Settler;
import model.unit.Worker;

public class Delete extends AbstarctActoin{
    int id;
    public Delete(Ranged unit, int id, BaseCivilization b ) {
        super(unit);
        this.id = id;
    }

    public Delete(Melee unit, int id, BaseCivilization b) {
        super(unit);
        this.id = id;
    }

    public Delete(Worker unit, int id, BaseCivilization b) {
        super(unit);
        this.id = id;
    }

    public Delete(Settler unit, int id, BaseCivilization b) {
        super(unit);
        this.id = id;
    }

    public void delete(){
       // b.units.remove(id);


    }
}
