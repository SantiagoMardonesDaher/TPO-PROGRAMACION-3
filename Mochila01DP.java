package progra3.TPO_PROGRAMACION_MARDONES;

public class Mochila01DP {

    static int mochilaConReconstruccion(int n, int W, int[] w, int[] v, int[] tomadosOut){
        int[][] dp = new int[n+1][W+1];

        for(int i=1; i<=n; i++){
            for(int c=0; c<=W; c++){
                if(w[i] > c){
                    dp[i][c] = dp[i-1][c];
                }else{
                    int sin_i = dp[i-1][c];
                    int con_i = dp[i-1][c - w[i]] + v[i];
                    dp[i][c] = (con_i > sin_i) ? con_i : sin_i;
                }
            }
        }


        int c = W;
        for(int i=n; i>=1; i--){
            if(dp[i][c] != dp[i-1][c]){       
                tomadosOut[i] = 1;
                c -= w[i];
            }else{
                tomadosOut[i] = 0;
            }
        }
        return dp[n][W];
    }

    static int mochilaValorSolo(int n, int W, int[] w, int[] v){
        int[] dp = new int[W+1];
        for(int i=1; i<=n; i++){
            for(int c=W; c>=w[i]; c--){
                int con_i = dp[c - w[i]] + v[i];
                if(con_i > dp[c]) dp[c] = con_i;
            }
        }
        return dp[W];
    }

    //pruebas 
    static void imprimirSeleccion(int[] tomados){
        System.out.print("Objetos tomados: ");
        for(int i=1;i<tomados.length;i++) if(tomados[i]==1) System.out.print(i+" ");
        System.out.println();
    }

    public static void main(String[] args){
        int n = 3, W = 10;
        int[] w = new int[]{0, 6, 3, 4};  // w[1]=6, w[2]=3, w[3]=4
        int[] v = new int[]{0, 30,14,16}; // v[1]=30, v[2]=14, v[3]=16

        int[] tomados = new int[n+1];
        int valor = mochilaConReconstruccion(n, W, w, v, tomados);
        System.out.println("Valor óptimo = " + valor);    
        imprimirSeleccion(tomados);                             

        // Prueba extra
        int n2=4, W2=7;
        int[] w2 = new int[]{0,2,3,4,5};
        int[] v2 = new int[]{0,6,8,7,10};
        int[] toma2 = new int[n2+1];
        int val2 = mochilaConReconstruccion(n2, W2, w2, v2, toma2);
        System.out.println("Valor óptimo = " + val2);
        imprimirSeleccion(toma2);

      
        int valSolo = mochilaValorSolo(n2, W2, w2, v2);
        System.out.println("Valor óptimo (solo valor) = " + valSolo);
    }
}

