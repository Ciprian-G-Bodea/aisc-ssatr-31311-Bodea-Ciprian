package net.agten.heatersimulator.domain.algorithm;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class FuzzyTempController implements ControllerAlgorithm  {

    String filename = "fuzzyController.fcl";
    FIS fis = FIS.load(filename, true);

    public FuzzyTempController() {

        if (fis == null) {
            System.err.println("Can't load file: '" + filename + "'");
            System.exit(1);
        }

        // Get default function block
        FunctionBlock fb = fis.getFunctionBlock(null);

        // Set inputs
        fb.setVariable("current_temp", 8.5);
        fb.setVariable("desired_temp", 7.5);

        // Evaluate
        fb.evaluate();

        // Show output variable's chart
        fb.getVariable("result").defuzzify();

        // Print ruleSet
        System.out.println(fb);
        System.out.println("result: " + fb.getVariable("result").getValue());


        // Show
        JFuzzyChart.get().chart(fb);

        // Set inputs
        fis.setVariable("current_temp", 3);
        fis.setVariable("desired_temp", 7);

        // Evaluate
        fis.evaluate();

        // Show output variable's chart
        Variable result = fb.getVariable("result");
        JFuzzyChart.get().chart(result, result.getDefuzzifier(), true);

        // Print ruleSet
        System.out.println(fis);
    }

    @Override
    public short nextValue(short curAdc) {
        return 0;
    }

    @Override
    public void setTargetAdc(short targetAdc) {

    }
}
