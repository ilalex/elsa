/*
 * Р В¤РЎС“Р Р…Р С”РЎвЂ Р С‘РЎРЏ fitness
 */
import net.sourceforge.jswarm_pso.FitnessFunction;
import java.util.*;

public class MyFitnessFunction extends FitnessFunction {
 private int N;
 private double t_max;
 private int f;
 private double H_max;
 
 java.util.Vector<Double> Bexp; //Р пїЅР Р…Р Т‘РЎС“Р С”РЎвЂ Р С‘РЎРЏ
 java.util.Vector<Double> Hexp; //Р пїЅР Р…Р Т‘РЎС“Р С”РЎвЂ Р С‘РЎРЏ

 public MyFitnessFunction(java.util.Vector<Double> LBexp, int LN, double Lt_max, int Lf, double LH_max)
 {
	Bexp=new java.util.Vector<Double>();
	Bexp=LBexp;
	N=LN;
	t_max=Lt_max;
	f=Lf;
	H_max=LH_max;
	this.setMaximize(false);
  }
 
@Override
public double evaluate(double[] position) {
	// TODO Auto-generated method stub
	double ret_val=1000000000.0;
	double summ;
	java.util.Vector<Double> B;
	java.util.Vector<Double> H;
	//if (position[0]<position[4] && position[3]<position[4])
	//{yzk
	B=new java.util.Vector<Double>();
	JAModelClass jaModel=new JAModelClass(N,t_max,f,H_max);
	jaModel.setA(position[0]);
	jaModel.setALPHA(position[1]);
	jaModel.setC(position[2]);
	jaModel.setK(position[3]);
	jaModel.setMS(position[4]);
	jaModel.solve();
	B=jaModel.getB();
	H=jaModel.getH();
		
 
	summ=0;
		
	 for (int i=1;i<B.size();i++)
	  summ+= java.lang.Math.pow(java.lang.Math.abs(Bexp.get(i)-(B.get(i))),2);
	   //summ+=java.lang.Math.pow((Bexp.get(i)-(B.get(i))/b_max),2);
 
	if (position[0]>0&&position[1]>0&&position[2]>0&&position[3]>0&&position[4]>0) 
     ret_val=0.5*summ;//((double)1/(Bexp.size()))*summ;
         
	  
 	return ret_val;
	 	 
}
 
private double getMax(java.util.Vector<Double> V){
	double max=0;
	for (int i=0;i<V.size();i++){
	 if (V.get(i)>max)
	  max=V.get(i);
	}
	return max;
}



}
