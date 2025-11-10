package progra3.TPO_PROGRAMACION_MARDONES;

class ParMasCercano2D {

    // --------- Tipos básicos ---------
    static class Punto {
        double x, y;
        Punto(double x, double y){ this.x = x; this.y = y; }
    }
    static class Par {
        Punto a, b;
        double d;
        Par(Punto a, Punto b, double d){ this.a = a; this.b = b; this.d = d; }
    }

    // --------- Distancia ---------
    static double distancia(Punto p, Punto q){
        double dx = p.x - q.x, dy = p.y - q.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    // --------- Solución ingenua ---------
    static Par solucionIngenua(Punto[] P){
        int n = P.length;
        Par mejor = new Par(P[0], P[1], distancia(P[0], P[1]));
        for(int i=0;i<n-1;i++){
            for(int j=i+1;j<n;j++){
                double d = distancia(P[i], P[j]);
                if(d < mejor.d) mejor = new Par(P[i], P[j], d);
            }
        }
        return mejor;
    }

    // --------- MergeSort por X ---------
    static void ordenarMergeX(Punto[] a){
        Punto[] aux = new Punto[a.length];
        ordenarMergeX(a, aux, 0, a.length-1);
    }
    static void ordenarMergeX(Punto[] a, Punto[] aux, int izq, int der){
        if(izq >= der) return;
        int med = (izq + der) / 2;
        ordenarMergeX(a, aux, izq, med);
        ordenarMergeX(a, aux, med+1, der);
        mezclarX(a, aux, izq, med, der);
    }
    static void mezclarX(Punto[] a, Punto[] aux, int izq, int med, int der){
        int i=izq, j=med+1, k=izq;
        while(i<=med && j<=der){
            if(a[i].x <= a[j].x) aux[k++] = a[i++];
            else aux[k++] = a[j++];
        }
        while(i<=med) aux[k++] = a[i++];
        while(j<=der) aux[k++] = a[j++];
        for(int t=izq;t<=der;t++) a[t] = aux[t];
    }

    // --------- MergeSort por Y ---------
    static void ordenarMergeY(Punto[] a){
        Punto[] aux = new Punto[a.length];
        ordenarMergeY(a, aux, 0, a.length-1);
    }
    static void ordenarMergeY(Punto[] a, Punto[] aux, int izq, int der){
        if(izq >= der) return;
        int med = (izq + der) / 2;
        ordenarMergeY(a, aux, izq, med);
        ordenarMergeY(a, aux, med+1, der);
        mezclarY(a, aux, izq, med, der);
    }
    static void mezclarY(Punto[] a, Punto[] aux, int izq, int med, int der){
        int i=izq, j=med+1, k=izq;
        while(i<=med && j<=der){
            if(a[i].y <= a[j].y) aux[k++] = a[i++];
            else aux[k++] = a[j++];
        }
        while(i<=med) aux[k++] = a[i++];
        while(j<=der) aux[k++] = a[j++];
        for(int t=izq;t<=der;t++) a[t] = aux[t];
    }

    // --------- Núcleo Divide y Vencerás ---------
    static Par masCercanoRec(Punto[] Px, Punto[] Py){
        int n = Px.length;
        if(n <= 3) return solucionIngenua(Px);

        int mitad = n/2;
        double xMed = Px[mitad].x;

        // dividir Px
        Punto[] PxIzq = new Punto[mitad];
        Punto[] PxDer = new Punto[n - mitad];
        for(int i=0;i<mitad;i++) PxIzq[i] = Px[i];
        for(int i=mitad;i<n;i++) PxDer[i - mitad] = Px[i];

        // ---- construir PyIzq / PyDer manteniendo orden por y (ARREGLADO) ----
        Punto[] PyIzq = new Punto[mitad];
        Punto[] PyDer = new Punto[n - mitad];

        int ii = 0, id = 0;
        Punto[] iguales = new Punto[n]; // puntos con x == xMed
        int eq = 0;

        // separar estrictamente <, > y guardar los ==
        for (int i = 0; i < n; i++) {
            if (Py[i].x < xMed)       PyIzq[ii++] = Py[i];
            else if (Py[i].x > xMed)  PyDer[id++] = Py[i];
            else                      iguales[eq++] = Py[i];
        }
        // completar Izq con los "iguales" hasta llegar a mitad; resto a Der
        int k = 0;
        while (ii < mitad && k < eq) PyIzq[ii++] = iguales[k++];
        while (k < eq)               PyDer[id++] = iguales[k++];
        // --------------------------------------------------------------------

        Par izq  = masCercanoRec(PxIzq, PyIzq);
        Par der  = masCercanoRec(PxDer, PyDer);
        Par mejor = izq.d <= der.d ? izq : der;
        double delta = mejor.d;

        // franja central (ordenada por y porque viene de Py)
        Punto[] franja = new Punto[n];
        int s = 0;
        for(int i=0;i<n;i++){
            if(Math.abs(Py[i].x - xMed) <= delta) franja[s++] = Py[i];
        }

        for(int i=0;i<s;i++){
            for(int j=i+1; j<s && (franja[j].y - franja[i].y) < delta; j++){
                double d = distancia(franja[i], franja[j]);
                if(d < mejor.d){
                    mejor = new Par(franja[i], franja[j], d);
                    delta = d;
                }
            }
        }
        return mejor;
    }

    static Par parMasCercanoDivideYVenceras(Punto[] P){
        int n = P.length;
        Punto[] Px = new Punto[n];
        Punto[] Py = new Punto[n];
        for(int i=0;i<n;i++){ Px[i] = P[i]; Py[i] = P[i]; }
        ordenarMergeX(Px);
        ordenarMergeY(Py);
        return masCercanoRec(Px, Py);
    }

    // --------- Utilidad: crear puntos ----------
    static Punto[] puntos(double[][] a){
        Punto[] P = new Punto[a.length];
        for(int i=0;i<a.length;i++) P[i] = new Punto(a[i][0], a[i][1]);
        return P;
    }

    // --------- Pruebas y tiempos ----------
    public static void main(String[] args){
        Punto[][] casos = new Punto[][]{
            puntos(new double[][]{{0,0},{3,4},{7,1},{1,1}}),
            puntos(new double[][]{{-1,-1},{-2,-2},{-3,-3},{2,2},{2.1,2.1}}),
            puntos(new double[][]{{10,10},{11,11},{12,12},{13,13},{14,14}}),
            puntos(new double[][]{{0,0},{100,100},{0,1},{100,101},{50,50}}),
            puntos(new double[][]{{5,9},{1,3},{2,8},{4,4},{9,9},{6,1}}),
            puntos(new double[][]{{0,0},{0,0.5},{0,0.7},{1,1},{2,2},{3,3}})
        };

        for(int c=0;c<casos.length;c++){
            Punto[] P = casos[c];

            long t1 = System.nanoTime();
            Par a = solucionIngenua(P);
            long t2 = System.nanoTime();
            Par b = parMasCercanoDivideYVenceras(P);
            long t3 = System.nanoTime();

            System.out.println("Caso " + (c+1));
            System.out.println(" Ingenua: distancia=" + a.d + " tiempo(ns)=" + (t2-t1));
            System.out.println(" Divide y Vencerás: distancia=" + b.d + " tiempo(ns)=" + (t3-t2));
            System.out.println();
        }
    }
}
