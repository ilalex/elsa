import org.jgap.*;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.DoubleGene;
/*
 * Класс определения параметров при помощи генетического алгоритма.
 */

public class GAClass implements Runnable {
    private double bfitness;     //Функция вписываемости без погрешности Bsim=Bexp
    private JAModelClass jaModel;//Модель Джилса Аттертона
    private double H_max;       //Максимальная напряженность поля
    private int N;
    private double t_max;
    private int f;
    private double M0;          //Магнитная проницаемость
    double e;                   //Точность
    private int MaxEvolve;
    private Configuration conf;
    private FitnessFunction myFunc;
    private Gene[] resultGenes;
    private Genotype population;
    private Gene[] sampleGenes;
    private boolean IdentParamOk;

    private java.util.Vector<Double> B; //Вектор результатов моделирования
    private java.util.Vector<Double> Bexp; //Вектор результатов эксперимента

    private double best_a; //Лучшее значение для А
    private double best_alpha; //Лучшее значение для АLPHA
    private double best_k; //Лучшее значение для А
    private double best_c; //Лучшее значение для C
    private double best_MS; //Лучшее значение для MS

    /*
     * Конструктор класса
     */
    public GAClass(java.util.Vector<Double> LBexp, int LN, double Lt_max, int Lf, double LH_max, double Le, int LMaxEvolve) {

        Bexp = LBexp; //Вектор результатов эксперимента
        H_max = LH_max;
        N = LN;
        t_max = Lt_max;
        f = Lf;
        e = Le;
        MaxEvolve = LMaxEvolve;
        conf = new DefaultConfiguration();
        myFunc = new MyGAFitnessFunction(Bexp, N, t_max, f, H_max);
        IdentParamOk = false;

    }

    /*
     * Статус нахождения параметров параметров
     */
    public boolean getIdentParamStatus() {
        return IdentParamOk;
    }

    /*
     * Поиск параметров
     */
    public void IdentParam() throws InvalidConfigurationException {
        double max_position[] = {10000.0, 0.01, 0.99, 10000, java.lang.Math.pow(10, 7)};
        double min_position[] = {10.0, java.lang.Math.pow(10, -12), 0.01, 0.01, 100.0};
        conf.setFitnessFunction(myFunc);
        sampleGenes = new Gene[5];

        sampleGenes[0] = new DoubleGene(conf, min_position[0], max_position[0]);  // A
        sampleGenes[1] = new DoubleGene(conf, min_position[1], max_position[1]);  // ALPHA
        sampleGenes[2] = new DoubleGene(conf, min_position[2], max_position[2]);  // C
        sampleGenes[3] = new DoubleGene(conf, min_position[3], max_position[3]);  // K
        sampleGenes[4] = new DoubleGene(conf, min_position[4], max_position[4]);  // MS

        Chromosome sampleChromosome = new Chromosome(conf);
        sampleChromosome.setGenes(sampleGenes);
        conf.setSampleChromosome(sampleChromosome);
        conf.setPopulationSize(5000);
        population = Genotype.randomInitialGenotype(conf);
        while (evolve() < e) ;
        IdentParamOk = true;
    }

    /*
     * Поиск точек
     */
    private double evolve() throws InvalidConfigurationException {
        double bestFitness;
        int j;
        Chromosome bestSolutionSoFar;

        j = 0;
        do {
            population.evolve();

            bestSolutionSoFar = (Chromosome) population.getFittestChromosome();
            bestFitness = bestSolutionSoFar.getFitnessValue();
            resultGenes = bestSolutionSoFar.getGenes();

            j++;
            if (j > MaxEvolve) {
                System.out.print("-----------------------GA Class------------------------------------\r\n");
                System.out.print("bestFitness=" + Double.toString(1 / bestFitness) + "\r\n");
                System.out.print("resultGenes[0]=" + resultGenes[0].getAllele().toString() + "\r\n");
                System.out.print("resultGenes[1]=" + resultGenes[1].getAllele().toString() + "\r\n");
                System.out.print("resultGenes[2]=" + resultGenes[2].getAllele().toString() + "\r\n");
                System.out.print("resultGenes[3]=" + resultGenes[3].getAllele().toString() + "\r\n");
                System.out.print("resultGenes[4]=" + resultGenes[4].getAllele().toString() + "\r\n");
                j = 0;
                //break;
            }
        }
        while ((1 / bestFitness) > e);

        System.out.print("-----------------------GA Class OK!------------------------------------\r\n");
        System.out.print("bestFitness=" + Double.toString(1 / bestFitness) + "\r\n");
        System.out.print("resultGenes[0]=" + resultGenes[0].getAllele().toString() + "\r\n");
        System.out.print("resultGenes[1]=" + resultGenes[1].getAllele().toString() + "\r\n");
        System.out.print("resultGenes[2]=" + resultGenes[2].getAllele().toString() + "\r\n");
        System.out.print("resultGenes[3]=" + resultGenes[3].getAllele().toString() + "\r\n");
        System.out.print("resultGenes[4]=" + resultGenes[4].getAllele().toString() + "\r\n");

        resultGenes = bestSolutionSoFar.getGenes();
        best_a = Double.parseDouble(resultGenes[0].getAllele().toString());
        best_alpha = Double.parseDouble(resultGenes[1].getAllele().toString());
        best_c = Double.parseDouble(resultGenes[2].getAllele().toString());
        best_k = Double.parseDouble(resultGenes[3].getAllele().toString());
        best_MS = Double.parseDouble(resultGenes[4].getAllele().toString());
        return bestFitness;
    }

    /*
     *  Лучший коэффициент А
     */

    public double getBestA() {
        //best_parm_find(); //Находим лучшие коэффициенты
        return best_a; //Лучшее значение для А
    }

    /*
     *  Лучший коэффициент ALPHA
     */
    public double getBestALPHA() {
        //best_parm_find(); //Находим лучшие коэффициенты
        return best_alpha; //Лучшее значение для АLPHA
    }

    /*
     *  Лучший коэффициент K
     */
    public double getBestK() {
        //best_parm_find(); //Находим лучшие коэффициенты
        return best_k; //Лучшее значение для K

    }

    /*
     *  Лучший коэффициент C
     */
    public double getBestC() {
        //best_parm_find(); //Находим лучшие коэффициенты
        return best_c; //Лучшее значение для C
    }

    /*
     *  Лучший коэффициент MS
     */
    public double getBestMS() {
        return best_MS; //Лучшее значение для MS
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        System.out.print("GA class runned\r\n");
        try {
            IdentParam();
        } catch (Exception ex) {
            System.out.print("Exception:" + ex.getMessage());
        }
    }

}
