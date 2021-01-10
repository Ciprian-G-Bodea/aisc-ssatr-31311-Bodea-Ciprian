package mas.ssatr.bodea.ciprian;

import java.util.ArrayList;
import java.util.List;

public class Transition {
    public List<Place> PreviousPlaces;
    public List<Place> NextPlaces;
    public String Name;
    private int Time = 1;
    private boolean isExecuting;
    int auxTime;

    public Transition(String name) {
        this.PreviousPlaces = new ArrayList<>();
        this.NextPlaces = new ArrayList<>();
        this.Name = name;
    }

    public void AddNextPlace(Place p) {

        NextPlaces.add(p);
    }

    public void AddPreviousPlace(Place p) {

        PreviousPlaces.add(p);
    }

    public void setTime(int time) {

        Time = time;
    }


    public void ExecuteTransition() {
        if (IsExecutable()) {
            for (Place input : PreviousPlaces) {
                if (input.HasToken()) {
                    input.RemoveToken();
                }
            }
            isExecuting = true;
        }
        auxTime++;

        if (isExecuting && enoughTimeHasPassed(auxTime)) {
            for (Place output : NextPlaces) {
                output.AddToken();
            }
            isExecuting = false;
            auxTime = 0;
        }
    }

    public int getTime() {
        return Time;
    }

    public boolean IsExecutable() {
        for (Place input : PreviousPlaces) {
            if (input.HasToken() == false) {
                return false;
            }
        }
        return true;
    }

    public boolean getIsExecuting() {
        return this.isExecuting;
    }

    public boolean enoughTimeHasPassed(int auxTime) {
        return auxTime > Time;
    }
}

