/*
 * Модель Джона Чена
 */

public class JCModelClass {
    private double BS; //Индукция насыщения
    private double BR; //Остаточная индукция
    private double HC; //Коэрцитивная сила
    private double H_max; //Напряженность поля (максимум)
    private double t_max; //Время конечное
    private double N; //Множитель
    private double f; //Частота
    private double M0; //Магнитная постоянная
    private double dt; //Приращение времени
    private int step; //Количество шагов
    private java.util.Vector<Double> Time; //Архив времени
    private java.util.Vector<Double> HV; //Напряженность
    private java.util.Vector<Double> B; //Индукция
    private java.util.Vector<Double> Bbeg; //Индукция (начальная/основная кривая намагничивания)
    private java.util.Vector<Double> Hbeg; //Напряженность (начальная/основная кривая намагничивания)

    //Конструктор класса
    public JCModelClass(double LBS, double LBR, double LHC, double LH_max, double Lt_max, double LN, double Lf) {
        BS = (double) LBS; //Индукция насыщения
        BR = (double) LBR; //Остаточная индукция
        HC = (double) LHC; //Коэрцитивная сила
        H_max = (double) LH_max; //Напряженность поля (максимум)
        t_max = (double) Lt_max;
        f = Lf; //Частота
        N = (double) LN; //Множитель
        step = (int) (N); //Количество шагов
        Time = new java.util.Vector();
        ; //Архив времени
        HV = new java.util.Vector(); //Напряженность
        B = new java.util.Vector(); //Индукция
        Bbeg = new java.util.Vector(); //Индукция (начальная/основная кривая намагничивания)
        Hbeg = new java.util.Vector(); //Напряженность (начальная/основная кривая намагничивания)
        M0 = (4 * java.lang.Math.PI) / 10000000; //Магнитная постоянная
        dt = (double) 1 / N;
    }

    //Установка BS
    public void setBSAgain(double LBS) {
        BS = (double) LBS; //Индукция насыщения
    }

    //Установка BR
    public void setBRAgain(double LBR) {
        BR = (double) LBR; //Остаточная индукция
    }

    //Установка НС
    public void setHCAgain(double LHC) {
        HC = (double) LHC; //Коэрцитивная сила
    }

    //Установка H_max
    public void setHMAXAgain(double LH_max) {
        H_max = LH_max; //Напряженность поля (максимум)
    }

    /*
     * H(t)
     */
    public double H(double t) {
        double ret_val;
        ret_val = H_max * java.lang.Math.sin(2 * java.lang.Math.PI * f * t);
        return ret_val;
    }

    /*
     * Решение
     */
    public void solve() {
        int j;
        double t = 0.0;
        double Ht_old = 0;
        double BH_plus = 0.0;
        double BH_minus = 0.0;
        double Bd = 1;
        boolean initial_curve = true;


        //Очистка векторов
        Time.clear();
        HV.clear();
        B.clear();
        Bbeg.clear();
        Hbeg.clear();
        while (t <= t_max) {
            t = t + dt;
            Time.add(t);
            HV.add(H(t));

            if (initial_curve) {
                if (H(t) > Ht_old || H(t) == Ht_old) {
                    BH_plus = BS * (H(t) + HC);
                    BH_plus = BH_plus / (java.lang.Math.abs(H(t) + HC) + (HC) * ((BS / BR) - 1));
                    BH_plus = BH_plus + M0 * H(t);
                    BH_minus = BS;
                    BH_minus = BH_minus * (H(t) - HC);
                    BH_minus = BH_minus / (java.lang.Math.abs(H(t) - HC) + HC * ((BS / BR) - 1));
                    BH_minus = BH_minus + M0 * H(t);
                    B.add((BH_plus + BH_minus) / 2);
                    Bbeg.add((BH_plus + BH_minus) / 2);
                    Hbeg.add(H(t));
                } else
                    initial_curve = false;
            }

            if (!initial_curve) {
                if (H(t) > Ht_old || H(t) == Ht_old) {
                    BH_plus = BS * (H(t) + HC);
                    BH_plus = BH_plus / (java.lang.Math.abs(H(t) + HC) + (HC) * ((BS / BR) - 1));
                    BH_plus = BH_plus + M0 * H(t);
                    B.add(BH_plus);
                } else {
                    BH_minus = BS;
                    BH_minus = BH_minus * (H(t) - HC);
                    BH_minus = BH_minus / (java.lang.Math.abs(H(t) - HC) + HC * ((BS / BR) - 1));
                    BH_minus = BH_minus + M0 * H(t);
                    B.add(BH_minus);
                }
            }
            Ht_old = H(t);
        }
    }

    /*
     *  Результат H
     */
    public java.util.Vector<Double> getH() {
        return HV;
    }

    /*
     *  Результат B
     */
    public java.util.Vector<Double> getB() {
        return B;
    }

    /*
     *  Результат Time
     */
    public java.util.Vector<Double> getTime() {
        return Time;
    }

    /*
     *  Начальня кривая намагничивания (индукция)
     */
    public java.util.Vector<Double> getBbeg() {
        return Bbeg;
    }

    /*
     *  Начальня кривая намагничивания (индукция)
     */
    public java.util.Vector<Double> getHbeg() {
        return Hbeg;
    }
}
