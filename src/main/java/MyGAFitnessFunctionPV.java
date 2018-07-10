/*
 * Функция fitness для генетического алгоритма (определение проектных переменных)
 */

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;


public class MyGAFitnessFunctionPV extends FitnessFunction {
    private java.util.Vector<Double> Bproj; //Индукция (переменные из проектов)
    private java.util.Vector<Double> Hproj; //Напряженность (переменные из проектов)
    private java.util.Vector<Double> Bmagn; //Индукция (Кривая намагничивания)
    private java.util.Vector<Double> Hmagn; //Напряженность (Кривая намагничивания)
    private double t_max; //Время конечное
    private double N; //Множитель
    private double f; //Частота
    private double HStep; //Шаг для построения кривой намагничивания

    public MyGAFitnessFunctionPV(java.util.Vector<Double> LBproj, java.util.Vector<Double> LHproj, double LHStep, double Lt_max, double LN, double Lf) {
        //Индукция проектная
        Bproj = new java.util.Vector();
        Bproj = LBproj;
        //Напряженность проектная
        Hproj = new java.util.Vector();
        Hproj = Hproj;
        t_max = Lt_max; //Максимальное время
        f = Lf; //Частота
        N = LN; //Множитель
        HStep = LHStep; //Шаг для построения кривой намагничивания

        //Индукция (Кривая намагничивания)
        Bmagn = new java.util.Vector();
        Bmagn.clear();
        Hmagn = new java.util.Vector(); //Напряженность (Кривая намагничивания)
        Hmagn.clear();
    }

    @Override
    protected double evaluate(IChromosome arg0) {
        double ret_val;
        double summ;
        double B_max;
        double H_max;
        double dHStep = 0.0;
        double BS = 0.0;
        double BR = 0.0;
        double HC = 0.0;
        double Hmax = 0.0;
        double Hmax_s = 0.0;
        double min_dev = 0.0;
        double temp;
        double hhh;

        Gene gene[] = arg0.getGenes();
        BS = Double.parseDouble(gene[0].getAllele().toString());
        BR = Double.parseDouble(gene[1].getAllele().toString());
        HC = Double.parseDouble(gene[2].getAllele().toString());
        Hmax = Double.parseDouble(gene[3].getAllele().toString());
        ret_val = 1000000;
        //if (BS/BR<1.2){
        // System.out.printf(Locale.UK, "BS=%.8f;BR=%.8f;HC=%.8f;Hmax=%.8f\r\n",BS,BR,HC,Hmax);
        //dHStep=Hmax/HStep;
        //Hmax_s+=dHStep;
        JCModelClass jcModel = new JCModelClass(BS, BR, HC, Hmax, t_max, N, f);
        jcModel.solve();
        //System.out.printf(Locale.UK, "Hmax_s=%.8f;Hmax=%.8f;dHStep=%.8f\r\n",Hmax_s,Hmax,dHStep);
		 /*while (Hmax_s<Hmax){
  	      jcModel.solve();
		  Bmagn.add(getMaxB(jcModel.getB()));
		  Hmagn.add(getMaxH(jcModel.getH()));
		  Hmax_s+=dHStep;
		  jcModel.setHMAXAgain(Hmax_s);
		  //System.out.printf(Locale.UK, "getMaxB=%.8f;getMaxH=%.8f;\r\n",getMaxB(jcModel.getB()),getMaxH(jcModel.getH()));
		 }*/

        Bmagn = jcModel.getBbeg();
        Hmagn = jcModel.getHbeg();
        // System.out.printf(Locale.UK, "BmagnSize=%.8f;HmagnSize=%.8f;\r\n",(double)Bmagn.size(),(double)Hmagn.size());

        B_max = getMax(Bproj);
        hhh = getTens(getMin(Bproj));
        //System.out.printf(Locale.UK, "hhh=%.8f;b_min=%.8f;\r\n",hhh,getMin(Bproj));
        summ = 0.0;
        for (int i = 0; i < Bproj.size(); i++) {
            min_dev = 10000;
            for (int j = 0; j < Bmagn.size(); j++) {
                temp = java.lang.Math.abs((Bproj.get(i) * hhh) - (Bmagn.get(j) * hhh));
                if (temp < min_dev) {
                    min_dev = temp;
                }
            }
            summ += java.lang.Math.pow(((min_dev) / B_max), 2);
        }

        hhh = getTens(getMin(Hproj));
        H_max = getMax(Hproj);
        for (int i = 0; i < Hproj.size(); i++) {
            min_dev = 10000;
            for (int j = 0; j < Hmagn.size(); j++) {
                temp = java.lang.Math.abs((Hproj.get(i) * hhh) - (Hmagn.get(j) * hhh));
                if (temp < min_dev) {
                    min_dev = temp;
                }
            }
            summ += java.lang.Math.pow(((min_dev) / H_max), 2);
        }

        ret_val = ((double) 1 / (Bproj.size() * 2)) * summ;
	     /*if (ret_val<(double)1.0)
	     {
	      System.out.printf(Locale.UK, "summ=%.8f; min_dev=%.8f\r\n",summ,min_dev);
	      System.out.printf(Locale.UK, "ret_val=%.8f\r\n",ret_val);
	     }*/
        //}
        return 1 / ret_val;
    }

    //Максимальное значение индукции в векторе
    private double getMax(java.util.Vector<Double> V) {
        double max = 0;
        for (int i = 0; i < V.size(); i++) {
            if (V.get(i) > max)
                max = V.get(i);
        }
        return max;
    }

    //Максимальное значение напряженности в векторе
    private double getMin(java.util.Vector<Double> V) {
        double min = 100000000;
        for (int i = 0; i < V.size(); i++) {
            if (V.get(i) < min)
                min = V.get(i);
        }
        return min;
    }

    //Взять десятки
    private double getTens(double num) {
        double tens = 1;
        while ((num * tens) < 1)
            tens *= 10;

        return tens;
    }
}
