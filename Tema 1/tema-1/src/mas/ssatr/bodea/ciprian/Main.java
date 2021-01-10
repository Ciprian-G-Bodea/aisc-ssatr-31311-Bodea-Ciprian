package mas.ssatr.bodea.ciprian;

public class Main {
    public static void main(String[] args) {
        OutputFileWriter.DeletePreviousOutputs();
        PetriNetLoader Loader = new PetriNetLoader();
        Loader.readInputFile("src/mas/ssatr/bodea/ciprian/input.json");
        SimulationEngine SimulatePetriNet = new SimulationEngine(Loader.getPetriNetModel());
        SimulatePetriNet.Simulate();
    }
}