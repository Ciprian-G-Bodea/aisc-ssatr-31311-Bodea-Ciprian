package mas.ssatr.bodea.ciprian;

public class SimulationEngine {

    private final PetriNetModel petriNetModel;
    public static int ApplicationTime = 0;
    public static int LastExecutedTransitionTime;
    private boolean isActive = true;

    public SimulationEngine(PetriNetModel petriNetModel) {
        this.petriNetModel = petriNetModel;
    }

    public void Simulate() {
        this.writeStartingLine();
        while (isActive) {
            petriNetModel.step();
            if (ApplicationTime > LastExecutedTransitionTime + petriNetModel.getMaxTime()) {
                isActive = false;
            }
        }
    }

    private void writeStartingLine() {
        String line = "No.  ";
        int numberOfPlaces = petriNetModel.Places.size();
        int numberOfDigits = (int) Math.log10(numberOfPlaces) + 1;
        for (int i = 0; i < numberOfPlaces; i++) {
            // https://stackoverflow.com/questions/473282/how-can-i-pad-an-integer-with-zeros-on-the-left

            line += " P" + String.format("%0" + numberOfDigits + "d", i);
        }
        line += "\n";
        OutputFileWriter.WriteOneLine(line);
    }
}
