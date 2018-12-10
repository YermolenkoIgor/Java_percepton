package java_perceptron;

class TMatrix {

    int N, M;
    double[][] arr;
    
    public TMatrix()
    {
        this.N = 2;
        this.M = 2;
        this.arr = new double[this.N][this.M];
        //System.out.println("default");
    }

    public TMatrix(int n, int m)
    {
        this.N = n;
        this.M = m;
        this.arr = new double[this.N][this.M];
        //System.out.println("constr-d with par-s");
    }

    public TMatrix(double [][] paramMatrix)
    {
        this.N = paramMatrix.length;
        this.M = paramMatrix[0].length;
        this.arr = paramMatrix;
    }

    void print_line(int num)
    {
        for(int j = 0; j < this.M; j++)
            System.out.print(this.arr[num][j] + " ");
    }
    
    void print_Matrix()
    {
        for(int i = 0; i < this.N; i++)
        {
            for(int j = 0; j < this.M; j++)
                System.out.print(this.arr[i][j] + " ");
            System.out.println();
        }
    }
    
    boolean checksizes (TMatrix obj1, TMatrix obj2)
    {
        return (( obj1.N != obj2.N) | (obj1.M != obj2.M) | 
                ( this.N != obj1.N) | (this.M != obj1.M) | 
                ( this.N != obj2.N) | (this.M != obj2.M) );
    }
    
    void equel (TMatrix obj)
    {
        if( this.N != obj.N | this.M != obj.M )
            System.out.println("Matrixes must have same size for M1 = M2");
        else
            this.arr = obj.arr.clone();
    }
    
    void column_of(TMatrix obj, int num)
    {
        if( this.N != obj.M | this.M != 1 )
            System.out.println("Error in column_of" );
        else
            for(int i=0; i < this.N; i++)
                this.arr[i][0] = obj.arr[num][i];
    }

void Add (TMatrix obj1, TMatrix obj2)
    {
        if ( this.checksizes(obj1, obj2) )
            System.out.println("Matrixes must have same size for +" );
        else
            for (int i=0; i < this.N; i++)
                for (int j=0; j < this.M; j++)
                    this.arr[i][j] = obj1.arr[i][j] + obj2.arr[i][j];
    }

void Sub (TMatrix obj1, TMatrix obj2)
    {
        if ( this.checksizes(obj1, obj2) )
            System.out.println("Matrixes must have same size for -" );
        else
            for (int i=0; i < this.N; i++)
                for (int j=0; j < this.M; j++)
                    this.arr[i][j] = obj1.arr[i][j] - obj2.arr[i][j];
    }

void Mult (TMatrix obj1, TMatrix obj2)
    {
        //Результатом умножения матриц A n×m и B m×k будет матрица C n×k
        if ( ( obj1.M != obj2.N) | ( this.N != obj1.N) | (this.M != obj2.M) )
            System.out.println("Matrixes must have same size for *" );
        else
        {
            int i, j, k;
            for( i = 0; i < this.N; i++)
                for( j = 0; j < this.M; j++)
                    this.arr[i][j] = 0;

            for( i = 0; i < obj1.N; i++)
                for( j = 0; j < obj2.M; j++)
                    for( k = 0; k < obj1.M; k++)
                        this.arr[i][j] += obj1.arr[i][k]*obj2.arr[k][j];
    }
}

//умножение с транспонированным первам множителем
void Mult1 (TMatrix obj1, TMatrix obj2)
{
    if ( ( obj1.N != obj2.N) | ( this.N != obj1.M) | (this.M != obj2.M) )
        System.out.println("Matrixes must have same size for * transpon 1" );
    else
    {
        int i, j, k;
        for( i = 0; i < this.N; i++)
            for( j = 0; j < this.M; j++)
                this.arr[i][j] = 0;
                
        for( i = 0; i < obj1.M; i++)
            for( j = 0; j < obj2.M; j++)
                for( k = 0; k < obj1.N; k++)
                    this.arr[i][j] += obj1.arr[k][i]*obj2.arr[k][j];
    }
}

void Mult2 (TMatrix obj1, TMatrix obj2)//без зануления this
{
    if ( ( obj1.M != obj2.M) | ( this.N != obj1.N) | (this.M != obj2.N) )
        System.out.println("Matrixes must have same size for * transpon 2" );
    else
        for( int i = 0; i < obj1.N; i++)
            for( int j = 0; j < obj2.N; j++)
                for( int k = 0; k < obj1.M; k++)
                    this.arr[i][j] += obj1.arr[i][k]*obj2.arr[j][k];
}

void Mult (TMatrix obj1, double obj2)
{
    if (this.N != obj1.N | this.M != obj1.M)
        System.out.println("Matrixes must have same size for M*d" );
    else
        for (int i=0; i < this.N; i++)
            for (int j=0; j < this.M; j++)
                this.arr[i][j] = obj1.arr[i][j] * obj2;
}

void Mult (double obj1, TMatrix obj2)
{
    if (this.N != obj2.N | this.M != obj2.M)
        System.out.println("Matrixes must have same size for d*M" );
    else
        for (int i=0; i < this.N; i++)
            for (int j=0; j < this.M; j++)
                this.arr[i][j] = obj1 * obj2.arr[i][j];
}

void Adamar (TMatrix obj1, TMatrix obj2)//Произведение Адамара (Шура)
{
    if ( this.checksizes(obj1, obj2) )
        System.out.println("Matrixes must have same size for *.a" );
    else
        for (int i=0; i < this.N; i++)
            for (int j=0; j < this.M; j++)
                this.arr[i][j] = obj1.arr[i][j] * obj2.arr[i][j];
}

void Subeq (TMatrix obj)
{
    if(this.N != obj.N | this.M != obj.M)
        System.out.println("Matrixes must have same size for -=" );
    else
        for (int i=0; i < this.N; i++)
            for (int j=0; j < this.M; j++)
                this.arr[i][j] -= obj.arr[i][j];
}

void Addeq (TMatrix obj)
{
    if(this.N != obj.N | this.M != obj.M)
        System.out.println("Matrixes must have same size for +=" );
    else
        for (int i=0; i < this.N; i++)
            for (int j=0; j < this.M; j++)
                this.arr[i][j] += obj.arr[i][j];
}

void Divd (TMatrix obj1, double obj2)
{
    if (this.N != obj1.N | this.M != obj1.M)
        System.out.println("Matrixes must have same size for M/d" );
    else
        for (int i=0; i < this.N; i++)
            for (int j=0; j < this.M; j++)
                this.arr[i][j] = obj1.arr[i][j] / obj2;
}

void Multeq (double obj)
{
    for (int i=0; i < this.N; i++)
        for (int j=0; j < this.M; j++)
            this.arr[i][j] *= obj;
}

void Divdeq (double obj)
{
    if(obj == 0)
        System.out.println("divided by zero :(" );
    else
        for (int i=0; i<this.N; i++)
            for (int j=0; j<this.M; j++)
                this.arr[i][j] /= obj;
}

void Transpon (TMatrix obj)
{
    if(this.N != obj.M | this.M != obj.N)
        System.out.println("Matrixes must have same size for transformation" );
    else
        for (int i=0; i < this.N; i++)
            for (int j=0; j < this.M; j++)
                this.arr[i][j] = obj.arr[j][i];
}
}