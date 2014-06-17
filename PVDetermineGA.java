import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.DoubleGene;

/*
 *  Класс для определения параметров модели Джилса-Аттертона по пользовательским данным
 */


public class PVDetermineGA implements Runnable {
   private double e;     //Точность
   private int MaxEvolve;
   private Configuration conf;
   private FitnessFunction myFunc;
   private Gene[] resultGenes;
   private Genotype population;
   private Gene[] sampleGenes;
   private double BS;
   private double BR;
   private double HC;
   private double Hmax;
   private boolean IdentParamOk;
   private java.util.Vector<Double> Bproj;
   private java.util.Vector<Double> Hproj;

   //Конструктор класса
   public PVDetermineGA(java.util.Vector<Double> LBproj,java.util.Vector<Double> LHproj, double LHStep, double Lt_max, double LN, double Lf, double Le, int LMaxEvolve ){
	   e=Le;
	   MaxEvolve=LMaxEvolve;
	   Bproj=new java.util.Vector();
	   Bproj=LBproj;
	   Hproj=new java.util.Vector();
	   Hproj=LHproj; 
	   conf = new DefaultConfiguration();
	   myFunc =new MyGAFitnessFunctionPV(LBproj,LHproj,LHStep,Lt_max,LN,Lf);
	   IdentParamOk=false;
    }
   
      /*
	   * Статус нахождения параметров параметров
	   */
	  public boolean getIdentParamStatus()
	  {
	   return IdentParamOk;
	  }
	  
   
      /*
	   * Поиск параметров
	   */
	 public void IdentParam() throws InvalidConfigurationException
	  {
	    double maxBS=java.lang.Math.abs(getMax(Bproj)*5);	 
	    double maxBR=java.lang.Math.abs(maxBS*0.7);
	    double maxHmax=java.lang.Math.abs(getMax(Hproj)*45);
	    double maxHC=java.lang.Math.abs(getMax(Hproj)*1.1);
	    double max_position[]={maxBS,maxBR,maxHC,maxHmax};
	    double min_position[]={maxBS*0.8,maxBS*0.6,0,maxHmax*0.20};		 
		conf.setFitnessFunction(myFunc);
        sampleGenes = new Gene[4];
		
		sampleGenes[0] = new DoubleGene(conf,min_position[0],max_position[0]);  // BS
		sampleGenes[1] = new DoubleGene(conf,min_position[1],max_position[1]);  // BR
		sampleGenes[2] = new DoubleGene(conf,min_position[2],max_position[2]);  // HC
		sampleGenes[3] = new DoubleGene(conf,min_position[3],max_position[3]);  // Hmax
			
		Chromosome sampleChromosome = new Chromosome(conf);
		sampleChromosome.setGenes(sampleGenes);
		conf.setSampleChromosome(sampleChromosome);
		conf.setPopulationSize(5000);
		population = Genotype.randomInitialGenotype(conf);
		while (evolve()<e) ; 
		IdentParamOk=true;
	  }
	
	  /*
	   * Поиск точек 
	   */
	  private double evolve() throws InvalidConfigurationException
	  {
		double bestFitness;
		int j;
		Chromosome bestSolutionSoFar;
		
		j=0;
		do
		{
	     population.evolve();
	     
		 bestSolutionSoFar = (Chromosome) population.getFittestChromosome();
		 bestFitness=bestSolutionSoFar.getFitnessValue();
		 resultGenes=bestSolutionSoFar.getGenes(); 	
		 j++;
		 if (j>MaxEvolve)
		 {
			   System.out.print("-----------------------GA Class------------------------------------\r\n");
			   System.out.print("bestFitness="+Double.toString(1/bestFitness)+"\r\n");
			   System.out.print("resultGenes[0]="+resultGenes[0].getAllele().toString()+"\r\n");
			   System.out.print("resultGenes[1]="+resultGenes[1].getAllele().toString()+"\r\n");
			   System.out.print("resultGenes[2]="+resultGenes[2].getAllele().toString()+"\r\n");
			   System.out.print("resultGenes[3]="+resultGenes[3].getAllele().toString()+"\r\n");
		 j=0;	 
 	      //break;
		 }
		} 
		while ((1/bestFitness)>e);
		
		System.out.print("-----------------------GA Class OK!------------------------------------\r\n");
		System.out.print("bestFitness="+Double.toString(1/bestFitness)+"\r\n");
		System.out.print("resultGenes[0]="+resultGenes[0].getAllele().toString()+"\r\n");
		System.out.print("resultGenes[1]="+resultGenes[1].getAllele().toString()+"\r\n");
		System.out.print("resultGenes[2]="+resultGenes[2].getAllele().toString()+"\r\n");
		System.out.print("resultGenes[3]="+resultGenes[3].getAllele().toString()+"\r\n");
		
		resultGenes=bestSolutionSoFar.getGenes(); 		
		BS=Double.parseDouble(resultGenes[0].getAllele().toString());
		BR=Double.parseDouble(resultGenes[1].getAllele().toString());
		HC=Double.parseDouble(resultGenes[2].getAllele().toString());
		Hmax=Double.parseDouble(resultGenes[3].getAllele().toString());
	    return bestFitness;
	  }
	  
	  /*
	   *  Лучший коэффициент А 
	   */
	  
	  public double getBestBS()
	  {
		return BS; //Лучшее значение для BS
	  }
	  
	  /*
	   *  Лучший коэффициент ALPHA 
	   */
	  public double getBestBR()
	  {
		return BR; //Лучшее значение для BR
	  }
	  
	  /*
	   *  Лучший коэффициент K 
	   */
	  public double getBestHC()
	  {
		return HC; //Лучшее значение для HC

	  }

	  /*
	   *  Лучший коэффициент C 
	   */
	  public double getBestHMax()
	  {
		return Hmax; //Лучшее значение для Hmax
	  }
	  
	  @Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.print("GA class runned\r\n");
			try{
			 IdentParam();
			}
			catch (Exception ex)
			{
			 System.out.print("Exception:"+ex.getMessage());	 
			}
		}

  	   //Максимальное значение в векторе 
		public double getMax(java.util.Vector<Double> V){
			double max=0;
			for (int i=0;i<V.size();i++){
			 if (V.get(i)>max)
			  max=V.get(i);
			}
			return max;
		}
		

	  
}
