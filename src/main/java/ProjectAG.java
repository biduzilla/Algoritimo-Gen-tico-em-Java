import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ProjectAG {

    Population population = new Population();
    Individual fittest;
    Individual secondFittest;
    int generationCount = 0;
    List<String> metodos = Arrays.asList("Sozinho", "Em grupo", "Com ajuda do professor", "Reforço");
    String metodo = "Reforço";
    int metodoInt = 0;

    public static void main(String[] args) {

        Random rn = new Random();
        ProjectAG app = new ProjectAG();

        //Initialize population
        app.population.initializePopulation(5);

        //Calculate fitness of each individual
        app.population.calculateFitness();

        System.out.println("Geração: " + (app.generationCount + 1) + " - Fittest: " + app.population.fittest + " - Metodo: " + app.metodo);
        app.addFile("Geração: " + (app.generationCount + 1) + " - Fittest: " + app.population.fittest + " - Metodo: " + app.metodo);

        printIndividuals(app);

        //Enquanto a população obtém um indivíduo com aptidão máxima
        while (app.population.fittest < 5) {

            ++app.generationCount;

            //Seleção
            app.selection();

            //Crossover
            app.crossover();

            //Faça mutação sob uma probabilidade aleatória
            if (rn.nextInt() % 7 < 5) {
                app.mutation();
            }

            //Adicionar os descendentes mais apta à população
            app.addFittestOffspring();

            //Atualizar os valores de aptidão
            app.population.calculateFitness();


            //Tipo de estudos
            trocarMetodoEstudos(app);

            System.out.println("Geração: " + (app.generationCount + 1) + " Metodo: " + app.metodo);
            app.addFile("Geração: " + (app.generationCount + 1) + " Metodo: " + app.metodo);

            printIndividuals(app);

            app.addFile("");
        }

        System.out.println("\nSolução encontrada na geração " + (app.generationCount + 1) + " No método " + app.metodo);
        app.addFile("\nSolução encontrada na geração " + (app.generationCount + 1) + " No método " + app.metodo);

        System.out.println("Fitness: " + app.population.getFittest().fitness);
        app.addFile("Fitness: " + app.population.getFittest().fitness);

        System.out.print("Genes: ");
        app.addFile("Genes: ");
        for (int i = 0; i < 6; i++) {
            System.out.print(app.population.getFittest().genes[i]);
            app.addFileSemLinha(String.valueOf(app.population.getFittest().genes[i]));
        }

        System.out.println("\n");
        app.addFile("");

    }

    private static void printIndividuals(ProjectAG demo) {
        for (Individual ind :
                demo.population.individuals) {

            for (int gene :
                    ind.genes) {
                System.out.print(gene);
                demo.addFileSemLinha(String.valueOf(gene));
            }
            demo.addFile("");
            System.out.println();
        }
        System.out.println();
    }

    //Tipo de estudos
    private static void trocarMetodoEstudos(ProjectAG demo) {
        if (demo.metodoInt > 2) {
            demo.metodoInt = 0;
        } else {
            demo.metodoInt++;
        }

        switch (demo.metodoInt) {
            case 0:
                demo.metodo = demo.metodos.get(0);
                break;
            case 1:
                demo.metodo = demo.metodos.get(1);
                break;
            case 2:
                demo.metodo = demo.metodos.get(2);
                break;
            case 3:
                demo.metodo = demo.metodos.get(3);
                break;
        }
    }

    //Seleção
    void selection() {

        //Selecione o indivíduo mais apto
        fittest = population.getFittest();

        //Selecione o segundo indivíduo mais apto
        secondFittest = population.getSecondFittest();
    }

     void addFile(String linha) {
        try {
            Files.write(Paths
                            .get("C:\\Users\\lhcteles\\eclipse-workspace\\TsPlus\\src\\main\\java\\serviceImpl\\newFile.txt"),
                    (linha + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            System.out.println("Error salvar arquivo " + e.getCause());
        }
    }

     void addFileSemLinha(String linha) {
        try {
            Files.write(Paths
                            .get("C:\\Users\\lhcteles\\eclipse-workspace\\TsPlus\\src\\main\\java\\serviceImpl\\newFile.txt"),
                    (linha).getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            System.out.println("Error salvar arquivo " + e.getCause());
        }
    }

    //Crossover
    void crossover() {
        Random rn = new Random();

        //Seleciona random ponto para crossover
        int crossOverPoint = rn.nextInt(population.individuals[0].geneLength);

        //Troque valores entre os pais
        for (int i = 0; i < crossOverPoint; i++) {
            int temp = fittest.genes[i];
            fittest.genes[i] = secondFittest.genes[i];
            secondFittest.genes[i] = temp;
        }
    }

    //Mutação
    void mutation() {
        Random rn = new Random();

        //Selecione um ponto de mutação aleatório
        int mutationPoint = rn.nextInt(population.individuals[0].geneLength);

        //Inverter valores no ponto de mutação
        if (fittest.genes[mutationPoint] == 0) {
            fittest.genes[mutationPoint] = 1;
        } else {
            fittest.genes[mutationPoint] = 0;
        }

        mutationPoint = rn.nextInt(population.individuals[0].geneLength);

        if (secondFittest.genes[mutationPoint] == 0) {
            secondFittest.genes[mutationPoint] = 1;
        } else {
            secondFittest.genes[mutationPoint] = 0;
        }
    }

    //Get fittest offspring
    Individual getFittestOffspring() {
        if (fittest.fitness > secondFittest.fitness) {
            return fittest;
        }
        return secondFittest;
    }


    //Substitua o indivíduo menos apto pelo individuo mais apto gerado mais apta
    void addFittestOffspring() {

        //Atualizar os valores de aptidão dos filhos
        fittest.calcFitness();
        secondFittest.calcFitness();

        //Obtenha o índice do indivíduo menos apto
        int leastFittestIndex = population.getLeastFittestIndex();

        //Substitua o indivíduo menos apto pelo mais apta
        population.individuals[leastFittestIndex] = getFittestOffspring();
    }
}

