package java_perceptron;
import java.io.PrintStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Locale;
import java.util.Scanner;

class Perceptons{

    int layers, Ns[];
    double alfa;
    TMatrix[] Weights;
    TMatrix[] Bias;
    TMatrix[] Junc;
    
Perceptons()
{
    int i, j, k;
    layers = 2;
    Weights = new TMatrix[layers];
    Bias = new TMatrix[layers];
    Junc = new TMatrix[layers+1];
    
    Ns = new int[layers+1];
    Ns[0] = 4;//neurons in the input layer
    Ns[1] = 3;
    Ns[2] = 1;//neurons in the output layer

    alfa = 0.1;
    
//alfa - коэффициент инерциальности для сглаживания резких скачков 
//при перемещении по поверхности целевой функции
    for(i=0; i<layers; i++)  
        Weights[i] = new TMatrix(Ns[i+1], Ns[i]);
    
    for(i=0; i<layers; i++)
        Bias[i] = new TMatrix(Ns[i+1],1);
    
    for(i=0; i<=layers; i++)
        Junc[i] = new TMatrix(Ns[i],1);
    
    for(k=0; k<layers; k++)
        for(i=0; i<Ns[k+1]; i++)
        {
            for(j=0; j<Ns[k]; j++)
                Weights[k].arr[i][j] = Math.random()/Ns[k]-0.5/Ns[k];
            Bias[k].arr[i][0] = 0.5;
        }
    
    System.out.println("Constructed by default");
}

Perceptons(int lrs, int Neurons[], double alpha)
{
    int i, j, k;
    layers = lrs;
    Weights = new TMatrix[layers];
    Bias = new TMatrix[layers];
    Junc = new TMatrix[layers+1];
    
    Ns = new int[layers+1];
    for(i=0; i <= layers; i++)
        Ns[i] = Neurons[i];
    
    alfa = alpha;
    
    for(i=0; i<layers; i++)
        Bias[i] = new TMatrix(Ns[i+1],1);
    
    for(i=0; i<layers; i++)
        Weights[i] = new TMatrix(Ns[i+1], Ns[i]);
    
    for(i=0; i<=layers; i++)
        Junc[i] = new TMatrix(Ns[i],1);
    
    for(k=0; k<layers; k++)
        for(i=0; i<Ns[k+1]; i++)
            for(j=0; j<Ns[k]; j++)
                Weights[k].arr[i][j] = Math.random()/Ns[k]-0.5/Ns[k];
    
    for(k=0; k<layers; k++)
        for(i=0; i<Ns[k+1]; i++)
            Bias[k].arr[i][0] = 0.5;
    
    System.out.println("Constructed with parameters");
}

Perceptons(String filename) throws FileNotFoundException
{
    int i, j, k;
    
    try{
        File thefile = new File(filename);
        Scanner scnr = new Scanner(thefile);
        scnr.useLocale(Locale.US);//чтоб понимал точки как разделитель целых с дробными
        
        layers = scnr.nextInt();
        alfa = scnr.nextDouble();
        
        Weights = new TMatrix[layers];
        Bias = new TMatrix[layers];
        Junc = new TMatrix[layers+1];
        
        Ns = new int[layers+1];
        
        for(k=0; k<=layers; k++)
            Ns[k] = scnr.nextInt();

        for(i=0; i<layers; i++)
            Weights[i] = new TMatrix(Ns[i+1], Ns[i]);
    
        for(i=0; i<layers; i++)
            Bias   [i] = new TMatrix(Ns[i+1],1);
        
        for(i=0; i<=layers; i++)
            Junc[i] = new TMatrix(Ns[i],1);
        
        for(k=0; k<layers; k++)
            for(i=0; i<Ns[k+1]; i++)
                for(j=0; j<Ns[k]; j++)
                    Weights[k].arr[i][j] = scnr.nextDouble();
    
        for(k=0; k<layers; k++)
            for(i=0; i<Ns[k+1]; i++)
                Bias[k].arr[i][0] = scnr.nextDouble();
        
        System.out.println( "Constructed from " + filename );
    }
    catch(IOException ex){
        System.out.println(ex.getMessage());
    }
}

void get_weights()
{
    System.out.println( "Weights of the every layer" );
    for (int i = 0; i < layers; i++){
        Weights[i].print_Matrix();
        System.out.println();
    }
}

void get_bias()
{
    System.out.println( "Bias of the every layer" );
    for (int i = 0; i < layers; i++){
        Bias[i].print_Matrix();
        System.out.println();
    }
}

void actifun(TMatrix z)
{
    for(int i=0; i<z.N; i++)
        z.arr[i][0] = 1./(1 + Math.exp(-z.arr[i][0]));//signumoid       
}

void distrectution()//direct distribution
{
    for(int i=0; i<layers; i++)
    {
        Junc[i+1]. Mult( Weights[i], Junc[i] );
        Junc[i+1]. Addeq( Bias[i] );
        actifun( Junc[i+1] );
    }
}

void back_propagation(Neurotask aTask, double eps, int steps)
{
    int i, j, k = 0;
    TMatrix evalbox = new TMatrix(aTask.Nout, 1);
    TMatrix evalfun = new TMatrix(aTask.Nout, 1);
    TMatrix zeros = new TMatrix(aTask.Nout, 1);
    TMatrix[] dfdz = new TMatrix[layers+1];
    TMatrix[] delt = new TMatrix[layers];
    TMatrix[] dW = new TMatrix[layers];
    TMatrix[] dB = new TMatrix[layers];
    
    for(i=0; i<layers; i++)
        dW[i] = new TMatrix(Ns[i+1], Ns[i]);
    
    for(i=0; i<layers; i++)
        dB[i] = new TMatrix(Ns[i+1],1);
    
    for(i=0; i<layers; i++)
        delt[i] = new TMatrix(Ns[layers-i],1);
    
    for(i=0; i<=layers; i++)
        dfdz[i] = new TMatrix(Ns[i],1);//память как на узлы
    
    alfa /= aTask.Nteach;
    
    System.out.println( "Start learning..." );
    
    while(k < steps)
    {
        for(i=0; i<aTask.Nteach; i++)
        {
            Junc[0].column_of( aTask.Xtrain, i );//Junc[0][0] = X[i];
            this.distrectution();
            
            delt[0].column_of( aTask.Ytrain, i );
            delt[0].Sub( Junc[layers], delt[0] );
            dfdz[layers].Adamar( Junc[layers], Junc[layers] );
            dfdz[layers].Sub( Junc[layers], dfdz[layers] );
            delt[0].Adamar( delt[0], dfdz[layers] );
            
            for(j=1; j<layers; j++)
            {
                delt[j].Mult1( Weights[layers-j], delt[j-1] );
                dfdz[layers-j].Adamar( Junc[layers-j], Junc[layers-j] );
                dfdz[layers-j].Sub( Junc[layers-j], dfdz[layers-j] );
                delt[j].Adamar( delt[j], dfdz[layers-j] );
            }
                
            for(j=0; j<layers; j++)
            {
                dW[j].Mult2( delt[layers-j-1], Junc[j] );
                dB[j].Addeq( delt[layers-j-1] );
                
                Weights[j].Divdeq( alfa );
                Weights[j].Subeq ( dW[j] );
                Weights[j].Multeq( alfa );
                
                Bias[j].Divdeq( alfa );
                Bias[j].Subeq ( dB[j] );
                Bias[j].Multeq( alfa );
            }
            
            evalbox.column_of( aTask.Ytrain, i );
            evalbox.Subeq ( Junc[layers] );
            evalbox.Adamar( evalbox, evalbox );
            evalfun.Addeq ( evalbox );
        }
        k++;
        evalfun.Multeq( 0.5/aTask.Nteach );
        
        if(k%100 == 0)
        {
            System.out.print( k + " ");
            evalfun.print_Matrix();
        }
        
        
        if( evalfun.arr[0][0] < eps )
            break;
        
        evalfun.equel ( zeros );
    }
    alfa *= aTask.Nteach;
    System.out.println( "Teached!" );
}

void neuro_exam(Neurotask aTask)
{
    for(int i=0; i < aTask.Ntest; i++)
    {
        Junc[0].column_of( aTask.Xtest, i );
        this.distrectution();
        
        Junc[0].print_Matrix();
        System.out.println(  "ANN think:" ); 
        Junc[layers].print_Matrix();
        System.out.print(  "ans from file: " ); 
        aTask.Ytest.print_line(i);
        System.out.println("\n");
    }
    System.out.println( "ANN examed" );
}

void neuro_to_file(String filename)
{
    try{
        int i, j, k;
        File fos = new File( filename );
        PrintStream writer = new PrintStream(fos);

        writer.println(layers);
        writer.println(alfa);

        for(k=0; k<=layers; k++)
            writer.println( Ns[k] );

        for(k=0; k<layers; k++)
            for(i=0; i<Ns[k+1]; i++)
                for(j=0; j<Ns[k]; j++)
                    writer.println( Weights[k].arr[i][j] );

        for(k=0; k<layers; k++)
            for(i=0; i<Ns[k+1]; i++)
                writer.println( Bias[k].arr[i][0] );
        
        System.out.println("Perceptron configuration saved to " + filename);
        
        writer.flush();//void flush() - финализирует выходное состояние, очищая все буферы вывода
    }
    catch(IOException ex){
        System.out.println(ex.getMessage());
    }
}


}