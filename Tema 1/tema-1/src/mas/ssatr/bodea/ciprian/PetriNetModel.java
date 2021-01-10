package mas.ssatr.bodea.ciprian;

import java.util.ArrayList;
import java.util.List;

public class PetriNetModel {

    public List<Place> Places;
    public List<Transition> Transitions;

    public PetriNetModel() {
        this.Places = new ArrayList<>();
        this.Transitions = new ArrayList<>();
    }

    public void step() {
        List<Transition> executableTransitions = new ArrayList<>();
        for (Transition transition : Transitions) {
            if (transition.IsExecutable() || transition.getIsExecuting()) {
                executableTransitions.add(transition);
            }
        }

     //   for(int i = 0; i < executableTransitions.size(); i++ ){
        for (Transition transition : executableTransitions) {
            transition.ExecuteTransition();
            SimulationEngine.LastExecutedTransitionTime = SimulationEngine.ApplicationTime;
        }

        SimulationEngine.ApplicationTime++;
        writeSteps();
    }

    private void writeSteps() {
        int numberOfPlaces = Places.size();
        int numberOfDigits = (int) Math.log10(numberOfPlaces) + 1; // get number of digits using math formula

        OutputFileWriter.WriteOneLine("Step:");
        for (Place place : Places) {
            String padding = "";
            for (int i = 0 ; i <= numberOfDigits; i++)
            {
                padding += " ";
            }

            OutputFileWriter.WriteOneLine(padding + place.getTokens());
        }
        OutputFileWriter.WriteOneLine("\n");
    }

    public int getMaxTime() {
        int max = 0;
        for (var transition : Transitions) {
            if (max < transition.getTime()) {
                max = transition.getTime();
            }
        }
        return max;
    }
}
