package org.example;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.BinaryVariable;

public class Main {
    public static void main(String[] args) {
        Executor executor = new Executor()
                .withProblemClass(CommercialIInvestment.class)
                .withAlgorithm("NSGAII")
                .withMaxEvaluations(10000);

        NondominatedPopulation result = executor.run();
        CommercialIInvestment market = new CommercialIInvestment();

        for (Solution solution : result) {
            BinaryVariable shopee = (BinaryVariable) solution.getVariable(0);
            BinaryVariable lazada = (BinaryVariable) solution.getVariable(1);

            System.out.println("Shopee Products:");
            for (int i = 0; i < shopee.getNumberOfBits(); i++) {
                if (shopee.get(i)) {
                    System.out.println("- " + market.products[i]);
                }
            }

            System.out.println("Lazada Products:");
            for (int i = 0; i < lazada.getNumberOfBits(); i++) {
                if (lazada.get(i)) {
                    System.out.println("- " + market.products[i]);
                }
            }

            System.out.println("Total Profit: " + (-solution.getObjective(0)));
            System.out.println("Total Cost: " + solution.getObjective(1));
            System.out.println("-----------------------------------");
        }
    }
}
