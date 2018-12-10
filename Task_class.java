package java_perceptron;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.File;
import java.util.Locale;
import java.util.Scanner;

class Neurotask{

    int Nteach, Ntest;
    int Ninp, Nout;
    TMatrix Xtrain;
    TMatrix Ytrain;
    TMatrix Xtest;
    TMatrix Ytest;

    public Neurotask ()
{
    Nteach = 8;
    Ntest = 8;
    Ninp = 3;
    Nout = 1;

    double [][]mass = {
        {0, 0, 0},
        {0, 0, 1},
        {0, 1, 0},
        {0, 1, 1},
        {1, 0, 0},
        {1, 0, 1},
        {1, 1, 0},
        {1, 1, 1}
    };
    
    double [][]answ = {
        {0},{1},{0},{0},{1},{1},{0},{0} };
    
    Xtrain = new TMatrix(mass);
    Ytrain = new TMatrix(answ);
    Xtest  = new TMatrix(mass);
    Ytest  = new TMatrix(answ);

    System.out.println( "Chosen task by default" );
}
    
public Neurotask (String filename) throws FileNotFoundException
{
    int i, j;
    
    try{
        File thefile = new File(filename);
        Scanner scnr = new Scanner(thefile);

        this.Nteach = scnr.nextInt();
        this.Ntest = scnr.nextInt();
        this.Ninp = scnr.nextInt();
        this.Nout = scnr.nextInt();

        Xtrain = new TMatrix(Nteach, Ninp);
        Ytrain = new TMatrix(Nteach, Nout);
        Xtest  = new TMatrix(Ntest, Ninp);
        Ytest  = new TMatrix(Ntest, Nout);

        scnr.useLocale(Locale.US);//чтоб понимал точки как разделитель целых с дробными
        
        for(i=0; i<Nteach; i++)
        {
            for(j=0; j<Ninp; j++)
                Xtrain.arr[i][j] = scnr.nextDouble();
            for(j=0; j<Nout; j++)
                Ytrain.arr[i][j] = scnr.nextDouble();
        }

        for(i=0; i<Ntest; i++)
        {
            for(j=0; j<Ninp; j++)
                Xtest.arr[i][j] = scnr.nextDouble();
            for(j=0; j<Nout; j++)
                Ytest.arr[i][j] = scnr.nextDouble();
        }

            System.out.println( "Chosen task " + filename );
        }

        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

public void get_task(){
    
    System.out.println( "Quantity of learn data: " + Nteach );
    System.out.println( "Quantity of test  data: " + Ntest  );
    System.out.println( "\nLearn data:" );
    Xtrain.print_Matrix();
    System.out.println( "\nLearn answers:" );
    Ytrain.print_Matrix();
    System.out.println( "\nTest data:" );
    Xtest.print_Matrix();
    System.out.println( "\nTest answers:" );
    Ytest.print_Matrix();
    }

void taskmaker(String filename)
{
    try{
        int k, oprtr;
        double a, b, c = -666;
        File fos = new File( filename );
        PrintStream writer = new PrintStream(fos);

        writer.println(400);
        writer.println(16);
        writer.println(3);
        writer.println(1);
        
        for(k=0; k<1000; k++)
        {
            oprtr = (int)(Math.random()*100);
            a = 0.5*Math.random();
            b = 0.5*Math.random();
            oprtr %= 4;
            
            switch(oprtr)
            {
                case 0:
                    c = a + b;
                break;
                case 1:
                    c = a * b;
                break;
                case 2:
                    c = Math.max(a, b) - Math.min(a, b);
                break;
                case 3:
                    c = a + a;
                break;
                    
                default:
                System.out.println( "ALARMA!!" );    
            }
            
            writer.println(a);
            writer.println(b);
            writer.println(c);
            writer.println(oprtr*0.1);
        }
        
        System.out.println("Ariphmetic tasks saved to " + filename);
        
        writer.flush();//void flush() - финализирует выходное состояние, очищая все буферы вывода
    }
    catch(IOException ex){
        System.out.println(ex.getMessage());
    }
}
};

