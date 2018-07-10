/*
 * Главный класс приложения
 */

import org.jgap.InvalidConfigurationException;

import java.util.Locale;

public class ElsaMainClass {

    /**
     * @param args
     * @throws InvalidConfigurationException
     */

    public static void main(String[] args) throws InvalidConfigurationException {
        java.util.Vector<Double> M; //Намагниченность
        java.util.Vector<Double> Time; //Архив времени
        java.util.Vector<Double> H; //Напряженность
        java.util.Vector<Double> B; //Индукция
        java.util.Vector<Double> Bexp; //Индукция
        java.util.Vector<Double> Hexp; //Индукция
        java.util.Vector<Double> Bproj; //Индукция
        java.util.Vector<Double> Hproj; //Индукция
        java.util.Vector<Double> Bmagn; //Индукция
        java.util.Vector<Double> Hmagn; //Индукция
        double fitness;
        int i;

        B = new java.util.Vector<Double>();
        Bexp = new java.util.Vector<Double>();
        Hexp = new java.util.Vector<Double>();
        H = new java.util.Vector<Double>();
        M = new java.util.Vector<Double>();
        Bproj = new java.util.Vector<Double>();
        Hproj = new java.util.Vector<Double>();
        Bmagn = new java.util.Vector<Double>();
        Hmagn = new java.util.Vector<Double>();

        // TODO Auto-generated method stub
        JAModelClass jaModel = new JAModelClass(10000, 1.2, 1, 10000);
        jaModel.setA(1000);
        jaModel.setALPHA(0.001);
        jaModel.setC(0.1);
        jaModel.setK(1000);
        jaModel.setMS(1600000);
        jaModel.solve();
        B = jaModel.getB();
        H = jaModel.getH();


        double BS;
        double BR;
        double HC;
        double Hmax;
        BS = 0.450;
        BR = 0.170;
        HC = 15;
        Hmax = 250;
        JCModelClass jcModel = new JCModelClass(BS, BR, HC, Hmax, 1.2, 10000, 1);
        jcModel.solve();
        Bexp = jcModel.getB();
        Hexp = jcModel.getH();

        System.out.print("B:\r\n");
        for (i = 0; i < Bexp.size(); i++)
            System.out.print(Bexp.get(i).toString() + "\r\n");

        System.out.print("H:\r\n");
        for (i = 0; i < Hexp.size(); i++)
            System.out.print(Hexp.get(i).toString() + "\r\n");

        double BestA = 0.0;
        double BestALPHA = 0.0;
        double BestC = 0.0;
        double BestK = 0.0;
        double BestMS = 0.0;
        double BestFitness = 10000000.0;

        PSOClass psoClass1 = new PSOClass(Bexp, 10000, 1.2, 1, Hmax, 0.000001, 500, 1);
        //psoClass.IdentParam();
        Thread psoThread1 = new Thread(psoClass1);
        psoThread1.start();

        PSOClass psoClass2 = new PSOClass(Bexp, 10000, 1.2, 1, Hmax, 0.000001, 500, 2);
        //psoClass.IdentParam();
        Thread psoThread2 = new Thread(psoClass2);
        psoThread2.start();

        PSOClass psoClass3 = new PSOClass(Bexp, 10000, 1.2, 1, Hmax, 0.000001, 500, 3);
        //psoClass.IdentParam();
        Thread psoThread3 = new Thread(psoClass3);
        psoThread3.start();

        PSOClass psoClass4 = new PSOClass(Bexp, 10000, 1.2, 1, Hmax, 0.000001, 500, 4);
        //psoClass.IdentParam();
        Thread psoThread4 = new Thread(psoClass4);
        psoThread4.start();

        PSOClass psoClass5 = new PSOClass(Bexp, 10000, 1.2, 1, Hmax, 0.000001, 500, 5);
        //psoClass.IdentParam();
        Thread psoThread5 = new Thread(psoClass5);
        psoThread5.start();

        PSOClass psoClass6 = new PSOClass(Bexp, 10000, 1.2, 1, Hmax, 0.000001, 500, 6);
        //psoClass.IdentParam();
        Thread psoThread6 = new Thread(psoClass6);
        psoThread6.start();

        PSOClass psoClass7 = new PSOClass(Bexp, 10000, 1.2, 1, Hmax, 0.000001, 500, 7);
        //psoClass.IdentParam();
        Thread psoThread7 = new Thread(psoClass7);
        psoThread7.start();

        PSOClass psoClass8 = new PSOClass(Bexp, 10000, 1.2, 1, Hmax, 0.000001, 500, 8);
        //psoClass.IdentParam();
        Thread psoThread8 = new Thread(psoClass8);
        psoThread8.start();

        PSOClass psoClass9 = new PSOClass(Bexp, 10000, 1.2, 1, Hmax, 0.000001, 500, 9);
        //psoClass.IdentParam();
        Thread psoThread9 = new Thread(psoClass9);
        psoThread9.start();

        PSOClass psoClass10 = new PSOClass(Bexp, 10000, 1.2, 1, Hmax, 0.00001, 500, 10);
        //psoClass.IdentParam();
        Thread psoThread10 = new Thread(psoClass10);
        psoThread10.start();

        boolean cycle = true;
        boolean psoClass1Set = false;
        boolean psoClass2Set = false;
        boolean psoClass3Set = false;
        boolean psoClass4Set = false;
        boolean psoClass5Set = false;
        boolean psoClass6Set = false;
        boolean psoClass7Set = false;
        boolean psoClass8Set = false;
        boolean psoClass9Set = false;
        boolean psoClass10Set = false;

        while (cycle) {
            if (BestFitness > psoClass1.getBestFitness()) {
                BestA = psoClass1.getBestA();
                BestALPHA = psoClass1.getBestALPHA();
                BestC = psoClass1.getBestC();
                BestK = psoClass1.getBestK();
                BestMS = psoClass1.getBestMS();
                BestFitness = psoClass1.getBestFitness();
                psoClass1Set = true;
                psoClass2Set = true;
                psoClass3Set = true;
                psoClass4Set = true;
                psoClass5Set = true;
                psoClass6Set = true;
                psoClass7Set = true;
                psoClass8Set = true;
                psoClass9Set = true;
                psoClass10Set = true;
            } else {
                if (BestA > 0 && psoClass1Set) {
                    psoClass1.setBestA(BestA);
                    psoClass1.setBestALPHA(BestALPHA);
                    psoClass1.setBestC(BestC);
                    psoClass1.setBestK(BestK);
                    psoClass1.setBestMS(BestMS);
                    psoClass1.setBestFitness(BestFitness);
                    psoClass1Set = false;
                }
            }

            if (psoClass1.getIdentParamStatus() && cycle) {
                BestA = psoClass1.getBestA();
                BestALPHA = psoClass1.getBestALPHA();
                BestC = psoClass1.getBestC();
                BestK = psoClass1.getBestK();
                BestMS = psoClass1.getBestMS();

                psoThread1.interrupt();
                psoThread2.interrupt();
                psoThread3.interrupt();
                psoThread4.interrupt();
                psoThread5.interrupt();
                psoThread6.interrupt();
                psoThread7.interrupt();
                psoThread8.interrupt();
                psoThread9.interrupt();
                psoThread10.interrupt();
                cycle = false;
            }

            if (BestFitness > psoClass2.getBestFitness()) {
                BestA = psoClass2.getBestA();
                BestALPHA = psoClass2.getBestALPHA();
                BestC = psoClass2.getBestC();
                BestK = psoClass2.getBestK();
                BestMS = psoClass2.getBestMS();
                BestFitness = psoClass2.getBestFitness();
                psoClass1Set = true;
                psoClass2Set = true;
                psoClass3Set = true;
                psoClass4Set = true;
                psoClass5Set = true;
                psoClass6Set = true;
                psoClass7Set = true;
                psoClass8Set = true;
                psoClass9Set = true;
                psoClass10Set = true;
            } else {
                if (BestA > 0 && psoClass2Set) {
                    psoClass2.setBestA(BestA);
                    psoClass2.setBestALPHA(BestALPHA);
                    psoClass2.setBestC(BestC);
                    psoClass2.setBestK(BestK);
                    psoClass2.setBestMS(BestMS);
                    psoClass2.setBestFitness(BestFitness);
                    psoClass2Set = false;
                }
            }

            if (psoClass2.getIdentParamStatus() && cycle) {
                BestA = psoClass2.getBestA();
                BestALPHA = psoClass2.getBestALPHA();
                BestC = psoClass2.getBestC();
                BestK = psoClass2.getBestK();
                BestMS = psoClass2.getBestMS();

                psoThread1.interrupt();
                psoThread2.interrupt();
                psoThread3.interrupt();
                psoThread4.interrupt();
                psoThread5.interrupt();
                psoThread6.interrupt();
                psoThread7.interrupt();
                psoThread8.interrupt();
                psoThread9.interrupt();
                psoThread10.interrupt();
                cycle = false;
            }

            if (BestFitness > psoClass3.getBestFitness()) {
                BestA = psoClass3.getBestA();
                BestALPHA = psoClass3.getBestALPHA();
                BestC = psoClass3.getBestC();
                BestK = psoClass3.getBestK();
                BestMS = psoClass3.getBestMS();
                BestFitness = psoClass3.getBestFitness();
                psoClass1Set = true;
                psoClass2Set = true;
                psoClass3Set = true;
                psoClass4Set = true;
                psoClass5Set = true;
                psoClass6Set = true;
                psoClass7Set = true;
                psoClass8Set = true;
                psoClass9Set = true;
                psoClass10Set = true;
            } else {
                if (BestA > 0 && psoClass3Set) {
                    psoClass3.setBestA(BestA);
                    psoClass3.setBestALPHA(BestALPHA);
                    psoClass3.setBestC(BestC);
                    psoClass3.setBestK(BestK);
                    psoClass3.setBestMS(BestMS);
                    psoClass3.setBestFitness(BestFitness);
                    psoClass3Set = false;
                }
            }

            if (psoClass3.getIdentParamStatus() && cycle) {
                BestA = psoClass3.getBestA();
                BestALPHA = psoClass3.getBestALPHA();
                BestC = psoClass3.getBestC();
                BestK = psoClass3.getBestK();
                BestMS = psoClass3.getBestMS();

                psoThread1.interrupt();
                psoThread2.interrupt();
                psoThread3.interrupt();
                psoThread4.interrupt();
                psoThread5.interrupt();
                psoThread6.interrupt();
                psoThread7.interrupt();
                psoThread8.interrupt();
                psoThread9.interrupt();
                psoThread10.interrupt();
                cycle = false;
            }

            if (BestFitness > psoClass4.getBestFitness()) {
                BestA = psoClass4.getBestA();
                BestALPHA = psoClass4.getBestALPHA();
                BestC = psoClass4.getBestC();
                BestK = psoClass4.getBestK();
                BestMS = psoClass4.getBestMS();
                BestFitness = psoClass4.getBestFitness();
                psoClass1Set = true;
                psoClass2Set = true;
                psoClass3Set = true;
                psoClass4Set = true;
                psoClass5Set = true;
                psoClass6Set = true;
                psoClass7Set = true;
                psoClass8Set = true;
                psoClass9Set = true;
                psoClass10Set = true;
            } else {
                if (BestA > 0 && psoClass4Set) {
                    psoClass4.setBestA(BestA);
                    psoClass4.setBestALPHA(BestALPHA);
                    psoClass4.setBestC(BestC);
                    psoClass4.setBestK(BestK);
                    psoClass4.setBestMS(BestMS);
                    psoClass4.setBestFitness(BestFitness);
                    psoClass4Set = false;
                }
            }

            if (psoClass4.getIdentParamStatus() && cycle) {
                BestA = psoClass4.getBestA();
                BestALPHA = psoClass4.getBestALPHA();
                BestC = psoClass4.getBestC();
                BestK = psoClass4.getBestK();
                BestMS = psoClass4.getBestMS();

                psoThread1.interrupt();
                psoThread2.interrupt();
                psoThread3.interrupt();
                psoThread4.interrupt();
                psoThread5.interrupt();
                psoThread6.interrupt();
                psoThread7.interrupt();
                psoThread8.interrupt();
                psoThread9.interrupt();
                psoThread10.interrupt();
                cycle = false;
            }

            if (BestFitness > psoClass5.getBestFitness()) {
                BestA = psoClass5.getBestA();
                BestALPHA = psoClass5.getBestALPHA();
                BestC = psoClass5.getBestC();
                BestK = psoClass5.getBestK();
                BestMS = psoClass5.getBestMS();
                BestFitness = psoClass5.getBestFitness();
                psoClass1Set = true;
                psoClass2Set = true;
                psoClass3Set = true;
                psoClass4Set = true;
                psoClass5Set = true;
                psoClass6Set = true;
                psoClass7Set = true;
                psoClass8Set = true;
                psoClass9Set = true;
                psoClass10Set = true;
            } else {
                if (BestA > 0 && psoClass5Set) {
                    psoClass5.setBestA(BestA);
                    psoClass5.setBestALPHA(BestALPHA);
                    psoClass5.setBestC(BestC);
                    psoClass5.setBestK(BestK);
                    psoClass5.setBestMS(BestMS);
                    psoClass5.setBestFitness(BestFitness);
                    psoClass5Set = false;
                }
            }

            if (psoClass5.getIdentParamStatus() && cycle) {
                BestA = psoClass5.getBestA();
                BestALPHA = psoClass5.getBestALPHA();
                BestC = psoClass5.getBestC();
                BestK = psoClass5.getBestK();
                BestMS = psoClass5.getBestMS();

                psoThread1.interrupt();
                psoThread2.interrupt();
                psoThread3.interrupt();
                psoThread4.interrupt();
                psoThread5.interrupt();
                psoThread6.interrupt();
                psoThread7.interrupt();
                psoThread8.interrupt();
                psoThread9.interrupt();
                psoThread10.interrupt();
                cycle = false;
            }

            if (BestFitness > psoClass6.getBestFitness()) {
                BestA = psoClass6.getBestA();
                BestALPHA = psoClass6.getBestALPHA();
                BestC = psoClass6.getBestC();
                BestK = psoClass6.getBestK();
                BestMS = psoClass6.getBestMS();
                BestFitness = psoClass6.getBestFitness();
                psoClass1Set = true;
                psoClass2Set = true;
                psoClass3Set = true;
                psoClass4Set = true;
                psoClass5Set = true;
                psoClass6Set = true;
                psoClass7Set = true;
                psoClass8Set = true;
                psoClass9Set = true;
                psoClass10Set = true;
            } else {
                if (BestA > 0 && psoClass6Set) {
                    psoClass6.setBestA(BestA);
                    psoClass6.setBestALPHA(BestALPHA);
                    psoClass6.setBestC(BestC);
                    psoClass6.setBestK(BestK);
                    psoClass6.setBestMS(BestMS);
                    psoClass6.setBestFitness(BestFitness);
                    psoClass6Set = false;
                }
            }

            if (psoClass6.getIdentParamStatus() && cycle) {
                BestA = psoClass6.getBestA();
                BestALPHA = psoClass6.getBestALPHA();
                BestC = psoClass6.getBestC();
                BestK = psoClass6.getBestK();
                BestMS = psoClass6.getBestMS();

                psoThread1.interrupt();
                psoThread2.interrupt();
                psoThread3.interrupt();
                psoThread4.interrupt();
                psoThread5.interrupt();
                psoThread6.interrupt();
                psoThread7.interrupt();
                psoThread8.interrupt();
                psoThread9.interrupt();
                psoThread10.interrupt();
                cycle = false;
            }

            if (BestFitness > psoClass7.getBestFitness()) {
                BestA = psoClass7.getBestA();
                BestALPHA = psoClass7.getBestALPHA();
                BestC = psoClass7.getBestC();
                BestK = psoClass7.getBestK();
                BestMS = psoClass7.getBestMS();
                BestFitness = psoClass7.getBestFitness();
                psoClass1Set = true;
                psoClass2Set = true;
                psoClass3Set = true;
                psoClass4Set = true;
                psoClass5Set = true;
                psoClass6Set = true;
                psoClass7Set = true;
                psoClass8Set = true;
                psoClass9Set = true;
                psoClass10Set = true;
            } else {
                if (BestA > 0 && psoClass7Set) {
                    psoClass7.setBestA(BestA);
                    psoClass7.setBestALPHA(BestALPHA);
                    psoClass7.setBestC(BestC);
                    psoClass7.setBestK(BestK);
                    psoClass7.setBestMS(BestMS);
                    psoClass7.setBestFitness(BestFitness);
                    psoClass7Set = false;
                }
            }

            if (psoClass7.getIdentParamStatus() && cycle) {
                BestA = psoClass7.getBestA();
                BestALPHA = psoClass7.getBestALPHA();
                BestC = psoClass7.getBestC();
                BestK = psoClass7.getBestK();
                BestMS = psoClass7.getBestMS();

                psoThread1.interrupt();
                psoThread2.interrupt();
                psoThread3.interrupt();
                psoThread4.interrupt();
                psoThread5.interrupt();
                psoThread6.interrupt();
                psoThread7.interrupt();
                psoThread8.interrupt();
                psoThread9.interrupt();
                psoThread10.interrupt();
                cycle = false;
            }

            if (BestFitness > psoClass8.getBestFitness()) {
                BestA = psoClass8.getBestA();
                BestALPHA = psoClass8.getBestALPHA();
                BestC = psoClass8.getBestC();
                BestK = psoClass8.getBestK();
                BestMS = psoClass8.getBestMS();
                BestFitness = psoClass8.getBestFitness();
                psoClass1Set = true;
                psoClass2Set = true;
                psoClass3Set = true;
                psoClass4Set = true;
                psoClass5Set = true;
                psoClass6Set = true;
                psoClass7Set = true;
                psoClass8Set = true;
                psoClass9Set = true;
                psoClass10Set = true;
            } else {
                if (BestA > 0 && psoClass8Set) {
                    psoClass8.setBestA(BestA);
                    psoClass8.setBestALPHA(BestALPHA);
                    psoClass8.setBestC(BestC);
                    psoClass8.setBestK(BestK);
                    psoClass8.setBestMS(BestMS);
                    psoClass8.setBestFitness(BestFitness);
                    psoClass8Set = false;
                }
            }

            if (psoClass8.getIdentParamStatus() && cycle) {
                BestA = psoClass8.getBestA();
                BestALPHA = psoClass8.getBestALPHA();
                BestC = psoClass8.getBestC();
                BestK = psoClass8.getBestK();
                BestMS = psoClass8.getBestMS();

                psoThread1.interrupt();
                psoThread2.interrupt();
                psoThread3.interrupt();
                psoThread4.interrupt();
                psoThread5.interrupt();
                psoThread6.interrupt();
                psoThread7.interrupt();
                psoThread8.interrupt();
                psoThread9.interrupt();
                psoThread10.interrupt();
                cycle = false;
            }

            if (BestFitness > psoClass9.getBestFitness()) {
                BestA = psoClass9.getBestA();
                BestALPHA = psoClass9.getBestALPHA();
                BestC = psoClass9.getBestC();
                BestK = psoClass9.getBestK();
                BestMS = psoClass9.getBestMS();
                BestFitness = psoClass9.getBestFitness();
                psoClass1Set = true;
                psoClass2Set = true;
                psoClass3Set = true;
                psoClass4Set = true;
                psoClass5Set = true;
                psoClass6Set = true;
                psoClass7Set = true;
                psoClass8Set = true;
                psoClass9Set = true;
                psoClass10Set = true;
            } else {
                if (BestA > 0 && psoClass9Set) {
                    psoClass9.setBestA(BestA);
                    psoClass9.setBestALPHA(BestALPHA);
                    psoClass9.setBestC(BestC);
                    psoClass9.setBestK(BestK);
                    psoClass9.setBestMS(BestMS);
                    psoClass9.setBestFitness(BestFitness);
                    psoClass9Set = false;
                }
            }

            if (psoClass9.getIdentParamStatus() && cycle) {
                BestA = psoClass9.getBestA();
                BestALPHA = psoClass9.getBestALPHA();
                BestC = psoClass9.getBestC();
                BestK = psoClass9.getBestK();
                BestMS = psoClass9.getBestMS();

                psoThread1.interrupt();
                psoThread2.interrupt();
                psoThread3.interrupt();
                psoThread4.interrupt();
                psoThread5.interrupt();
                psoThread6.interrupt();
                psoThread7.interrupt();
                psoThread8.interrupt();
                psoThread9.interrupt();
                psoThread10.interrupt();
                cycle = false;
            }

            if (BestFitness > psoClass10.getBestFitness()) {
                BestA = psoClass10.getBestA();
                BestALPHA = psoClass10.getBestALPHA();
                BestC = psoClass10.getBestC();
                BestK = psoClass10.getBestK();
                BestMS = psoClass10.getBestMS();
                BestFitness = psoClass10.getBestFitness();
                psoClass1Set = true;
                psoClass2Set = true;
                psoClass3Set = true;
                psoClass4Set = true;
                psoClass5Set = true;
                psoClass6Set = true;
                psoClass7Set = true;
                psoClass8Set = true;
                psoClass9Set = true;
                psoClass10Set = true;
            } else {
                if (BestA > 0 && psoClass10Set) {
                    psoClass10.setBestA(BestA);
                    psoClass10.setBestALPHA(BestALPHA);
                    psoClass10.setBestC(BestC);
                    psoClass10.setBestK(BestK);
                    psoClass10.setBestMS(BestMS);
                    psoClass10.setBestFitness(BestFitness);
                    psoClass10Set = false;
                }
            }

            if (psoClass10.getIdentParamStatus() && cycle) {
                BestA = psoClass10.getBestA();
                BestALPHA = psoClass10.getBestALPHA();
                BestC = psoClass10.getBestC();
                BestK = psoClass10.getBestK();
                BestMS = psoClass10.getBestMS();

                psoThread1.interrupt();
                psoThread2.interrupt();
                psoThread3.interrupt();
                psoThread4.interrupt();
                psoThread5.interrupt();
                psoThread6.interrupt();
                psoThread7.interrupt();
                psoThread8.interrupt();
                psoThread9.interrupt();
                psoThread10.interrupt();
                cycle = false;
            }

        }

        System.out.print("-------------------PSO Ok---------------------------\r\n");
        System.out.printf(Locale.UK, "A=%.8f,Alpha=%.8f, C=%.8f,K=%.8f\r\n,MS=%.8f\r\n", BestA, BestALPHA, BestC, BestK, BestMS);
        JAModelClass jaModel1 = new JAModelClass(10000, 1.2, 1, Hmax);
        jaModel1.setA(BestA);
        jaModel1.setALPHA(BestALPHA);
        jaModel1.setC(BestC);
        jaModel1.setK(BestK);
        jaModel1.setMS(BestMS);
        jaModel1.solve();
        B = jaModel1.getB();
        H = jaModel1.getH();
        System.out.print("B:\r\n");
        for (i = 0; i < B.size(); i++)
            System.out.print(B.get(i).toString() + "\r\n");

        System.out.print("H:\r\n");
        for (i = 0; i < H.size(); i++)
            System.out.print(H.get(i).toString() + "\r\n");

    }


}

