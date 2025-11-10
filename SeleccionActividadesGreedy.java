package progra3.TPO_PROGRAMACION_MARDONES;

class SeleccionDeActividadesGreedy {

    static class Actividad {
        int inicio, fin;
        int id; 
        Actividad(int id, int inicio, int fin){
            this.id = id; this.inicio = inicio; this.fin = fin;
        }
    }


    static void ordenarPorFin(Actividad[] a){
        Actividad[] aux = new Actividad[a.length];
        ordenarPorFin(a, aux, 0, a.length - 1);
    }
    static void ordenarPorFin(Actividad[] a, Actividad[] aux, int izq, int der){
        if(izq >= der) return;
        int med = (izq + der) / 2;
        ordenarPorFin(a, aux, izq, med);
        ordenarPorFin(a, aux, med + 1, der);
        mezclarPorFin(a, aux, izq, med, der);
    }
    static void mezclarPorFin(Actividad[] a, Actividad[] aux, int izq, int med, int der){
        int i = izq, j = med + 1, k = izq;
        while(i <= med && j <= der){
      
            if(a[i].fin < a[j].fin || (a[i].fin == a[j].fin && a[i].inicio <= a[j].inicio)){
                aux[k++] = a[i++];
            } else {
                aux[k++] = a[j++];
            }
        }
        while(i <= med) aux[k++] = a[i++];
        while(j <= der) aux[k++] = a[j++];
        for(int t = izq; t <= der; t++) a[t] = aux[t];
    }

    //Selección Greedy 
    static Actividad[] seleccionMaxima(Actividad[] A){
        if(A.length == 0) return new Actividad[0];
        ordenarPorFin(A);

        Actividad[] sel = new Actividad[A.length]; 
        int cant = 0;
        int finUltimo = Integer.MIN_VALUE;

        for(int i = 0; i < A.length; i++){
            if(A[i].inicio >= finUltimo){
                sel[cant++] = A[i];
                finUltimo = A[i].fin;
            }
        }
        Actividad[] res = new Actividad[cant];
        for(int i=0;i<cant;i++) res[i] = sel[i];
        return res;
    }

    static Actividad[] caso(int[][] paresInicioFin){
        Actividad[] a = new Actividad[paresInicioFin.length];
        for(int i=0;i<paresInicioFin.length;i++){
            a[i] = new Actividad(i+1, paresInicioFin[i][0], paresInicioFin[i][1]);
        }
        return a;
    }

    //Pruebas y tiempos
    public static void main(String[] args){
        Actividad[][] casos = new Actividad[][]{
            caso(new int[][]{ {1,4}, {3,5}, {0,6}, {5,7}, {8,9}, {5,9} }),              // clásico
            caso(new int[][]{ {0,1}, {1,2}, {2,3}, {3,4}, {0,4} }),                      // encadenadas
            caso(new int[][]{ {0,6}, {1,4}, {3,5}, {5,7}, {5,9}, {8,9}, {2,13} }),       // mezcla
            caso(new int[][]{ {1,3}, {2,5}, {4,6}, {6,7}, {5,9}, {8,10}, {10,11} }),     // solapa parcial
            caso(new int[][]{ {1,2}, {1,2}, {1,2}, {2,3}, {2,3}, {3,4} })                // con empates
        };

        for(int c=0;c<casos.length;c++){
            Actividad[] A = casos[c];

            long t1 = System.nanoTime();
            Actividad[] S = seleccionMaxima(A);
            long t2 = System.nanoTime();

            System.out.println("Caso " + (c+1) + ": seleccionadas " + S.length + " actividades. tiempo(ns)=" + (t2 - t1));
            System.out.print("  IDs (inicio,fin) en orden de fin: ");
            for(int i=0;i<S.length;i++){
                System.out.print(S[i].id + "(" + S[i].inicio + "," + S[i].fin + ") ");
            }
            System.out.println("\n");
        }
    }
}
