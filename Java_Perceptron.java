package java_perceptron;

class Java_Perceptron {

    public static void main(String[] args) throws java.lang.Exception
    {
    double epsilon = 1e-3;
    
    //Neurotask nt1 = new Neurotask();
    //Neurotask nt1 = new Neurotask("Tendention.txt");
    //Neurotask nt1 = new Neurotask("Segmentation.txt");
    Neurotask nt1 = new Neurotask("Outpoot.txt");
    //nt1.get_task();
    
    int Players = 5;//Phantom layers
    int[] NIEL = { nt1.Ninp, 20, 40, 20, 10, 4, nt1.Nout };//neurons in every layer
    
    //Perceptons p1 = new Perceptons();
    Perceptons p1 = new Perceptons(Players+1, NIEL, 1e-4);
    //Perceptons p1 = new Perceptons("Tend_4_2.txt");
    
    //p1.get_weights();
    //p1.get_bias();
    
    p1.back_propagation(nt1, epsilon, 8000);
    //p1.neuro_exam(nt1);
    
    //p1.get_weights();
    //p1.get_bias();
    //p1.neuro_to_file("Arfm1.txt");
    //nt1.taskmaker("Arifmetica.txt");
    }
}
/*
Если захочется дикой оптимизации, то можно убрать чексайзис
из каждого метода для матриц и поставить его перед выполнением формул в циклах.
Еще можно эвалфун и эвалбокс переделать в дабл - будет меньше матриц
Для красоты надо раскидать бэкпропагэйшн по парочке методов
*/