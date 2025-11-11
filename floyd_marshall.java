

public class floyd_marshall {
    
    private static final int INF = Integer.MAX_VALUE / 2;
    
    private static final String[] NODOS = {"LAS", "LAN", "BR", "EU", "KR"};
    private static final int N = NODOS.length;
    
    public static void main(String[] args) {
        int[][] grafo = {
            //LAS  LAN   BR   KR   EU
            {  0, INF,  40, INF, 185 },  // LAS
            {155,   0, INF, 130, INF },  // LAN
            { 45,  90,   0, INF, INF },  // BR
            {INF, 150, INF,   0, INF },  // KR
            {INF, INF, INF, 140,   0 }   // EU
        };
        
        System.out.println("== ALGORITMO DE FLOYD ==\n");
        System.out.println("MATRIZ INICIAL:");
        imprimirMatriz(grafo);
        
        int[][] resultado = floyd(grafo);
        
        System.out.println("\nMATRIZ FINAL:");
        imprimirMatriz(resultado);
    }
    
    // Algoritmo de Floyd
    public static int[][] floyd(int[][] grafo) {
        int[][] R = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                R[i][j] = grafo[i][j];
            }
        }
        
        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (i != j && existeArista(R, i, k) && existeArista(R, k, j)) {
                        int distanciaActual = R[i][j];
                        int distanciaPorK = R[i][k] + R[k][j];
                        if (existeArista(R, i, j)) {
                            if (distanciaPorK < distanciaActual) {
                                R[i][j] = distanciaPorK;
                            }
                        } else {
                            R[i][j] = distanciaPorK;
                        }
                    }
                }
            }
        }
        
        return R;
    }
    
    private static boolean existeArista(int[][] matriz, int i, int j) {
        return matriz[i][j] != INF;
    }
    
    private static void imprimirMatriz(int[][] matriz) {
        // Encabezado
        System.out.print("Desde\\Hacia |");
        for (int j = 0; j < N; j++) {
            System.out.printf(" %5s |", NODOS[j]);
        }
        System.out.println();
        System.out.print("------------|");
        for (int j = 0; j < N; j++) {
            System.out.print("-------|");
        }
        System.out.println();
        
        // Filas
        for (int i = 0; i < N; i++) {
            System.out.printf("%-11s |", NODOS[i]);
            for (int j = 0; j < N; j++) {
                if (matriz[i][j] == INF) {
                    System.out.print("   INF |");
                } else {
                    System.out.printf(" %5d |", matriz[i][j]);
                }
            }
            System.out.println();
        }
    }

}
