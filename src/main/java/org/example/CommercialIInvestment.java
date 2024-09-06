package org.example;

import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.BinaryVariable;

// a multi-objective problem for a factory investment into two online marketplace: shoppe and lazada
//
// the factory has 6 products: "television", "speaker", "refrigerator", "washingMachine", "microwave","computer"
// the factory can sell their product in shoppe or lazada or both
// each product has production cost and profit
// the fees differs for each platform for each product
//
// (ASSUME THIS PROBLEM HAS NOT MENTIONED THE QUANTITY OF EACH PRODUCT)
//
// this problem has 2 variables, 2 objectives, 1 constraint
// variables: binary subset of products for each market place: shoppe and lazada
// objectives: maximize total profit and minimize production cost
// constraint: the production cost must not exceed budget of 2000
public class CommercialIInvestment implements Problem {

    private final String[] marketPlace = {"shopee", "lazada"};
    final String[] products = {"television", "speaker", "refrigerator", "washingMachine", "microwave","computer"};
    private final double[] profit = {500,400,700,550,300,1000};
    private final double[] costForProduce = {200,100,300,250,130,400};
    private final double[] costForShoppee = {50,40,70,55,30,100};
    private final double[] getCostForLazada = {40,30,60,45,20,85};

    private final double budget = 2000 ;

    public String getName(){
        return "" ;
    }
    public CommercialIInvestment(){
        super();
    }

    @Override
    public int getNumberOfVariables() {
        return 2;
    }

    @Override
    public int getNumberOfObjectives() {
        return 2;
    }

    @Override
    public int getNumberOfConstraints() {
        return 1;
    }


    // fitness function
    @Override
    public void evaluate(Solution solution) {
        BinaryVariable shoppeProducts = (BinaryVariable) solution.getVariable(0);
        BinaryVariable lazadaProducts = (BinaryVariable) solution.getVariable(1);

        double totalProfit = 0;
        double totalCost = 0 ;

        for(int i = 0 ; i < shoppeProducts.getNumberOfBits(); i++){
            if (shoppeProducts.get(i)){
                totalProfit += profit[i];
                totalCost += costForProduce[i];
                totalCost += costForShoppee[i];
            }
        }
        for(int i = 0 ; i < lazadaProducts.getNumberOfBits(); i++){
            if (lazadaProducts.get(i)){
                totalProfit += profit[i];
                totalCost += costForProduce[i];
                totalCost += getCostForLazada[i];
            }
        }

        solution.setObjective(0, -totalProfit);
        solution.setObjective(1,totalCost);

        // survival selection
        double limit = totalCost - budget ;
        solution.setConstraint(0, limit > 0 ? limit : 0);
    }

    @Override
    public Solution newSolution() {

        // population initialization
        Solution solution = new Solution(this.getNumberOfVariables(), this.getNumberOfObjectives(), this.getNumberOfConstraints());
        solution.setVariable(0, new BinaryVariable(products.length));
        solution.setVariable(1, new BinaryVariable(products.length));
        return  solution;
    }

    @Override
    public void close() {

    }
}
