/*
 * Функция fitness для генетического алгоритма
 */

import org.jgap.Chromosome;
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.Gene;

public class MyGAFitnessFunction extends FitnessFunction{
	private JAModelClass jaModel;
	java.util.Vector<Double> B; //Индукция
	java.util.Vector<Double> Bexp; //Индукция
	
	public MyGAFitnessFunction(java.util.Vector<Double> LBexp, int LN, double Lt_max, int Lf, double LH_max)
	{
		B=new java.util.Vector<Double>();
		Bexp=new java.util.Vector<Double>();
		Bexp=LBexp;
		jaModel=new JAModelClass(LN,Lt_max,Lf,LH_max); //Инициализируем класс модели Джилса Аттертона
	}
	
	@Override
	protected double evaluate(IChromosome arg0) {
		
		// TODO Auto-generated method stub
		
		double ret_val;
		double summ;
		double b_max;
		Gene gene[]=arg0.getGenes();
		
		jaModel.setA(Double.parseDouble(gene[0].getAllele().toString()));
		jaModel.setALPHA(Double.parseDouble(gene[1].getAllele().toString()));
		jaModel.setC(Double.parseDouble(gene[2].getAllele().toString()));
		jaModel.setK(Double.parseDouble(gene[3].getAllele().toString()));
		jaModel.setMS(Double.parseDouble(gene[4].getAllele().toString()));
		jaModel.solve();
		B=jaModel.getB();
		b_max=0.0;
		for (int i=0; i<Bexp.size(); i++)
		 if (Bexp.get(i)>b_max)
			 b_max=Bexp.get(i);

		summ=java.lang.Math.pow(((Bexp.get(0)-B.get(0))/b_max),2);
	    for (int i=1;i<B.size();i++)
	      summ=summ+java.lang.Math.pow(((Bexp.get(i)-B.get(i))/b_max),2);
	    //if (Double.isNaN(summ))
	    // summ=2000000000*random.nextDouble();	
	     ret_val=((double)1/B.size())*summ;
	     	     
		/* System.out.print("bestFitness="+Double.toString(ret_val)+"\r\n");
		 System.out.print("resultGenes[0]="+gene[0].getAllele().toString()+"\r\n");
		 System.out.print("resultGenes[1]="+gene[1].getAllele().toString()+"\r\n");
		 System.out.print("resultGenes[2]="+gene[2].getAllele().toString()+"\r\n");
		 System.out.print("resultGenes[3]="+gene[3].getAllele().toString()+"\r\n");
		 System.out.print("resultGenes[4]="+gene[4].getAllele().toString()+"\r\n");*/
		 
		return 1/ret_val;
	}

}
