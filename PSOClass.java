/*
 * Алгоритм роя частиц
 */
import java.util.*;
import net.sourceforge.jswarm_pso.Swarm;
//import net.sourceforge.jswarm_pso.alpine.MyFitnessFunction;

public class PSOClass implements Runnable{
  private double bfitness;     //Функция вписываемости без погрешности Bsim=Bexp
  private JAModelClass jaModel;//Модель Джилса Аттертона
  private double H_max;       //Максимальная напряженность поля
  private int N;           
  private double t_max;
  private int f;
  private double M0;		  //Магнитная проницаемость
  double e;                   //Точность
  private int MaxEvolve;
  private boolean IdentParamOk;
  private String pso_num;
 
  private java.util.Vector<Double> B; //Вектор результатов моделирования
  private java.util.Vector<Double> Bexp; //Вектор результатов эксперимента 
  private java.util.Vector<Double> Hexp; //Вектор результатов эксперимента
  
  private double best_a; //Лучшее значение для А
  private double best_alpha; //Лучшее значение для АLPHA
  private double best_k; //Лучшее значение для А
  private double best_c; //Лучшее значение для C
  private double best_MS; //Лучшее значение для MS
  
  private double bestFitness;
  private double old_bestfitness;
  private double ext_bestfitness;
  private boolean external_set;
  
  
  /*
   * Конструктор класса
   */	
  public PSOClass(java.util.Vector<Double> LBexp, int LN, double Lt_max, int Lf, double LH_max, double Le, int LMaxEvolve,int num)
  {

	 Bexp= LBexp; //Вектор результатов эксперимента
	 H_max=LH_max;
	 N=LN;           
	 t_max=Lt_max;
	 f=Lf;
	 e=Le;
	 MaxEvolve=LMaxEvolve;
	 IdentParamOk=false;
	 pso_num=Integer.toString(num);
	 bestFitness=1000000.0;
	 old_bestfitness=1000000.0;
	 ext_bestfitness=1000000.0;
	 external_set=false;
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
  public void IdentParam()
  {
	double bestFitness_l;
	System.out.print("PSO class "+pso_num+"runned\r\n");
	bestFitness_l=evolve();
	while (bestFitness_l>e) bestFitness_l=evolve(); 
	IdentParamOk=true;
	System.out.print("-----------------------PSO Class "+pso_num+" OKK!------------------------------------\r\n");
	System.out.print("bestFitness="+Double.toString(bestFitness_l)+"\r\n");
	System.out.print("A="+Double.toString(best_a)+"\r\n");
	System.out.print("ALPHA="+Double.toString(best_alpha)+"\r\n");
	System.out.print("C="+Double.toString(best_c)+"\r\n");
	System.out.print("K="+Double.toString(best_k)+"\r\n");
	System.out.print("MS="+Double.toString(best_MS)+"\r\n");
  }

  /*
   * Поиск точек 
   */
  private double evolve()
  {
	double p[];
	double max_position[]={10000.0,0.01,0.99,10000,java.lang.Math.pow(10,7)};
	double min_position[]={10.0,java.lang.Math.pow(10,-7),0.0001,1,1.0};
	//double max_velocity[]={10000.0,0.01,0.99,1000,java.lang.Math.pow(10,4)};
	//double max_velocity[]={10000,1,1,10000,java.lang.Math.pow(10,7)};
	//double min_velocity[]={-10000,-1,-1,-10000,-java.lang.Math.pow(10,7)};
	//double min_velocity[]={1,0.01,0.01,1,1};
	double particle_threshold=10.0;
	int j;
	java.lang.String temp;  
	net.sourceforge.jswarm_pso.Particle particle[];
	// Create a swarm (using 'MyParticle' as sample particle 
	// and 'MyFitnessFunction' as finess function)
	Swarm swarm = new Swarm(Swarm.DEFAULT_NUMBER_OF_PARTICLES
				, new MyParticle()
				, new MyFitnessFunction(Bexp,N,t_max,f,H_max));
	// Set position (and velocity) constraints. 
	// i.e.: where to look for solutions
	swarm.setMaxPosition(max_position);
	swarm.setMinPosition(min_position);
	//swarm.setMaxVelocity(max_velocity);
	//swarm.setMinVelocity(min_velocity);
	//swarm.setInertia(0.99);
	//swarm.setGlobalIncrement(1);
	// Optimize a few times
	j=0;
	do
	{
	// for (int i=0;i<200;i++) 
	   swarm.evolve();
	 bestFitness=swarm.getBestFitness();
	// System.out.print("-----------------------PSO Class "+pso_num+"------------------------------------\r\n");
	 //System.out.println(swarm.toStringStats());
	 //System.out.println("Real BF="+Double.toString(bestFitness)+"\r\n");
	 //System.out.println("Old BF="+Double.toString(old_bestfitness)+"\r\n");
	 
	 if (external_set&&bestFitness>ext_bestfitness){
	   p=swarm.getBestPosition();
	   p[0]=best_a;
	   p[1]=best_alpha;
	   p[2]=best_c;
	   p[3]=best_k;
	   p[4]=best_MS;
 	   swarm.setBestPosition(p);
	   

	   
	   max_position[0]=p[0]*particle_threshold+p[0];
	   max_position[1]=p[1]*particle_threshold+p[1];
       max_position[2]=p[2]*particle_threshold+p[2];
       max_position[3]=p[3]*particle_threshold+p[3];
       max_position[4]=p[4]*particle_threshold+p[4];
       
       min_position[0]=p[0]-p[0]*particle_threshold;
       min_position[1]=p[1]-p[1]*particle_threshold;
	   min_position[2]=p[2]-p[2]*particle_threshold;
	   min_position[3]=p[3]-p[3]*particle_threshold;
	   min_position[4]=p[4]-p[4]*particle_threshold;

	   // swarm.setMaxPosition(max_position);
	//   swarm.setMinPosition(min_position);

       old_bestfitness=ext_bestfitness;
       external_set=false;
	 }
	 
	/* j++;
	 if (j>MaxEvolve*100)
	  {
	   System.out.print("-----------------------PSO Class "+pso_num+"  deadlock!------------------------------------\r\n");		 
       break;
	  }
	 
		 /*   System.out.print("-----------------------PSO Class "+pso_num+"------------------------------------\r\n"); 
       System.out.println(swarm.toStringStats());
	  }
	 // {*/
     /*System.out.println(swarm.toStringStats());*/
		if (old_bestfitness>bestFitness)
		{
	 	 System.out.print("-----------------------PSO Class "+pso_num+"------------------------------------\r\n");
	     System.out.println(swarm.toStringStats());
	     
	 	 p=swarm.getBestPosition();
 	 	 best_a=p[0];
		 best_alpha=p[1];
		 best_c=p[2];
		 best_k=p[3];
		 best_MS=p[4];
		/* System.out.println("best_a="+Double.toString(best_a)+"\r\n");
		 System.out.println("best_al="+Double.toString(best_alpha)+"\r\n");
		 System.out.println("best_c="+Double.toString(best_c)+"\r\n");
		 System.out.println("best_k="+Double.toString(best_k)+"\r\n");
		 System.out.println("best_MS="+Double.toString(best_MS)+"\r\n");*/
	    	     	     
	     max_position[0]=p[0]*particle_threshold+p[0];
	     max_position[1]=p[1]*particle_threshold+p[1];
	     max_position[2]=p[2]*particle_threshold+p[2];
	     max_position[3]=p[3]*particle_threshold+p[3];
	     max_position[4]=p[4]*particle_threshold+p[4];
	   
	     min_position[0]=p[0]-p[0]*particle_threshold;
	     min_position[1]=p[1]-p[1]*particle_threshold;
	     min_position[2]=p[2]-p[2]*particle_threshold;
	     min_position[3]=p[3]-p[3]*particle_threshold;
	     min_position[4]=p[4]-p[4]*particle_threshold;

	   // swarm.setMaxPosition(max_position);
	  //  swarm.setMinPosition(min_position);
	    old_bestfitness=bestFitness;
	   }
	  // break;
	// }
	} 
	while (bestFitness>e);
	//System.out.print("best fitness="+Double.toString(bestFitness)+"\r\n");		
	
	/*System.out.print("-----------------------PSO Class "+pso_num+" OK------------------------------------\r\n"); 
    System.out.println(swarm.toStringStats());*/
	p=swarm.getBestPosition();
	best_a=p[0];
	best_alpha=p[1];
	best_c=p[2];
	best_k=p[3];
	best_MS=p[4];
    return bestFitness;
  }
  
  /*
   *  Лучший коэффициент А 
   */
  
  public double getBestA()
  {
	//best_parm_find(); //Находим лучшие коэффициенты  
	return best_a; //Лучшее значение для А
  }
  
  /*
   *  Лучший коэффициент ALPHA 
   */
  public double getBestALPHA()
  {
	//best_parm_find(); //Находим лучшие коэффициенты  
	return best_alpha; //Лучшее значение для АLPHA
  }
  
  /*
   *  Лучший коэффициент K 
   */
  public double getBestK()
  {
    //best_parm_find(); //Находим лучшие коэффициенты
	return best_k; //Лучшее значение для K

  }

  /*
   *  Лучший коэффициент C 
   */
  public double getBestC()
  {
	//best_parm_find(); //Находим лучшие коэффициенты
	return best_c; //Лучшее значение для C
  }

  /*
   *  Лучший коэффициент MS 
   */
  public double getBestMS()
  {
    return best_MS; //Лучшее значение для MS
  }
  
  /*
   *  Лучшая функция вписываемости 
   */
  public double getBestFitness()
  {
	return bestFitness;
  }
  
  /*
   *  Лучший коэффициент А 
   */
  
  public void setBestA(double A)
  {
	//best_parm_find(); //Находим лучшие коэффициенты  
	best_a=A; //Лучшее значение для А
	external_set=true;
  }
  
  /*
   *  Лучший коэффициент ALPHA 
   */
  public void setBestALPHA(double alpha)
  {
	//best_parm_find(); //Находим лучшие коэффициенты  
	best_alpha=alpha; //Лучшее значение для АLPHA
	external_set=true;
  }
  
  /*
   *  Лучший коэффициент K 
   */
  public void setBestK(double K)
  {
    //best_parm_find(); //Находим лучшие коэффициенты
	best_k=K; //Лучшее значение для K
	external_set=true;
  }

  /*
   *  Лучший коэффициент C 
   */
  public void setBestC(double C)
  {
	//best_parm_find(); //Находим лучшие коэффициенты
	best_c=C; //Лучшее значение для C
	external_set=true;
  }

  /*
   *  Лучший коэффициент MS 
   */
  public void setBestMS(double MS)
  {
    best_MS=MS; //Лучшее значение для MS
    external_set=true;
  }
  
  /*
   *  Лучшая функция вписываемости 
   */
  public void setBestFitness(double Fitness)
  {
	ext_bestfitness=Fitness; //Лучшее значение для MS
    external_set=true;
  }

@Override
public void run() {
	// TODO Auto-generated method stub
	//System.out.print("PSO class runned\r\n");
	try{
	 IdentParam();
	}
	catch (Exception ex)
	{
	 System.out.print("Exception:"+ex.getMessage());	 
	}
}
  
}
