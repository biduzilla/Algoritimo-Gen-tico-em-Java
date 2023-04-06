import java.util.Random;

public class Individual {

    int fitness = 0;
    int[] genes = new int[6];
    int geneLength = 6;

    public Individual() {
        Random rn = new Random();

        //Gera genes random
        for (int i = 0; i < genes.length; i++) {
            genes[i] = Math.abs(rn.nextInt() % 2);
        }

        fitness = 0;
    }

    //Calcula fitness
    public void calcFitness() {
        fitness = 0;
        for (int i = 0; i < 6; i++) {
            if (genes[i] == 1) {
                ++fitness;
            }
        }
    }
}